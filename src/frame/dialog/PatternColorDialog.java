package frame.dialog;

import frame.*;
import pattern.PatternSettings;
import util.*;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;

public class PatternColorDialog extends JDialog implements MyContainable {
    PatternSettings settings;
    JComboBox<ColorTarget> colorTargets;
    JComboBox<NameWrapper<AbstractColorChooserPanel>> colorModels;
    JPanel colorModelPanel;
    CardLayout colorModelPanelLayout;
    JColorChooser colorChooser;
    ColorPreviewPanel previewPanel;

    JButton invertButton;
    JButton invertAllButton;

    public PatternColorDialog(Frame owner, PatternSettings settings) {
        super(owner, "Color Settings", true);
        this.settings = settings;
        build();
    }

    @Override
    public void createComponents() {
        colorTargets = new JComboBox<>();
        colorTargets.addItem(new ColorTarget(() -> settings.mountainColor, c -> settings.setMountainColor(c), "Mountain Folds"));
        colorTargets.addItem(new ColorTarget(() -> settings.valleyColor, c -> settings.setValleyColor(c), "Valley Folds"));
        colorTargets.addItem(new ColorTarget(() -> settings.borderColor, c -> settings.setBorderColor(c), "Border"));
        colorTargets.addItem(new ColorTarget(() -> settings.backgroundColor, c -> settings.setBackgroundColor(c), "Background"));

        colorChooser = new JColorChooser();
        AbstractColorChooserPanel[] colorChooserPanels = colorChooser.getChooserPanels();

        colorModels = new JComboBox();
        for (AbstractColorChooserPanel panel : colorChooserPanels)
            if ("RGB".equals(panel.getDisplayName()) || "HSL".equals(panel.getDisplayName()))
                colorModels.addItem(new NameWrapper<>(panel, panel.getDisplayName()));

        colorModelPanel = MyFrameTools.createJPanelWithBorder("");
        colorModelPanelLayout = new CardLayout();
        previewPanel = new ColorPreviewPanel();
        colorChooser.setPreviewPanel(previewPanel);

        invertButton = new JButton("Invert");
        invertAllButton = new JButton("Invert All");
    }

    @Override
    public void addComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.BOTH;

        constraints.gridx = 0;
        constraints.gridy = 0;
        add(MyFrameTools.createJLabelWithFont("Color Target:", MyFonts.ARIAL_UNICODE_14), constraints);

        constraints.gridx = 1;
        add(colorTargets, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        add(MyFrameTools.createJLabelWithFont("Color Model:", MyFonts.ARIAL_UNICODE_14), constraints);

        constraints.gridx = 1;
        add(colorModels, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        add(previewPanel, constraints);

        colorModelPanel.setLayout(colorModelPanelLayout);
        for (int i = 0; i < colorModels.getItemCount(); i++)
            colorModelPanel.add(colorModels.getItemAt(i).getObject(), colorModels.getItemAt(i).toString());

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        add(colorModelPanel, constraints);

        constraints.gridy = 3;
        add(MyFrameTools.addHorizontally(null, false, invertButton, invertAllButton), constraints);

        setContentPane(MyFrameTools.addComponentWithEdgeSpacing(null, getContentPane(), 5));
    }

    @Override
    public void bindActions() {
        previewPanel.addPropertyChangeListener(e -> {
            previewPanel.previewColor = colorChooser.getColor();
            previewPanel.repaint();

            ((ColorTarget) colorTargets.getSelectedItem()).modifyTarget(colorChooser.getColor());
        });

        colorTargets.addItemListener(e -> updateCurrentColor());
        colorModels.addItemListener(e -> colorModelPanelLayout.show(colorModelPanel, colorModels.getSelectedItem().toString()));

        invertButton.addActionListener(e -> colorChooser.setColor(ColorFunctions.invert(colorChooser.getColor())));
        invertAllButton.addActionListener(e -> {
            settings.setColors(
                ColorFunctions.invert(settings.mountainColor),
                ColorFunctions.invert(settings.valleyColor),
                ColorFunctions.invert(settings.borderColor),
                ColorFunctions.invert(settings.backgroundColor)
            );
            updateCurrentColor();
        });
    }

    @Override
    public void configureSettings() {
        updateCurrentColor();

        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(getOwner());
    }

    void updateCurrentColor() {
        colorChooser.setColor(((ColorTarget) colorTargets.getSelectedItem()).getColor());
    }

    public void showColorDialog() {
        updateCurrentColor();
        setVisible(true);
    }

    class ColorPreviewPanel extends JPanel {
        Color previewColor;

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(previewColor);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, getWidth()-1, getHeight()-1);
        }
    }
}
