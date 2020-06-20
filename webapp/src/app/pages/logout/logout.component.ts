import { Component, OnInit } from '@angular/core';
import {ApiService} from "../../services/api.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-logout',
  template: ""
})
export class LogoutComponent {

  constructor(private loginservice: ApiService, private router: Router) {
    this.logout()
  }


  logout = () => {
    this.loginservice.logout();
    localStorage.clear()
    this.router.navigate(["/login"]);
  }
}
