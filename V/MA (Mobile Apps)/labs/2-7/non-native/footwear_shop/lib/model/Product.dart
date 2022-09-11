

class Product {
   int pid;
   String brand;
   String gender;
   String type;
   int price;
   String image;
   int inCart;
   int quantity;

   Product({this.pid, this.brand, this.gender, this.type, this.price, this.image, this.inCart, this.quantity});

   int subtotal() => price * quantity;

   Map<String, dynamic> toMap() {
      return {
        'id': pid,
        'brand': brand,
        'gender': gender,
        'type': type,
        'price': price,
        'image': image,
        'in_cart': inCart,
        'quantity': quantity
      };
   }

   factory Product.fromJson(Map<String, dynamic> json) {
     return Product(
       pid: json['pid'] as int,
       brand: json['brand'] as String,
       gender: json['gender'] as String,
       type: json['type'] as String,
       price: json['price'] as int,
       image: json['image'] as String,
       inCart: json['in_cart'] as int,
       quantity: json['quantity'] as int,
     );
   }

   @override
   bool operator ==(Object other) =>
      identical(this, other) ||
      other is Product &&
          runtimeType == other.runtimeType &&
          brand == other.brand &&
          gender == other.gender &&
          type == other.type &&
          price == other.price &&
          image == other.image;

   @override
   int get hashCode =>
      brand.hashCode ^
      gender.hashCode ^
      type.hashCode ^
      price.hashCode ^
      image.hashCode;

   @override
   String toString() {
     return 'Product{id: $pid, brand: $brand, gender: $gender, type: $type, price: $price, image: $image, inCart: $inCart, quantity: $quantity}';
   }
}
