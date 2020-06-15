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
  searchedHobbies: Hobby[] = [];
  searchString: String;
  addNewHobbyClicked: boolean = false;
  confirmHobbyToProfile: boolean = false;
  newHobbyName: String;

  hobbyConfirmMessage = '';
  newHobbyConfirmMessage = '';

  constructor() {
  }

  ngOnInit(): void {
  }


  onSelect(hobby: Hobby): void {
    this.selectedHobby = hobby;
  }

  onClickHobbyConfirm(): void {
    this.hobbyConfirmMessage = 'Hobby added to your profile!';
    this.confirmHobbyToProfile = true;
  }

  onClickSearch(searchText: string) {
    this.searchedHobbies = [];
    this.addNewHobbyClicked = false;
    searchText = searchText.toLowerCase();
    for (let hobby of this.hobbies) {
      if (hobby.name.toLowerCase().includes(searchText)) {
        this.searchedHobbies.push(hobby);
      }
    }
  }

  onClickAddNewHobby(): void {
    this.confirmHobbyToProfile = false;
    this.addNewHobbyClicked = true;
    this.searchedHobbies = [];

  }

  onClickAddConfirmHobby(newHobby: string): void {
    this.hobbies.push({ name : newHobby});
    this.hobbyConfirmMessage = 'Hobby added to list!'
  }
}

