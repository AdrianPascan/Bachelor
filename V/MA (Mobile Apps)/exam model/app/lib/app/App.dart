import 'package:app/app/sections/stats/StatsView.dart';
import 'package:app/app/sections/student/StudentView.dart';
import 'package:app/app/sections/teacher/TeacherView.dart';
import 'package:app/common/CommonFrontend.dart';
import 'package:flutter/material.dart';

class App extends StatefulWidget {
  @override
  _AppState createState() => _AppState();
}

class _AppState extends State<App> {
  int _selectedIndex = 0;
  static List<Widget> _widgetOptions = <Widget>[
    TeacherView(),
    StudentView(),
    StatsView(),
  ];

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _widgetOptions.elementAt(_selectedIndex),
      bottomNavigationBar: BottomNavigationBar(
        items: const <BottomNavigationBarItem>[
          BottomNavigationBarItem(
            icon: Icon(Icons.account_box),
            label: CommonFrontend.teacherLabel,
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.school),
            label: CommonFrontend.studentLabel,
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.bar_chart),
            label: CommonFrontend.statsLabel,
          ),
        ],
        currentIndex: _selectedIndex,
        onTap: _onItemTapped,
      ),
    );
  }
}
