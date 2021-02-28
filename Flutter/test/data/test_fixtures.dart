import 'package:demo_flutter/data/model.dart';

final currencies = Currencies(map: {
  'EUR': "Euro",
  'GBP': "Pound",
});

final rates = Rates(base: 'EUR', date: DateTime.now().atStartOfDay(), map: {
  'GBP': 1.2,
});

final model = Model()
  ..from = 'EUR'
  ..to = 'GBP';

extension _Date on DateTime {
  DateTime atStartOfDay() {
    return DateTime(year, month, day);
  }
}
