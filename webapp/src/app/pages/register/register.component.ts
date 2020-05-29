import {Component, OnInit} from '@angular/core';
import {LoginService} from "../../services/login.service";
import { FormBuilder} from '@angular/forms';
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registerForm;
  errormessage: string;
  constructor(private loginservice:LoginService,private formBuilder: FormBuilder,private router:Router) {
    if(this.loginservice.isLoggedIn()){
      this.router.navigate(["/matches"])
    }
    this.registerForm = this.formBuilder.group({
      username: '',
      password: '',
      firstname:'',
      lastname:'',
      phonenumber: '',
      country: '',
      city: '',
    });


  }
  ngOnInit(): void {

  }
  onSubmit(formdata: Object){
    console.log(formdata);
    this.validateRegister(formdata["username"],
      formdata["password"],
      formdata["firstname"],
      formdata["lastname"]
      ,formdata["phonenumber"]
      ,formdata["country"]
      ,formdata["city"]

    );
  }
  validateRegister(username: string,password: string,firstname:string,lastname:string, phonenumber:string,country: string, city:string){
    username=username.trim();
    password=password.trim();
    firstname=firstname.trim();
    lastname=lastname.trim();
    phonenumber=phonenumber.trim();
    country=country.trim();
    city=city.trim();


    if(!username || !password){
      this.errormessage="Please fill in at least an username and a password";
      return;
    }
    if(username.length<6 || password.length<6){
      this.errormessage="The password or username should not be shorter than 6 characters";
      return;
    }

      this.errormessage="";
      //register on backend
      //if already exists: errormessage="This username or password already exist"


  }

}
