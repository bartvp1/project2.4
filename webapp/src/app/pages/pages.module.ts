import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import {AppComponent} from "../app.component";
import {HomeComponent} from "./home/home.component";
import {HobbylistComponent} from "./hobbies/hobbies.component";
import {AccountComponent} from "./account/account.component";
import {MatchesComponent} from "./matches/matches.component";
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {LogoutComponent} from "./logout/logout.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";


@NgModule({
  imports: [CommonModule, RouterModule, NgbModule, ReactiveFormsModule, FormsModule],
  declarations: [
    HomeComponent,
    HobbylistComponent,
    AccountComponent,
    MatchesComponent,
    LoginComponent,
    RegisterComponent,
    LogoutComponent
  ],
  exports: [
    HomeComponent,
    HobbylistComponent,
    AccountComponent,
    MatchesComponent,
    LoginComponent,
    RegisterComponent,
    LogoutComponent
  ]
})
export class PagesModule {}
