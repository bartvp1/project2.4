import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HobbyComponent } from './hobby/hobby.component';
import { HobbylistComponent } from './hobbylist/hobbylist.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

@NgModule({
  declarations: [HobbyComponent, HobbylistComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports:[HobbyComponent, HobbylistComponent]
})
export class HobbymoduleModule { }
