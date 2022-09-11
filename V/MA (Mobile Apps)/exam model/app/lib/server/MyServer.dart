import 'dart:convert';
import 'dart:developer';

import 'package:app/model/Exam.dart';
import 'package:connectivity/connectivity.dart';
import 'package:flutter/foundation.dart';
import 'package:http/http.dart' as http;


class MyServer {
  // // emulator
  // static const String baseURL = 'http://10.0.2.2:3000';
  // device
  static const String baseURL = 'http://192.168.8.102:3000';

  static const String examURL = baseURL + '/exam';
  static const String examsURL = baseURL + '/exams';
  static const String draftExamsURL = baseURL + '/draft';
  static const String joinExamURL = baseURL + '/join';
  static const String groupExamsURL = baseURL + '/group';

  static List<Exam> _parseExams(String responseBody) {
    final parsed = jsonDecode(responseBody).cast<Map<String, dynamic>>();

    return parsed
        .map<Exam>((json) => Exam.fromJson(json))
        .toList();
  }

  static Future<List<Exam>> getExams() async {
    log('SERVER: getExams');

    final http.Response response = await http.get(examsURL);

    if (response.statusCode == 200) {
        return compute(_parseExams, response.body);
    }

    throw Exception('Failed to load exams');
  }

  static Future<List<Exam>> getDraftExams() async {
    log('SERVER: getDraftExams');

    final http.Response response = await http.get(draftExamsURL);

    if (response.statusCode == 200) {
      return compute(_parseExams, response.body);
    }

    throw Exception('Failed to load draft exams');
  }

  static Future<List<Exam>> getGroupExams(String group) async {
    log('SERVER: getGroupExams');

    final http.Response response = await http.get(groupExamsURL + '/$group');

    if (response.statusCode == 200) {
      return compute(_parseExams, response.body);
    }

    throw Exception('Failed to load group exams');
  }

  static Future<Exam> getExam(int id) async {
    log('SERVER: getExam');

    final http.Response response = await http.get(examURL + '/$id');

    if (response.statusCode == 200) {
      return Exam.fromJson(jsonDecode(response.body));
    }

    throw Exception('Failed to load exam');
  }
  
  static void postExam(Exam exam) {
    log('SERVER: postExam');

    http.post(
      examURL,
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: jsonEncode(exam.toJson())
    );
  }

  static void postJoinExam(int id) {
    log('SERVER: postJoinExam');

    http.post(
        joinExamURL,
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        },
        body: jsonEncode(<String, dynamic> {
          'id': id,
        })
    );
  }
}