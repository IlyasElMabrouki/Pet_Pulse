import { Component, OnInit, Input, ElementRef } from '@angular/core';
import {
  faBackwardStep,
  faForwardStep,
} from '@fortawesome/free-solid-svg-icons';
import { PetManagementService } from '../services/pet-management.service';
import { AuthService } from '../services/auth.service';
import { Report } from '../models/report.model';
import { AdoptService } from '../services/adopt.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-adopt',
  templateUrl: './adopt.component.html',
  styleUrls: ['./adopt.component.css'],
})
export class AdoptComponent implements OnInit {
  icons = { faForwardStep, faBackwardStep };
  BreedOptions!: [{ label: string; value: number }];
  // Pets: Pet[] = [];
  adoptionPosts:  Report[] = [];
  page: number = 1;
  petAgeEnd: number = 40;
  petAgeStart: number = 1;
  breed!: any;
  specie: string= 'DOG';
  health!: any;
  size: number = 6;
  city!: string;

  constructor(
    private PetsService: PetManagementService,
    private authService: AuthService,
    private adoptService: AdoptService,
    public router: Router
  ) {}

  ngOnInit() {
    this.getAdoptionReports();
  }


  onBreedChange() {
    console.log(this.breed);
    this.getAdoptionReports(
      // this.page,
      // this.size,
      // this.city
    );
    }

    onCityChange() {
      this.getAdoptionReports(
        // this.page,
        // this.size,
        // this.city
      );
      }
      

  selectSpecie = () => {
    console.log(this.specie);
    this.getAdoptionReports(
      // this.page,
      // this.size,
      // this.city
    );
  };

  moveForword = () => {
    this.page++;
    this.getAdoptionReports(
      // this.page,
      // this.size,
      // this.city
    );
  };

  moveBackword = () => {
    if (this.page - 1 > 0) this.page--;
    this.getAdoptionReports(
      // this.page,
      // this.size,
      // this.city
      // this.min,
      // this.max,
      // this.specie,
      // this.breed,
      // this.health
    );
  };

  getAdoptionReports = () => {
      this.adoptService.getAdoptionReports(this.page, this.size, this.city,this.breed,this.specie,this.petAgeStart,this.petAgeEnd).subscribe(
        {
          next: (response) => {
            this.adoptionPosts = response.content.map((report: any) => ({
              ...report,
              pet: {
                ...report.pet,
                images: report.pet.images.map((image: any) => image.url)
              }
            }));
          },
          error: (error) => {
            console.log(error);
          },
        }
      );
  };

  petAgeStartAgeChange = (event: any) => {
    this.petAgeStart = JSON.parse(event.target.value);
    console.log(this.petAgeStart);
    this.getAdoptionReports(
      // this.page,
      // this.size,
      // this.city
    );
  };

  petAgeEndAgeChange = (event: any) => {
    this.petAgeEnd = JSON.parse(event.target.value);
    this.getAdoptionReports(
      // this.page,
      // this.size,
      // this.city
    );
  };
}
