import { Injectable } from '@angular/core';

import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';

import { Observable, finalize } from 'rxjs';

import { AuthService } from './auth.service';




@Injectable()

export class AppHttpInterceptor implements HttpInterceptor {




  constructor(private authService: AuthService) { }




  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<any>> {
   

    let req = request.clone({

      headers: request.headers.set('Authorization', 'Bearer ' + this.authService.accessToken)

    });

    console.log('Request sent to server', req);

    return next.handle(req).pipe(

      finalize(() => {

        console.log('Request completed');

      })

    );

  }

}