import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../data/model.dart';

class CurrenciesDropdownButtonFormField extends StatelessWidget {
  final String Function(Model) _selector;
  final Function(Model, String) _provider;

  const CurrenciesDropdownButtonFormField({
    Key key,
    @required String Function(Model) selector,
    @required Function(Model, String) provider,
  })  : _selector = selector,
        _provider = provider,
        super(key: key);

  @override
  Widget build(BuildContext context) {
    return Consumer<List<DropdownMenuItem>>(
      builder: (context, items, child) {
        return Selector<Model, String>(
          selector: (context, model) => _selector(model),
          builder: (context, value, child) {
            return DropdownButtonFormField(
              value: value,
              items: items,
              onChanged: (value) => _provider(context.read<Model>(), value),
            );
          },
        );
      },
    );
  }
}

class CurrenciesProxyProvider extends StatelessWidget {
  final List<Widget> _children;

  const CurrenciesProxyProvider({Key key, List<Widget> children = const []})
      : _children = children,
        super(key: key);

  @override
  Widget build(BuildContext context) {
    return ProxyProvider<Currencies, List<DropdownMenuItem>>(
      update: (context, currencies, __) {
        return currencies.entries.map(_createItem).toList();
      },
      child: Wrap(
        spacing: 8.0,
        runSpacing: 8.0,
        children: _children,
      ),
    );
  }

  static DropdownMenuItem _createItem(MapEntry e) {
    return DropdownMenuItem(
      value: e.key,
      child: Center(
        child: Text(e.value),
      ),
    );
  }
}
