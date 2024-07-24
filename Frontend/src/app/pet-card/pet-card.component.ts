import {Component, Input} from '@angular/core';
import {Pet} from "../models/pet.model";
import { PetManagementService } from '../services/pet-management.service';

@Component({
  selector: 'app-pet-card',
  templateUrl: './pet-card.component.html',
  styleUrl: './pet-card.component.css'
})
export class PetCardComponent {
  @Input() pet!: Pet;

  constructor(public petService: PetManagementService) {}

  ngOnInit() {
    console.log("from pet card component", this.pet);
  }
}
