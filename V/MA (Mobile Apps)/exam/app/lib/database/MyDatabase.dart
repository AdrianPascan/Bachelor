import 'dart:developer';

import 'package:app/model/Rule.dart';
import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart';

class MyDatabase {
  static Future<Database> _database;

  static void initialize() async {
    log('DATABASE: initialize');

    _database = openDatabase(
      join(await getDatabasesPath(), 'app.db'),
      onCreate: (db, version) async {
        return await db.execute(
            "CREATE TABLE rules(id INTEGER PRIMARY KEY, name TEXT, level INTEGER, status TEXT, mfrom INTEGER, mto INTEGER);");
      },
      version: 1,
    );
  }

  static Future<List<Rule>> getRules() async {
    log('DATABASE: getRules');

    final Database db = await _database;

    final List<Map<String, dynamic>> maps = await db.query('rules');

    return List.generate(maps.length, (i) {
      return Rule(
        maps[i]['id'],
        maps[i]['name'],
        maps[i]['level'],
        maps[i]['status'],
        maps[i]['mfrom'],
        maps[i]['mto'],
      );
    });
  }

  static void insertRule(Rule rule) async {
    log('DATABASE: insertRule');

    final Database db = await _database;

    await db.insert(
      'rules',
      rule.toMap(),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
  }

  static void insertRules(List<Rule> rules) {
    log('DATABASE: insertExams');

    for(Rule rule in rules) {
      insertRule(rule);
    }
  }

  static void updateRule(Rule rule) async {
    log('DATABASE: updateRule');

    final db = await _database;

    await db.update(
      'rules',
      rule.toMap(),
      where: "id = ?",
      whereArgs: [rule.id],
    );
  }
  
  static void deleteRules() async {
    log('DATABASE: deleteRules');

    final db = await _database;
    
    await db.execute('DELETE FROM rules');
  }
}