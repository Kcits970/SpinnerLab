package frame.main;

import constants.ProjectConstants;
import frame.*;
import frame.dialog.*;
import image.*;
import pattern.PatternSettings;
import util.MyStringFunctions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyFrame extends JFrame implements MyContainable {
	PatternPreviewPanel previewPanel;
	PatternAdjustmentPanel adjustmentPanel;
	PatternSettings patternSettings;

	AboutDialog aboutDialog;
	SaveAsDialog saveAsDialog;

	public MyFrame() {
		build();
	}

	@Override
	public void createComponents() {
		patternSettings = new PatternSettings();
		adjustmentPanel = new PatternAdjustmentPanel(patternSettings);
		previewPanel = new PatternPreviewPanel(patternSettings);

		aboutDialog = new AboutDialog(this);
		saveAsDialog = new SaveAsDialog(this);
	}

	@Override
	public void addComponents() {
		JPanel previewContainer = new JPanel();
		previewContainer.setBorder(MyFrameTools.createMyTitledBorder("Preview"));
		MyFrameTools.addComponentWithEdgeSpacing(previewContainer, previewPanel, 5);

		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		c.add(previewContainer);
		c.add(adjustmentPanel);
	}

	@Override
	public void bindActions() {}

	@Override
	public void configureSettings() {
		setTitle(ProjectConstants.PROJECT_NAME);
		setJMenuBar(new MyMenuBar());

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	class MyMenuBar extends JMenuBar implements MyContainable {
		JMenuItem saveMenuItem;
		JMenuItem exitMenuItem;
		JMenuItem aboutMenuItem;

		public MyMenuBar() {
			build();
		}

		@Override
		public void createComponents() {
			saveMenuItem = new JMenuItem("Save As...");
			exitMenuItem = new JMenuItem("Exit");
			aboutMenuItem = new JMenuItem("About " + ProjectConstants.PROJECT_NAME);
		}

		@Override
		public void addComponents() {
			JMenu fileMenu = new JMenu("File");
			fileMenu.add(saveMenuItem);
			fileMenu.addSeparator();
			fileMenu.add(exitMenuItem);

			JMenu aboutMenu = new JMenu("About");
			aboutMenu.add(aboutMenuItem);

			add(fileMenu);
			add(aboutMenu);
		}

		@Override
		public void bindActions() {
			saveMenuItem.addActionListener(e -> {
				ImageSettings imageSettings = saveAsDialog.getSaveSettings();
				if (imageSettings == null) return;
				try {
					new ImageRenderer(imageSettings, patternSettings).renderImage();
					JOptionPane.showMessageDialog(MyFrame.this, String.format("Successfully saved to '%s'", MyStringFunctions.truncate(imageSettings.filename, 30), "Info", JOptionPane.INFORMATION_MESSAGE));
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(MyFrame.this, String.format("Failed to save to '%s'", MyStringFunctions.truncate(imageSettings.filename, 30), "Error", JOptionPane.ERROR_MESSAGE));
				}
			});
			exitMenuItem.addActionListener(e -> System.exit(0));
			aboutMenuItem.addActionListener(e -> aboutDialog.setVisible(true));
		}

		@Override
		public void configureSettings() {
			saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
			exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		}
	}
}