import { Component, OnInit } from '@angular/core';
import {ApiService} from "../../services/api.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-logout',
  template: ""
})
export class LogoutComponent implements OnInit{

  constructor(private loginservice: ApiService, private router: Router) {}

  ngOnInit() {
    this.loginservice.logout();
    this.router.navigate(["/login"]);
  }
}
