import 'package:app/common/CommonFrontend.dart';
import 'package:app/database/MyDatabase.dart';
import 'package:app/model/Exam.dart';
import 'package:app/server/MyServer.dart';
import 'package:flutter/material.dart';

class StudentView extends StatefulWidget {
  @override
  _StudentViewState createState() => _StudentViewState();
}

class _StudentViewState extends State<StudentView> {
  List<Exam> exams;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: _appBar(),
      body: _body(),
    );
  }

  AppBar _appBar() {
    return AppBar(
      title: const Text(CommonFrontend.studentLabel),
    );
  }

  Widget _body() {
    return FutureBuilder<List<Exam>>(
      future: MyServer.getDraftExams(),

      builder: (context, snapshot) {
        if (snapshot.hasError) {
          CommonFrontend.showToast(context, snapshot.error.toString());
          return _errorView();
        }

        if (snapshot.hasData) {
          exams = snapshot.data;
          return _listView();
        }

        return Center(child: CircularProgressIndicator());
      },
    );
  }

  Widget _errorView() {
    return Center(child: Text("No connection to the Internet..."));
  }

  Widget _listView() {
    return ListView.builder(
        itemCount: exams.length,
        itemBuilder: _listItemBuilder
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
      title: Text('${exam.id}: ${exam.name.toUpperCase()}'),
      subtitle: Text('${exam.group}, ${exam.type}'),
      trailing: IconButton(icon: Icon(Icons.add_box), onPressed: () => onAddPressed(exam)),
    );
  }

  void onAddPressed(Exam exam) {
    MyServer.postJoinExam(exam.id);

    exam.students += 1;
    MyDatabase.updateExam(exam);
  }
}
