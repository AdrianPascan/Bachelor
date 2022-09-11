import { Component, OnInit } from '@angular/core';
import {CustomerService} from "../shared/customer.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-customer-new',
  templateUrl: './customer-new.component.html',
  styleUrls: ['./customer-new.component.css']
})
export class CustomerNewComponent implements OnInit {

  constructor(private customerService: CustomerService,
              private location: Location) {
  }

  ngOnInit(): void {
  }

  saveCustomer(firstName: string, lastName: string, age: string) {
    console.log("saving customer: ", firstName, lastName, age);

    this.customerService.saveCustomer({
      id: 0,
      firstName,
      lastName,
      age: +age
    })
      .subscribe(customer => console.log("saved customer: ", customer))

    this.location.back();
  }
}
