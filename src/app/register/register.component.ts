import {Component, OnInit, Output} from '@angular/core';
import {LoginService} from "../login.service";
import { FormBuilder} from '@angular/forms';
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
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
      phonenumber: '',
      country: '',
      city: '',
      address:''
    });


  }
  ngOnInit(): void {

  }
  onSubmit(formdata: Object){
    console.log(formdata);
    this.validateRegister(formdata["username"],
      formdata["password"]
      ,formdata["phonenumber"]
      ,formdata["country"]
      ,formdata["city"]
      ,formdata["address"]

    );
  }
  validateRegister(username: string,password: string, phonenumber:string,country: string, city:string,address:string){

    let valid=true;
    if(valid){
      //handle register
    } else {
      this.errormessage="Some fields are empty";
    }



  }

}
