import 'dart:developer';

import 'package:app/model/Exam.dart';
import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart';

class MyDatabase {
  static Future<Database> _database;

  static void initialize() async {
    log('DATABASE: initialize');

    _database = openDatabase(
      join(await getDatabasesPath(), 'exams.db'),
      onCreate: (db, version) async {
        return await db.execute(
            "CREATE TABLE exams(id INTEGER PRIMARY KEY, name TEXT, sgroup TEXT, details TEXT, status TEXT, students INTEGER DEFAULT 0, type TEXT);");
      },
      version: 1,
    );
  }

  static Future<List<Exam>> getExams() async {
    log('DATABASE: getExams');

    final Database db = await _database;

    final List<Map<String, dynamic>> maps = await db.query('exams');

    return List.generate(maps.length, (i) {
      return Exam(
        maps[i]['id'],
        maps[i]['name'],
        maps[i]['sgroup'],
        maps[i]['details'],
        maps[i]['status'],
        maps[i]['students'],
        maps[i]['type'],
      );
    });
  }

  static void insertExam(Exam exam) async {
    log('DATABASE: insertExam');

    final Database db = await _database;

    await db.insert(
      'exams',
      exam.toMap(),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
  }

  static void insertExams(List<Exam> exams) {
    log('DATABASE: insertExams');

    for(Exam exam in exams) {
      insertExam(exam);
    }
  }

  static void updateExam(Exam exam) async {
    log('DATABASE: updateExam');

    final db = await _database;

    await db.update(
      'exams',
      exam.toMap(),
      where: "id = ?",
      whereArgs: [exam.id],
    );
  }
  
  static void deleteExams() async {
    log('DATABASE: deleteExams');

    final db = await _database;
    
    await db.execute('DELETE FROM exams');
  }
}