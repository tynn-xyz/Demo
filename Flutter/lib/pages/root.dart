import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:provider/provider.dart';

import '../data/model.dart';
import '../widgets/widgets.dart';

class RootPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    final l10n = AppLocalizations.of(context);
    return Scaffold(
      appBar: AppBar(
        title: Text(l10n.appTitle),
        actions: [
          Selector<Rates, bool>(
            selector: (context, rates) => rates?.base == null,
            builder: (context, isDisabled, _) {
              return IconButton(
                icon: Icon(
                  Icons.details_rounded,
                ),
                tooltip: l10n.ratesTitle,
                onPressed: isDisabled
                    ? null
                    : () {
                        Navigator.pushNamed(context, "/rates");
                      },
              );
            },
          ),
          IconButton(
            icon: Icon(
              Icons.api_rounded,
            ),
            tooltip: l10n.apiTitle,
            onPressed: () {
              Navigator.pushNamed(context, '/api');
            },
          ),
        ],
      ),
      body: Padding(
        padding: EdgeInsets.all(16.0),
        child: CurrenciesProxyProvider(
          children: [
            Selector<Model, double>(
              selector: (context, model) => model.amount,
              builder: (context, value, _) {
                return DecimalTextFormField(
                  initialValue: value.toString(),
                  textAlign: TextAlign.center,
                  onChanged: (value) {
                    debugPrint('value 1 ' + value.toString());
                    context.read<Model>().amount = value;
                  },
                );
              },
            ),
            CurrenciesDropdownButtonFormField(
              selector: (model) => model.from,
              provider: (model, value) => model.from = value,
            ),
            Center(
              child: Selector2<Model, Rates, double>(
                selector: (context, model, rates) => rates?.apply(model) ?? 0.0,
                builder: (context, amount, _) {
                  return Text(
                    amount.toString(),
                    style: Theme.of(context).textTheme.headline4,
                  );
                },
              ),
            ),
            CurrenciesDropdownButtonFormField(
              selector: (model) => model.to,
              provider: (model, value) => model.to = value,
            ),
            Align(
              alignment: AlignmentDirectional.bottomEnd,
              child: Selector<Rates, DateTime>(
                selector: (context, rates) => rates?.date,
                builder: (context, date, _) {
                  return DateText(date: date);
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}

extension _AppliedRates on Rates {
  double apply(Model model) {
    final rate = this[model.to];
    if (rate == null) {
      return null;
    }
    return model.amount * rate;
  }
}
