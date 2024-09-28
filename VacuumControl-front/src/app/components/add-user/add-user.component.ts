import { Component } from '@angular/core';
import {GetService} from "../../services/get.service";

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent {

  firstName: string = "";
  lastName: string = "";
  email: string = "";
  password: string = "";
  readP: boolean = false;
  createP: boolean = false;
  updateP: boolean = false;
  deleteP: boolean = false;
  permissions: any[] = [];
  message: string = "";
  messageField: string = "";

  constructor(private getService : GetService) {
  }

  addUser(){
    this.permissions = [];
    if(this.readP == true) this.permissions.push({permissionId : 1, name: "can_read_users"});
    if(this.createP == true) this.permissions.push({permissionId : 2, name: "can_create_users"});
    if(this.updateP == true) this.permissions.push({permissionId : 3, name: "can_update_users"});
    if(this.deleteP == true) this.permissions.push({permissionId : 4, name: "can_delete_users"});

    if(this.firstName != "")
    {
      this.message = "";
    }

    this.getService.addUser(this.firstName, this.lastName, this.email, this.password, this.permissions).subscribe(res =>{
    },
      error => {
      if(error)
        this.message = "All fields are required and email must be unique(maybe is already used)";
      })

    this.firstName = "";
    this.lastName = "";
    this.email = "";
    this.password = "";
    this.createP = false;
    this.deleteP = false;
    this.updateP = false;
    this.readP = false;
  }

}
