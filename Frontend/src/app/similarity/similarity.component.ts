import { Component } from '@angular/core';
import { HttpClient, HttpEventType } from '@angular/common/http';
import {Pet} from "../models/pet.model";
import {Router} from "@angular/router";
interface ApiResponse {
  similar_images_info: { image_name: string }[];
}


@Component({
  selector: 'app-similarity',
  templateUrl: './similarity.component.html',
  styleUrls: ['./similarity.component.css']
})
export class SimilarityComponent {
  selectedFile: File | null = null;
  imagePreview: string | ArrayBuffer | null = null;
  isLoading = false;
  uploadProgress = 0;
  showResponse = false;
  imagesUrl: string[] = [];
  apiResponse: ApiResponse | undefined;
  reports: any[] = [];
  uploadSuccess = false;
  showFailedMessage = false;

  constructor(private http: HttpClient,private router: Router) {}

  onFileSelected(event: Event): void {
   this.reports=[]
    this.uploadSuccess=false
    this.showResponse=false
    this.showFailedMessage=false
    this.imagePreview=null
    const fileInput = event.target as HTMLInputElement;
    if (fileInput.files && fileInput.files.length > 0) {
      this.selectedFile = fileInput.files[0];
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result;
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }

  onUpload(): void {
    this.reports=[]
    this.uploadSuccess=false
    this.showResponse=false
    this.showFailedMessage=false
    if (!this.selectedFile) {
      return;
    }

    this.isLoading = true;
    this.uploadProgress = 0;
    const formData = new FormData();
    formData.append('image', this.selectedFile);

    console.log('FormData content:', formData.get('image'));
    this.http.post('http://127.0.0.1:5030/find_similar_images', formData, {
      reportProgress: true,
      observe: 'events'
    }).subscribe(
      event => {
        if (event.type === HttpEventType.UploadProgress) {
          if (event.total) {
            this.uploadProgress = Math.round((100 * event.loaded) / event.total);
            console.log(`Upload Progress: ${this.uploadProgress}%`);
            this.showFailedMessage=false;
            this.uploadSuccess = true;

          }
        } else if (event.type === HttpEventType.Response) {

          this.apiResponse = event.body as ApiResponse;

          if (this.apiResponse.similar_images_info) {
            this.uploadSuccess=false
            this.imagesUrl = this.apiResponse.similar_images_info.map((item: { image_name: string }) => item.image_name);

            this.imagesUrl.forEach(imageUrl => {


              this.http.get(`http://localhost:8084/reports/image/${imageUrl}`).subscribe(
                (response:  any )=> {
                  response.image=imageUrl;
                  this.reports.push(response);
                },
                error => {
                  console.error('Error fetching image:', error);
                }
              );
            });

              setTimeout(() => {
                if (this.reports.length === 0) {
                  this.uploadSuccess=false
                  this.showFailedMessage=true
                } else {
                  this.showFailedMessage=false
                }
              }, 900); // Add a delay of 1000 milliseconds


            this.showResponse = true;

          } else {
            this.showFailedMessage = true;
            console.error('No similar_images_info array in the response:', event.body);
          }

          this.isLoading = false;
          this.uploadProgress = 0;
        }
      },
      error => {
        console.error('Upload failed', error);
        this.isLoading = false;
        this.uploadProgress = 0;
      }
    );

  }
  navigateToReport(reportId: number) {
    this.router.navigate(['/lost-reports', reportId]);
  }
}
