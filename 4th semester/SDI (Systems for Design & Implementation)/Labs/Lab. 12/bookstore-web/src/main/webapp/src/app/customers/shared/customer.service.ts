import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";

import {Observable} from "rxjs";
import {Customer} from "./customer.model";
import {map} from "rxjs/operators";


@Injectable()
export class CustomerService {
  private customersUrl = 'http://localhost:8080/api/customers';

  constructor(private httpClient: HttpClient) {
  }

  getCustomers(): Observable<Customer[]> {
    return this.httpClient
      .get<Array<Customer>>(this.customersUrl);
  }

  getCustomer(id: number): Observable<Customer> {
    return this.getCustomers()
      .pipe(
        map(customers => customers.find(customer => customer.id === id))
      );
  }

  saveCustomer(customer: Customer): Observable<Customer> {
    console.log("saveCustomer", customer);

    return this.httpClient
      .post<Customer>(this.customersUrl, customer);
  }

  updateCustomer(customer): Observable<Customer> {
    const url = `${this.customersUrl}/${customer.id}`;
    return this.httpClient
      .put<Customer>(url, customer);
  }

  deleteCustomer(id: number): Observable<any> {
    const url = `${this.customersUrl}/${id}`;
    return this.httpClient
      .delete(url);
  }
}
