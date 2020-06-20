import {Component, OnDestroy, OnInit} from '@angular/core';
import {ApiService} from "../../services/api.service";
import { FormBuilder} from '@angular/forms';
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit, OnDestroy{
  registerForm;
  errorsub;
  errormessage: string;
  request;

  constructor(private loginservice:ApiService, private formBuilder: FormBuilder, private router:Router) {
    if(this.loginservice.isLoggedIn()){
      this.router.navigate(["/matches"]);
    }
    this.registerForm = this.formBuilder.group({
      username: '',
      password: '',
      firstname:'',
      lastname:'',
      phonenumber: '',
      country:'Nederland',
      city:'Groningen'
    });
  }

  onSubmit(formdata: Object){
    this.request = this.loginservice.register(
      formdata["username"],
      formdata["password"],
      formdata["firstname"],
      formdata["lastname"],
      formdata["phonenumber"],
      formdata["country"],
      formdata["city"],
    )
  }

  ngOnInit(): void {
    if (this.loginservice.isLoggedIn()) {
      this.router.navigate(["/profile"]);
    } else {
      this.loginservice.error.subscribe(
        (change: string) => {
          this.errormessage = change;
        }
      );
    }
  }

  ngOnDestroy() {
    if(this.loginservice.error !== undefined) this.loginservice.error.unsubscribe()
    if(this.request !== undefined) this.request.unsubscribe()
  }

}
