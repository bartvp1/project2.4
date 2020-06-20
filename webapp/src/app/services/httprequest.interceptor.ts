import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpHeaders} from '@angular/common/http';

@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    /*
    let token = localStorage.getItem("token");
    let request = req.clone().;

    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json; charset=utf-8');
    headers.set('Access-Control-Allow-Origin', '*');
    headers.set('Authorization', `Bearer abcd`);


    if (token) headers.set('Authorization', `Bearer ${token}`)




    return next.handle(request);

     */
    return new Observable<HttpEvent<any>>();

  }

}


