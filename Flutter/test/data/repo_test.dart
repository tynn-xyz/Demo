import 'package:demo_flutter/data/repo.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mockito/mockito.dart';

import 'test_mocks.dart';

void main() {
  final api = MockApi();
  final repo = Repo(api: api);

  test('Delegate Api getCurrencies', () async {
    await repo.getCurrencies().toList();
    verify(api.getCurrencies());
  });

  test('Delegate Api getLatestRates', () async {
    final from = "EUR";
    await repo.getLatestRates(from).toList();
    verify(api.getLatest(from));
  });
}
