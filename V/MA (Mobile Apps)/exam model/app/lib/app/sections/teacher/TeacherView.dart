import 'package:app/app/sections/teacher/TeacherExamAdd.dart';
import 'package:app/app/sections/teacher/TeacherExamDetails.dart';
import 'package:app/common/CommonBackend.dart';
import 'package:app/common/CommonFrontend.dart';
import 'package:app/database/MyDatabase.dart';
import 'package:app/model/Exam.dart';
import 'package:app/server/MyServer.dart';
import 'package:flutter/material.dart';
import 'package:sqflite/sqflite.dart';

class TeacherView extends StatefulWidget {
  @override
  _TeacherViewState createState() => _TeacherViewState();
}

class _TeacherViewState extends State<TeacherView> {
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
      title: const Text(CommonFrontend.teacherLabel),
      actions: [
        IconButton(icon: Icon(Icons.add), onPressed: _onAddExamPressed),
      ],
    );
  }

  _onAddExamPressed() {
    Navigator.of(context).push(
        MaterialPageRoute<void>(
          builder: (BuildContext context) {
            return TeacherExamAdd();
          },
        )
    );
  }

  Widget _body() {
    return FutureBuilder<List<Exam>>(
      future: CommonBackend.examsInDatabase ? MyDatabase.getExams() : MyServer.getExams(),

      builder: (context, snapshot) {
        if (snapshot.hasError) {
          CommonFrontend.showToast(context, snapshot.error.toString());
          return _errorView();
        }

        if (snapshot.hasData) {
          exams = snapshot.data;
          if (!CommonBackend.examsInDatabase) {
            CommonBackend.setExamMaxId(exams);
            // print("COMMON FRONTEND: MAX_ID = ${CommonBackend.examMaxId}");


            MyDatabase.deleteExams();
            MyDatabase.insertExams(exams);
            CommonBackend.examsInDatabase = true;
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
      trailing: IconButton(icon: Icon(Icons.arrow_forward), onPressed: () => onDetailsPressed(exam)),
    );
  }

  void onDetailsPressed(Exam exam) {
    Navigator.of(context).push(
        MaterialPageRoute<void>(
          builder: (BuildContext context) {
            return TeacherExamDetails(exam.id);
          },
        )
    );
  }
}
