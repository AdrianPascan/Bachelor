import { Component, OnInit } from '@angular/core';
import {Customer} from "../shared/customer.model";
import {CustomerService} from "../shared/customer.service";

@Component({
  selector: 'app-customer-list',
  templateUrl: './customer-list.component.html',
  styleUrls: ['./customer-list.component.css']
})
export class CustomerListComponent implements OnInit {
  customers: Customer[];

  constructor(private customerService: CustomerService) { }

  ngOnInit(): void {
    this.customerService.getCustomers()
      .subscribe(customers => this.customers = customers);
  }

}
