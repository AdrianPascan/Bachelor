import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";

import {Observable} from "rxjs";
import {Book} from "./book.model";
import {map} from "rxjs/operators";


@Injectable()
export class BookService {
  private booksUrl = 'http://localhost:8080/api/books';

  constructor(private httpClient: HttpClient) {
  }

  getBooks(): Observable<Book[]> {
    return this.httpClient
      .get<Array<Book>>(this.booksUrl);
  }

  getBook(id: number): Observable<Book> {
    console.log(id);
    return this.getBooks()
      .pipe(
        map(books => books.find(book => book.id === id))
      );
  }

  saveBook(book: Book): Observable<Book> {
    console.log("saveBook", book);

    return this.httpClient
      .post<Book>(this.booksUrl, book);
  }

  updateBook(book): Observable<Book> {
    const url = `${this.booksUrl}/${book.id}`;
    return this.httpClient
      .put<Book>(url, book);
  }

  deleteBook(id: number): Observable<any> {
    const url = `${this.booksUrl}/${id}`;
    return this.httpClient
      .delete(url);
  }
}
