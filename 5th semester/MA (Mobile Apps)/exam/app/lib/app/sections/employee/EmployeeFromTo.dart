import 'package:app/common/CommonFrontend.dart';
import 'package:app/model/Rule.dart';
import 'package:app/server/MyServer.dart';
import 'package:flutter/material.dart';

class EmployeeFromTo extends StatelessWidget {
  int from;
  int to;
  List<Rule> rules;

  EmployeeFromTo(this.from, this.to);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: _appBar(),
      body: _body(),
    );
  }

  AppBar _appBar() {
    return AppBar(
      title: const Text('From To'),
    );
  }

  Widget _body() {
    return FutureBuilder<List<Rule>>(
      future: MyServer.getRules(),

      builder: (context, snapshot) {
        if (snapshot.hasError) {
          CommonFrontend.showToast(context, snapshot.error.toString());
          return _errorView();
        }

        if (snapshot.hasData) {
          rules = snapshot.data.where((rule) => rule.from >= from && rule.to <= to).toList();

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
    return Center(child: Text("No rules from $from to $to"));
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
}
