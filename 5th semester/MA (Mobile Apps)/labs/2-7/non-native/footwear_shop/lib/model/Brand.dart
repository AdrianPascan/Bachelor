enum Brand {
  DR_MARTENS,
  LOUBOUTIN,
  VALENTINO
}

extension BrandExtension on Brand {
  static const strings = {
    Brand.DR_MARTENS: 'Dr. Martens',
    Brand.LOUBOUTIN: 'Christian Louboutin',
    Brand.VALENTINO: 'Valentino Garavani',
  };

  String get string => strings[this];
}