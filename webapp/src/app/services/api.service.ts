import {Injectable} from '@angular/core';
import * as moment from 'moment';
import * as jwt_decode from 'jwt-decode';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Subject} from "rxjs";
import {Router} from "@angular/router";

const API_URL = 'http://127.0.0.1:5000/';
const API_URL_LOGIN = API_URL + 'login';
const API_URL_SIGNUP = API_URL + 'signup';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  public error = new Subject();
  headers: HttpHeaders = new HttpHeaders();

  constructor(private http: HttpClient, private router: Router) {
    this.headers = this.headers.set('Content-Type', 'application/json');

  }


  public login(username: string, password: string) {
    let token = localStorage.getItem("token");
    if (token) this.headers = this.headers.set('Authorization', `Bearer ${token}`)

    let cred: UserLogin = {username, password}

    return this.http.post(API_URL_LOGIN, cred, {headers: this.headers})
      .subscribe(
        (res: Response) => {
          console.log("login ok");
          this.setSession(res)
        },
        (error: HttpError) => {
          console.log("login error");
          this.handleError(error);
        }
      )
  }

  public register(username: string, password: string, firstname: string, lastname: string, phone: string, country: string, city) {
    let new_user: UserSignUp = {username, password, firstname, lastname, phone, country, city}

    return this.http.post(API_URL_SIGNUP, new_user, {headers: this.headers})
      .subscribe(
        (res: Response) => {
          console.log("registration ok")
          this.setSession(res)
        },
        (error: HttpError) => {
          console.log("registration error");
          this.handleError(error);
        }
      )
  }

  private handleError(error) {
    console.log("handleError: "+error)
    this.error.next(error.error.message)
  }

  private setSession(response: Response) {
    console.log("auth OK")
    this.error.next(undefined);
    let token = response.token;

    localStorage.setItem('token', token);
    localStorage.setItem('username', jwt_decode(token).sub)

    this.router.navigate(["/profile"]);
  }

  public getUsername() {
    return localStorage.getItem("username");
  }

  public getJwtExpiration() {
    return jwt_decode(localStorage.getItem("token")).exp;
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem("username");
  }

  public isLoggedIn() {
      if(localStorage.getItem("token")){ //token in localStorage
        console.log("token found")
        if(moment().unix() !< this.getJwtExpiration()){ //token not yet expired
          console.log("not expired")
          return true;
        } else {
          localStorage.clear() //remove redundant token
        }
      }
      return false

  }

}


interface UserLogin {
  username: string,
  password: string,
}

interface UserSignUp {
  username: string,
  password: string,
  firstname: string,
  lastname: string,
  phone: string,
  country: string,
  city: string
}

interface Response {
  token: string;
}

interface HttpError {
  error: any,
  headers: any,
  message: string,
  ok: boolean,
  status: number,
  statusText: string,
  url: string
}
