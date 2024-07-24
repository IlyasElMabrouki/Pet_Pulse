import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { ActivatedRoute } from '@angular/router';
import { AdoptService } from '../services/adopt.service';

@Component({
  selector: 'app-apply-adoption',
  templateUrl: './apply-adoption.component.html',
  styleUrl: './apply-adoption.component.css'
})
export class ApplyAdoptionComponent {

  userId: number | undefined;
  reportId: number | undefined;
  reason: string | undefined;
  petExperience: string | undefined;
  numberOfPets: number | undefined;
  applyFrom: FormGroup | undefined;

  // Constructor
  constructor(private fb: FormBuilder, private authService: AuthService, private route: ActivatedRoute, private adoptService: AdoptService) {
  }

  ngOnInit() {
    // const id = this.route.snapshot.paramMap.get('id');
    // this.reportId = id ? +id : undefined;

    this.applyFrom = this.fb.group({
      userId: "",
      reportId: "",
      reason: "",
      petExperience: "",
      numberOfPets: "",
    });
  }

  onSubmit(): void
  {
    if(!this.applyFrom?.valid) {
    {  
      console.log('Invalid form');
    }

    console.log(this.applyFrom?.value);
    this.userId = this.authService.id;

    console.log("test");

    const id = this.route.snapshot.paramMap.get('id');
    this.reportId = id ? +id : undefined;

    this.applyFrom = this.fb.group({
      userId: this.userId,
      reportId: this.reportId,
      reason: this.reason,
      petExperience: this.petExperience,
      numberOfPets: this.numberOfPets,
    });

    console.log(this.applyFrom.value);

    this.adoptService.applyAdoption(this.applyFrom.value).subscribe({
      next: (response: any) => {
        console.log('Application submitted successfully:', response);
      },
      error: (error: any) => {
        console.error('Error submitting application:', error);
      }
    });
    
  }

  }
}
