import { Component } from '@angular/core';
import { AuthService } from './services/auth.service';
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'loging-registration';

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit() {
    this.authService.loadJwtFromLocalStorage();
  }

  isLoginPage(): boolean {
    return this.router.url === '/login';
  }

  isRegisterPage(): boolean {
    return this.router.url === '/register';
  }
}
