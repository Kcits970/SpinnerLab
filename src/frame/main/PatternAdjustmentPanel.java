package frame.main;

import frame.*;
import frame.dialog.PatternColorDialog;
import pattern.PatternSettings;

import javax.swing.*;
import java.awt.*;

public class PatternAdjustmentPanel extends JPanel implements MyContainable {
	PatternSettings settings;

	MySliderPanel.Double baseScaleSlider;
	MySliderPanel.Integer rotationSlider;
	MySliderPanel.Integer numOfSidesSlider;
	MySliderPanel.Double altDistScaleSlider;
	JCheckBox antiAliasCheckBox;
	JCheckBox removeBorderCheckBox;
	JButton colorButton;
	JButton resetButton;

	PatternColorDialog colorDialog;
	
	public PatternAdjustmentPanel(PatternSettings settings) {
		this.settings = settings;
		build();
	}

	@Override
	public void createComponents() {
		baseScaleSlider = new MySliderPanel.Double("Base Scale:", "x", 0.5, 2, 1, 2);
		rotationSlider = new MySliderPanel.Integer("Rotation:", "\u00b0", 0, 360, 0);
		numOfSidesSlider = new MySliderPanel.Integer("Sides:", "", 3, 30, 6);
		altDistScaleSlider = new MySliderPanel.Double("Alt Dist Scale:", "x", 0.5, 2, 1, 2);
		antiAliasCheckBox = new JCheckBox("Anti Aliasing");
		removeBorderCheckBox = new JCheckBox("Remove Border");
		colorButton = new JButton("Color Settings");
		resetButton = new JButton("Reset");

		colorDialog = new PatternColorDialog((Frame) SwingUtilities.getWindowAncestor(this), settings);
	}

	@Override
	public void addComponents() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(baseScaleSlider);
		add(Box.createVerticalStrut(10));
		add(rotationSlider);
		add(Box.createVerticalStrut(10));
		add(numOfSidesSlider);
		add(Box.createVerticalStrut(10));
		add(altDistScaleSlider);
		add(Box.createVerticalStrut(10));
		add(MyFrameTools.addHorizontally(null, false, antiAliasCheckBox, removeBorderCheckBox));
		add(MyFrameTools.addHorizontally(null, false, colorButton, resetButton));
	}

	@Override
	public void bindActions() {
		baseScaleSlider.getSlider().addChangeListener(e -> settings.setBaseScale(baseScaleSlider.getValue()));
		rotationSlider.getSlider().addChangeListener(e -> settings.setRotation(rotationSlider.getValue()));
		numOfSidesSlider.getSlider().addChangeListener(e -> settings.setNumOfSides(numOfSidesSlider.getValue()));
		altDistScaleSlider.getSlider().addChangeListener(e -> settings.setAltDistScale(altDistScaleSlider.getValue()));
		antiAliasCheckBox.addItemListener(e -> settings.setAntiAlias(antiAliasCheckBox.isSelected()));
		removeBorderCheckBox.addItemListener(e -> settings.setRemoveBorder(removeBorderCheckBox.isSelected()));

		colorButton.addActionListener(e -> colorDialog.showColorDialog());
		resetButton.addActionListener(e -> {
			baseScaleSlider.setValue(1.0);
			rotationSlider.setValue(0);
			numOfSidesSlider.setValue(6);
			altDistScaleSlider.setValue(1.0);
			antiAliasCheckBox.setSelected(false);
			removeBorderCheckBox.setSelected(false);
			settings.resetColors();
		});
	}

	@Override
	public void configureSettings() {
		settings.setBaseScale(baseScaleSlider.getValue());
		settings.setRotation(rotationSlider.getValue());
		settings.setNumOfSides(numOfSidesSlider.getValue());
		settings.setAltDistScale(altDistScaleSlider.getValue());
	}
}