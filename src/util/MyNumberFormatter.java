package util;

import javax.swing.*;
import java.text.ParseException;

public abstract class MyNumberFormatter extends JFormattedTextField.AbstractFormatter {
    String suffix;
    Number min, max;

    public MyNumberFormatter(String suffix, Number min, Number max) {
        this.suffix = suffix;
        this.min = min;
        this.max = max;
    }

    public static class Integer extends MyNumberFormatter {
        public Integer(String suffix, int min, int max) {
            super(suffix, min, max);
        }

        @Override
        public Object stringToValue(String text) throws ParseException {
            text = (text.endsWith(suffix)) ? text.substring(0, text.length() - suffix.length()) : text;

            try {
                int parse = java.lang.Integer.parseInt(text);
                if (parse < (java.lang.Integer) min || parse > (java.lang.Integer) max) throw new ParseException("", 0);
                return parse;
            } catch (Exception e) {throw new ParseException("", 0);}
        }

        @Override
        public String valueToString(Object value) {
            return value + suffix;
        }
    }

    public static class Double extends MyNumberFormatter {
        int precision;

        public Double(String suffix, double min, double max, int precision) {
            super(suffix, min, max);
            this.precision = precision;
        }

        @Override
        public Object stringToValue(String text) throws ParseException {
            text = (text.endsWith(suffix)) ? text.substring(0, text.length() - suffix.length()) : text;

            try {
                double parse = java.lang.Double.parseDouble(text);
                if (parse < (java.lang.Double) min || parse > (java.lang.Double) max) throw new ParseException("", 0);
                return parse;
            } catch (Exception e) {throw new ParseException("", 0);}
        }

        @Override
        public String valueToString(Object value) {
            String formatString = "%." + precision + "f";
            return String.format(formatString, value) + suffix;
        }
    }
}