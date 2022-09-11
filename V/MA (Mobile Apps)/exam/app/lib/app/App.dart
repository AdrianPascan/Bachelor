import 'package:app/app/sections/employee/EmployeeView.dart';
import 'package:app/app/sections/staff/StaffView.dart';
import 'package:app/common/CommonFrontend.dart';
import 'package:flutter/material.dart';

class App extends StatefulWidget {
  @override
  _AppState createState() => _AppState();
}

class _AppState extends State<App> {
  int _selectedIndex = 0;
  static List<Widget> _widgetOptions = <Widget>[
    StaffView(),
    EmployeeView(),
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
            icon: Icon(Icons.account_balance),
            label: CommonFrontend.staffLabel,
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.account_box),
            label: CommonFrontend.employeeLabel,
          ),
        ],
        currentIndex: _selectedIndex,
        onTap: _onItemTapped,
      ),
    );
  }
}
