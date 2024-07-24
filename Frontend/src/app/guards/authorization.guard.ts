
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, GuardResult, MaybeAsync, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class authorizationGuard  {
 
  constructor(private authService: AuthService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {

    route: ActivatedRouteSnapshot;
    state: RouterStateSnapshot;
    
    if(this.authService.roles?.includes('ADMIN')){ 
      return true;
    }
    else{
      console.log(this.authService.roles);
      this.router.navigateByUrl('/notAuthorized');
      return false;
    }

  }
}