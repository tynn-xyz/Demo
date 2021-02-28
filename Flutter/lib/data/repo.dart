import 'api.dart';
import 'model.dart';

class Repo {
  final Api _api;

  Repo({Api api}) : _api = api ?? Api();

  Stream<Currencies> getCurrencies() async* {
    yield await _api.getCurrencies();
  }

  Stream<Rates> getLatestRates(String from) async* {
    yield await _api.getLatest(from);
  }
}
