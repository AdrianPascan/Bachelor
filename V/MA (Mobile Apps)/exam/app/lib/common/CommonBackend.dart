import 'dart:math';

import 'package:app/model/Rule.dart';

class CommonBackend {
  static bool rulesInDatabase = false;
  static int ruleMaxId = 0;

  static setRuleMaxId(List<Rule> rules) {
    ruleMaxId = rules
        .map((exam) => exam.id)
        .reduce(max);
  }
}