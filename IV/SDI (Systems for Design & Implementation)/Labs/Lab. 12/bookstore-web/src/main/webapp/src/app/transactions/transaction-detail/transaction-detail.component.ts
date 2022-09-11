import {Component, Input, OnInit} from '@angular/core';
import {Transaction} from "../shared/transaction.model";
import {TransactionService} from "../shared/transaction.service";
import {ActivatedRoute, Params} from "@angular/router";
import {Location} from "@angular/common";
import {switchMap} from "rxjs/operators";

@Component({
  selector: 'app-transaction-detail',
  templateUrl: './transaction-detail.component.html',
  styleUrls: ['./transaction-detail.component.css']
})
export class TransactionDetailComponent implements OnInit {

  @Input() transaction: Transaction;

  constructor(private transactionService: TransactionService,
              private route: ActivatedRoute,
              private location: Location) {
  }

  ngOnInit(): void {
    this.route.params
      .pipe(switchMap((params: Params) => this.transactionService.getTransaction(+params['customerId'], +params['bookId'])))
      .subscribe(transaction => { this.transaction = transaction; console.log(transaction)});
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.transactionService.updateTransaction(this.transaction)
      .subscribe(_ => this.goBack());
  }
}
