import { Component} from '@angular/core';
import {LoginService} from "./login.service";
import {Router} from "@angular/router";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'hobbylist';

  logout(){
    //handle logout with the login service



    this.loginservice.validlogin=false;
  }
  loadlogin(){

    this.loginservice.validlogin=true;
  }
  checkValidLogin(){
    return this.loginservice.validlogin;
  }
  constructor(private loginservice: LoginService, private router:Router){
    loginservice.validlogin=false;
    //wait for login
    loginservice.loggedin.subscribe(
      ()=>{this.loadlogin();
      //load matches
        this.router.navigate(['/','matches']);

      });
  }

}
