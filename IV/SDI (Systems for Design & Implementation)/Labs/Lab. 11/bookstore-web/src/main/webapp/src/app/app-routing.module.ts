import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {CustomersComponent} from "./customers/customers.component";
import {BooksComponent} from "./books/books.component";
import {TransactionsComponent} from "./transactions/transactions.component";


const routes: Routes = [
  {path: 'customers', component: CustomersComponent},
  {path: 'books', component: BooksComponent},
  {path: 'transactions', component: TransactionsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
