import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CustomersComponent} from "./customers/customers.component";
import {BooksComponent} from "./books/books.component";
import {TransactionsComponent} from "./transactions/transactions.component";
import {CustomerNewComponent} from "./customers/customer-new/customer-new.component";
import {BookNewComponent} from "./books/book-new/book-new.component";
import {TransactionNewComponent} from "./transactions/transaction-new/transaction-new.component";
import {CustomerDetailComponent} from "./customers/customer-detail/customer-detail.component";
import {BookDetailComponent} from "./books/book-detail/book-detail.component";
import {TransactionDetailComponent} from "./transactions/transaction-detail/transaction-detail.component";


const routes: Routes = [
  {path: 'customers', component: CustomersComponent},
  {path: 'customer/new', component: CustomerNewComponent},
  {path: 'customer/detail/:id', component: CustomerDetailComponent},

  {path: 'books', component: BooksComponent},
  {path: 'book/new', component: BookNewComponent},
  {path: 'book/detail/:id', component: BookDetailComponent},

  {path: 'transactions', component: TransactionsComponent},
  {path: 'transaction/new', component: TransactionNewComponent},
  {path: 'transaction/detail/:customerId/:bookId', component: TransactionDetailComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
