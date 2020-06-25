import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatchlistComponent } from './matchlist/matchlist.component';

@NgModule({
  declarations: [MatchlistComponent],
  imports: [
    CommonModule
  ],
  exports:[MatchlistComponent]

})

export class MatchesModule { }
