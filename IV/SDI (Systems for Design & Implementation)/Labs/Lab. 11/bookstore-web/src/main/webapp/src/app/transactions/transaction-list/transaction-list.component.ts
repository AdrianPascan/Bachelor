import { Component, OnInit } from '@angular/core';
import {Transaction} from "../shared/transaction.model";
import {TransactionService} from "../shared/transaction.service";

@Component({
  selector: 'app-transaction-list',
  templateUrl: './transaction-list.component.html',
  styleUrls: ['./transaction-list.component.css']
})
export class TransactionListComponent implements OnInit {
  transactions: Transaction[];

  constructor(private transactionService: TransactionService) { }

  ngOnInit(): void {
    this.transactionService.getTransactions()
      .subscribe(transactions => this.transactions = transactions);
  }

}
