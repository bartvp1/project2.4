import {Component, OnInit} from '@angular/core';
import {LoginService} from "../../services/login.service";
import { FormBuilder} from '@angular/forms';
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm;
  errorsub;
  errormessage: string;
  constructor(private loginservice:LoginService,private formBuilder: FormBuilder,private router:Router) {

    this.loginForm = this.formBuilder.group({
      username: '',
      password: ''
    });


  }
  ngOnInit(): void {
    if(this.loginservice.isLoggedIn()){
      this.router.navigate(["/matches"]);
    }
    this.errorsub=this.loginservice.error.subscribe(

      (error:string)=>{
        //no error
        if(!error){
          this.router.navigate(["/login"]);

        }
        //error
        this.errormessage=error;
      })

  }

  ngOnDestroy(){
    this.errorsub.unsubscribe();
  }
  onSubmit(formdata: Object){
    let login= this.loginservice.login(formdata["username"].trim(),formdata["password"].trim()).subscribe();
    login.unsubscribe();


  }

}