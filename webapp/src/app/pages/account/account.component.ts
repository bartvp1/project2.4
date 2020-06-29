import { Component, OnInit } from '@angular/core';
import {ApiService} from "../../services/api.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {HttpError, TokenResponse, User} from "../../models/interfaces";
import {shareReplay} from "rxjs/operators";

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss']
})
export class AccountComponent implements OnInit {

  accountForm: FormGroup;
  error: string;
  isEnabled: boolean = false;

  constructor(private apiservice:ApiService, public formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.error = undefined
    this.accountForm = this.formBuilder.group({
      username: '',
      password: '',
      phone: '',
      firstname: '',
      lastname: '',
      city: '',
      country: '',
      active: null
    });
    document.getElementsByClassName("btn")[0].setAttribute("disabled","disabled")

    this.apiservice.get_account_data().subscribe(
      (e:User) => {
        this.apiservice.cache.set("userdata",e)
        this.fill_form(e);
        this.isEnabled = e.active == 1;
      },
      () => {
        console.error("Something went wrong getting userdata")
        if(this.apiservice.cache.get("userdata"))
          this.fill_form(<User>this.apiservice.cache.get("userdata"))
      }
      );
  }

  private fill_form(userdata:User){
    this.accountForm = this.formBuilder.group({
      username: userdata.username,
      password: '',
      phone: userdata.phone,
      firstname: userdata.firstname,
      lastname: userdata.lastname,
      city: userdata.city,
      country: userdata.country,
      active: userdata.active
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
    let f_active = formdata["active"] ? 1: 0
    let user:User = {username: f_username, password: f_password, firstname: f_firstname, lastname: f_lastname,phone: f_phone,country: f_country,city: f_city,active: f_active}

    this.apiservice.update_account_data(user).subscribe(
      (e: TokenResponse) => {
        this.apiservice.setSession(e)
        location.reload()
      },
      (e: HttpError) => this.error = this.apiservice.handleError(e)
    )
  }


}
