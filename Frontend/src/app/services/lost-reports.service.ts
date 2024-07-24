import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import { Observable } from "rxjs";
import { environment } from "../../environments/environment";
import { PetManagementService } from "./pet-management.service";
import {NgForm} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class LostReportsService {



  constructor(private http: HttpClient, private petService: PetManagementService) { }

  baseUrl = environment.baseUrl;

  getLostReports(): Observable<any[]> {

    return this.http.get<any[]>(`${this.baseUrl}/reports`);
  }

  getLostReportById(reportId: string | undefined): Observable<any>{
    return this.http.get<any[]>(`${this.baseUrl}/reports/${reportId}`);
  }

  getUserById(userId: number | undefined): Observable<any>{
    return this.http.get<any[]>(`${this.baseUrl}/users/${userId}`);
  }
  getReportsByUserId(userId:number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/reports/user/${userId}`);
  }

  getPetsByUserId(userId: string): Observable<any[]> {
    return this.http.get<any>(`${this.baseUrl}/pets/user/${userId}?&page=0&size=30`)
  }

  updateReport(report: NgForm) {
    return this.http.put(`${this.baseUrl}/reports`, report, {observe: 'response' });
  }
  addReport(report: NgForm) {
    return this.http.post(`${this.baseUrl}/reports`, report, {observe: 'response' });
  }

  deleteReport(reportId: string){
    return this.http.delete(`${this.baseUrl}/reports/${reportId}`, {observe: 'response' });
  }

}
