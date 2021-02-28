import 'package:demo_flutter/main.dart';
import 'package:demo_flutter/pages/pages.dart';
import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';

import 'data/test_fakes.dart';
import 'data/test_fixtures.dart';

void main() {
  testWidgets('Defaults navigation smoke test', (WidgetTester tester) async {
    await tester.pumpWidget(App(providers: FakeProviders.defaults()));
    expect(find.byType(RootPage), findsOneWidget);

    await tester.tapIconAndPumpAndSettle(Icons.details_rounded);
    expect(find.byType(RatesPage), findsNothing);
    expect(find.byIcon(Icons.info_rounded), findsNothing);
    await tester.tapIconAndPumpAndSettle(Icons.api_rounded);
    expect(find.byType(ApiPage), findsOneWidget);

    expect(find.byIcon(Icons.api_rounded), findsNothing);
    expect(find.byIcon(Icons.details_rounded), findsNothing);
    await tester.tapIconAndPumpAndSettle(Icons.info_rounded);
    expect(find.byType(LicensePage), findsOneWidget);
  });

  testWidgets('Fixtures navigation smoke test', (WidgetTester tester) async {
    await tester.pumpWidget(App(providers: FakeProviders.fixtures()));
    expect(find.byType(RootPage), findsOneWidget);

    expect(find.byIcon(Icons.api_rounded), findsOneWidget);
    expect(find.byIcon(Icons.info_rounded), findsNothing);
    await tester.tapIconAndPumpAndSettle(Icons.details_rounded);
    expect(find.byType(RatesPage), findsOneWidget);
    expect(find.text(currencies[rates.base]), findsOneWidget);
    expect(find.text(currencies[rates.entries.first.key]), findsOneWidget);
    expect(find.text(rates.entries.first.value.toString()), findsOneWidget);

    expect(find.byIcon(Icons.info_rounded), findsNothing);
    expect(find.byIcon(Icons.details_rounded), findsNothing);
    await tester.tapIconAndPumpAndSettle(Icons.api_rounded);
    expect(find.byType(ApiPage), findsOneWidget);

    expect(find.byIcon(Icons.api_rounded), findsNothing);
    expect(find.byIcon(Icons.details_rounded), findsNothing);
    await tester.tapIconAndPumpAndSettle(Icons.info_rounded);
    expect(find.byType(LicensePage), findsOneWidget);
  });
}

extension _AppTester on WidgetTester {
  Future<void> tapIconAndPumpAndSettle(IconData icon) async {
    await tap(find.byIcon(icon));
    await pumpAndSettle();
  }
}
