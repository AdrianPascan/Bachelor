class Operation {
  int id;
  String type;
  int pid;
  int quantity;

  Operation({this.id, this.type, this.pid, this.quantity});

  Map<String, dynamic> toMap(bool withId) {
    Map<String, dynamic> map = {
      'type': type,
      'pid': pid,
      'quantity': quantity,
    };
    if(withId) {
      map['id'] = id;
    }
    return map;
  }
}