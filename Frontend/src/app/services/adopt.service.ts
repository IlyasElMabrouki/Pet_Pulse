import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdoptService {

  constructor(private http: HttpClient) { }

  baseUrl = environment.baseUrl;


  getAdoptionReports(page: number, size: number, city: string, breed: string, specie: string,petAgeStart: number, petAgeEnd: number) {
    page = page - 1;
    return this.http.get<any>(this.baseUrl + '/reports/adoptFilter', {
      params: {
        page,
        size,
        type: 'ADOPTION',
        city,
        petSpecie: specie,
        petBreed: breed,
        petAgeStart,
        petAgeEnd
      }
    });
  }


  // post adoption report
  postAdoptionReport(data: any) {
    
    data = {
      title: data.title,
      description: data.description,
      city: data.city,
      address: data.address,
      type: 'ADOPTION',
      additionalNotes: data.notes,
      pet_id: data.petId,
      user_id: data.userId
    };

    console.log(data);

    return this.http.post<any>(this.baseUrl + '/reports', data);

  }


  applyAdoption(value: any): Observable<any> {
    
    const adoptionApplication = {
      userId: value.userId,
      reportId: value.reportId,
      reason: value.reason,
      petExperience: value.petExperience,
      numberOfPets: value.numberOfPets
    };

    return this.http.post<any>(this.baseUrl + '/applications/adopt', adoptionApplication);
  }


}
