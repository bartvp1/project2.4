import {Component} from '@angular/core';
import {ApiService} from "../../services/api.service";
import {Hobby, HttpError, User} from "../../models/interfaces";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-hobbies',
  templateUrl: './hobbies.component.html',
  styleUrls: ['./hobbies.component.css'],
})


export class HobbylistComponent {

  hobbylist:Subscription;
  accountDetails:Subscription;
  hobbies: Hobby[] = [];
  selectedHobby: Hobby;
  selectedDeleteHobby: Hobby;
  searchedHobbies: Hobby[] = [];
  searchedHobbiesList: Hobby[] = [];

  searchString: string;
  myhobbies: Hobby[] = [];
  newHobbyName: string;
  hobbyConfirmMessage = undefined;

  constructor(private service: ApiService) {
    this.update_hobbylists()
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
    if(this.myhobbies.length < 15) {
      console.log(this.myhobbies)
    this.service.assign_hobby(this.selectedHobby.id).subscribe(
      () => {},
      (error:HttpError) => this.hobbyConfirmMessage = error.error.message,
      () => this.update_hobbylists()
    );
    this.selectedHobby = null;
    }else{
      this.hobbyConfirmMessage = "Maxmimum of 15 hobbies allowed!"
    }
  }

  onClickHobbyDeleteConfirm(): void {
    this.service.unassign_hobby(this.selectedDeleteHobby.id).subscribe(
      () => {},
      (e:HttpError) => this.hobbyConfirmMessage = e.error.message,
      () => this.update_hobbylists()
    );
    this.selectedDeleteHobby = null;
  }

  onClickSearch(searchText: string) {
    this.hobbyConfirmMessage = undefined;
    this.searchedHobbies = [];
    this.selectedHobby = null;
    searchText = searchText.toLowerCase().trim();
    for (let hobby of this.hobbies) {
      if (hobby.name.toLowerCase().trim().includes(searchText)) {
        this.searchedHobbies.push(hobby);
      }
    }
  }

  onClickAddHobby(newHobby: string): void {
      this.service.addHobby(newHobby.trim().toLowerCase().charAt(0).toUpperCase() + newHobby.slice(1)).subscribe(
        (e) => {

          this.service.assign_hobby(e.id).subscribe(
            () => {},
            (error:HttpError) => this.hobbyConfirmMessage = error.error.message,
            () => this.update_hobbylists()
          );
        },
        () => this.hobbyConfirmMessage = "Error"
      );
  }

  private update_hobbylists(){
    if(this.hobbylist != undefined) this.hobbylist.unsubscribe()
    if(this.accountDetails != undefined) this.accountDetails.unsubscribe()
    this.hobbylist = this.service.get_hobbies().subscribe(
      (e: Hobby[]) => {
        this.hobbies = e
        this.searchedHobbies = this.hobbies
      }
    );
    this.accountDetails = this.service.get_account_data().subscribe(
      (e: User) =>
      {
        this.myhobbies = e.hobbySet
        let temp = this.myhobbies.map(function (item) {return item["name"]})
        this.searchedHobbiesList = this.searchedHobbies.filter(item2 => !temp.includes(item2["name"]))
      }
    );
  }
}
