import {Component, OnInit} from '@angular/core';
import {Pet} from "../models/pet.model";
import {ActivatedRoute, Router} from "@angular/router";
import {PetManagementService} from "../services/pet-management.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-pet-details',
  templateUrl: './pet-details.component.html',
  styleUrl: './pet-details.component.css'
})
export class PetDetailsComponent implements OnInit{
  pet!: Pet;
  petFormGroup! : FormGroup;
  ownerId! : number;
  private s3BaseUrl = 'https://petpulse.s3.amazonaws.com/';

  constructor(private route: ActivatedRoute, private fb : FormBuilder, private petService: PetManagementService, private router: Router, private authService: AuthService) {
    
  }

  ngOnInit(): void {
    this.ownerId = this.authService.id;
    this.petFormGroup = this.fb.group({
      ownerId: ['', Validators.required],
      name: ['', Validators.required],
      breed: ['', Validators.required],
      age: ['', [Validators.required, Validators.min(0)]]
    });
    const petId = this.route.snapshot.paramMap.get('id');
    if (petId) {
      this.petService.getPetById(Number(petId)).subscribe(pet => {
        this.pet = pet;
        this.petFormGroup.setValue({
          ownerId: this.ownerId,
          name: pet.name,
          breed: pet.breed,
          age: pet.age,
        });
      });
    }
  }

  getImageUrl(image: string): string {
    return `${this.s3BaseUrl}${image}`;
  }

  deletePet() {
    if (this.pet.id) {
      this.petService.deletePet(this.pet.id).subscribe(() => {
        alert('Pet deleted successfully');
        this.router.navigate(['/pets']);
      });
    }
  }

  updatePet(): void {
    if (this.petFormGroup.valid) {
      const formData = new FormData();
      formData.append('name', this.petFormGroup.get('name')!.value);
      formData.append('breed', this.petFormGroup.get('breed')!.value);
      formData.append('specie', 'DOG');
      formData.append('age', this.petFormGroup.get('age')!.value);
      formData.append('ownerId', this.petFormGroup.get('ownerId')!.value);

      this.petService.updatePet(formData, this.pet.id).subscribe(response => {
        console.log('Pet updated successfully:', response);
        this.router.navigate(['/pets']);
      }, error => {
        console.error('Error creating pet:', error);
      });
    }
  }

}
