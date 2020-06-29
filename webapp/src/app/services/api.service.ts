import {Injectable} from '@angular/core';
import * as moment from 'moment';
import * as jwt_decode from 'jwt-decode';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, Subject} from "rxjs";
import {HttpError, TokenResponse, User} from "../models/interfaces";

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
  public subject: Subject<any[]>
  private readonly default_headers;
  public cache: Map<string, object>;

  constructor(private http: HttpClient) {
    this.subject = new Subject<any[]>()
    this.default_headers = new HttpHeaders().set('Content-Type', 'application/json')
    this.cache = new Map();
  }

  public login(username: string, password: string): Observable<any> {
    let cred: User = {username, password}
    return this.http.post(API_URL_LOGIN, cred, {headers: this.default_headers})
  }

  public addHobby(hobbyName: string): Observable<any> {
    return this.http.post(API_URL_HOBBIES, hobbyName, {headers: this.default_headers})
  }

  public register(username: string, password: string, firstname: string, lastname: string, phone: string, country: string, city): Observable<any> {
    let new_user: User = {username, password, firstname, lastname, phone, country, city}
    return this.http.post(API_URL_SIGNUP, new_user, {headers: this.default_headers})
  }

  public logout(): void {
    this.http.post(API_URL_LOGOUT,"", {headers: ApiService.authorization_headers()})
      .subscribe(
        () => console.log("logout ok"),
        () => console.error("logout error"),
        () => localStorage.clear()
      )
  }

  public update_account_data(userdata: User): Observable<any> {
    return this.http.put(API_URL_USERDATA, userdata, {headers: ApiService.authorization_headers()})
  }

  public get_account_data(): Observable<any> {
    return this.http.get(API_URL_USERDATA, {headers: ApiService.authorization_headers()})
  }


  public get_matches(): Observable<any> {
    return this.http.get(API_URL_MATCHES, {headers: ApiService.authorization_headers()})
  }

  public get_hobbies(): Observable<any> {
    return this.http.get(API_URL_HOBBIES)
  }

  public assign_hobby(hobbyId: number): Observable<any> {
    return this.http.post(API_URL_USER_HOBBIES + hobbyId, "", {headers: ApiService.authorization_headers()})
  }

  public unassign_hobby(hobbyId: number): Observable<any> {
    return this.http.delete(API_URL_USER_HOBBIES + hobbyId, {headers: ApiService.authorization_headers()})
  }


  public handleError(error: HttpError): string {
    console.log("handleError: " + error.error.message)
    console.log(error)
    let errorMsg = "";

    error.error.message.split(",").forEach((line: string) => errorMsg.concat(line))
    console.log("error: " + errorMsg)

    if (error.error.message != undefined) {
      if (error.error.message.includes(",")) {
        for (let i of error.error.message.split(", "))
          errorMsg += "<p class='error_msg'>" + i + "</p>"
        return errorMsg
      }
      return "<p class='error_msg'>" + error.error.message + "</p>"
    }
    return "<p class='error_msg'>Undefined error</p>"

  }

  public setSession(response: TokenResponse): void {
    console.log("auth OK")
    let token = response.token;
    localStorage.setItem('token', token);
    localStorage.setItem('username', jwt_decode(token).sub)
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
    if (token)
      authorized_header = authorized_header
        .set('Content-Type', 'application/json')
        .set('Authorization', `Bearer ${token}`)
    return authorized_header;
  }

  public isLoggedIn(): boolean {
    if (localStorage.getItem("token"))  // token in localStorage
      if (moment().unix() ! < this.getJwtExpiration()) return true  // token not yet expired
      else localStorage.clear()  //  remove redundant token
    return false  //  no token found
  }
}
