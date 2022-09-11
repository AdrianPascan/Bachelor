import { Component, OnInit } from '@angular/core';
import {SensorService} from "../shared/sensor.service";
import {Sensor} from "../shared/sensor.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-all',
  templateUrl: './all.component.html',
  styleUrls: ['./all.component.css']
})
export class AllComponent implements OnInit {
  sensors: Array<Sensor>;
  error: string;

  constructor(private sensorService: SensorService,
              private router: Router) {
  }

  ngOnInit(): void {
    console.log("init:");
    this.getSensors();
    console.log("sensors:");
    console.log(this.sensors);
  }

  getSensors() {
    this.sensorService.getSensors()
      .subscribe(
        sensors => {this.sensors = sensors; console.log("sensors", sensors); },
        error => {this.error = error; console.log("error", error); }
      );
  }
}
