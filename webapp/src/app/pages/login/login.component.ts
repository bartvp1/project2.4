import {Component, OnDestroy, OnInit} from '@angular/core';
import {ApiService} from "../../services/api.service";
import {FormBuilder} from '@angular/forms';
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy {
  loginForm;
  request;

  constructor(public loginservice: ApiService, private formBuilder: FormBuilder, private router: Router) {

    this.loginForm = this.formBuilder.group({
      username: '',
      password: ''
    });


  }

  ngOnInit(): void {
    if (this.loginservice.isLoggedIn()) {
      this.router.navigate(["/profile"]);
    }
    this.loginservice.error = undefined;
  }

  ngOnDestroy() {
    if(this.request !== undefined) this.request.unsubscribe()
  }

  onSubmit(formdata: Object) {
    this.request = this.loginservice.login(formdata["username"].trim(), formdata["password"].trim())
  }

}
