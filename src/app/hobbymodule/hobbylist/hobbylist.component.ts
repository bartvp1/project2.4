import { Component, OnInit } from '@angular/core';
import {LoginService} from "../../login.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-hobbylist',
  templateUrl: './hobbylist.component.html',
  styleUrls: ['./hobbylist.component.css']
})
export class HobbylistComponent implements OnInit {


  constructor(private loginservice: LoginService,private router: Router ) {
    //TODO: when reloading page receive if login is valid if login is valid, set validlogin to true
    //and call this.loginservice.loggedin.next();
    if(!loginservice.validlogin){
      this.router.navigate(["/","login"])
    }

  }

  ngOnInit(): void {
  }

}
