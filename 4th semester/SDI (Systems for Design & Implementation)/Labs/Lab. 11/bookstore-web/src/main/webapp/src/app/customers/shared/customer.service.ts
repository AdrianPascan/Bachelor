import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";

import {Observable} from "rxjs";
import {Customer} from "./customer.model";


@Injectable()
export class CustomerService {
  private customersUrl = 'http://localhost:8081/api/customers';

  constructor(private httpClient: HttpClient) {
  }

  getCustomers(): Observable<Customer[]> {
    return this.httpClient
      .get<Array<Customer>>(this.customersUrl);
  }
}
