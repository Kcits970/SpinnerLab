package frame.dialog;

import frame.MyContainable;
import frame.MyFonts;
import frame.MyFrameTools;
import image.ImageSettings;
import util.MyNumberFormatterFactory;

import javax.swing.*;
import java.awt.*;

public class SaveAsDialog extends JDialog implements MyContainable {
    JSpinner widthSpinner;
    JSpinner heightSpinner;
    JTextField filenameField;
    JButton saveButton;
    JButton cancelButton;
    JButton defaultButton;

    ImageSettings saveSettings;

    public SaveAsDialog(Frame owner) {
        super(owner, "Save As", true);
        build();
    }

    @Override
    public void createComponents() {
        widthSpinner = new JSpinner(new SpinnerNumberModel(720, 1, 10000, 1));
        heightSpinner = new JSpinner(new SpinnerNumberModel(720, 1, 10000, 1));

        filenameField = new JTextField(16);
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        defaultButton = new JButton("Default");
    }

    @Override
    public void addComponents() {
        Container c = getContentPane();
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        add(getImageSizePanel());
        add(Box.createVerticalStrut(5));
        add(getFilenamePanel());

        setContentPane(MyFrameTools.addComponentWithEdgeSpacing(null, c, 5));
    }

    public Container getImageSizePanel() {
        JPanel imageSizePanel = new JPanel();
        imageSizePanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.BOTH;

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.ipadx = 5;
        imageSizePanel.add(MyFrameTools.createJLabelWithFont("Width:", MyFonts.ARIAL_UNICODE_14), constraints);

        constraints.gridy = 1;
        imageSizePanel.add(MyFrameTools.createJLabelWithFont("Height:", MyFonts.ARIAL_UNICODE_14), constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.ipadx = 0;
        imageSizePanel.add(widthSpinner, constraints);

        constraints.gridy = 1;
        imageSizePanel.add(heightSpinner, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        constraints.weightx = 1.0;
        imageSizePanel.add(Box.createHorizontalGlue(), constraints);

        return MyFrameTools.addComponentWithEdgeSpacing(MyFrameTools.createJPanelWithBorder("Image Size"), imageSizePanel, 5);
    }

    public Container getFilenamePanel() {
        JPanel filenamePanel = new JPanel();
        filenamePanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.ipadx = 5;
        filenamePanel.add(MyFrameTools.createJLabelWithFont("Filename:", MyFonts.ARIAL_UNICODE_14), constraints);

        constraints.gridx = 1;
        filenamePanel.add(filenameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        filenamePanel.add(Box.createVerticalStrut(5), constraints);

        constraints.gridy = 2;
        filenamePanel.add(MyFrameTools.addHorizontally(null, false, saveButton, cancelButton, defaultButton), constraints);

        return MyFrameTools.addComponentWithEdgeSpacing(MyFrameTools.createJPanelWithBorder("Save Format"), filenamePanel, 5);
    }

    @Override
    public void bindActions() {
        saveButton.addActionListener(e -> {
            saveSettings = new ImageSettings(getWidthSpinnerValue(), getHeightSpinnerValue(), filenameField.getText(), "png");
            dispose();
        });
        cancelButton.addActionListener(e -> {
            saveSettings = null;
            dispose();
        });
        defaultButton.addActionListener(e -> filenameField.setText("creasepattern.png"));
    }

    @Override
    public void configureSettings() {
        ((JSpinner.DefaultEditor) widthSpinner.getEditor()).getTextField().setFormatterFactory(new MyNumberFormatterFactory.Integer("px", 1, 10000));
        ((JSpinner.DefaultEditor) heightSpinner.getEditor()).getTextField().setFormatterFactory(new MyNumberFormatterFactory.Integer("px", 1, 10000));
        widthSpinner.setFont(MyFonts.ARIAL_UNICODE_14);
        heightSpinner.setFont(MyFonts.ARIAL_UNICODE_14);
        filenameField.setFont(MyFonts.ARIAL_UNICODE_14);

        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(getOwner());
    }

    int getWidthSpinnerValue() {
        return (Integer) widthSpinner.getValue();
    }

    int getHeightSpinnerValue() {
        return (Integer) heightSpinner.getValue();
    }

    public ImageSettings getSaveSettings() {
        setVisible(true);
        return saveSettings;
    }
}
