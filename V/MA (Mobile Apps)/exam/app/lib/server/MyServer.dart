import 'dart:convert';
import 'dart:developer';

import 'package:app/model/Rule.dart';
import 'package:connectivity/connectivity.dart';
import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;


class MyServer {
  // // emulator
  // static const String baseURL = 'http://10.0.2.2:2019';
  // device
  static const String baseURL = 'http://192.168.8.102:2019';

  static const String ruleURL = baseURL + '/rule';
  static const String rulesURL = baseURL + '/rules';
  static const String updateURL = baseURL + '/update';
  static const String levelURL = baseURL + '/level';

  static List<Rule> _parseRules(String responseBody) {
    final parsed = jsonDecode(responseBody).cast<Map<String, dynamic>>();

    return parsed
        .map<Rule>((json) => Rule.fromJson(json))
        .toList();
  }

  static Future<List<Rule>> getRules() async {
    log('SERVER: getRules');

    final http.Response response = await http.get(rulesURL);

    if (response.statusCode == 200) {
        return compute(_parseRules, response.body);
    }

    throw Exception('Failed to load rules');
  }

  static Future<List<Rule>> getLevelRules(int level) async {
    log('SERVER: getLevelRules');

    final http.Response response = await http.get(levelURL + '/$level');

    if (response.statusCode == 200) {
      return compute(_parseRules, response.body);
    }

    throw Exception('Failed to load rules');
  }

  static Future<Rule> getRule(int id) async {
    log('SERVER: getRule');

    final http.Response response = await http.get(ruleURL + '/$id');

    if (response.statusCode == 200) {
      return Rule.fromJson(jsonDecode(response.body));
    }

    throw Exception('Failed to load rule');
  }
  
  static void postRule(Rule rule) {
    log('SERVER: postRule');

    http.post(
      ruleURL,
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(rule.toJson())
    );
  }

  static void postUpdate(Rule rule) {
    log('SERVER: postUpdate');

    http.post(
        updateURL,
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        },
        body: jsonEncode(rule.toJson())
    );
  }
}