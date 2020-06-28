import {Component, OnInit} from '@angular/core';
import {ApiService} from "../../services/api.service";
import {Hobby, HttpError, User} from "../../models/interfaces";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-hobbies',
  templateUrl: './hobbies.component.html',
  styleUrls: ['./hobbies.component.css'],
})


export class HobbylistComponent implements OnInit {

  hobbylistSub: Subscription;
  accountDetailsSub: Subscription;

  hobbies: Hobby[] = [];
  myhobbies: Hobby[] = [];
  selectedHobby: Hobby;
  selectedDeleteHobby: Hobby;
  searchedHobbies: Hobby[] = [];
  searchString: string;
  newHobbyName: string;
  hobbyConfirmMessage = undefined;

  constructor(private service: ApiService) {
  }

  ngOnInit() {
    this.update_hobbylists()
  }

  onSelect(hobby: Hobby): void {
    this.selectedDeleteHobby = null;
    this.selectedHobby = hobby;
    this.hobbyConfirmMessage = undefined;
  }

  onSelect2(hobby: Hobby): void {
    this.selectedHobby = null
    this.selectedDeleteHobby = hobby;
    this.hobbyConfirmMessage = undefined;
  }

  clearSelection(): void {
    this.selectedDeleteHobby = null;
    this.selectedHobby = null;
    this.hobbyConfirmMessage = undefined;
    this.newHobbyName = undefined
  }

  search(event?) {
    this.clearSelection()

    this.searchedHobbies = []
    let temp = this.myhobbies.map(function (item) {return item["name"]})
    let filtered = this.hobbies.filter(item2 => !temp.includes(item2["name"]))
    for (let hobby of filtered) {
      if (event) {
        if (hobby.name.toLowerCase().trim().includes(event))
          this.searchedHobbies.push(hobby);
      } else this.searchedHobbies.push(hobby);
    }
  }

  onClickHobbyConfirm(): void {
    if (this.myhobbies.length < 15)
      this.service.assign_hobby(this.selectedHobby.id).subscribe(
        () => this.update_hobbylists(),
        (error: HttpError) => this.hobbyConfirmMessage = error.error.message,
        () => this.clearSelection()
      );
    else
      this.hobbyConfirmMessage = "Maxmimum of 15 hobbies allowed!"
  }

  onClickHobbyDeleteConfirm(): void {
    this.service.unassign_hobby(this.selectedDeleteHobby.id).subscribe(
      () => this.update_hobbylists(),
      (e: HttpError) => this.hobbyConfirmMessage = e.error.message,
      () => this.clearSelection()
    );
  }

  onClickAddHobby(newHobby: string): void {
    this.service.addHobby(newHobby.trim().toLowerCase().charAt(0).toUpperCase() + newHobby.slice(1)).subscribe(
      (e) =>
        this.service.assign_hobby(e.id).subscribe(
          () => this.update_hobbylists(),
          (error: HttpError) => this.hobbyConfirmMessage = error.error.message
        )
      ,
      () => this.hobbyConfirmMessage = "Error",
      () => this.clearSelection()
    );
  }

  private update_hobbylists() {
    if (this.hobbylistSub != undefined) this.hobbylistSub.unsubscribe()
    if (this.accountDetailsSub != undefined) this.accountDetailsSub.unsubscribe()

    this.hobbylistSub = this.service.get_hobbies().subscribe(
      (e: Hobby[]) => this.hobbies = e);

    this.accountDetailsSub = this.service.get_account_data().subscribe(
      (e: User) => {this.myhobbies = e.hobbySet;this.search()}
    );
  }

}
