import 'package:flutter/material.dart';

class Themes {
  Themes._();

  static final _primarySwatch = Colors.blueGrey;

  static final _inputDecoration = InputDecorationTheme(
    border: OutlineInputBorder(),
  );

  static final light = ThemeData(
    brightness: Brightness.light,
    primarySwatch: _primarySwatch,
    appBarTheme: const AppBarTheme(
      brightness: Brightness.dark,
    ),
    inputDecorationTheme: _inputDecoration,
  );

  static final dark = ThemeData(
    brightness: Brightness.dark,
    primarySwatch: _primarySwatch,
    inputDecorationTheme: _inputDecoration,
    // fix teal primary color in dark mode
    accentColor: light.accentColor,
    primaryColor: light.primaryColor,
    toggleableActiveColor: light.toggleableActiveColor,
  );
}
