import { Component } from "@angular/core";
import {ApiService} from '../../services/api.service';

declare interface RouteInfo {
  path: string;
  title: string;
  icon: string;
  class: string;
}
export const ROUTES: RouteInfo[] = [
  {
    path: "welcome",
    title: "Home",
    icon: "icon-bank",
    class: ""
  },
  {
    path: "profile",
    title: "My Hobbies",
    icon: "icon-badge",
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
    icon: "icon-settings",
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
    icon: "icon-single-02",
    class: ""
  }
];

@Component({
  selector: "app-sidebar",
  templateUrl: "./sidebar.component.html",
  styleUrls: ["./sidebar.component.css"]
})
export class SidebarComponent {

  constructor(public apiService: ApiService) {}

  isMobileMenu() {
    return window.innerWidth <= 991;
  }

  get_menuItems(): RouteInfo[] {
    if (this.apiService.isLoggedIn()) return ROUTES.slice(0, ROUTES.length - 1)
    return ROUTES.slice(0, 1).concat(ROUTES.slice(ROUTES.length - 1))
  }

}
