import 'dart:math';

import 'package:app/model/Exam.dart';

class CommonBackend {
  static bool examsInDatabase = false;
  static int examMaxId = 0;

  static setExamMaxId(List<Exam> exams) {
    examMaxId = exams
        .map((exam) => exam.id)
        .reduce(max);
  }
}