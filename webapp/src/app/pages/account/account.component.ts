import { Component, OnInit } from '@angular/core';
import {ApiService} from "../../services/api.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";
import {Hobby, HttpError, TokenResponse, User} from "../../models/interfaces";

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss']
})
export class AccountComponent implements OnInit {

  accountForm: FormGroup;
  error: string;

  constructor(private apiservice:ApiService, public formBuilder: FormBuilder,private router:Router) {}

  ngOnInit(): void {
    this.error = undefined
    this.accountForm = this.formBuilder.group({
      username: '',
      password: '',
      phone: '',
      firstname: '',
      lastname: '',
      city: '',
      country: ''
    });
    document.getElementsByClassName("btn")[0].setAttribute("disabled","disabled")

    this.apiservice.get_account_data().subscribe(
      (e:User) => {
        this.fill_form(e);
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

    document.getElementsByClassName("btn")[0].removeAttribute("disabled")
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

    this.apiservice.update_account_data(user).subscribe(
      (e: TokenResponse) => {
        this.apiservice.setSession(e)
        console.log("put user ok")
        location.reload()
      },
      (e: HttpError) => {
        this.error = this.apiservice.handleError(e)
        console.error("put user error");
      }
    );
  }


}
