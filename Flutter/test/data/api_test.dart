import 'dart:convert';

import 'package:demo_flutter/data/api.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:http/http.dart';
import 'package:intl/intl.dart';
import 'package:mockito/mockito.dart';

import 'test_fixtures.dart';
import 'test_mocks.dart';

void main() {
  final client = MockClient();
  final api = Api(client: client);

  test('GET currencies', () async {
    when(client.get(Uri.parse('https://api.frankfurter.app/currencies')))
        .thenAnswer((_) async => Response(jsonEncode(currencies), 200));
    expect(await api.getCurrencies(), _equals(currencies));
  });

  test('GET latest from', () async {
    final from = rates.base;
    final json = {
      "base": from,
      "date": DateFormat('yyyy-MM-dd').format(rates.date),
      "rates": rates,
    };
    when(client.get(Uri.parse('https://api.frankfurter.app/latest?from=$from')))
        .thenAnswer((_) async => Response(jsonEncode(json), 200));
    expect(await api.getLatest(from), _equals(rates));
  });
}

Matcher _equals(expected) {
  return predicate((actual) => actual == expected, expected.toString());
}
