import { Component, OnInit } from '@angular/core';
import { HOBBIES } from '../../../../mock-hobbies';
import {Hobby} from '../../../../hobby';


@Component({
  selector: 'app-hobbylist',
  templateUrl: './hobbylist.component.html',
  styleUrls: ['./hobbylist.component.css']
})


export class HobbylistComponent implements OnInit {

  hobbies: Hobby[] = HOBBIES;
  selectedHobby: Hobby;
  searchedHobbies: Hobby[] = [];
  searchString: string;
  addNewHobbyClicked: boolean = false;
  confirmHobbyToProfile: boolean = false;
  newHobbyName: string;

  hobbyConfirmMessage = '';
  newHobbyConfirmMessage = '';
  constructor() {
    this.onClickSearch('');

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
    this.selectedHobby = null;
    searchText = searchText.toLowerCase().trim();
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

