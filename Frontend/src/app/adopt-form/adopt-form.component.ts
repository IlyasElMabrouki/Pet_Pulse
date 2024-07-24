import { Component } from '@angular/core';
import { PetManagementService } from '../services/pet-management.service';
import { Pet } from '../models/pet.model';
import { AuthService } from '../services/auth.service';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AdoptService } from '../services/adopt.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-adopt-form',
  templateUrl: './adopt-form.component.html',
  styleUrl: './adopt-form.component.css'
})
export class AdoptFormComponent {

  // gets pets by user id and store them
  pets:Pet[] = [];
  ownerId!: number;
  adoptForm! : FormGroup;


  constructor(private petsService: PetManagementService, private authService: AuthService, private fb : FormBuilder, private adoptService: AdoptService, private router: Router) { }

  ngOnInit() {
    this.ownerId = this.authService.id;

    this.adoptForm = this.fb.group({
      title: '',
      description: '',
      city: '',
      address: '',
      type: '',
      petId: '',
      notes: '',
      userId: this.ownerId
    });
    
    this.petsService.getPetsByUserId(this.authService.id, '', 0, 100)
      .subscribe({
        next: (response) => {
          this.pets = response.content;
          console.log(this.pets);
        },
        error: (error) => {
          console.error(error);
        }
      });
    }

    onSubmit() {
      if (this.adoptForm.valid) {
        console.log(this.adoptForm.value);
        this.adoptService.postAdoptionReport(this.adoptForm.value).subscribe({
          next: (response) => {
            console.log('Adoption report created successfully:', response);
            // redirect to /adoptions
            this.router.navigateByUrl('/adopt');

          },
          error: (error) => {
            console.error('Error creating adoption report:', error);
          }
        });
      }
    }
  


  }
