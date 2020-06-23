import {Injectable} from '@angular/core';
import * as moment from 'moment';
import * as jwt_decode from 'jwt-decode';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {Observable, Subscription} from "rxjs";

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

  static default_headers = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(private http: HttpClient, private router: Router) {}

  public login(username: string, password: string): void {
    let cred: UserLogin = {username, password}

    this.http.post(API_URL_LOGIN, cred, {headers: ApiService.default_headers})
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

  public register(username: string, password: string, firstname: string, lastname: string, phone: string, country: string, city): void {
    let new_user: UserSignUp = {username, password, firstname, lastname, phone, country, city}

    this.http.post(API_URL_SIGNUP, new_user, {headers: ApiService.default_headers})
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

  public logout(): void {
    this.http.get(API_URL_LOGOUT, {headers: ApiService.authorization_headers()})
      .subscribe(
        () => {
          console.log("logout ok")
          localStorage.removeItem('token');
          localStorage.removeItem("username");
        },
        () => {
          console.error("logout error");
        },
        () => {
          localStorage.removeItem('token');
          localStorage.removeItem("username");
        }
      )
  }

  public get_matches(): void {
    this.http.get(API_URL_MATCHES, {headers: ApiService.authorization_headers()})
      .subscribe(
        (e: Match[]) => {
          // TODO process data
        },
        (e: HttpError) => {
          console.error("failed fetching matches");
        },
      );
  }

  public get_myhobbies(): void {
    this.http.get(API_URL_MYHOBBIES, {headers: ApiService.authorization_headers()})
      .subscribe(
        (e: Hobby[]) => {
          // TODO process data
        },
        (e: HttpError) => {
          console.error("failed fetching hobbies");
        },
      );
  }


  private handleError(error: HttpError): void {
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

  private setSession(response: Response): void {
    console.log("auth OK")
    this.error = undefined;
    let token = response.token;

    localStorage.setItem('token', token);
    localStorage.setItem('username', jwt_decode(token).sub)

    this.router.navigate(["/profile"]);
  }

  public getUsername(): string {
    return localStorage.getItem("username");
  }

  public getJwtExpiration(): number {
    return jwt_decode(localStorage.getItem("token")).exp;
  }

  private static authorization_headers(): HttpHeaders {
    let authorized_header: HttpHeaders = new HttpHeaders();
    let token = localStorage.getItem("token");
    if (token) {
      authorized_header = authorized_header
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${token}`)
    }

    return authorized_header;
  }


  public isLoggedIn(): boolean {
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

interface Hobby {
  id: number,
  naam: string
}
