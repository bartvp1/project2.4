import { Component, OnInit } from '@angular/core';
import {ApiService} from "../../../services/api.service";
import {HttpError, Match} from "../../../models/interfaces"

@Component({
  selector: 'app-matchlist',
  templateUrl: './matchlist.component.html',
  styleUrls: ['./matchlist.component.css']
})
export class MatchlistComponent implements OnInit {
  matches: Match[];
  constructor(private service: ApiService) {}

  ngOnInit(): void {
    this.service.get_matches().subscribe(
      (e: Match[]) => {
        this.matches = e;
      },
      (e: HttpError) => {
        console.error("failed fetching matches "+e);
      },
    );
  }

}
