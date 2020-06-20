import { Component, OnInit } from '@angular/core';
import {ApiService} from "../../services/api.service";
import {FormBuilder} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.scss']
})
export class AccountComponent implements OnInit {

  accountForm;
  errorsub;
  errormessage: string;
  constructor(private formBuilder: FormBuilder,private router:Router) {

    this.accountForm = this.formBuilder.group({
      username: null,
      email:'none',
      password: ''
    });


  }
  ngOnInit(): void {
    //if(this.loginservice.isLoggedIn()){
      //this.router.navigate(["/matches"]);
    //}
   /*
    this.errorsub=this.loginservice.error.subscribe(

      (error:string)=>{
        //no error
        if(!error){
          this.router.navigate(["/login"]);

        }
        //error
        this.errormessage=error;
      })
*/
  }

  onSubmit(formdata: Object){
    //let login= this.loginservice.login(formdata["username"].trim(),formdata["password"].trim()).subscribe();
    //login.unsubscribe();


  }

}
