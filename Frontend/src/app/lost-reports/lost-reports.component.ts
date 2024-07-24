import { Component, OnInit } from '@angular/core';
import {LostReportsService} from "../services/lost-reports.service";
import {PetManagementService} from "../services/pet-management.service";
import {Pet} from "../models/pet.model";


@Component({
  selector: 'app-lost-reports',
  templateUrl: './lost-reports.component.html',
  styleUrls: ['./lost-reports.component.css']
})
export class LostReportsComponent implements OnInit {
  reports: any[] = [];
  filteredReports: any[] = [];
  searchTerm: string = '';

  private s3BaseUrl = 'https://petpulse.s3.amazonaws.com/';

  constructor(private lostReportsService: LostReportsService, private petmanagementservice: PetManagementService) {
  }

  ngOnInit(): void {
    this.getLostReports();

  }

  getImageUrl(image: string): string {
    return `${this.s3BaseUrl}${image}`;
  }

  getLostReports(): void {
    this.lostReportsService.getLostReports().subscribe(
      (reports: any[]) => {
        this.reports = reports;
        this.fetchPetDataForReports();
        this.fetchReporterNameForReports();
      },
      (error: any) => {
        console.error('Error fetching lost reports:', error);
      }
    );
  }

  fetchPetDataForReports(): void {
    this.reports.forEach(report => {
      this.petmanagementservice.getPetById(report.pet_id).subscribe(
        (petData: Pet) => {
          report.petData = petData;
        },
        (error: any) => {
          console.error(`Error fetching pet data for report with ID ${report.id}:`, error);
        }
      );
    });
  }

  fetchReporterNameForReports(): void {
    this.reports.forEach(report => {
      this.lostReportsService.getUserById(report.user_id).subscribe(
        (user: any) => {
          report.reporterName = user.username;
        },
        (error: any) => {
          console.error(`Error fetching reporter name for report with ID ${report.id}:`, error);
        }
      );
    });
  }

  onSearch(): void {
    console.log(this.searchTerm);
    if (this.searchTerm.trim() === '') {
      this.getLostReports();

    } else {
      this.filteredReports = this.reports.filter(report =>
        report.city && report.city.toLowerCase().includes(this.searchTerm.toLowerCase())
      );

      this.reports = this.filteredReports
    }
  }

}
