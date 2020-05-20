import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatchComponent } from './match/match.component';
import { MatchlistComponent } from './matchlist/matchlist.component';
import {HobbyComponent} from "../hobbymodule/hobby/hobby.component";
import {HobbylistComponent} from "../hobbymodule/hobbylist/hobbylist.component";



@NgModule({
  declarations: [MatchComponent, MatchlistComponent],
  imports: [
    CommonModule
  ],
  exports:[MatchComponent,MatchlistComponent]
})
export class MatchmoduleModule { }
