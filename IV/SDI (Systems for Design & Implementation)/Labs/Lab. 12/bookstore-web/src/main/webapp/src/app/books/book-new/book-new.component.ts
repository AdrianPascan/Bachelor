import { Component, OnInit } from '@angular/core';
import {BookService} from "../shared/book.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-book-new',
  templateUrl: './book-new.component.html',
  styleUrls: ['./book-new.component.css']
})
export class BookNewComponent implements OnInit {

  constructor(private bookService: BookService,
              private location: Location) { }

  ngOnInit(): void {
  }

  saveBook(isbn: string, title: string, author: string, price: string) {
    console.log("saving book", isbn, title, author, price);

    this.bookService.saveBook({
      id: 0,
      isbn,
      title,
      author,
      price: +price
    })
      .subscribe(book => console.log("saved book: ", book));

    this.location.back();
  }
}
