import {Component, OnInit, Output} from '@angular/core';
import {LoginService} from "../login.service";
import { FormBuilder} from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm;
  errormessage: string;
  constructor(private loginservice:LoginService,private formBuilder: FormBuilder) {
    this.loginForm = this.formBuilder.group({
      username: '',
      password: ''
    });


  }
  ngOnInit(): void {

  }
  onSubmit(formdata: Object){
    this.validateLogin(formdata["username"],formdata["password"]);
  }
  validateLogin(username: string,password: string){
    //validate here with the login service

    let valid=true;
    if(valid){
      //jwt is received
      //send next
      this.loginservice.loggedin.next();
    } else{
      this.errormessage="Wrong credentials.";
    }



  }

}
