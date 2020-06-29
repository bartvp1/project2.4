import {NgModule} from "@angular/core";
import { CommonModule } from "@angular/common";
import { BrowserModule } from "@angular/platform-browser";
import { Routes, RouterModule } from "@angular/router";

import { HomeComponent } from "./pages/home/home.component";
import {AccountComponent} from "./pages/account/account.component";
import {HobbylistComponent} from "./pages/hobbies/hobbies.component";
import {MatchesComponent} from "./pages/matches/matches.component";
import {AuthGuard} from "./services/auth.guard";
import {NonAuthGuard} from "./services/nonauth.guard";
import {LoginComponent} from './pages/login/login.component';
import {RegisterComponent} from './pages/register/register.component';
import {LogoutComponent} from "./pages/logout/logout.component";

export const routes: Routes = [
  { path: "welcome", component: HomeComponent },
  { path: "login", component: LoginComponent, canActivate: [NonAuthGuard]},
  { path: "register", component: RegisterComponent, canActivate:[NonAuthGuard]},
  { path: "account", component: AccountComponent, canActivate:[AuthGuard]},
  { path: "profile", component: HobbylistComponent, canActivate:[AuthGuard]},
  { path: "matches", component: MatchesComponent, canActivate:[AuthGuard]},
  { path: "logout", component: LogoutComponent,canActivate:[AuthGuard]},
  { path: "**", pathMatch: "full", redirectTo: "welcome"},
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

