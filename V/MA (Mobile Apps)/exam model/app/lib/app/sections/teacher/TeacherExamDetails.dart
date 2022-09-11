import 'package:app/common/CommonFrontend.dart';
import 'package:app/connectivity/ConnectivityService.dart';
import 'package:app/connectivity/ConnectivityStatus.dart';
import 'package:app/model/Exam.dart';
import 'package:app/server/MyServer.dart';

import 'package:flutter/material.dart';

class TeacherExamDetails extends StatelessWidget {
  int examId;

  TeacherExamDetails(this.examId);

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
    return FutureBuilder<Exam>(
      future: MyServer.getExam(examId),

      builder: (context, snapshot) {
        if (snapshot.hasError) {
          CommonFrontend.showToast(context, snapshot.error.toString());
          return Center(
            child: Text("No connection to the Internet..."),
          );
        }

        if (snapshot.hasData) {
          Exam exam = snapshot.data;

          return _view(exam);
        }

        return Center(child: CircularProgressIndicator());
      },
    );
  }

  Widget _view(Exam exam) {
    return Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          DataTable(
              columns: [
                DataColumn(label: Text('NAME')),
                DataColumn(label: Text('VALUE')),
              ],
              rows: [
                DataRow(cells: [
                  DataCell(Text('Id')),
                  DataCell(Text('${exam.id}')),
                ]),
                DataRow(cells: [
                  DataCell(Text('Name')),
                  DataCell(Text('${exam.name}')),
                ]),
                DataRow(cells: [
                  DataCell(Text('Group')),
                  DataCell(Text('${exam.group}')),
                ]),
                DataRow(cells: [
                  DataCell(Text('Details')),
                  DataCell(Text('${exam.details}')),
                ]),
                DataRow(cells: [
                  DataCell(Text('Status')),
                  DataCell(Text('${exam.status}')),
                ]),
                DataRow(cells: [
                  DataCell(Text('Type')),
                  DataCell(Text('${exam.type}')),
                ]),
              ]
          )
        ]
    );
  }
}
