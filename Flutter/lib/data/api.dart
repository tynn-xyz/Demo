import 'dart:convert';

import 'package:http/http.dart';

import 'model.dart';

class Api {
  Client _client;

  Api({Client client}) : _client = client ?? Client();

  Future<Currencies> getCurrencies() async {
    final response = await _get('/currencies');
    return Currencies.fromJson(response.toJson());
  }

  Future<Rates> getLatest(String from) async {
    final response = await _get('/latest', {'from': from});
    return Rates.fromJson(response.toJson());
  }

  Future<Response> _get(String path, [Map<String, dynamic> query]) {
    return _client.get(Uri.https('api.frankfurter.app', path, query));
  }
}

extension _ValidateResponse on Response {
  Map<String, dynamic> toJson() {
    if (statusCode != 200) {
      throw Exception('Failed to load data');
    }
    return jsonDecode(body);
  }
}
