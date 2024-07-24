import {Component, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import { LostReportsService } from "../services/lost-reports.service";
import { Report } from "../models/report.model";
import {jwtDecode} from "jwt-decode";
import {PetManagementService} from "../services/pet-management.service";
import {Pet} from "../models/pet.model";
import {NgForm} from "@angular/forms";


@Component({
  selector: 'app-add-report',
  templateUrl: './add-report.component.html',
  styleUrls: ['./add-report.component.css']
})
export class AddReportComponent {
  pets:any[] | any;
  reportData!: Report;
  id: string | undefined
  showSuccessMessage: boolean = false; // Flag to show success message

  @ViewChild('reportForm') reportForm!: NgForm; // ViewChild to access the form

  constructor(
    private router: Router,
    private lostReportsService: LostReportsService,

  ) {

  }
  ngOnInit(): void {
    const jwtToken = localStorage.getItem('jwt-token');
    if (jwtToken) {
      const decodedToken: any = jwtDecode(jwtToken);
      console.log(decodedToken);
      this.id = decodedToken.id;
      console.log(this.id)
      this.getPetsByUserId(this.id)
    }
    this.getPetsByUserId(this.id)

  }
  getPetsByUserId(userId: string | undefined): void {
    if (userId !== undefined) {
      this.lostReportsService.getPetsByUserId(userId).subscribe(
        (response: any) => {
          if (response && response.content) {
            this.pets = response.content;
            console.log(this.pets);
          } else {
            console.error('No pet data found in the response content.');
          }
        },
        (error: any) => {
          console.error('Error fetching pets:', error);
        }
      );
    } else {
      console.error('User ID is undefined. Cannot fetch pets.');
    }
  }

  onSubmit(): void {
    if (this.reportForm.valid) {
      const formData = this.reportForm.value;
      formData['user_id'] = 7; // Add user_id to the form data
      console.log('Form Data:', formData);

      this.lostReportsService.addReport(formData).subscribe(
         (response: any) => {
           if (response.status === 201) {
             this.showSuccessMessage = true;
             setTimeout(() => {
               this.showSuccessMessage = false;
               this.router.navigate(['/my-reports']);
             }, 300);
           }
         },
         (error: any) => {
           console.error('Error:', error);
         }
       );
    } else {
      console.error('Form is invalid');
    }
  }
}
