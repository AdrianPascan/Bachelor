import 'package:app/app/sections/employee/EmployeeFromTo.dart';
import 'package:app/common/CommonFrontend.dart';
import 'package:app/database/MyDatabase.dart';
import 'package:app/model/Rule.dart';
import 'package:app/server/MyServer.dart';
import 'package:flutter/material.dart';

import 'EmployeeLevel.dart';

class EmployeeView extends StatefulWidget {
  @override
  _EmployeeViewState createState() => _EmployeeViewState();
}

class _EmployeeViewState extends State<EmployeeView> {
  List<Rule> exams;
  int from = 0;
  int to = 0;
  int level = 0;
  final fromController = TextEditingController();
  final toController = TextEditingController();
  final levelController = TextEditingController();

  @override
  void dispose() {
    fromController.dispose();
    toController.dispose();
    levelController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: _appBar(),
      body: _body(),
    );
  }

  AppBar _appBar() {
    return AppBar(
      title: const Text(CommonFrontend.employeeLabel),
    );
  }

  Widget _body() {
    return Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,

        children: [
          TextField(
            controller: fromController,
            decoration: InputDecoration(labelText: 'from'),
          ),
          TextField(
            controller: toController,
            decoration: InputDecoration(labelText: 'to'),
          ),
          RaisedButton(onPressed: _onFilterFromToPressed, child: Text('Filter from-to')),

          TextField(
            controller: levelController,
            decoration: InputDecoration(labelText: 'level'),
          ),
          RaisedButton(onPressed: _onFilterLevelPressed, child: Text('Filter level')),
        ]
    );
  }

  void _onFilterFromToPressed() {
    int from = int.parse(fromController.text);
    int to = int.parse(toController.text);

    Navigator.of(context).push(
        MaterialPageRoute<void>(
          builder: (BuildContext context) {
            return EmployeeFromTo(from, to);
          },
        )
    );
  }

  void _onFilterLevelPressed() {
    int level = int.parse(levelController.text);

    Navigator.of(context).push(
        MaterialPageRoute<void>(
          builder: (BuildContext context) {
            return EmployeeLevel(level);
          },
        )
    );
  }
}
