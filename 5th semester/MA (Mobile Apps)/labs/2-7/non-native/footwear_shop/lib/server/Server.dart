import 'dart:convert';
import 'dart:developer';
import 'dart:io';

import 'package:connectivity/connectivity.dart';
import 'package:flutter/foundation.dart';
import 'package:footwear_shop/model/Product.dart';
import 'package:http/http.dart' as http;


class Server {
  // // emulator
  // static const String URL = 'http://10.0.2.2:5000';
  // device
  static const String URL = 'http://192.168.8.102:5000';

  static Future<bool> hasConnection() async {
    ConnectivityResult connectivityResult = await Connectivity().checkConnectivity();
    return !(connectivityResult == ConnectivityResult.none);
  }

  static List<Product> _parseProducts(String responseBody) {
    final parsed = jsonDecode(responseBody).cast<Map<String, dynamic>>();

    return parsed
        .map<Product>((json) => Product.fromJson(json))
        .toList();
  }

  static Future<List<Product>> getProducts() async {
    log('SERVER: getRules');

    if (await hasConnection()) {
      final http.Response response = await http.get(URL + '/products');

      if (response.statusCode == 200) {
        return compute(_parseProducts, response.body);
      }
    }

    throw Exception('Failed to load products');
  }

  static Future<List<Product>> getProductsInCart() async {
    if (await hasConnection()) {
      final http.Response response = await http.get(URL + '/products/in_cart');

      if (response.statusCode == 200) {
        return compute(_parseProducts, response.body);
      }
    }

    throw Exception('Failed to load products in cart');
  }

  static Future<bool> addProductToCart(int pid) async {
    if (await hasConnection()) {
      final http.Response response = await http.get(URL + '/products/add/$pid');
      return true;
    }

    return false;
  }

  static Future<bool> deleteProductFromCart(int pid) async {
    if (await hasConnection()) {
      final http.Response response = await http.get(
          URL + '/products/delete/$pid');
      return true;
    }

    return false;
  }

  static Future<bool> updateProductFromCart(int pid, int newQuantity) async {
    if (await hasConnection()) {
      final http.Response response = await http.get(
          URL + '/products/update/$pid/$newQuantity');
      return true;
    }

    return false;
  }
}