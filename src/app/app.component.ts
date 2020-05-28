import { Component} from '@angular/core';
import {LoginService} from "./login.service";
import {Router} from "@angular/router";
import {SwPush} from "@angular/service-worker";
import {HttpClient} from "@angular/common/http";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private loginservice: LoginService, private router:Router,private swPush:SwPush,private http: HttpClient){
    if (swPush.isEnabled) {

      swPush.requestSubscription({
        serverPublicKey: "BHZX9cdrYz3nQpd-teqYtlvkQWngasxcX5UTSsTGdFIjSBLulClF7NkE0kiQgW4LtJkyvwlgzMJOzHj12OrntDA"

      })
        .then(subscription => {
          http.post('http://localhost:5000/api/subscription',subscription).subscribe();
        })
        .catch(console.error);
    }

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
