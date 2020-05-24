import { Component} from '@angular/core';
import {LoginService} from "./login.service";
import {Router} from "@angular/router";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private loginservice: LoginService, private router:Router){

  }
  title = 'hobbylist';

  logout(){
    //handle logout with the login service
    this.loginservice.logout();


  }
isLoggedin(){
  return this.loginservice.isLoggedIn();
}


}
