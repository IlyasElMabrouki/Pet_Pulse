import { Component, Input } from '@angular/core';
import { Report } from '../../models/report.model';
import { faHeart, faComments } from '@fortawesome/free-solid-svg-icons';
import { PetManagementService } from '../../services/pet-management.service';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css'],
  styles: [
  ],
})
export class CardComponent {
  @Input() Report!: Report;
  imageUrl!: string;

  constructor(public petService: PetManagementService) {}

  ngOnInit() {
    
    
    if (!this.Report.pet.images[0]) {
      this.imageUrl = 'https://images.unsplash.com/photo-1444212477490-ca407925329e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8OXx8ZG9nc3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60';
    }
    else {
      this.imageUrl = this.petService.getImageUrl(this.Report.pet.images[0]);
    }
  }

  icons = { faHeart, faComments };
  likeColor = 'red';
}
