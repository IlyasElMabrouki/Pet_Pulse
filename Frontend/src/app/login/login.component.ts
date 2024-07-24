import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  error = '';
  jwtToken = '';
  loginForm: FormGroup | undefined;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {}
    

  ngOnInit(): void {
    if (this.authService.isAuthenticated) {
      this.router.navigateByUrl('/home');
    }
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  handleLogin() {
    this.authService.login(this.loginForm?.value)
      .subscribe({
        next: (data) => {
          this.loginForm?.reset();
          
          this.authService.loadProfile(data);
          this.router.navigateByUrl('/home');
          this.jwtToken = data;
          console.log(data);
        },
        error: error => {
          this.error = error;
          console.log(error);
        }
      });
  }

  toRegister() {
    this.router.navigateByUrl('/register');
}
    

}
