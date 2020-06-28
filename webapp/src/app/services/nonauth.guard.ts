import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import {ApiService} from "./api.service";

@Injectable({
  providedIn: 'root'
})
export class NonAuthGuard implements CanActivate {
  constructor(private loginservice:ApiService){}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    return !this.loginservice.isLoggedIn();
  }
}
