import {Book} from "../../books/shared/book.model";
import {Customer} from "../../customers/shared/customer.model";

export class Transaction {
  customer: Customer;
  book: Book;
  quantity: number;

  constructor(customer: Customer, book: Book, quantity: number) {
    this.customer = customer;
    this.book = book;
    this.quantity = quantity;
  }
}
