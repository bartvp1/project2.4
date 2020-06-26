import {Component, OnInit} from '@angular/core';
import {ApiService} from "../../services/api.service";
import {FormBuilder} from '@angular/forms';
import {Router} from "@angular/router";
import {HttpError, TokenResponse} from "../../models/interfaces";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm;
  error: string;
  constructor(public apiService: ApiService, private formBuilder: FormBuilder, private router: Router) {
    this.loginForm = this.formBuilder.group({
      username: '',
      password: ''
    });
  }

  ngOnInit(): void {
    this.error = undefined;
  }

  onSubmit(formdata: Object) {
    this.apiService.login(formdata["username"].trim(), formdata["password"].trim()).subscribe(
      (res: TokenResponse) => {
        console.log("login ok")
        this.error = undefined
        this.apiService.setSession(res)
        this.router.navigate(["/profile"]);
      },
      (error: HttpError) => {
        console.log("login error")
        this.error = this.apiService.handleError(error)
      }
    )
  }

}
