import { Injectable } from '@angular/core';
import {
  HttpEvent, HttpInterceptor, HttpHandler, HttpRequest
} from '@angular/common/http';

import { Observable } from 'rxjs';

/** Pass untouched request through to the next request handler. */
@Injectable()
export class Http_interceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler):
    Observable<HttpEvent<any>> {

    const token = localStorage.getItem("token");

    //if there is a token send it
    if (token) {

      const secureReq = req.clone({

        setHeaders:{
          Authorization : `Bearer ${localStorage.getItem("token")}`
        }
      });

      return next.handle(secureReq);

    } else {

      //don't send token
      return next.handle(req);
    }
  }



}


