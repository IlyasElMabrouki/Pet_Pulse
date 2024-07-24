import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from './register/register.component';
import { ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './login/login.component';
import {HttpClient, HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import { HomeComponent } from './home/home.component';
import { NotAuthorizedComponent } from './not-authorized/not-authorized.component';
import { AdminComponent } from './admin/admin.component';
import { PetListComponent } from './pet-list/pet-list.component';
import { PetCardComponent } from './pet-card/pet-card.component';
import { PetFormComponent } from './pet-form/pet-form.component';
import { PetDetailsComponent } from './pet-details/pet-details.component';
import { PaginationComponent } from './pagination/pagination.component';
import { ChatbotModalComponent } from './chatbot-modal/chatbot-modal.component';
import { FormsModule } from '@angular/forms';
import {TranslateLoader, TranslateModule} from "@ngx-translate/core";
import {CustomTranslateLoader} from "./translate/translate-loader";
import { LanguageSwitcherComponent } from './language-switcher/language-switcher.component';
import {RouterModule} from "@angular/router";
import {LostReportsComponent} from "./lost-reports/lost-reports.component";
import {ReportDetailsComponent} from "./report-details/report-details.component";
import { MyReportsComponent } from './my-reports/my-reports.component';
import { ManageReportComponent } from './manage-report/manage-report.component';
import { ShowApplicationsComponent } from './show-applications/show-applications.component';
import { SimilarityComponent } from './similarity/similarity.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MatInputModule} from "@angular/material/input";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {MatCardModule} from "@angular/material/card";
import { NavbarComponent } from './navbar/navbar.component';
import { AppHttpInterceptor } from './services/app-http.interceptor';
import { AdoptComponent } from './adopt/adopt.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CardComponent } from './components/card/card.component';

import { MatSliderModule } from '@angular/material/slider';
import { MatSelectModule } from '@angular/material/select';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CommonModule } from '@angular/common';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { AddReportComponent } from './add-report/add-report.component';
import { AdoptFormComponent } from './adopt-form/adopt-form.component';
import { ApplyAdoptionComponent } from './apply-adoption/apply-adoption.component';
import { ApplyFindingComponent } from './apply-finding/apply-finding.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    NotAuthorizedComponent,
    AdminComponent,
    PetListComponent,
    PetCardComponent,
    PetFormComponent,
    PetDetailsComponent,
    PaginationComponent,
    ChatbotModalComponent,
    LanguageSwitcherComponent,
    LostReportsComponent,
    ReportDetailsComponent,
    MyReportsComponent,
    ManageReportComponent,
    ShowApplicationsComponent,
    SimilarityComponent,
    NavbarComponent,
    LostReportsComponent,
    AdoptComponent,
    CardComponent,
    AddReportComponent,
    AdoptFormComponent,
    ApplyAdoptionComponent,
    ApplyFindingComponent
],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
    MatButtonModule,
    MatIconModule,
    MatInputModule,
    MatProgressBarModule,
    MatCardModule,
    FontAwesomeModule,
    MatSliderModule,
    MatIconModule,
    MatSelectModule,
    BrowserAnimationsModule,
    CommonModule,
    LeafletModule,

    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useClass: CustomTranslateLoader,
        deps: [HttpClient]
      }
    }),
  ],
  providers: [
    provideAnimationsAsync(),
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AppHttpInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
