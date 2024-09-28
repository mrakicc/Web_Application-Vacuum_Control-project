import { Component } from '@angular/core';
import {GetService} from "../../services/get.service";
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent {

  userId: number = 0;
  firstName: string = "";
  lastName: string = "";
  email: string = "";
  readP: boolean = false;
  createP: boolean = false;
  updateP: boolean = false;
  deleteP: boolean = false;
  password: string = "";
  permissions: any[] = [];
  message: string = "";

  constructor(private getService : GetService,private route: ActivatedRoute) {
    this.email = <string>this.route.snapshot.paramMap.get('email');
    this.fillData();
  }

  fillData(){
    this.getService.getUser(this.email).subscribe(result =>{
      this.userId = result.body.userId;
      this.firstName = result.body.name;
      this.lastName = result.body.last_name;
      this.email = result.body.email;
      this.permissions = result.body.permissions;
      this.password  = result.body.password;
      console.log(this.permissions);
      for(let i = 0 ; i< this.permissions.length; i++)
      {
        if(result.body.permissions[i].name == "can_read_users") this.readP = true;
        if(result.body.permissions[i].name == "can_create_users") this.createP = true;
        if(result.body.permissions[i].name == "can_update_users") this.updateP = true;
        if(result.body.permissions[i].name == "can_delete_users") this.deleteP = true;
      }
    })
  }

  editUser(){
    this.permissions = [];
    if(this.readP == true) this.permissions.push({permissionId : 1, name: "can_read_users"});
    if(this.createP == true) this.permissions.push({permissionId : 2, name: "can_create_users"});
    if(this.updateP == true) this.permissions.push({permissionId : 3, name: "can_update_users"});
    if(this.deleteP == true) this.permissions.push({permissionId : 4, name: "can_delete_users"});
    this.getService.editUser(this.userId, this.firstName, this.lastName, this.email, this.password, this.permissions).subscribe(res =>{

      },
      error => {
        if(error)
          this.message = "All fields are required and email must be unique(maybe is already used)";
      })

    this.fillData();
  }

}
