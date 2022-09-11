import 'package:app/common/CommonBackend.dart';
import 'package:app/database/MyDatabase.dart';
import 'package:app/model/Rule.dart';
import 'package:app/server/MyServer.dart';
import 'package:flutter/material.dart';

class StaffRuleAdd extends StatefulWidget {
  @override
  _StaffRuleAddState createState() => _StaffRuleAddState();
}

class _StaffRuleAddState extends State<StaffRuleAdd> {
  final nameController = TextEditingController();
  final levelController = TextEditingController();
  final statusController = TextEditingController();
  final fromController = TextEditingController();
  final toController = TextEditingController();

  @override
  void dispose() {
    nameController.dispose();
    levelController.dispose();
    statusController.dispose();
    fromController.dispose();
    toController.dispose();
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
      title: Text('Register Rule'),
    );
  }

  Widget _body() {
    return Column(
      children: [
        TextField(
          controller: nameController,
          decoration: InputDecoration(labelText: 'Name'),
        ),
        TextField(
          controller: levelController,
          decoration: InputDecoration(labelText: 'Level'),
        ),
        TextField(
          controller: statusController,
          decoration: InputDecoration(labelText: 'Status'),
        ),
        TextField(
          controller: fromController,
          decoration: InputDecoration(labelText: 'From'),
        ),
        TextField(
          controller: toController,
          decoration: InputDecoration(labelText: 'To'),
        ),

        RaisedButton(onPressed: _onAddPressed, child: Text('Register'))
      ],
    );
  }

  void _onAddPressed() {
    CommonBackend.ruleMaxId += 1;
    Rule rule = Rule(CommonBackend.ruleMaxId, nameController.text, int.parse(levelController.text), statusController.text, int.parse(fromController.text), int.parse(toController.text));
    MyDatabase.insertRule(rule);
    MyServer.postRule(rule);

    Navigator.of(context).pop();
  }
}
