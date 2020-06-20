import { Component, OnInit } from "@angular/core";
import {LoginComponent} from '../../pages/login/login.component';
import {ApiService} from '../../services/api.service';

declare interface RouteInfo {
  path: string;
  title: string;
  icon: string;
  class: string;
}
export const ROUTES: RouteInfo[] = [
  {
    path: "",
    title: "Home",
    icon: "icon-chart-pie-36",
    class: ""
  },
  {
    path: "notifications",
    title: "Notifications",
    icon: "icon-bell-55",
    class: ""
  },
  {
    path: "profile",
    title: "User Profile",
    icon: "icon-single-02",
    class: ""
  },
  {
    path: "matches",
    title: "Matches",
    icon: "icon-bell-55",
    class: ""
  },
  {
    path: "account",
    title: "Account Settings",
    icon: "icon-single-02",
    class: ""
  },
  {
    path: "logout",
    title: "Log out",
    icon: "icon-single-02",
    class: ""
  },
  {
    path: "login",
    title: "Log in",
    icon: "icon-bell-55",
    class: ""
  }
];

@Component({
  selector: "app-sidebar",
  templateUrl: "./sidebar.component.html",
  styleUrls: ["./sidebar.component.css"]
})
export class SidebarComponent implements OnInit {
  menuItems: any[];

  constructor(public loginService:ApiService) {}

  ngOnInit() {
    this.menuItems = ROUTES.filter(menuItem => menuItem);
  }
  isMobileMenu() {
    if (window.innerWidth > 991) {
      return false;
    }
    return true;
  }
  get_menuItems():any[]{
    if(this.loginService.isLoggedIn()){
      return ROUTES.slice(0,ROUTES.length-1)
    }
    return ROUTES.slice(0,1).concat(ROUTES.slice(ROUTES.length-1))

  }
}
