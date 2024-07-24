
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, GuardResult, MaybeAsync, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationGuard {

  constructor(private authService: AuthService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {

    route: ActivatedRouteSnapshot;
    state: RouterStateSnapshot;

    if(this.authService.isAuthenticated){
      return true;
    }
    else{
      this.router.navigateByUrl('/login');
      return false;
    }

  }
}
