import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HobbyComponent } from './hobby/hobby.component';
import { HobbylistComponent } from './hobbylist/hobbylist.component';




@NgModule({
  declarations: [HobbyComponent, HobbylistComponent],
  imports: [
    CommonModule
  ],
  exports:[HobbyComponent,HobbylistComponent]
})
export class HobbymoduleModule { }
