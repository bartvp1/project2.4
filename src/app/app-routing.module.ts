import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {ProfileComponent} from "./profile/profile.component";
import {HobbylistComponent} from "./hobbymodule/hobbylist/hobbylist.component";
import {MatchlistComponent} from "./matchmodule/matchlist/matchlist.component";
import {ReactiveFormsModule} from "@angular/forms";
import {RegisterComponent} from "./register/register.component";
import {GuardGuard} from "./guard.guard";




const routes: Routes = [
  { path: '',component:MatchlistComponent,canActivate:[GuardGuard]},
  { path: 'login',component:LoginComponent},
  { path: 'hobbies',component:HobbylistComponent,canActivate:[GuardGuard]},
  { path: 'profile',component:ProfileComponent,canActivate:[GuardGuard]},
  { path: 'matches',component:MatchlistComponent, canActivate:[GuardGuard]},
  { path: 'register',component:RegisterComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
