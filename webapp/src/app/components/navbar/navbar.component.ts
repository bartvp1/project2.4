import { Component, OnInit } from "@angular/core";
import { Location } from "@angular/common";
import {ApiService} from "../../services/api.service";

@Component({
  selector: "app-navbar",
  templateUrl: "./navbar.component.html",
  styleUrls: ["./navbar.component.css"]
})
export class NavbarComponent implements OnInit {
  private listTitles: any[];
  location: Location;
  mobile_menu_visible: any = 0;
  private toggleButton: any;
  private sidebarVisible: boolean;
  public isCollapsed = true;

  constructor(location: Location, public loginService :ApiService) {
    this.location = location;
    this.sidebarVisible = false;
  }

  ngOnInit() {
    this.toggleButton = document.getElementsByClassName("navbar-toggle")[0];
  }

  sidebarOpen() {
    const toggleButton = this.toggleButton;
    const mainPanel = <HTMLElement>(document.getElementsByClassName("main-panel")[0]);
    const html = document.getElementsByTagName("html")[0];

    if (window.innerWidth < 991)
      mainPanel.style.position = "fixed";


    setTimeout(() =>
      toggleButton.classList.add("toggled")
    , 500);

    html.classList.add("nav-open");
    this.sidebarVisible = true;
  }

  sidebarClose() {
    const html = document.getElementsByTagName("html")[0];
    this.toggleButton.classList.remove("toggled");
    const mainPanel = <HTMLElement>(document.getElementsByClassName("main-panel")[0]);

    if (window.innerWidth < 991)
      setTimeout(() =>
        mainPanel.style.position = ""
      , 500);
    this.sidebarVisible = false;
    html.classList.remove("nav-open");
  }

  sidebarToggle() {
    const $toggle = document.getElementsByClassName("navbar-toggler")[0];

    if (this.sidebarVisible === false)
      this.sidebarOpen();
    else
      this.sidebarClose();

    const html = document.getElementsByTagName("html")[0];

    if (this.mobile_menu_visible == 1) {
      html.classList.remove("nav-open");
      if ($layer)
        $layer.remove();

      setTimeout(() =>
        $toggle.classList.remove("toggled")
      , 400);
      this.mobile_menu_visible = 0;
    } else {
      setTimeout(() =>
          $toggle.classList.add("toggled")
        , 430);

      var $layer = document.createElement("div");
      $layer.setAttribute("class", "close-layer");

      if (html.querySelectorAll(".main-panel"))
        document.getElementsByClassName("main-panel")[0].appendChild($layer);
      else if (html.classList.contains("off-canvas-sidebar"))
        document
          .getElementsByClassName("wrapper-full-page")[0]
          .appendChild($layer);


      setTimeout(() =>
        $layer.classList.add("visible")
      , 100);

      $layer.onclick = function() {
        html.classList.remove("nav-open");
        this.mobile_menu_visible = 0;
        $layer.classList.remove("visible");
        setTimeout(() => {
          $layer.remove()
          $toggle.classList.remove("toggled");
        }, 400);
      }.bind(this);

      html.classList.add("nav-open");
      this.mobile_menu_visible = 1;
    }
  }

  getTitle() {
    let titlee = this.location.prepareExternalUrl(this.location.path());
    if (titlee.charAt(0) === "#")
      titlee = titlee.slice(2);

    for (let item = 0; item < this.listTitles.length; item++) {
      if (this.listTitles[item].path === titlee)
        return this.listTitles[item].title;
    }
    return "HOME";
  }
}
