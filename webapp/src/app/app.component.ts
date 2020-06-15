import {Component, OnInit} from '@angular/core';
import {LoginService} from './services/login.service';
import {Router} from '@angular/router';
import {SwPush} from '@angular/service-worker';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  public sidebarColor = 'red';

  constructor(
    private loginservice: LoginService,
    private router:Router,
    private swPush:SwPush,
    private http: HttpClient,
  ){
    /*
    if (swPush.isEnabled) {

      swPush.requestSubscription({
        serverPublicKey: 'BHZX9cdrYz3nQpd-teqYtlvkQWngasxcX5UTSsTGdFIjSBLulClF7NkE0kiQgW4LtJkyvwlgzMJOzHj12OrntDA'

      })
        .then(subscription => {
          http.post('http://localhost:5000/api/subscription',subscription).subscribe();
        })
        .catch(console.error);
    }

     */
  }



  isLoggedin(){
    return this.loginservice.isLoggedIn();
  }

  changeSidebarColor(color){
    const sidebar = document.getElementsByClassName('sidebar')[0];
    const mainPanel = document.getElementsByClassName('main-panel')[0];

    this.sidebarColor = color;

    if(sidebar !== undefined){
      sidebar.setAttribute('data',color);
    }
    if(mainPanel !== undefined){
      mainPanel.setAttribute('data',color);
    }
  }
  changeDashboardColor(color){
    const body = document.getElementsByTagName('body')[0];
    if (body && color === 'white-content') {
      body.classList.add(color);
    }
    else if(body.classList.contains('white-content')) {
      body.classList.remove('white-content');
    }
  }
  ngOnInit()
  {
    this.changeSidebarColor('blue');
  }
}
