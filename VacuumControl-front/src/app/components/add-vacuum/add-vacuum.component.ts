import { Component } from '@angular/core';
import {GetService} from "../../services/get.service";

@Component({
  selector: 'app-add-vacuum',
  templateUrl: './add-vacuum.component.html',
  styleUrls: ['./add-vacuum.component.css']
})
export class AddVacuumComponent {

  name: string = "";
  userEmail: string = "";
  message: string = "";
  messageField: string = "";

  constructor(private getService : GetService) {
    this.userEmail = getService.getEmail();
  }

  addVacuum(){

    if(this.name != "")
    {
      this.message = "";
    }

    this.getService.addVacuum(this.userEmail, this.name).subscribe(res =>{
      },
      error => {
        if(error)
          this.message = "Name is required must be unique(maybe is already used)";
      })

    this.name = "";
  }

}
