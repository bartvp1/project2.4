import {NgModule} from "@angular/core";
import { CommonModule } from "@angular/common";
import { BrowserModule } from "@angular/platform-browser";
import { Routes, RouterModule } from "@angular/router";

import { HomeComponent } from "./pages/home/home.component";
import {AccountComponent} from "./pages/account/account.component";
import {HobbiesComponent} from "./pages/hobbies/hobbies.component";
import {MatchesComponent} from "./pages/matches/matches.component";
import {Guard} from "./services/guard.guard";
import {LoginComponent} from './pages/login/login.component';
import {RegisterComponent} from './pages/register/register.component';
import {LogoutComponent} from "./pages/logout/logout.component";

export const routes: Routes = [
  { path: "welcome", component: HomeComponent },
  { path: "login", component: LoginComponent},
  { path: "register", component: RegisterComponent},
  { path: "account", component: AccountComponent, canActivate:[Guard]},
  { path: "profile", component: HobbiesComponent, canActivate:[Guard]},
  { path: "matches", component: MatchesComponent, canActivate:[Guard]},
  { path: "logout", component: LogoutComponent,canActivate:[Guard]},
  { path: "**", redirectTo: "welcome"}
];

@NgModule({
  imports: [
    CommonModule,
    BrowserModule,
    RouterModule.forRoot(routes, {
      useHash: true
    })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}

