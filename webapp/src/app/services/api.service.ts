import {Injectable} from '@angular/core';
import * as moment from 'moment';
import * as jwt_decode from 'jwt-decode';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs";

const API_URL = 'http://127.0.0.1:5000/';
const API_URL_LOGIN = API_URL + 'login';
const API_URL_SIGNUP = API_URL + 'signup';
const API_URL_LOGOUT = API_URL + 'logout';
const API_URL_MATCHES = API_URL + 'matches';
const API_URL_MYHOBBIES = API_URL + 'hobbies/myhobbies';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  public error: string;

  headers: HttpHeaders = new HttpHeaders();

  constructor(private http: HttpClient, private router: Router) {
    this.headers = this.headers.set('Content-Type', 'application/json');
    this.get_matches()
  }


  public login(username: string, password: string) {
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

  private handleError(error: HttpError) {
    console.log("handleError: " + error.error.message)
    console.log(error)
    this.error = "";
    error.error.message.split(",").forEach((line: string) => {
      this.error.concat(line)
    })
    for (let i of error.error.message.split(", ")) {
      this.error += "<p class='error_msg'>" + i + "</p>"
    }
    console.log("error: " + this.error)
    //console.log(error.error.message.split(",").)
  }

  private setSession(response: Response) {
    console.log("auth OK")
    this.error = undefined;
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

  private static authorization_header(): HttpHeaders {
    let authorized_header: HttpHeaders = new HttpHeaders();
    let token = localStorage.getItem("token");
    if (token) {
      authorized_header = authorized_header
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${token}`)
    }

    return authorized_header;
  }

  public logout() {
    return this.http.get(API_URL_LOGOUT, {headers: ApiService.authorization_header()})
      .subscribe(
        () => {
          console.log("logout ok")
          localStorage.removeItem('token');
          localStorage.removeItem("username");
        },
        () => {
          console.log("logout error");
        },
        () => {
          localStorage.removeItem('token');
          localStorage.removeItem("username");
        }
      )
  }

  public get_matches() {
    return this.http.get(API_URL_MATCHES, {headers: ApiService.authorization_header()})
      .subscribe(
        (e: Match[]) => {
          console.log("matches: " + e)
        },
        (e: HttpError) => {
          console.log("failed fetching matches");
        },
      )
  }

  public isLoggedIn() {
    if (localStorage.getItem("token")) { // token in localStorage
      if (moment().unix() ! < this.getJwtExpiration()) { // token not yet expired
        return true;
      } else {
        localStorage.clear() //remove redundant token
      }
    }
    //no token found
    return false;
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
  error: {
    status: number,
    message: string
  },
  headers: HttpHeaders,
  message: string,
  name: string,
  ok: boolean,
  status: number,
  statusText: string,
  url: string
}

interface Match {
  naam: string,
  phone: string,
  city: string,
  country: string
}
