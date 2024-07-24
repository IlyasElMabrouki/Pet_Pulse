import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { NotAuthorizedComponent } from './not-authorized/not-authorized.component';

import {PetListComponent} from "./pet-list/pet-list.component";
import {PetFormComponent} from "./pet-form/pet-form.component";
import {PetDetailsComponent} from "./pet-details/pet-details.component";
import {AdminComponent} from "./admin/admin.component";
import {AuthenticationGuard} from "./guards/authentication.guard";
import {authorizationGuard} from "./guards/authorization.guard";
import {LostReportsComponent} from "./lost-reports/lost-reports.component";
import {ReportDetailsComponent} from "./report-details/report-details.component";
import {MyReportsComponent} from "./my-reports/my-reports.component";
import {ManageReportComponent} from "./manage-report/manage-report.component";
import {ShowApplicationsComponent} from "./show-applications/show-applications.component";
import {SimilarityComponent} from "./similarity/similarity.component";
import { AdoptComponent } from './adopt/adopt.component';
import {AddReportComponent} from "./add-report/add-report.component";
import { AdoptFormComponent } from './adopt-form/adopt-form.component';
import { ApplyAdoptionComponent } from './apply-adoption/apply-adoption.component';
import {ApplyFindingComponent} from "./apply-finding/apply-finding.component";

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'notAuthorized', component: NotAuthorizedComponent },
  { path: 'lost-reports/:id', component: ReportDetailsComponent },
  { path: 'admin', component: AdminComponent, canActivate: [AuthenticationGuard,authorizationGuard] },
  { path: 'home', component: HomeComponent, canActivate: [AuthenticationGuard]},
  { path: 'pets', component: PetListComponent},
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'pet-details/:id', component: PetDetailsComponent },
  {path:'lost-reports',component:LostReportsComponent},
  {path:'my-reports',component:MyReportsComponent},
  {path:'my-reports/:id',component:ManageReportComponent},
  {path:'show-applications/:id',component:ShowApplicationsComponent},
  {path:'similarity',component:SimilarityComponent},
  { path: 'new-pet', component: PetFormComponent, canActivate: [AuthenticationGuard]},
  { path: 'pet-details/:id', component: PetDetailsComponent, canActivate: [AuthenticationGuard] },
  { path: 'adopt', component: AdoptComponent },
  {path:'add-report',component:AddReportComponent},
  { path: 'adopt-form', component: AdoptFormComponent, canActivate: [AuthenticationGuard]},
  { path: 'apply-finding/:id', component: ApplyFindingComponent},
  { path: 'apply-for-adoption/:id', component: ApplyAdoptionComponent, canActivate: [AuthenticationGuard] }
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
