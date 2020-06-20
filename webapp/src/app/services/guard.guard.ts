import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import {ApiService} from "./api.service";

@Injectable({
  providedIn: 'root'
})
export class Guard implements CanActivate {
  constructor(private loginservice:ApiService, private router: Router){

  }
  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    console.log(this.loginservice.isLoggedIn())
    return this.loginservice.isLoggedIn();
    //return this.checkLogin();
  }
  checkLogin(): boolean {
    if(this.loginservice.isLoggedIn()){
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
