import {NgModule} from "@angular/core";
import { CommonModule } from "@angular/common";
import { BrowserModule } from "@angular/platform-browser";
import { Routes, RouterModule } from "@angular/router";

import { HomeComponent } from "./pages/home/home.component";
import { NotificationsComponent } from "./pages/notifications/notifications.component";
import {AccountComponent} from "./pages/account/account.component";
import {ProfileComponent} from "./pages/profile/profile.component";
import {MatchesComponent} from "./pages/matches/matches.component";
import {Guard} from "./services/guard.guard";
import {LoginComponent} from './pages/login/login.component';
import {RegisterComponent} from './pages/register/register.component';
import {LogoutComponent} from "./pages/logout/logout.component";

export const routes: Routes = [
  { path: "", component: HomeComponent },
  { path: "login", component: LoginComponent},
  { path: "register", component: RegisterComponent},
  { path: "account", component: AccountComponent, canActivate:[Guard]},
  { path: "profile", component: ProfileComponent, canActivate:[Guard]},
  { path: "matches", component: MatchesComponent, canActivate:[Guard]},
  { path: "notifications",component: NotificationsComponent, canActivate:[Guard]},
  { path: "logout", component: LogoutComponent,canActivate:[Guard]},
  { path: "**", redirectTo: ""}
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

