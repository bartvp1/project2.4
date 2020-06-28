import {Component, OnInit} from '@angular/core';
import {ApiService} from "../../services/api.service";
import {FormBuilder, FormGroup} from '@angular/forms';
import {Router} from "@angular/router";
import {HttpError, TokenResponse} from "../../models/interfaces";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit{
  registerForm: FormGroup;
  error: string;

  constructor(public apiService: ApiService, private formBuilder: FormBuilder, private router:Router) {
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
    this.apiService.register(
      formdata["username"],
      formdata["password"],
      formdata["firstname"],
      formdata["lastname"],
      formdata["phonenumber"],
      formdata["country"],
      formdata["city"],
    ).subscribe(
      (res: TokenResponse) => {
        console.log("registration ok")
        this.apiService.setSession(res)
        this.router.navigate(["/profile"]);
      },
      (error: HttpError) => {
        console.log("registration error");
        this.error = this.apiService.handleError(error);
      }
    )
  }

  ngOnInit(): void {
    this.error = undefined;
  }

}
