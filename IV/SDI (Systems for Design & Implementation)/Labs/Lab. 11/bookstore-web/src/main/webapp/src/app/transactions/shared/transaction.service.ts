import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";

import {Observable} from "rxjs";
import {Transaction} from "./transaction.model";


@Injectable()
export class TransactionService {
  private transactionsUrl = 'http://localhost:8081/api/transactions';

  constructor(private httpClient: HttpClient) {
  }

  getTransactions(): Observable<Transaction[]> {
    return this.httpClient
      .get<Array<Transaction>>(this.transactionsUrl);
  }
}
