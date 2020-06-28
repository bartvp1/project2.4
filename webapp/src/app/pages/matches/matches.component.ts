import {Component, OnInit} from '@angular/core';
import {ApiService} from "../../services/api.service";
import {Hobby, HttpError, Match} from "../../models/interfaces"

@Component({
  selector: 'app-matches',
  templateUrl: './matches.component.html',
  styleUrls: ['./matches.component.scss']
})
export class MatchesComponent implements OnInit {
  matches: Match[] = [];

  constructor(private service: ApiService) {}

  sameHobby(h: Hobby, m: Match): boolean {
    for (let index = 0; index < m.sameHobbies.length; ++index)
      if (m.sameHobbies[index].name == h.name)
        return true
    return false;
  }

  ngOnInit(): void  {
    this.service.get_matches().subscribe(
      (e: Match[]) => this.matches = e,
      (e: HttpError) => console.error("failed fetching matches " + e),
    );
    console.log(this.matches)
  }


}
