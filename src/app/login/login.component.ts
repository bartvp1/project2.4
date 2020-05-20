import {Component, OnInit, Output} from '@angular/core';
import {EventEmitter} from "events";
import {LoginService} from "../login.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private loginservice:LoginService) { }
  ngOnInit(): void {

  }

  validateLogin(){
    //validate here with the login service



    //if valid send next
    this.loginservice.loggedin.next();
  }

}
