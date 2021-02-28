import 'package:flutter_stream_notifiers/flutter_stream_notifiers.dart';
import 'package:provider/provider.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:stream_transform/stream_transform.dart';

import 'model.dart';
import 'repo.dart';

class Providers {
  final Repo _repo;

  Providers({Repo repo}) : _repo = repo ?? Repo();

  List<InheritedProvider> get list {
    return [
      _createSharedPreferencesProvider(),
      _createModelProxyProvider(),
      _createCurrenciesProvider(),
      _createFromProxyProvider(),
      _createRatesProvider(),
    ];
  }

  FutureProvider<SharedPreferences> _createSharedPreferencesProvider() {
    return FutureProvider(
      create: (context) => SharedPreferences.getInstance(),
    );
  }

  ChangeNotifierProxyProvider<SharedPreferences, Model>
      _createModelProxyProvider() {
    return ChangeNotifierProxyProvider(
      create: (context) => Model(),
      update: (context, prefs, model) {
        return model..prefs = prefs;
      },
    );
  }

  StreamProvider<Currencies> _createCurrenciesProvider() {
    return StreamProvider(
      initialData: Currencies(),
      create: (context) => _repo.getCurrencies(),
    );
  }

  StreamProvider<Rates> _createRatesProvider() {
    return StreamProvider(
      create: (context) {
        // fake a StreamProxyProvider
        return context.read<_FromProxy>().stream.switchMap((proxy) {
          return _repo.getLatestRates(proxy.from);
        });
      },
    );
  }

  ChangeNotifierProxyProvider<Model, _FromProxy> _createFromProxyProvider() {
    return ChangeNotifierProxyProvider(
      create: (context) => _FromProxy(),
      update: (context, model, stream) {
        return stream..updateFrom(model.from);
      },
    );
  }
}

class _FromProxy extends ChangeStreamNotifier<_FromProxy> {
  String from;

  _FromProxy();

  void updateFrom(String from) {
    if (this.from != from) {
      this.from = from;
      notifyListeners();
    }
  }
}
