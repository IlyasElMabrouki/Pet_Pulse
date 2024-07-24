import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';

interface AuthResponse {
  'token': string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private router: Router) { }

  isAuthenticated : boolean = false;
  id : any;
  username : any;
  roles : any;
  accessToken : string = '';
  baseUrl = environment.baseUrl;

  login(value: any):Observable<any> {
    let user = {
      email: value.email,
      password: value.password
    }
    return this.http.post<any>(this.baseUrl + '/auth/login', user);
  }

  register(value: any):Observable<any> {
    let user = {
      email: value.email,
      password: value.password,
      firstName: value.firstName,
      lastName: value.lastName
    }

    return this.http.post<any>(this.baseUrl + '/auth/register', user);
  }

  loadProfile(data: AuthResponse) {
    this.isAuthenticated = true;
    this.accessToken = data['token'];

    let decodedJwt:any = jwtDecode(this.accessToken);

    this.username = decodedJwt.sub;
    this.roles = decodedJwt.role;
    this.id = decodedJwt.id;

    console.log('Id:', this.id);


    // set the access token in local storage
    localStorage.setItem('jwt-token', this.accessToken);
  }

  loadJwtFromLocalStorage() {
    let jwt = localStorage.getItem('jwt-token');
    if (jwt){
      this.loadProfile({ 'token': jwt });
      // this.router.navigateByUrl('/home');
    }
  }

  logout() {
    this.isAuthenticated = false;
    this.accessToken = '';
    this.username = '';
    this.roles = '';
    localStorage.removeItem('jwt-token');
    this.router.navigateByUrl('/login');
  }



}
