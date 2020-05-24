import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatchComponent } from './match/match.component';
import { MatchlistComponent } from './matchlist/matchlist.component';





@NgModule({
  declarations: [MatchComponent, MatchlistComponent],
  imports: [
    CommonModule
  ],
  exports:[MatchComponent,MatchlistComponent]
})
export class MatchmoduleModule { }
