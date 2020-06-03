import { Component, OnInit } from '@angular/core';
import {LoginService} from "../../services/login.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-logout',
  template: ""
})
export class LogoutComponent {

  constructor(private loginservice: LoginService,private router: Router) {
    this.logout()
  }


  logout = () => {
    this.loginservice.logout();
    localStorage.clear()
    this.router.navigate(["/login"]);
  }
}
