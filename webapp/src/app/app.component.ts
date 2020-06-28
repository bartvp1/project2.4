import {Component, OnInit} from '@angular/core';
import {ApiService} from './services/api.service';
import {Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {MessagingService} from "./services/messaging.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {

  constructor(
    private loginservice: ApiService,
    private router:Router,
    private http: HttpClient,
    private firebaseService: MessagingService
  ){}

  changeSidebarColor(color){
    const sidebar = document.getElementsByClassName('sidebar')[0];
    const mainPanel = document.getElementsByClassName('main-panel')[0];

    if(sidebar !== undefined)
      sidebar.setAttribute('data',color);

    if(mainPanel !== undefined)
      mainPanel.setAttribute('data',color);
  }

  changeDashboardColor(color){
    const body = document.getElementsByTagName('body')[0];
    if (body && color === 'white-content')
      body.classList.add(color);
    else if(body.classList.contains('white-content'))
      body.classList.remove('white-content');

  }

  ngOnInit() {
    this.changeSidebarColor('blue');
    this.firebaseService.requestPermission()
    this.firebaseService.receiveMessage()
  }
}
