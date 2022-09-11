import 'dart:async';

import 'package:connectivity/connectivity.dart';

import 'ConnectivityStatus.dart';

class ConnectivityService {
  static ConnectivityStatus connectivityStatus;

  static void start() async {
    connectivityStatus = _getStatusFromResult(await Connectivity().checkConnectivity());

    Connectivity().onConnectivityChanged.listen((ConnectivityResult result) {
      connectivityStatus = _getStatusFromResult(result);
    });
  }

  // Convert from the third part enum to our own enum
  static ConnectivityStatus _getStatusFromResult(ConnectivityResult result) {
    switch (result) {
      case ConnectivityResult.mobile:
        return ConnectivityStatus.Cellular;
      case ConnectivityResult.wifi:
        return ConnectivityStatus.WiFi;
      case ConnectivityResult.none:
        return ConnectivityStatus.Offline;
      default:
        return ConnectivityStatus.Offline;
    }
  }
}