import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SensorComponent } from './sensor/sensor.component';
import { AllComponent } from './sensor/all/all.component';
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {SensorService} from "./sensor/shared/sensor.service";

@NgModule({
  declarations: [
    AppComponent,
    SensorComponent,
    AllComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [SensorService],
  bootstrap: [AppComponent]
})
export class AppModule { }
