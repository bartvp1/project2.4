import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatchlistComponent } from './matchlist/matchlist.component';
import {Match} from "./match";

@NgModule({
  declarations: [MatchlistComponent],
  imports: [
    CommonModule
  ],
  exports:[MatchlistComponent]

})

export class MatchesModule { }
