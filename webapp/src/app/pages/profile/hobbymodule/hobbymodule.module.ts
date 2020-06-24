import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HobbylistComponent } from './hobbylist/hobbylist.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
@NgModule({
  declarations: [ HobbylistComponent],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports:[HobbylistComponent]
})
export class HobbymoduleModule { }
