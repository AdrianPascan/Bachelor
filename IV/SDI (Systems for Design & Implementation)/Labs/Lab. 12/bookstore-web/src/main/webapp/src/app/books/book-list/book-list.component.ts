import { Component, OnInit } from '@angular/core';
import {Book} from "../shared/book.model";
import {BookService} from "../shared/book.service";
import {Router} from "@angular/router";

@Component({
  // moduleId: module.id,
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {
  errorMessage: string;
  books: Array<Book>;
  selectedBook: Book;

  constructor(private bookService: BookService,
              private router: Router) { }

  ngOnInit(): void {
    this.getBooks();
  }

  getBooks() {
    this.bookService.getBooks()
      .subscribe(
        books => this.books = books,
        error => this.errorMessage = <any>error
      );
  }

  onSelect(book: Book): void {
    this.selectedBook = book;
  }

  gotoDetail(): void {
    this.router.navigate(['/book/detail', this.selectedBook.id]);
  }

  deleteBook(book: Book) {
    console.log("deleting book: ", book);

    this.bookService.deleteBook(book.id)
      .subscribe(_ => {
        console.log("book deleted");

        this.books = this.books
          .filter(b => b.id !== book.id);
      });
  }
}
