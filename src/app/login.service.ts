import { Injectable } from '@angular/core';
import * as moment from 'moment';
import * as jwt_decode from 'jwt-decode';
import {HttpClient} from "@angular/common/http";
import { shareReplay, tap } from 'rxjs/operators'
import {Subject} from "rxjs";
const API_URL = 'http://localhost:5000/api/';
@Injectable({
  providedIn: 'root'
})
export class LoginService {
  error=new Subject();

  constructor(private http: HttpClient) {

  }

  login(name:string, password:string ) {


    return this.http.post<User>(API_URL+'login', {name, password})
      .pipe (
        tap (
          res => this.setSession(res),
          err => this.handleError(err),
        ),
        shareReplay()
      )
  }
  logout(){
    //TODO blacklist on server


    localStorage.removeItem('token');
    localStorage.removeItem('expires_at');
  }

  public isLoggedIn() {
    return moment().isBefore(this.getExpiration());
  }
  private setSession(authResult) {
    console.log(authResult);
    console.log("Setting session");
    const expiresAt = moment().add(authResult.expiresIn,'second');

    localStorage.setItem('token', authResult.token);
    localStorage.setItem("expires_at", JSON.stringify(expiresAt.valueOf()) );
    this.error.next(undefined);

  }

  public getExpiration() {
    const expiration = localStorage.getItem("expires_at");
    const expiresAt = JSON.parse(expiration);
    return moment(expiresAt);
  }

  private handleError(error) {
    if(error.statusText=="Not Found"){
      this.error.next("Wrong credentials.");
    } else {
      this.error.next("No connection.");
    }


  }

}
interface User {
  name:String,
  password:String,
}
