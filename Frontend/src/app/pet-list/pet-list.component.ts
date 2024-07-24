import {Component, OnInit} from '@angular/core';
import {Pet} from "../models/pet.model";
import {PetManagementService} from "../services/pet-management.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-pet-list',
  templateUrl: './pet-list.component.html',
  styleUrl: './pet-list.component.css'
})
export class PetListComponent implements OnInit {

  userId! : number;
  pets: Pet[] = [];
  currentPage: number = 1;
  itemsPerPage: number = 4;
  totalPages: number = 1;
  searchFormGroup! : FormGroup;

  constructor(private petService: PetManagementService, private fb: FormBuilder, private authService: AuthService) {
    this.searchFormGroup = this.fb.group({
      search: ''
    });
  }

  ngOnInit(): void {
    this.userId = this.authService.id;
    this.getPets();
    this.searchFormGroup.get('search')?.valueChanges.subscribe(value => {
      this.onSearch(value);
    });
  }

  getPets(): void {
    this.petService.getPetsByUserId(this.userId, '', this.currentPage - 1, this.itemsPerPage).subscribe(response => {
      this.pets = response.content;
      this.totalPages = response.totalPages;
    });
  }

  onPageChange(newPage: number): void {
    this.currentPage = newPage;
    this.getPets();
  }

  onSearch(searchTerm: string): void {
    console.log('Search term:', searchTerm);
    this.petService.getPetsByUserId(this.userId, searchTerm, this.currentPage - 1, this.itemsPerPage).subscribe(response => {
      this.pets = response.content;
      this.totalPages = response.totalPages;
    });
  }

}
