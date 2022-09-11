enum Gender  {
  MALE,
  FEMALE,
  UNISEX
}

extension GenderExtension on Gender {
  static const strings = {
    Gender.MALE: "men's",
    Gender.FEMALE: "women's",
    Gender.UNISEX: "unisex",
  };

  String get string => strings[this];
}