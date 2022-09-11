import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import {FormsModule} from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CustomersComponent } from './customers/customers.component';
import { BooksComponent } from './books/books.component';
import { TransactionsComponent } from './transactions/transactions.component';
import { BookListComponent } from './books/book-list/book-list.component';
import { CustomerListComponent } from './customers/customer-list/customer-list.component';
import { TransactionListComponent } from './transactions/transaction-list/transaction-list.component';
import {CustomerService} from "./customers/shared/customer.service";
import {BookService} from "./books/shared/book.service";
import {TransactionService} from "./transactions/shared/transaction.service";
import { CustomerNewComponent } from './customers/customer-new/customer-new.component';
import { TransactionNewComponent } from './transactions/transaction-new/transaction-new.component';
import { BookNewComponent } from './books/book-new/book-new.component';
import { BookDetailComponent } from './books/book-detail/book-detail.component';
import { CustomerDetailComponent } from './customers/customer-detail/customer-detail.component';
import { TransactionDetailComponent } from './transactions/transaction-detail/transaction-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    CustomersComponent,
    BooksComponent,
    TransactionsComponent,
    BookListComponent,
    CustomerListComponent,
    TransactionListComponent,
    CustomerNewComponent,
    TransactionNewComponent,
    BookNewComponent,
    BookDetailComponent,
    CustomerDetailComponent,
    TransactionDetailComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [CustomerService, BookService, TransactionService],
  bootstrap: [AppComponent]
})
export class AppModule { }
