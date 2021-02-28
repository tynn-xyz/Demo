import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:provider/provider.dart';

import 'data/provider.dart';
import 'pages/pages.dart';
import 'theme.dart';

void main() => runApp(App());

class App extends StatelessWidget {
  final Providers _providers;

  App({Providers providers}) : _providers = providers ?? Providers();

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: _providers.list,
      child: MaterialApp(
        onGenerateTitle: (context) => AppLocalizations.of(context).appTitle,
        localizationsDelegates: AppLocalizations.localizationsDelegates,
        supportedLocales: AppLocalizations.supportedLocales,
        theme: Themes.light,
        darkTheme: Themes.dark,
        initialRoute: '/',
        routes: {
          '/': (context) => RootPage(),
          '/api': (context) => ApiPage(),
          '/rates': (context) => RatesPage(),
        },
      ),
    );
  }
}
