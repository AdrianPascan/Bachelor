import 'package:app/app/sections/staff/StaffRuleDetails.dart';
import 'package:app/common/CommonBackend.dart';
import 'package:app/common/CommonFrontend.dart';
import 'package:app/database/MyDatabase.dart';
import 'package:app/model/Rule.dart';
import 'package:app/server/MyServer.dart';
import 'package:flutter/material.dart';

import 'StaffRuleAdd.dart';

class StaffView extends StatefulWidget {
  @override
  _StaffViewState createState() => _StaffViewState();
}

class _StaffViewState extends State<StaffView> {
  List<Rule> rules;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: _appBar(),
      body: _body(),
    );
  }

  AppBar _appBar() {
    return AppBar(
      title: const Text(CommonFrontend.staffLabel),
      actions: [
        IconButton(icon: Icon(Icons.add), onPressed: _onAddRulePressed),
      ],
    );
  }

  _onAddRulePressed() {
    Navigator.of(context).push(
        MaterialPageRoute<void>(
          builder: (BuildContext context) {
            return StaffRuleAdd();
          },
        )
    );
  }

  Widget _body() {
    return FutureBuilder<List<Rule>>(
      future: CommonBackend.rulesInDatabase ? MyDatabase.getRules() : MyServer.getRules(),

      builder: (context, snapshot) {
        if (snapshot.hasError) {
          CommonFrontend.showToast(context, snapshot.error.toString());
          return _errorView();
        }

        if (snapshot.hasData) {
          rules = snapshot.data;
          if (!CommonBackend.rulesInDatabase) {
            CommonBackend.setRuleMaxId(rules);


            MyDatabase.deleteRules();
            MyDatabase.insertRules(rules);
            CommonBackend.rulesInDatabase = true;
          }

          return _listView();
        }

        return Center(child: CircularProgressIndicator());
      },
    );
  }

  Widget _errorView() {
    return Center(child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Text("No connection to the Internet..."),

          RaisedButton(onPressed: () => setState(() => {}), child: Text("Retry"))
        ],
      )
    );
  }

  Widget _listView() {
    return ListView.builder(
        itemCount: rules.length,
        itemBuilder: _listItemBuilder
    );
  }

  Widget _listItemBuilder(BuildContext context, int index) {
    if (index > rules.length) {
      return null;
    }
    return _listItem(rules[index]);
  }

  Widget _listItem(Rule rule) {
    return ListTile(
      isThreeLine: true,
      title: Text('${rule.id}: ${rule.name.toUpperCase()}'),
      subtitle: Text('lvl. ${rule.level}, ${rule.status}, ${rule.from} -> ${rule.to}'),
      trailing: IconButton(icon: Icon(Icons.arrow_forward), onPressed: () => onDetailsPressed(rule)),
    );
  }

  void onDetailsPressed(Rule rule) {
    Navigator.of(context).push(
        MaterialPageRoute<void>(
          builder: (BuildContext context) {
            return StaffRuleDetails(rule.id);
          },
        )
    ).then((value) {
      setState(() {});
    });
  }
}
