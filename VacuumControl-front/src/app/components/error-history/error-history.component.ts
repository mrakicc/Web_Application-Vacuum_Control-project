import { Component } from '@angular/core';
import {GetService} from "../../services/get.service";

@Component({
  selector: 'app-error-history',
  templateUrl: './error-history.component.html',
  styleUrls: ['./error-history.component.css']
})
export class ErrorHistoryComponent {
  userEmail:string = "";
  errorList:Array<any> = [];
  constructor(private getService : GetService) {
    this.userEmail = getService.getEmail();
    this.errorHistory();
  }

  errorHistory(){
    this.errorList = [];
    this.getService.errorMessage(this.userEmail).subscribe(res => {
      console.log(res.body);

      for(let i = 0; i < res.body.length; i++) {

        let message = {
          idMessage:res.body[i].id,
          vacuumId: res.body[i].vacuumId,
          date: res.body[i].date,
          operation: res.body[i].operation,
          message: res.body[i].message
        };

        this.errorList.push(message);
      }
    },
    );
  }

}
