import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:webview_flutter/webview_flutter.dart';

class ApiPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(AppLocalizations.of(context).apiTitle),
        actions: [
          IconButton(
            icon: Icon(
              Icons.info_rounded,
            ),
            tooltip: MaterialLocalizations.of(context).licensesPageTitle,
            onPressed: () {
              showLicensePage(context: context);
            },
          ),
        ],
      ),
      body: WebView(
        initialUrl: 'https://www.frankfurter.app/',
        javascriptMode: JavascriptMode.unrestricted,
      ),
    );
  }
}
