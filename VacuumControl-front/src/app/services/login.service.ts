import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private token: string = "";

  constructor(private httpClient : HttpClient) {
  }

  logIn(email: string, password: string) : Observable<any>{
    return this.httpClient.post('http://localhost:8080/auth/login',{email, password},{observe:'response'})
  }

  getUser(email:string) : Observable<any> {
    return  this.httpClient.get('http://localhost:8080/api/users/' + email, {observe:'response',headers:{
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Access-Control-Allow-Headers': 'Content-Type',
        'Access-Control-Allow-Origin': '*',
        "Authorization":"Bearer " + this.getToken()
      }})
  }

  public setToken(jwt: string):void {
    console.log(jwt);
    localStorage.setItem("token", jwt);
  }

  getToken():string{
    if(localStorage.getItem("token") === null) {
      return "";
    }
    let value = localStorage.getItem("token");
    if(typeof value === 'string') {
      this.token = value;
    }
    return  this.token;
  }

}
