import 'package:collection/collection.dart';
import 'package:flutter/foundation.dart';
import 'package:intl/intl.dart';
import 'package:shared_preferences/shared_preferences.dart';

class Model extends ChangeNotifier {
  static const _fromKey = 'model.from';
  static const _toKey = 'model.to';

  SharedPreferences _prefs;

  double _amount = 1;
  String _from;
  String _to;

  double get amount => _amount;

  String get from => _from;

  String get to => _to;

  set amount(double value) {
    if (value != _amount) {
      _amount = value;
      notifyListeners();
    }
  }

  set from(String value) {
    if (value != _from) {
      if (value == _to) {
        _to = _from;
      }
      _from = value;
      notifyListeners();
      _prefs?.setString(_fromKey, value);
    }
  }

  set to(String value) {
    if (value != _to) {
      if (value == _from) {
        _from = _to;
      }
      _to = value;
      notifyListeners();
      _prefs?.setString(_toKey, value);
    }
  }

  set prefs(SharedPreferences prefs) {
    if (prefs == null) return;
    _prefs = prefs;
    _from = prefs.getString(_fromKey) ?? _from ?? 'EUR';
    _to = prefs.getString(_toKey) ?? _to ?? 'GBP';
    notifyListeners();
  }
}

class Currencies extends _DelegatingCurrencyMap<String> {
  Currencies({
    Map<String, String> map = const {},
  }) : super(map);

  factory Currencies.fromJson(Map<String, dynamic> json) {
    return Currencies(
      map: json.asMap(),
    );
  }
}

final _formatter = DateFormat('yyyy-MM-dd');

class Rates extends _DelegatingCurrencyMap<num> {
  final String base;
  final DateTime date;

  Rates({
    this.base,
    this.date,
    Map<String, num> map = const {},
  }) : super(map);

  factory Rates.fromJson(Map<String, dynamic> json) {
    return Rates(
      base: json['base'],
      date: _formatter.parse(json['date']),
      map: json.getAsMap('rates'),
    );
  }

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is Rates &&
          runtimeType == other.runtimeType &&
          base == other.base &&
          date == other.date &&
          super == other;

  @override
  int get hashCode => base.hashCode ^ date.hashCode ^ super.hashCode;

  @override
  String toString() {
    return 'Rates{base: $base, date: $date, ${super.toString()}}';
  }
}

class _DelegatingCurrencyMap<T> extends DelegatingMap<String, T> {
  _DelegatingCurrencyMap(Map<String, T> map) : super(map);

  @override
  bool operator ==(Object other) =>
      identical(this, other) ||
      other is _DelegatingCurrencyMap &&
          runtimeType == other.runtimeType &&
          mapEquals(this, other);
}

extension _JsonMap on Map<String, dynamic> {
  Map<String, T> asMap<T>() {
    return map((key, value) => MapEntry(key, value));
  }

  Map<String, T> getAsMap<T>(String key) {
    return (this[key] as Map<String, dynamic>).asMap();
  }
}
