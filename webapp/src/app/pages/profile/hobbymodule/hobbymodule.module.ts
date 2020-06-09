import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HobbyComponent } from './hobby/hobby.component';
import { HobbylistComponent } from './hobbylist/hobbylist.component';
import {FormsModule} from '@angular/forms';

@NgModule({
  declarations: [HobbyComponent, HobbylistComponent],
  imports: [
    CommonModule,
    FormsModule,
  ],
  exports:[HobbyComponent, HobbylistComponent]
})
export class HobbymoduleModule { }
