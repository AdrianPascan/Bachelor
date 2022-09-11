import { Component, OnInit } from '@angular/core';
import {Location} from "@angular/common";
import {TransactionService} from "../shared/transaction.service";
import {Customer} from "../../customers/shared/customer.model";
import {Book} from "../../books/shared/book.model";
import {CustomerService} from "../../customers/shared/customer.service";
import {BookService} from "../../books/shared/book.service";
import {Transaction} from "../shared/transaction.model";

@Component({
  selector: 'app-transaction-new',
  templateUrl: './transaction-new.component.html',
  styleUrls: ['./transaction-new.component.css']
})
export class TransactionNewComponent implements OnInit {
  errorMessage: string;
  customers: Array<Customer>;
  selectedCustomer: Customer;
  books: Array<Book>;
  selectedBook: Book;

  constructor(private transactionService: TransactionService,
              private customerService: CustomerService,
              private bookService: BookService,
              private location: Location) { }

  ngOnInit(): void {
    this.getCustomers();
    this.getBooks();
  }

  getCustomers() {
    this.customerService.getCustomers()
      .subscribe(
        customers => this.customers = customers,
        error => this.errorMessage = <any>error
      );
  }

  getBooks() {
    this.bookService.getBooks()
      .subscribe(
        books => this.books = books,
        error => this.errorMessage = <any>error
      );
  }

  onSelectCustomer(customer: Customer): void {
    this.selectedCustomer = customer;
  }

  onSelectBook(book: Book): void {
    this.selectedBook = book;
  }

  saveTransaction(quantity: string) {
    console.log("saving transaction: ", this.selectedCustomer, this.selectedBook, quantity);

    this.transactionService.saveTransaction({
      customer: this.selectedCustomer,
      book: this.selectedBook,
      quantity: +quantity
    })
      .subscribe(transaction => console.log("saved transaction: ", transaction))

    this.location.back();
  }
}
