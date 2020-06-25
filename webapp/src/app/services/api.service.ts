import {Injectable} from '@angular/core';
import * as moment from 'moment';
import * as jwt_decode from 'jwt-decode';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import {Observable, Subject} from "rxjs";
import {Hobby, HttpError, Match, TokenResponse, User} from "../models/interfaces";


const API_URL = 'http://127.0.0.1:5000/';
const API_URL_LOGIN = API_URL + 'login';
const API_URL_SIGNUP = API_URL + 'signup';
const API_URL_LOGOUT = API_URL + 'logout';
const API_URL_MATCHES = API_URL + 'user/me/matches';
const API_URL_USERDATA = API_URL + 'user/me/';
const API_URL_USER_HOBBIES = API_URL + 'user/me/hobbies/';
const API_URL_HOBBIES = API_URL + 'hobbies';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  public error: string;

  public subject: Subject<any[]> = new Subject<any[]>()
  static default_headers = new HttpHeaders().set('Content-Type', 'application/json');
  constructor(private http: HttpClient, private router: Router) {}

  public login(username: string, password: string): void {
    let cred: User = {username, password}

    this.http.post(API_URL_LOGIN, cred, {headers: ApiService.default_headers})
      .subscribe(
        (res: TokenResponse) => {
          console.log("login ok");
          this.setSession(res)
        },
        (error: HttpError) => {
          console.log("login error");
          this.handleError(error);
        }
      )
  }
  public addHobby(hobbyName: string): Observable<any> {
    return this.http.post(API_URL_HOBBIES, hobbyName,{headers: ApiService.default_headers})

  }

  public register(username: string, password: string, firstname: string, lastname: string, phone: string, country: string, city): void {
    let new_user: User = {username, password, firstname, lastname, phone, country, city}

    this.http.post(API_URL_SIGNUP, new_user, {headers: ApiService.default_headers})
      .subscribe(
        (res: TokenResponse) => {
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
    this.http.post(API_URL_LOGOUT, {headers: ApiService.authorization_headers()})
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

  public update_account_data(userdata:User): void {
    this.http.put(API_URL_USERDATA, userdata, {headers: ApiService.authorization_headers()})
      .subscribe(
        (e:TokenResponse) => {
          this.setSession(e)
          console.log("update ok")
        },
        (e:HttpError) => {
          this.handleError(e)
          console.error("update error");
        }
      )
  }

  public get_account_data(): Observable<any> {
    return this.http.get(API_URL_USERDATA, {headers: ApiService.authorization_headers()})
  }


  public get_matches(): void {
    this.http.get(API_URL_MATCHES, {headers: ApiService.authorization_headers()})
      .subscribe(
        (e: Match[]) => {
          // TODO process data
          this.subject.next(e)
        },
        (e: HttpError) => {
          console.error("failed fetching matches");
        },
      );
  }
  public get_hobbies(): Observable<any> {
    return this.http.get(API_URL_HOBBIES)
  }

  public assign_hobby(hobbyId: number): Observable<any> {
    return this.http.post(API_URL_USER_HOBBIES+hobbyId, "",{headers: ApiService.authorization_headers()})
  }

  public unassign_hobby(hobbyId: number): Observable<any> {
    return this.http.delete(API_URL_USER_HOBBIES+hobbyId, {headers: ApiService.authorization_headers()})
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
  }

  private setSession(response: TokenResponse): void {
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
