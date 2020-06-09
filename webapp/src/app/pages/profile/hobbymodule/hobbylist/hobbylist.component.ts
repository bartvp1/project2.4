import { Component, OnInit } from '@angular/core';
import { HOBBIES } from '../../../../mock-hobbies';
import {Hobby} from '../../../../hobby';

@Component({
  selector: 'app-hobbylist',
  templateUrl: './hobbylist.component.html',
  styleUrls: ['./hobbylist.component.css']
})


export class HobbylistComponent implements OnInit {

  hobbies = HOBBIES;
  selectedHobby: Hobby;

  hobbyConfirmMessage = '';

  constructor( ) {
  }

  ngOnInit(): void {
  }

  onSelect(hobby: Hobby): void {
    this.selectedHobby = hobby;
  }

  onClickSearch(): void {

  }

  onClickHobbyConfirm(): void {
    this.hobbyConfirmMessage = 'Hobby added to your profile!';
  }
}
