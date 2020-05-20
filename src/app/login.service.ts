import { Injectable } from '@angular/core';
import {Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  loggedin: Subject<void>=new Subject();
  validlogin:boolean;
  constructor() { }
}
