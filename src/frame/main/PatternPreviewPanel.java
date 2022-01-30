package frame.main;

import pattern.*;
import util.MyObserver;

import javax.swing.JPanel;
import java.awt.*;

public class PatternPreviewPanel extends JPanel implements MyObserver {
	public static final int PREVIEW_PANEL_SIZE = 400;
	PatternSettings settings;
	PatternRenderer renderer;
	
	public PatternPreviewPanel(PatternSettings settings) {
		this.settings = settings;
		renderer = new PatternRenderer();
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(PREVIEW_PANEL_SIZE, PREVIEW_PANEL_SIZE));
		settings.addObserver(this);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		renderer.drawPattern((Graphics2D) g, settings, new Rectangle(getWidth(), getHeight()));
	}

	@Override
	public void update(Object o) {
		repaint();
	}
}