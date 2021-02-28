import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:provider/provider.dart';

import '../data/model.dart';
import '../widgets/widgets.dart';

class RatesPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Consumer2<Currencies, Rates>(
      builder: (context, currencies, rates, child) {
        return Scaffold(
          appBar: AppBar(
            title: Text(currencies[rates.base]),
            actions: [
              IconButton(
                icon: Icon(
                  Icons.api_rounded,
                ),
                tooltip: AppLocalizations.of(context).apiTitle,
                onPressed: () {
                  Navigator.pushNamed(context, '/api');
                },
              ),
            ],
          ),
          body: SafeArea(
            child: Stack(
              children: [
                Align(
                  alignment: AlignmentDirectional.bottomEnd,
                  child: Padding(
                    padding: EdgeInsets.all(16.0),
                    child: DateText(date: rates.date),
                  ),
                ),
                ListView(
                  children: rates?.apply(currencies),
                ),
              ],
            ),
          ),
        );
      },
    );
  }
}

extension _AppliedRates on Rates {
  List<Widget> apply(Currencies currencies) {
    return entries.map((e) {
      return ListTile(
        title: Text(currencies[e.key]),
        subtitle: Text(e.value.toString()),
      );
    }).toList();
  }
}
