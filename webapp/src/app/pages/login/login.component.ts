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
  errormessage: string;
  request;

  constructor(private loginservice: ApiService, private formBuilder: FormBuilder, private router: Router) {

    this.loginForm = this.formBuilder.group({
      username: '',
      password: ''
    });


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

  onSubmit(formdata: Object) {
    this.request = this.loginservice.login(formdata["username"].trim(), formdata["password"].trim())
  }

}
