import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:intl/intl.dart';

class DateText extends StatefulWidget {
  final DateTime date;

  const DateText({Key key, @required this.date}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _DateTextState();
  }
}

class _DateTextState extends State<DateText> with WidgetsBindingObserver {
  DateFormat _format;

  _DateTextState() : _format = _createFormat(Platform.localeName);

  @override
  void didChangeLocales(List<Locale> locales) {
    setState(() => _format = _createFormat(locales.first.toString()));
  }

  @override
  void initState() {
    super.initState();
    WidgetsBinding.instance.addObserver(this);
  }

  @override
  void dispose() {
    WidgetsBinding.instance.removeObserver(this);
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Text(
      widget.date?.format(_format) ?? '',
      style: Theme.of(context).textTheme.subtitle1,
    );
  }

  static DateFormat _createFormat(String locale) {
    return DateFormat.yMd(locale);
  }
}

extension _LocalDate on DateTime {
  String format(DateFormat format) {
    return format.format(this);
  }
}
