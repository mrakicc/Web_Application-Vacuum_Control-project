import { Component } from '@angular/core';
import {LoginService} from "../../services/login.service";
import { HttpHeaders } from '@angular/common/http';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  token: string = "";
  email: string = "";
  password: string = "";
  message: string = "";
  messageSuccess : string = "";

  constructor(private loginService: LoginService) {
    this.token = this.loginService.getToken();
  }

  logIn(){
    this.loginService.logIn(this.email, this.password).subscribe(res =>{
      console.log(res.body);
      this.setToken(res.body.jwt);
      this.loginService.getUser(this.email).subscribe(result =>{
        localStorage.setItem("email", result.body.email)
        localStorage.setItem("permissions", JSON.stringify(result.body.permissions));
        this.messageSuccess = "You have succesfully loged in";
        if(result.body.permissions.length == 0){
          this.message = "You don't have permission to do any function";
          document.getElementById('add')!.hidden = true;
          return;
        }
        this.message = "";
        for(let i = 0;i<result.body.permissions.length; i++)
        {
          if(result.body.permissions[i].name ="can_create_users")
          {
            document.getElementById('add')!.hidden = false;
          }
        }
      })
    },
      error => {
        this.messageSuccess = "";
        this.message = "Invalid email or password!";
      }
      );

  }

  setToken(token:string){
    this.loginService.setToken(token);
  }

}
