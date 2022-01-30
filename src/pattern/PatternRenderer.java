package pattern;

import util.ModuloArray;

import java.awt.*;
import java.awt.geom.*;

public class PatternRenderer {
	double baseVertexDistance;
	double alternationDistance;
	ModuloArray<Point2D.Double> baseVertices;
	ModuloArray<Ray> baseRays;
	Rectangle renderArea;
	PatternSettings settings;

	public void drawPattern(Graphics2D g2, PatternSettings settings, Rectangle renderArea) {
		this.settings = settings;
		this.renderArea = renderArea;
		reset();

		if (settings.antiAlias) g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		drawBackground(g2);
		drawBase(g2);
		drawAlternatingRays(g2);
		drawBorder(g2);
	}
	
	public void reset() {
		baseVertexDistance = Math.max(renderArea.width/2.0, renderArea.height/2.0) * settings.baseScale / 8;
		alternationDistance = baseVertexDistance * MyMath.cot(Math.PI/settings.numOfSides) * settings.altDistScale;
		baseVertices = new ModuloArray<>(settings.numOfSides);
		baseRays = new ModuloArray<>(settings.numOfSides);

		for (int i = 0; i < settings.numOfSides; i++)
			baseVertices.set(i, new Point2D.Double(
					renderArea.width/2.0 + baseVertexDistance * Math.cos(2*Math.PI * i / settings.numOfSides + Math.toRadians(settings.rotation)),
					renderArea.height/2.0 + baseVertexDistance * Math.sin(2*Math.PI * i / settings.numOfSides + Math.toRadians(settings.rotation))
			));

		for (int i = 0; i < settings.numOfSides; i++)
			baseRays.set(i, new Ray(baseVertices.get(i).x, baseVertices.get(i).y, baseVertices.get(i+1).x, baseVertices.get(i+1).y));
	}

	public void drawBackground(Graphics2D g2) {
		g2.setColor(settings.backgroundColor);
		g2.fill(renderArea);
	}

	public void drawBase(Graphics2D g2) {
		g2.setColor(settings.valleyColor);
		for (int i = 0; i < settings.numOfSides; i++) {
			g2.draw(baseRays.get(i).getAsLine());
			g2.draw(new Line2D.Double(renderArea.width/2.0, renderArea.height/2.0, baseVertices.get(i).x, baseVertices.get(i).y));
		}
	}

	public void drawAlternatingRays(Graphics2D g2) {
		for (int i = 0; i < settings.numOfSides; i++) {
			Ray r1 = baseRays.get(i);
			Ray r2 = baseRays.get(i+1);

			Point2D.Double origin = r2.origin;
			Point2D.Double previousOrigin = origin;

			for (int k = 1; ; k++) {
				g2.setColor((k % 2 == 1) ? settings.mountainColor : settings.valleyColor);
				Point2D.Double currentOrigin = MyMath.distancedPoint(origin, alternationDistance * k, MyMath.bisectorAngle(r1.theta, r2.theta));
				if (!renderArea.contains(previousOrigin)) break;
				g2.draw(new Line2D.Double(previousOrigin, currentOrigin));
				g2.draw(new Ray(currentOrigin, r1.theta).getAsLine());
				g2.draw(new Ray(currentOrigin, r2.theta).getAsLine());
				previousOrigin = currentOrigin;
			}
		}
	}

	public void drawBorder(Graphics2D g2) {
		if (!settings.removeBorder) {
			g2.setColor(settings.borderColor);
			g2.drawRect(0, 0, renderArea.width - 1, renderArea.height - 1);
		}
	}
}