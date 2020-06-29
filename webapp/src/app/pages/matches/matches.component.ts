import {Component, OnInit} from '@angular/core';
import {ApiService} from "../../services/api.service";
import {Hobby, HttpError, Match, User} from "../../models/interfaces"

@Component({
  selector: 'app-matches',
  templateUrl: './matches.component.html',
  styleUrls: ['./matches.component.scss']
})
export class MatchesComponent implements OnInit {
  matches: Match[] = [];

  constructor(public service: ApiService) {}

  sameHobby(h: Hobby, m: Match): boolean {
    for (let index = 0; index < m.sameHobbies.length; ++index)
      if (m.sameHobbies[index].name == h.name)
        return true
    return false;
  }

  ngOnInit(): void  {
    this.service.get_matches().subscribe(
      (e: Match[]) => {
        this.service.cache.set("matches", e)
        this.matches = e
      },
      () => {
        if(this.service.cache.get("userdata"))
          this.matches = <Match[]>this.service.cache.get("matches")
        },
    );
    console.log(this.matches)
  }


}
