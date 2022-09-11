class Exam {
  int id;
  String name;
  String group;
  String details;
  String status;
  int students;
  String type;

  Exam(this.id, this.name, this.group, this.details, this.status, this.students,
      this.type);

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'name': name,
      'sgroup': group,
      'details': details,
      'status': status,
      'students': students,
      'type': type,
    };
  }

  factory Exam.fromJson(Map<String, dynamic> json) {
    return Exam(
      json['id'] as int,
      json['name'] as String,
      json['group'] as String,
      json['details'] as String,
      json['status'] as String,
      json['students'] as int,
      json['type'] as String,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'group': group,
      'details': details,
      'status': status,
      'students': students,
      'type': type,
    };
  }
}