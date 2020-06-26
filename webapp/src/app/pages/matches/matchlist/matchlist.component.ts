import { Component, OnInit } from '@angular/core';
import {ApiService} from "../../../services/api.service";
import {Hobby, HttpError, Match} from "../../../models/interfaces"

@Component({
  selector: 'app-matchlist',
  templateUrl: './matchlist.component.html',
  styleUrls: ['./matchlist.component.css']
})
export class MatchlistComponent implements OnInit {
  matches: Match[] = null;
  constructor(private service: ApiService) {
    this.service.get_matches().subscribe(
      (e: Match[]) => {
        this.matches = e;
      },
      (e: HttpError) => {
        console.error("failed fetching matches "+e);
      },
    );
    console.log(this.matches)
  }

  sameHobby(h: Hobby,m:Match): boolean{
    for (let index = 0; index < m.sameHobbies.length; ++index) {
      let value = m.sameHobbies[index];
      if(value.name == h.name){
        return true
      }
    }
    return false;
  }

  ngOnInit(): void {}

}
