import {Component, Inject, LOCALE_ID} from '@angular/core';
import {GetService} from "../../services/get.service";
import {formatDate} from "@angular/common";

@Component({
  selector: 'app-search-vacuums',
  templateUrl: './search-vacuums.component.html',
  styleUrls: ['./search-vacuums.component.css']
})
export class SearchVacuumsComponent {
  userEmail:string = "";
  name: string = "";
  status: string = "";
  dateFrom: string = "";
  dateTo: string = "";
  showStart: boolean = false;
  showStop:boolean = false;
  showDischarge:boolean = false;
  showDelete: boolean = false;
  allVacuums: Array<any> = [];
  dateSchedule : string = "";
  localID:string;

  constructor(private getService : GetService, @Inject( LOCALE_ID ) localID: string) {
    this.localID = localID;
    this.userEmail = getService.getEmail();
    this.search();
    this.getPermission();
  }

  search(){
    this.allVacuums = [];
    if(this.dateFrom.length == 0 || this.dateTo.length == 0)
    {
      this.dateFrom = "null"
      this.dateTo = "null";
    }
    if(this.name.length == 0)
    {
      this.name = "null";
    }
    if(this.status.length == 0)
    {
      this.status = "null";
    }

    this.getService.searchVacuum(this.userEmail,this.name,this.status, this.dateFrom.toString(), this.dateTo.toString()
    ).subscribe(res =>{
      for(let i = 0; i < res.body.length; i++){

        let vacuum  ={
          id : res.body[i].id,
          name : res.body[i].name,
          dateAdded : res.body[i].dateAdded,
          status : res.body[i].status,
          active : res.body[i].active
        };

        this.allVacuums.push(vacuum);
        this.name="";
      }
    }, error => {
      console.log("ERRRRRRRRR");
      }
    );
  }

  start(id:number)
  {
    this.getService.startVacuum(this.userEmail,id).subscribe(res => {
      console.log(res.status);
    },
     error => {
      console.log("ERRRR START");
     }
      )
  }

  stop(id:number){
  this.getService.stopVacuum(this.userEmail,id).subscribe(res =>{

  },
    error => {
    console.log("ERRR STOP");
    })
  }

  discharge(id:number){
    this.getService.dischargeVacuum(this.userEmail,id).subscribe(() =>{
        console.log(200);
      },
      error => {
        console.log("ERRR DISCH");
      })
  }

  delete(id:number){
    this.getService.deleteVacuum(this.userEmail,id).subscribe(res =>{

      },
      error => {
        console.log("ERRR DELETE");
      })
  }

  getPermission():void{
    this.showStart = false;
    this.showStop = false;
    this.showDischarge = false;
    this.showDelete = false;
    var value = JSON.parse(localStorage.getItem("permissions")!);
    if(value != null){
      for(let i=0; i < value.length; i++) {
        if(value[i].name == "can_start_vacuum") this.showStart = true;
        if(value[i].name == "can_stop_vacuum") this.showStop = true;
        if(value[i].name == "can_discharge_vacuum") this.showDischarge = true;
        if(value[i].name == "can_remove_vacuum") this.showDelete = true;
      }
    }
  }

  scheduleStart(id:number) {
    this.getService.scheduleStartVacuum(this.userEmail, id, this.dateSchedule).subscribe(res => {
      console.log(res);
    });
  }

  scheduleStop(id:number) {
    this.getService.scheduleStopVacuum(this.userEmail, id, this.dateSchedule).subscribe(res => {
      console.log(res);
    });
  }

  scheduleDischarge(id:number) {
    this.getService.scheduleDischargeVacuum(this.userEmail, id, this.dateSchedule).subscribe(res => {
      console.log(res);
    });
  }
}
