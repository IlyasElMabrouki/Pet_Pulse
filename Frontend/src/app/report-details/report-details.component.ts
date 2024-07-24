import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { LostReportsService } from "../services/lost-reports.service";
import { Report } from "../models/report.model";
import { Pet } from "../models/pet.model";
import { PetManagementService } from "../services/pet-management.service";

@Component({
  selector: 'app-report-details',
  templateUrl: './report-details.component.html',
  styleUrls: ['./report-details.component.css']
})
export class ReportDetailsComponent implements OnInit {
  mainImageUrl: String | undefined ;
  reportId: string | undefined; // Define a property to store the report ID
  reportData: Report | undefined;
  petData: Pet | undefined;
  successMessage: string  |null| undefined;

  private s3BaseUrl = 'https://petpulse.s3.amazonaws.com/';

  constructor(
    private route: ActivatedRoute,
    private lostReportsService: LostReportsService,
    private petService: PetManagementService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.successMessage = params['success'] || null;
    });

    this.route.params.subscribe(params => {
      this.reportId = params['id'];
      this.getReportById(this.reportId);
    });
    if (this.successMessage) {
      setTimeout(() => {
        this.successMessage = null; // Hide the success message after 5 seconds
      }, 1000); // 5 seconds delay
    }
  }

  getImageUrl(image: string | undefined): string {
    return `${this.s3BaseUrl}${image}`;
  }
  getReportById(reportId: string | undefined): void {
    this.lostReportsService.getLostReportById(reportId).subscribe(
      (report: Report) => {
        this.reportData = report;
        // Call fetchPetDataForReports once reportData is available
        this.fetchPetDataForReports(report.pet_id);
      },
      (error: any) => {
        console.error('Error fetching lost report:', error);
      }
    );
  }

  fetchPetDataForReports(petId: number | undefined): void {
    if (petId) {
      this.petService.getPetById(petId).subscribe(
        (petData: Pet) => {
          this.petData = petData;
          this.setMainImage(this.petData?.images[0])
          this.fetchReporterNameForReport()
        },
        (error: any) => {
          console.error(`Error fetching pet data for report with ID ${this.reportId}:`, error);
        }
      );
    }
  }
  setMainImage(url: string | undefined) {
    this.mainImageUrl = url;
  }
  fetchReporterNameForReport(): void {
    this.lostReportsService.getUserById(this.reportData?.user_id).subscribe(
      (user: any) => {
        // @ts-ignore
        this.reportData.reporterName = user.username;
      },
      (error: any) => {
        // @ts-ignore
        console.error(`Error fetching reporter name for report with ID ${this.reportData.id}:`, error);
      }
    );
  }

  protected readonly Router = Router;
}
