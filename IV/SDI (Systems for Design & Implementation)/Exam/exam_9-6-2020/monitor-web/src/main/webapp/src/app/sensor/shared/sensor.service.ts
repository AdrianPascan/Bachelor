import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";

import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {Sensor} from "./sensor.model";


@Injectable()
export class SensorService {
  private sensorsUrl = 'http://localhost:8080/api/sensors';

  constructor(private httpClient: HttpClient) {
  }

  getSensors(): Observable<Sensor[]> {
    return this.httpClient
      .get<Array<Sensor>>(this.sensorsUrl);
  }

  // getSensorsNames(): Observable<string[]> {
  //   return this.httpClient
  //     .get<Array<string>>(this.sensorsUrl + "/names");
  // }
}
