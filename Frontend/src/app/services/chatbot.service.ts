import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {catchError, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ChatbotService {

  apiUrl = "http://localhost:8000/predict";

  constructor(private http: HttpClient) { }

  sendPrompt(query: string): Observable<any> {
    const requestData = { query: query };

    return this.http.post<any>(this.apiUrl, requestData);
  }
}
