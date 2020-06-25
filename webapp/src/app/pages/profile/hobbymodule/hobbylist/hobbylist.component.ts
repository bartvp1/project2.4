import {Component, OnInit} from '@angular/core';
import {Hobby} from "../../../../services/api.service";
import {ApiService} from "../../../../services/api.service";

@Component({
  selector: 'app-hobbylist',
  templateUrl: './hobbylist.component.html',
  styleUrls: ['./hobbylist.component.css'],
})


export class HobbylistComponent implements OnInit {
  ngOnInit(): void {
  }

  hobbies: Hobby[];
  selectedHobby: Hobby;
  selectedDeleteHobby: Hobby;
  searchedHobbies: Hobby[] = [];
  searchString: string;
  myhobbies: Hobby[] = [];
  newHobbyName: string;
  searchableHobbies: Hobby[];

  hobbyConfirmMessage = '';

  constructor(private service: ApiService) {

    service.get_hobbies()
    service.subject.subscribe((allHobbies) => {this.hobbyDataRetrieved(allHobbies)})
    service.get_myhobbies()
    service.subject2.subscribe((allMyHobbies) => {this.myHobbyDataRetrieved(allMyHobbies)})
  }

  hobbyDataRetrieved(allHobbies):void{
    this.hobbies = allHobbies
    this.searchableHobbies = allHobbies
    this.onClickSearch('');
  }
  myHobbyDataRetrieved(allMyHobbies):void{
    this.myhobbies = allMyHobbies.hobbySet;
  }

  onSelect(hobby: Hobby): void {
    this.selectedDeleteHobby = null;
    this.selectedHobby = hobby;
    this.hobbyConfirmMessage = "";
  }

  onSelect2(hobby: Hobby): void {
    this.selectedHobby = null
    this.selectedDeleteHobby = hobby;
    this.hobbyConfirmMessage = "";
  }

  onClickHobbyConfirm(): void {
    this.hobbyConfirmMessage = 'Hobby added to your profile!';
    this.service.assign_hobby(this.selectedHobby.id)
    this.service.get_myhobbies()
    this.service.get_myhobbies()
    this.service.get_myhobbies()

  }
  onClickHobbyDeleteConfirm(): void {
    this.hobbyConfirmMessage = 'Hobby deleted from your profile!';
    this.service.unassign_hobby(this.selectedDeleteHobby.id)
    this.service.get_myhobbies()
    this.service.get_myhobbies()
    this.service.get_myhobbies()


  }

  onClickSearch(searchText: string) {
    this.hobbyConfirmMessage = "";
    this.searchedHobbies = [];
    this.selectedHobby = null;
    searchText = searchText.toLowerCase().trim();
    for (let hobby of this.searchableHobbies) {
      if (hobby.name.toLowerCase().trim().includes(searchText)) {
        this.searchedHobbies.push(hobby);
      }
    }
  }

  onClickAddHobby(newHobby: string): void {
    this.service.addHobby(newHobby.trim().toLowerCase().charAt(0).toUpperCase() +
    newHobby.slice(1))
    this.hobbyConfirmMessage = 'Hobby added to list!'
    this.service.get_hobbies()
    this.service.get_hobbies()
    this.service.get_hobbies()


  }
}

