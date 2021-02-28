import 'package:demo_flutter/data/api.dart';
import 'package:demo_flutter/data/model.dart';
import 'package:demo_flutter/data/provider.dart';
import 'package:provider/provider.dart';

import 'test_fixtures.dart';

class FakeProviders implements Providers {
  @override
  List<InheritedProvider> list;

  FakeProviders._(Model model, Currencies currencies, Rates rates)
      : list = [
          ChangeNotifierProvider<Model>.value(value: model),
          Provider<Currencies>.value(value: currencies),
          Provider<Rates>.value(value: rates),
        ];

  FakeProviders.defaults() : this._(Model(), Currencies(), Rates());

  FakeProviders.fixtures() : this._(model, currencies, rates);
}
