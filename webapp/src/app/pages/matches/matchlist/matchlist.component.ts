import { Component, OnInit } from '@angular/core';
import {ApiService} from "../../../services/api.service";
import {Match} from "../../../services/api.service"

@Component({
  selector: 'app-matchlist',
  templateUrl: './matchlist.component.html',
  styleUrls: ['./matchlist.component.css']
})
export class MatchlistComponent implements OnInit {
  matches: Match[];
  constructor(private service: ApiService) {
    service.get_matches()
    this.service.subject.subscribe((matches) => {this.matches = matches})

  }

  ngOnInit(): void {
  }

}
