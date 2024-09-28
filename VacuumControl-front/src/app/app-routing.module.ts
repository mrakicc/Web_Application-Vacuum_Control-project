import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {AddUserComponent} from "./components/add-user/add-user.component";
import {EditUserComponent} from "./components/edit-user/edit-user.component";
import {AllUsersComponent} from "./components/all-users/all-users.component";
import {authGuard} from "../guards/auth.guard";
import {readGuard} from "../guards/read.guard";
import {createGuard} from "../guards/create.guard";
import {updateGuard} from "../guards/update.guard";
import {SearchVacuumsComponent} from "./components/search-vacuums/search-vacuums.component";
import {ErrorHistoryComponent} from "./components/error-history/error-history.component";
import {AddVacuumComponent} from "./components/add-vacuum/add-vacuum.component";
import {searchVacuumGuard} from "../guards/search-vacuum.guard";
import {addVacuumGuard} from "../guards/add-vacuum.guard";

const routes: Routes = [

  {
    path: "",
    component: LoginComponent,
    canDeactivate: [authGuard],
  },
  {
    path: "all",
    component: AllUsersComponent,
    canActivate:[readGuard]
  },
  {
    path: "add",
    component: AddUserComponent,
    canActivate:[createGuard]
  },
  {
    path: "edit/:email",
    component: EditUserComponent,
    canActivate:[updateGuard]
  },
  {
    path: "searchVacuum",
    component: SearchVacuumsComponent,
    canActivate:[searchVacuumGuard]
  },
  {
    path: "addVacuum",
    component: AddVacuumComponent,
    canActivate:[addVacuumGuard]
  },
  {
    path: "errorHistory",
    component: ErrorHistoryComponent,
    //canActivate:[updateGuard]
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
