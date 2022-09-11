import { Component, OnInit } from '@angular/core';
import {Customer} from "../shared/customer.model";
import {CustomerService} from "../shared/customer.service";
import {Router} from "@angular/router";

@Component({
  // moduleId: module.id,
  selector: 'app-customer-list',
  templateUrl: './customer-list.component.html',
  styleUrls: ['./customer-list.component.css']
})
export class CustomerListComponent implements OnInit {
  errorMessage: string;
  customers: Array<Customer>;
  selectedCustomer: Customer;

  constructor(private customerService: CustomerService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getCustomers();
  }

  getCustomers() {
    this.customerService.getCustomers()
      .subscribe(
        customers => this.customers = customers,
        error => this.errorMessage = <any>error
      );
  }

  onSelect(customer: Customer): void {
    this.selectedCustomer = customer;
  }

  gotoDetail(): void {
    this.router.navigate(['/customer/detail', this.selectedCustomer.id]);
  }

  deleteCustomer(customer: Customer) {
    console.log("deleting customer: ", customer);

    this.customerService.deleteCustomer(customer.id)
      .subscribe(_ => {
        console.log("customer deleted");

        this.customers = this.customers
          .filter(c => c.id !== customer.id);
      });
  }
}
