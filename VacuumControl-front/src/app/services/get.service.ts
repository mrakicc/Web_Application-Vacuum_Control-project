import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class GetService {

  private token: string = "";

  constructor(private httpClient : HttpClient) { }

  getAllUsers(): Observable<any> {
    return this.httpClient.get("http://localhost:8080/api/users/all", {observe: 'response', headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Access-Control-Allow-Headers': 'Content-Type',
        'Access-Control-Allow-Origin': '*',
        "Authorization":"Bearer " + this.getToken()
    }})
  }

  addUser(name:string, last_name:string, email:string, password:string, permissions:string[]) : Observable<any> {
    return  this.httpClient.post('http://localhost:8080/api/users',{name, last_name, email, password, permissions}, {observe:'response',headers:{
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Access-Control-Allow-Headers': 'Content-Type',
        'Access-Control-Allow-Origin': '*',
        "Authorization":"Bearer " + this.getToken()
      }})
  }

  editUser(userId:number, name:string, last_name:string, email:string, password:string,permissions:string[]) : Observable<any> {
    return  this.httpClient.put('http://localhost:8080/api/users',{userId, name, last_name, email, password,permissions}, {observe:'response',headers:{
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Access-Control-Allow-Headers': 'Content-Type',
        'Access-Control-Allow-Origin': '*',
        "Authorization":"Bearer " + this.getToken()
      }})
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

  deleteUser(id:number) : Observable<any> {
    return  this.httpClient.delete('http://localhost:8080/api/users/' + id, {observe:'response',headers:{
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Access-Control-Allow-Headers': 'Content-Type',
        'Access-Control-Allow-Origin': '*',
        "Authorization":"Bearer " + this.getToken()
      }})
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

  getEmail():string{
    if(localStorage.getItem("email") === null) {
      return "";
    }
    let value = localStorage.getItem("email");
    if(typeof value === 'string') {
      this.token = value;
    }
    return  this.token;

  }

  addVacuum(userEmail:string, name:string) : Observable<any> {
    return  this.httpClient.get('http://localhost:8080/api/vacuums/add/?email='+ userEmail+'&name='  +name, {observe:'response',headers:{
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Access-Control-Allow-Headers': 'Content-Type',
        'Access-Control-Allow-Origin': '*',
        "Authorization":"Bearer " + this.getToken()
      }})
  }

  searchVacuum(userEmail:string, name:string, status:string, date1:string, date2:string) : Observable<any> {
    return  this.httpClient.get('http://localhost:8080/api/vacuums/search/'+ userEmail+'/?name='  +name+'&status=' + status+'&date1='+date1+'&date2='+date2,
      {observe:'response',headers:{
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Access-Control-Allow-Headers': 'Content-Type',
        'Access-Control-Allow-Origin': '*',
        "Authorization":"Bearer " + this.getToken()
      }})
  }

  startVacuum(userEmail:string, id:number)
  {
    return  this.httpClient.get('http://localhost:8080/api/vacuums/start/' + userEmail + '/' + id,
      {observe:'response',headers:{
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Access-Control-Allow-Headers': 'Content-Type',
          'Access-Control-Allow-Origin': '*',
          "Authorization":"Bearer " + this.getToken()
        }})
  }
  stopVacuum(userEmail:string, id:number)
  {
    return  this.httpClient.get('http://localhost:8080/api/vacuums/stop/' + userEmail + '/' + id,
      {observe:'response',headers:{
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Access-Control-Allow-Headers': 'Content-Type',
          'Access-Control-Allow-Origin': '*',
          "Authorization":"Bearer " + this.getToken()
        }})
  }
  dischargeVacuum(userEmail:string, id:number)
  {
    return  this.httpClient.get('http://localhost:8080/api/vacuums/discharge/' + userEmail + '/' + id,
      {observe:'response',headers:{
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Access-Control-Allow-Headers': 'Content-Type',
          'Access-Control-Allow-Origin': '*',
          "Authorization":"Bearer " + this.getToken()
        }})
  }
  deleteVacuum(userEmail:string, id:number)
  {
    return  this.httpClient.delete('http://localhost:8080/api/vacuums/' + userEmail + '/' + id,
      {observe:'response',headers:{
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Access-Control-Allow-Headers': 'Content-Type',
          'Access-Control-Allow-Origin': '*',
          "Authorization":"Bearer " + this.getToken()
        }})
  }

  scheduleStartVacuum(userEmail:string, id:number, date:string)
  {
    return  this.httpClient.get('http://localhost:8080/api/vacuums/scheduleStart/?email=' + userEmail + '&id=' + id+'&date=' + date,
      {observe:'response',headers:{
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Access-Control-Allow-Headers': 'Content-Type',
          'Access-Control-Allow-Origin': '*',
          "Authorization":"Bearer " + this.getToken()
        }})
  }
  scheduleStopVacuum(userEmail:string, id:number, date:string)
  {
    return  this.httpClient.get('http://localhost:8080/api/vacuums/scheduleStop/?email=' + userEmail + '&id=' + id+'&date=' + date,
      {observe:'response',headers:{
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Access-Control-Allow-Headers': 'Content-Type',
          'Access-Control-Allow-Origin': '*',
          "Authorization":"Bearer " + this.getToken()
        }})
  }
  scheduleDischargeVacuum(userEmail:string, id:number, date:string)
  {
    return  this.httpClient.get('http://localhost:8080/api/vacuums/scheduleDischarge/?email=' + userEmail + '&id=' + id+'&date=' + date,
      {observe:'response',headers:{
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Access-Control-Allow-Headers': 'Content-Type',
          'Access-Control-Allow-Origin': '*',
          "Authorization":"Bearer " + this.getToken()
        }})
  }

  errorMessage(userEmail:string) : Observable<any>
  {
    return this.httpClient.get('http://localhost:8080/api/errors/' + userEmail,
      {observe:'response',headers:{
          'Content-Type': 'application/json',
          'Accept': 'application/json',
          'Access-Control-Allow-Headers': 'Content-Type',
          'Access-Control-Allow-Origin': '*',
          "Authorization":"Bearer " + this.getToken()
        }})
  }

}
