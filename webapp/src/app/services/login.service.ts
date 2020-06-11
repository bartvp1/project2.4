import { Injectable } from '@angular/core';
import * as moment from 'moment';
import * as jwt_decode from 'jwt-decode';
import {HttpClient} from "@angular/common/http";
import { shareReplay, tap } from 'rxjs/operators';
import {Subject} from "rxjs";
import {Router} from "@angular/router";
const API_URL = 'http://localhost:5000/api/';
@Injectable({
  providedIn: 'root'
})
export class LoginService {
  error=new Subject();

  constructor(private http: HttpClient,private router:Router) {}

  login(username:string, password:string ) {
    return this.http.post<User>(API_URL +'login', {username, password})
      .pipe (
        tap (
          res => this.setSession(res),
          err => this.handleError(err),
        ),
        shareReplay()
      )
  }
  logout(){
    // TODO blacklist on server
    localStorage.removeItem('token');
    localStorage.removeItem('expires_at');

  }

  public isLoggedIn() {
    // TODO

    return moment().isBefore(this.getExpiration());
  }
  private setSession(authResult) {
    console.log("Setting session");
    const expiresAt = moment().add(authResult.expiresIn,'second');

    localStorage.setItem('token', authResult.token);
    localStorage.setItem("expires_at", JSON.stringify(expiresAt.valueOf()) );
    this.error.next(undefined);
    this.router.navigate(["/matches"]);

  }

  public getUsername() {
    if (this.isLoggedIn()){
      // TODO fetch username
      return "return name";
    }
    return false
  }

  public getExpiration() {
    const expiration = localStorage.getItem("expires_at");
    const expiresAt = JSON.parse(expiration);
    return moment(expiresAt);
  }

  private handleError(error) {
    if(error.statusText==="Not Found"){
      this.error.next("Wrong credentials.");
    } else {
      this.error.next("No connection.");
    }
  }
}

interface User {
  username:String,
  password:String,
}
