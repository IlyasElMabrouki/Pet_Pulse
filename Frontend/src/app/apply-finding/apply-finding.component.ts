import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import {ActivatedRoute, Router} from '@angular/router';
import { AdoptService } from '../services/adopt.service';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-apply-finding',
  templateUrl: './apply-finding.component.html',
  styleUrls: ['./apply-finding.component.css']
})
export class ApplyFindingComponent {
  userId: number | undefined;
  reportId: number | undefined;
  reason: string | undefined;
  contact_info: string | undefined;
  proof_image: File | undefined;
  message: string | undefined;
  applyFrom: FormGroup | undefined;
  sighting_location: string | undefined;
  loading: boolean = false;


  constructor( private http: HttpClient,private fb: FormBuilder, private authService: AuthService, private route: ActivatedRoute, private adoptService: AdoptService, private router: Router) {}

  ngOnInit() {
    this.applyFrom = this.fb.group({
      userId: '',
      reportId: '',
      reason: '',
      contact_info: '',
      message: '',
      sighting_location: ''
    });
  }

  onFileSelected(event: any) {
    this.proof_image = event.target.files[0];
  }

  onSubmit(): void {
    if (!this.applyFrom?.valid) {
      console.log('Invalid form');
      return;
    }

    this.userId = this.authService.id;
    const id = this.route.snapshot.paramMap.get('id');
    this.reportId = id ? +id : undefined;

    // Update the properties with the form values
    this.message = this.applyFrom.get('message')?.value;
    this.contact_info = this.applyFrom.get('contact_info')?.value;
    this.sighting_location = this.applyFrom.get('sighting_location')?.value;

    const formData = new FormData();
    formData.append('userId', this.userId?.toString() ?? '');
    formData.append('reportId', this.reportId?.toString() ?? '');
    formData.append('contactInfo', this.contact_info ?? '');
    formData.append('message', this.message ?? '');
    formData.append('sightingLocation', this.sighting_location ?? '');
    formData.append('image', this.proof_image as Blob);

    console.log('FormData:');
    formData.forEach((value: Blob | string, key: string) => {
      console.log(`${key}: ${value}`);
    });
    this.loading = true; // Set loading flag to true

    this.http.post('http://localhost:8084/applications/find', formData).subscribe({
      next: (response: any) => {
        console.log('Application submitted successfully:', response);
        this.router.navigate(['/lost-reports', this.reportId], { queryParams: { success: 'Application submitted successfully' } });
        this.loading = false; // Set loading flag to false after successful submission
        },
      error: (error: any) => {
        console.error('Error submitting application:', error);
      }
    });
  }

}
