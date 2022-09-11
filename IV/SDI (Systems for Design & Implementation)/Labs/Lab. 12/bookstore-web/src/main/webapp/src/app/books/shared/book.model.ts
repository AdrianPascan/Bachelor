export class Book {
  id: number;
  isbn: string;
  title: string;
  author: string;
  price: number;

  constructor(id: number, isbn: string, title: string, author: string, price: number) {
    this.id = id;
    this.isbn = isbn;
    this.title = title;
    this.author = author;
    this.price = price;
  }
}
