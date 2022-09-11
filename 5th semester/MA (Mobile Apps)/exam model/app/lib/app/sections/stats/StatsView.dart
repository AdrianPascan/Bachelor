import 'package:app/common/CommonFrontend.dart';
import 'package:app/model/Exam.dart';
import 'package:app/server/MyServer.dart';
import 'package:flutter/material.dart';

class StatsView extends StatefulWidget {
  @override
  _StatsViewState createState() => _StatsViewState();
}

class _StatsViewState extends State<StatsView> {
  List<Exam> exams;
  String group = 'group';
  final groupController = TextEditingController();

  @override
  void dispose() {
    groupController.dispose();
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
      title: const Text(CommonFrontend.statsLabel),
    );
  }

  Widget _body() {
    return Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,

        children: [
          TextField(
            controller: groupController,
            decoration: InputDecoration(labelText: 'Group'),
          ),
          RaisedButton(onPressed: _onFilterPressed, child: Text('Filter')),

          Expanded(child: FutureBuilder<List<Exam>>(
            future: MyServer.getGroupExams(group),

            builder: (context, snapshot) {
              if (snapshot.hasError) {
                CommonFrontend.showToast(context, snapshot.error.toString());
                return _errorView();
              }

              if (snapshot.hasData) {
                exams = snapshot.data;
                exams.sort(_compareFunction);

                if (exams.isEmpty) {
                  return _emptyView();
                }


                return _listView();
              }

              return Center(child: CircularProgressIndicator());
            },
          ))
        ]
    );
  }

  int _compareFunction(Exam exam, Exam exam2) {
    int res = exam.type.compareTo(exam2.type);
    if (res < 0)
      return -1;
    if (res == 0) {
      if (exam.students >= exam2.students)
        return -1;
      if (exam.students == exam2.students)
        return 0;
      return 1;
    }
    return 1;
  }

  void _onFilterPressed() {
    setState(() {
      group = groupController.text;
    });
  }

  Widget _errorView() {
    return Center(child: Text("No connection to the Internet..."));
  }

  Widget _emptyView() {
    return Center(child: Text("No exams for group: $group"));
  }

  Widget _listView() {
    return ListView.builder(
        itemCount: exams.length,
        itemBuilder: _listItemBuilder,
        shrinkWrap: true,
    );
  }

  Widget _listItemBuilder(BuildContext context, int index) {
    if (index > exams.length) {
      return null;
    }
    return _listItem(exams[index]);
  }

  Widget _listItem(Exam exam) {
    return ListTile(
      isThreeLine: true,
      title: Text('${exam.id}: ${exam.name.toUpperCase()} (${exam.type}, ${exam.students} stud.)'),
      subtitle: Text('${exam.details}'),
    );
  }
}
