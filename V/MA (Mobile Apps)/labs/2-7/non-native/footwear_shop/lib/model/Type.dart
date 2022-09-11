enum Type {
  BOOTS,
  HIGH_HEELS
}

extension TypeExtension on Type {
  static const strings = {
    Type.BOOTS: 'boots',
    Type.HIGH_HEELS: 'high heels'
  };

  String get string => strings[this];
}