import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-show-applications',
  templateUrl: './show-applications.component.html',
  styleUrls: ['./show-applications.component.css']
})
export class ShowApplicationsComponent implements OnInit {
  id: number | undefined;
  applicationList: any[] = []; // Declare and initialize applicationList as an empty array

  constructor(private route: ActivatedRoute, private http: HttpClient) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.fetchApplications();
  }

  fetchApplications(): void {
    this.http.get<any[]>(`http://localhost:8084/applications/find/${this.id}`).subscribe(
      (response: any[]) => {
        this.applicationList = response;
        console.log('Applications:', this.applicationList);
      },
      (error: any) => {
        console.error('Error fetching applications:', error);
        // Handle error as needed
      }
    );
  }
}
