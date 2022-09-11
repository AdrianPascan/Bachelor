import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";

import {Observable} from "rxjs";
import {Transaction} from "./transaction.model";
import {map} from "rxjs/operators";


@Injectable()
export class TransactionService {
  private transactionsUrl = 'http://localhost:8080/api/transactions';

  constructor(private httpClient: HttpClient) {
  }

  getTransactions(): Observable<Transaction[]> {
    return this.httpClient
      .get<Array<Transaction>>(this.transactionsUrl);
  }

  getTransaction(customerId: number, bookId: number): Observable<Transaction> {
    console.log(customerId, bookId);
    return this.getTransactions()
      .pipe(
        map(transactions => transactions.find(transaction => transaction.customer.id === customerId && transaction.book.id === bookId))
      );
  }

  saveTransaction(transaction: Transaction): Observable<Transaction> {
    console.log("saveTransaction", transaction);

    return this.httpClient
      .post<Transaction>(this.transactionsUrl, transaction);
  }

  updateTransaction(transaction: Transaction): Observable<Transaction> {
    const url = `${this.transactionsUrl}/${transaction.customer.id}/${transaction.book.id}`;
    return this.httpClient
      .put<Transaction>(url, transaction);
  }

  deleteTransaction(customerId: number, bookId: number): Observable<any> {
    const url = `${this.transactionsUrl}/${customerId}/${bookId}`;
    return this.httpClient
      .delete(url);
  }
}
