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
  searchedHobbies: Hobby[];
  searchString: String;

  hobbyConfirmMessage = '';

  constructor( ) {
  }

  ngOnInit(): void {
  }



  onSelect(hobby: Hobby): void {
    this.selectedHobby = hobby;
  }

  onClickHobbyConfirm(): void {
    this.hobbyConfirmMessage = 'Hobby added to your profile!';
  }

  onClickSearch(searchText: string) {
    this.searchedHobbies = [];
    searchText = searchText.charAt(0).toUpperCase();
    for (let hobby of this.hobbies){
      if(hobby.name.includes(searchText)){
        this.searchedHobbies.push(hobby);
      }
    }
  }
}
