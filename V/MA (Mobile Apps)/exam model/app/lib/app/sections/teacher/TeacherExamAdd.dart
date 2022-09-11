import 'package:app/common/CommonBackend.dart';
import 'package:app/database/MyDatabase.dart';
import 'package:app/model/Exam.dart';
import 'package:app/server/MyServer.dart';
import 'package:flutter/material.dart';

class TeacherExamAdd extends StatefulWidget {
  @override
  _TeacherExamAddState createState() => _TeacherExamAddState();
}

class _TeacherExamAddState extends State<TeacherExamAdd> {
  final nameController = TextEditingController();
  final groupController = TextEditingController();
  final detailsController = TextEditingController();
  final statusController = TextEditingController();
  final studentsController = TextEditingController();
  final typeController = TextEditingController();

  @override
  void dispose() {
    nameController.dispose();
    groupController.dispose();
    detailsController.dispose();
    statusController.dispose();
    studentsController.dispose();
    typeController.dispose();
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
      title: Text('Register Exam'),
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
          controller: groupController,
          decoration: InputDecoration(labelText: 'Group'),
        ),
        TextField(
          controller: detailsController,
          decoration: InputDecoration(labelText: 'Details'),
        ),
        TextField(
          controller: statusController,
          decoration: InputDecoration(labelText: 'Status'),
        ),
        TextField(
          controller: studentsController,
          decoration: InputDecoration(labelText: 'Students'),
        ),
        TextField(
          controller: typeController,
          decoration: InputDecoration(labelText: 'Type'),
        ),

        RaisedButton(onPressed: _onAddPressed, child: Text('Register'))
      ],
    );
  }

  void _onAddPressed() {
    CommonBackend.examMaxId += 1;
    Exam exam = Exam(CommonBackend.examMaxId, nameController.text, groupController.text, detailsController.text, statusController.text, int.parse(studentsController.text), typeController.text);
    MyDatabase.insertExam(exam);
    MyServer.postExam(exam);

    Navigator.of(context).pop();
  }
}
