import { Component, OnInit } from '@angular/core';
import {Transaction} from "../shared/transaction.model";
import {TransactionService} from "../shared/transaction.service";
import {Router} from "@angular/router";

@Component({
  // moduleId: module.id,
  selector: 'app-transaction-list',
  templateUrl: './transaction-list.component.html',
  styleUrls: ['./transaction-list.component.css']
})
export class TransactionListComponent implements OnInit {
  errorMessage: string;
  transactions: Array<Transaction>;
  selectedTransaction: Transaction;

  constructor(private transactionService: TransactionService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getTransactions();
  }

  getTransactions() {
    this.transactionService.getTransactions()
      .subscribe(
        transactions => this.transactions = transactions,
        error => this.errorMessage = <any>error
      );
  }

  onSelect(transaction: Transaction): void {
    this.selectedTransaction = transaction;
  }

  gotoDetail(): void {
    this.router.navigate([`/transaction/detail/${this.selectedTransaction.customer.id}/${this.selectedTransaction.book.id}`]);
  }

  deleteTransaction(transaction: Transaction) {
    console.log("deleting transaction: ", transaction);

    this.transactionService.deleteTransaction(transaction.customer.id, transaction.book.id)
      .subscribe(_ => {
        console.log("transaction deleted");

        this.transactions = this.transactions
          .filter(t => t.customer.id !== transaction.customer.id && t.book.id != transaction.customer.id);
      });
  }
}
