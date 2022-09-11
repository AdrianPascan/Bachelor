import {Component, Input, OnInit} from '@angular/core';
import {Book} from "../shared/book.model";
import {ActivatedRoute, Params} from "@angular/router";
import {Location} from "@angular/common";
import {BookService} from "../shared/book.service";
import {switchMap} from "rxjs/operators";

@Component({
  selector: 'app-book-detail',
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.css']
})
export class BookDetailComponent implements OnInit {

  @Input() book: Book;

  constructor(private bookService: BookService,
              private route: ActivatedRoute,
              private location: Location) { }

  ngOnInit(): void {
    this.route.params
      .pipe(switchMap((params: Params) => this.bookService.getBook(+params['id'])))
      .subscribe(book => this.book = book);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.bookService.updateBook(this.book)
      .subscribe(_ => this.goBack());
  }
}
