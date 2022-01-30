package frame.main;

import frame.MyContainable;
import frame.MyFonts;
import frame.MyFrameTools;
import pattern.MyMath;
import util.MyNumberFormatterFactory;

import javax.swing.*;
import java.awt.*;

public abstract class MySliderPanel extends JPanel implements MyContainable {
    JLabel label;
    JSpinner spinner;
    JSlider slider;

    String suffix;
    Number min, max;

    public MySliderPanel(String labelText, String suffix, Number min, Number max) {
        label = new JLabel(labelText);
        this.suffix = suffix;
        this.min = min;
        this.max = max;
    }

    @Override
    public void createComponents() {}

    @Override
    public void addComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.EAST;
        add(MyFrameTools.addHorizontally(null, false, label, spinner), constraints);

        constraints.gridy = 1;
        add(slider, constraints);
    }

    @Override
    public void configureSettings() {
        label.setFont(MyFonts.ARIAL_UNICODE_14);
        spinner.setFont(MyFonts.ARIAL_UNICODE_14);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setColumns(5);
    }

    public JSlider getSlider() {return slider;}

    public abstract Number getValue();
    public abstract void setValue(Number n);

    public static class Integer extends MySliderPanel {
        public Integer(String labelText, String suffix, int min, int max, int value) {
            super(labelText, suffix, min, max);
            slider = new JSlider(min, max, value);
            spinner = new JSpinner(new SpinnerNumberModel(value, min, max, 1));

            build();
        }

        @Override
        public void bindActions() {
            spinner.addChangeListener(e -> slider.setValue((java.lang.Integer) spinner.getValue()));
            slider.addChangeListener(e -> spinner.setValue(slider.getValue()));
        }

        @Override
        public void configureSettings() {
            super.configureSettings();

            ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setFormatterFactory(
                    new MyNumberFormatterFactory.Integer(suffix, (java.lang.Integer) min, (java.lang.Integer) max));
        }

        @Override
        public java.lang.Integer getValue() {return slider.getValue();}

        @Override
        public void setValue(Number n) {slider.setValue((java.lang.Integer) n);}
    }

    public static class Double extends MySliderPanel {
        int precision;
        int precisionMultiplier;

        public Double(String labelText, String suffix, double min, double max, double value, int precision) {
            super(labelText, suffix, min, max);
            this.precision = precision;
            this.precisionMultiplier = MyMath.pow(10, precision);

            slider = new JSlider((int) (min * precisionMultiplier), (int) (max * precisionMultiplier), (int) (value * precisionMultiplier));
            spinner = new JSpinner(new SpinnerNumberModel(value, min, max, 1.0/precisionMultiplier));

            build();
        }

        @Override
        public void bindActions() {
            spinner.addChangeListener(e -> slider.setValue((int) ((java.lang.Double) spinner.getValue() * precisionMultiplier + 0.5)));
            slider.addChangeListener(e -> spinner.setValue((double) slider.getValue() / precisionMultiplier));
        }

        @Override
        public void configureSettings() {
            super.configureSettings();

            ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setFormatterFactory(
                    new MyNumberFormatterFactory.Double(suffix, (java.lang.Double) min, (java.lang.Double) max, precision));
        }

        @Override
        public java.lang.Double getValue() {return (double) slider.getValue() / precisionMultiplier;}

        @Override
        public void setValue(Number n) {spinner.setValue(n);}
    }
}
