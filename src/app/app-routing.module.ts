import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {ProfileComponent} from "./profile/profile.component";
import {HobbylistComponent} from "./hobbymodule/hobbylist/hobbylist.component";
import {MatchlistComponent} from "./matchmodule/matchlist/matchlist.component";
import {ReactiveFormsModule} from "@angular/forms";
import {Register} from "ts-node";
import {RegisterComponent} from "./register/register.component";




const routes: Routes = [
  { path: '',component:LoginComponent},
  { path: 'login',component:LoginComponent},
  { path: 'hobbies',component:HobbylistComponent},
  { path: 'profile',component:ProfileComponent},
  { path: 'matches',component:MatchlistComponent},
  { path: 'register',component:RegisterComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
