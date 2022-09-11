import 'dart:async';

import 'package:footwear_shop/model/Brand.dart';
import 'package:footwear_shop/model/Gender.dart';
import 'package:footwear_shop/model/Operation.dart';
import 'package:footwear_shop/model/Product.dart';
import 'package:footwear_shop/model/Type.dart';
import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart';
import 'package:flutter/widgets.dart';


class SQLite {
  static Future<Database> _database;

  static Future<void> initialize() async {
    WidgetsFlutterBinding.ensureInitialized();
    _database = openDatabase(
      join(await getDatabasesPath(), 'footwear3.db'),

      onCreate: (db, version) async {
        await db.execute("CREATE TABLE operations(id INTEGER PRIMARY KEY AUTOINCREMENT, type TEXT, pid INTEGER, quantity INTEGER DEFAULT 0);");
        return await db.execute("CREATE TABLE products(id INTEGER PRIMARY KEY AUTOINCREMENT, brand TEXT, gender TEXT, type TEXT, price INTEGER, image TEXT, in_cart INTEGER DEFAULT 0, quantity INTEGER DEFAULT 0);");
      },

      version: 1,
    );
  }

  static Future<List<Product>> getProducts() async {
    final Database db = await _database;

    final List<Map<String, dynamic>> maps = await db.query('products');

    return List.generate(maps.length, (i) {
      return Product(
        pid: maps[i]['id'],
        brand: maps[i]['brand'],
        gender: maps[i]['gender'],
        type: maps[i]['type'],
        price: maps[i]['price'],
        image: maps[i]['image'],
        inCart: maps[i]['in_cart'],
        quantity: maps[i]['quantity'],
      );
    });
  }

  static Future<List<Product>> getProductsInCart() async {
    final Database db = await _database;

    final List<Map<String, dynamic>> maps = await db.query('products', where: 'in_cart = 1');

    return List.generate(maps.length, (i) {
      return Product(
        pid: maps[i]['id'],
        brand: maps[i]['brand'],
        gender: maps[i]['gender'],
        type: maps[i]['type'],
        price: maps[i]['price'],
        image: maps[i]['image'],
        inCart: maps[i]['in_cart'],
        quantity: maps[i]['quantity'],
      );
    });
  }

  static Future<List<Operation>> getOperations() async {
    final Database db = await _database;

    final List<Map<String, dynamic>> maps = await db.query('operations');

    return List.generate(maps.length, (i) {
      return Operation(
        id: maps[i]['id'],
        type: maps[i]['type'],
        pid: maps[i]['pid'],
        quantity: maps[i]['quantity'],
      );
    });
  }

  static Future<void> insertProduct(Product product) async {
    final Database db = await _database;

    await db.insert(
      'products',
      product.toMap(),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
  }

  static Future<void> insertOperation(Operation operation) async {
    final Database db = await _database;

    await db.insert(
      'operations',
      operation.toMap(false),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
  }

  static Future<void> insertProducts(List<Product> products) async {
    for(Product product in products) {
      insertProduct(product);
    }
  }

  static Future<void> updateProduct(Product product) async {
    final db = await _database;

    await db.update(
      'products',
      product.toMap(),
      where: "id = ?",
      whereArgs: [product.pid],
    );
  }

  static Future<void> deleteProduct(int id) async {
    final db = await _database;

    await db.delete(
      'products',
      where: "id = ?",
      whereArgs: [id],
    );
  }

  static Future<void> deleteOperation(int id) async {
    final db = await _database;

    await db.delete(
      'operations',
      where: "id = ?",
      whereArgs: [id],
    );
  }

  static Future<void> populate() async {
    Product product = Product(
        pid: 1,
        brand: Brand.DR_MARTENS.string,
        gender: Gender.UNISEX.string,
        type: Type.BOOTS.string,
        price: 189,
        image: 'dr_martens.jpg',
        inCart: 0,
        quantity: 0,
    );
    await insertProduct(product);

    Product product2 = Product(
        pid: 2,
        brand: Brand.LOUBOUTIN.string,
        gender: Gender.FEMALE.string,
        type: Type.HIGH_HEELS.string,
        price: 699,
        image: 'louboutin.png',
        inCart: 0,
        quantity: 0,
    );
    await insertProduct(product2);

    Product product3 = Product(
        pid: 3,
        brand: Brand.VALENTINO.string,
        gender: Gender.FEMALE.string,
        type: Type.HIGH_HEELS.string,
        price: 749,
        image: 'valentino.jpg',
        inCart: 0,
        quantity: 0,
    );
    await insertProduct(product3);
  }
}