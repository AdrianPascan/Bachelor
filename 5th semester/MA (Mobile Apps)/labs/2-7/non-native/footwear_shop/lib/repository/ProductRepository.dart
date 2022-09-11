import 'package:footwear_shop/model/Product.dart';

class ProductRepository {
  List<Product> products = List();

  int total() {
    return products.isEmpty ?
    0 :
    products
        .map((product) => product.subtotal())
        .reduce((total, subtotal) => total += subtotal);
  }
}