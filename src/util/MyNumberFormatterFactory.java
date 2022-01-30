package util;

import javax.swing.*;

public abstract class MyNumberFormatterFactory extends JFormattedTextField.AbstractFormatterFactory {
    JFormattedTextField.AbstractFormatter formatter;

    @Override
    public JFormattedTextField.AbstractFormatter getFormatter(JFormattedTextField tf) {
        return formatter;
    }

    public static class Integer extends MyNumberFormatterFactory {
        public Integer(String suffix, int min, int max) {
            formatter = new MyNumberFormatter.Integer(suffix, min, max);
        }
    }

    public static class Double extends MyNumberFormatterFactory {
        public Double(String suffix, double min, double max, int precision) {
            formatter = new MyNumberFormatter.Double(suffix, min, max, precision);
        }
    }
}
