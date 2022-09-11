import 'package:app/common/CommonFrontend.dart';
import 'package:app/model/Rule.dart';
import 'package:app/server/MyServer.dart';
import 'package:flutter/material.dart';

class EmployeeLevel extends StatelessWidget {
  int level;
  List<Rule> rules;

  EmployeeLevel(this.level);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: _appBar(),
      body: _body(),
    );
  }

  AppBar _appBar() {
    return AppBar(
      title: const Text('Level'),
    );
  }

  Widget _body() {
    return FutureBuilder<List<Rule>>(
      future: MyServer.getLevelRules(level),

      builder: (context, snapshot) {
        if (snapshot.hasError) {
          CommonFrontend.showToast(context, snapshot.error.toString());
          return _errorView();
        }

        if (snapshot.hasData) {
          rules = snapshot.data;
          rules.sort(_compareFunction);

          if (rules.isEmpty) {
            return _emptyView();
          }

          return _listView();
        }

        return Center(child: CircularProgressIndicator());
      },
    );
  }

  Widget _errorView() {
    return Center(child: Text("No connection to the Internet..."));
  }

  Widget _emptyView() {
    return Center(child: Text("No rules for level $level"));
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
    );
  }

  int _compareFunction(Rule rule, Rule rule2) {
      if (rule.from < rule2.from)
        return -1;
      if (rule.from > rule2.from)
        return 1;
      if (rule.from == rule2.from) {
        if (rule.to < rule2.to)
          return -1;
        if (rule.to > rule2.to)
          return 1;
        if (rule.to == rule2.to) {
          return rule.status.compareTo(rule2.status);
        }
      }
      return 1;
  }
}
