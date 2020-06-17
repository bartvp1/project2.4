import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

const API_URL = 'http://localhost:5000/api/';

@Injectable({
  providedIn: 'root'
})
export class HobbyService {

  constructor(private http: HttpClient) {

  }



}

