import { Component, OnInit } from '@angular/core';
import {ApiService} from "../../services/api.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";
import {Hobby, User} from "../../models/interfaces";

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss']
})
export class AccountComponent implements OnInit {

  accountForm;
  errormessage: string;

  constructor(private apiservice:ApiService, public formBuilder: FormBuilder,private router:Router) {}

  ngOnInit(): void {
    this.apiservice.get_account_data().subscribe(
      (e:User) => {
        this.fill_form(e);
        console.log("update ok")
      },
      () => {
        console.error("Something went wrong getting userdata");
      });


  }

  private fill_form(userdata:User){
    this.accountForm = this.formBuilder.group({
      username: userdata.username,
      password: '',
      phone: userdata.phone,
      firstname: userdata.firstname,
      lastname: userdata.lastname,
      city: userdata.city,
      country: userdata.country
    });
  }

  onSubmit(formdata: Object){
    let f_username = formdata["username"]
    let f_password = formdata["password"]
    let f_firstname = formdata["firstname"]
    let f_lastname = formdata["lastname"]
    let f_phone = formdata["phone"]
    let f_country = formdata["country"]
    let f_city = formdata["city"]
    let user:User = {username: f_username, password: f_password, firstname: f_firstname, lastname: f_lastname,phone: f_phone,country: f_country,city: f_city}

    this.apiservice.update_account_data(user);
  }


}
