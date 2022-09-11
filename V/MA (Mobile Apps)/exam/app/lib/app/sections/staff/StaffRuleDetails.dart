import 'package:app/common/CommonBackend.dart';
import 'package:app/common/CommonFrontend.dart';
import 'package:app/database/MyDatabase.dart';
import 'package:app/model/Rule.dart';
import 'package:app/server/MyServer.dart';

import 'package:flutter/material.dart';

class StaffRuleDetails extends StatefulWidget {
  int ruleId;

  StaffRuleDetails(this.ruleId);

  @override
  _StaffRuleDetailsState createState() => _StaffRuleDetailsState(ruleId);
}

class _StaffRuleDetailsState extends State<StaffRuleDetails> {
  int ruleId;
  final nameController = TextEditingController();
  final levelController = TextEditingController();
  final statusController = TextEditingController();
  final fromController = TextEditingController();
  final toController = TextEditingController();

  _StaffRuleDetailsState(this.ruleId);

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
      title: Text('Details'),
    );
  }

  Widget _body() {
    return FutureBuilder<Rule>(
      future: MyServer.getRule(ruleId),

      builder: (context, snapshot) {
        if (snapshot.hasError) {
          CommonFrontend.showToast(context, snapshot.error.toString());
          return Center(
            child: Text("No connection to the Internet..."),
          );
        }

        if (snapshot.hasData) {
          Rule rule = snapshot.data;
          _set(rule);

          return _view(rule);
        }

        return Center(child: CircularProgressIndicator());
      },
    );
  }

  void _set(Rule rule) {
    nameController.text = rule.name;
    levelController.text = rule.level.toString();
    statusController.text = rule.status.toString();
    fromController.text = rule.from.toString();
    toController.text = rule.to.toString();
  }

  Widget _view(Rule rule) {
    return Column(
      children: [
        Text('ID: ${rule.id}'),
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

        RaisedButton(onPressed: () => _onUpdatePressed(rule), child: Text('Update'))
      ],
    );
  }

  void _onUpdatePressed(Rule rule) {
    rule.name = nameController.text;
    rule.level = int.parse(levelController.text);
    rule.status = statusController.text;
    rule.from = int.parse(fromController.text);
    rule.to = int.parse(toController.text);

    MyDatabase.updateRule(rule);
    MyServer.postUpdate(rule);

    Navigator.of(context).pop();
  }
}

