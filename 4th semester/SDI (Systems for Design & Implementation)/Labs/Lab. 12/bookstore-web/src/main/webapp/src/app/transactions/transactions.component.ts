import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.css']
})
export class TransactionsComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  addNewTransaction() {
    console.log("addNewTransaction - method entered (button pressed)");
    this.router.navigate(['transaction/new']);
  }
}
