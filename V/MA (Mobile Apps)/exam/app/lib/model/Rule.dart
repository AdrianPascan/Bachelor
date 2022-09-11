class Rule {
  int id;
  String name;
  int level;
  String status;
  int from;
  int to;

  Rule(this.id, this.name, this.level, this.status, this.from, this.to);

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'name': name,
      'level': level,
      'status': status,
      'mfrom': from,
      'mto': to,
    };
  }

  factory Rule.fromJson(Map<String, dynamic> json) {
    return Rule(
      json['id'] as int,
      json['name'] as String,
      json['level'] as int,
      json['status'] as String,
      json['from'] as int,
      json['to'] as int,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'level': level,
      'status': status,
      'from': from,
      'to': to,
    };
  }
}