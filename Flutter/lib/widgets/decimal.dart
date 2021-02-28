import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class DecimalTextFormField extends TextFormField {
  static final _whitespace = FilteringTextInputFormatter.deny(RegExp(r'\s'));

  DecimalTextFormField({
    Key key,
    String initialValue,
    TextAlign textAlign = TextAlign.start,
    ValueChanged<double> onChanged,
  }) : super(
          key: key,
          initialValue: initialValue,
          textAlign: textAlign,
          onChanged: (value) {
            if (onChanged != null) {
              onChanged(double.tryParse(value) ?? 0);
            }
          },
          maxLines: 1,
          keyboardType: TextInputType.number,
          inputFormatters: [
            _whitespace,
            _DecimalTextInputFormatter(),
          ],
        );
}

class _DecimalTextInputFormatter extends TextInputFormatter {
  @override
  TextEditingValue formatEditUpdate(
      TextEditingValue oldValue, TextEditingValue newValue) {
    return _isDoubleOrEmpty(newValue.text.trim()) ? newValue : oldValue;
  }

  bool _isDoubleOrEmpty(String text) {
    return text.isEmpty || double.tryParse(text) != null;
  }
}
