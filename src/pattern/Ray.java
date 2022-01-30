package pattern;

import java.awt.geom.*;

public class Ray {
	private static final int LINE_LENGTH = Short.MAX_VALUE;
	public Point2D.Double origin;
	public double theta; //radians

	public Ray(double x1, double y1, double x2, double y2) {
		this(new Point2D.Double(x1, y1), new Point2D.Double(x2, y2));
	}

	public Ray(Point2D.Double start, Point2D.Double cross) {
		origin = start;

		theta = Math.atan2(cross.getY() - start.getY(), cross.getX() - start.getX());
		theta = (theta < 0) ? theta + 2*Math.PI : theta;
	}
	
	public Ray(Point2D.Double start, double dir) {
		origin = start;
		theta = dir;
	}

	public Line2D.Double getAsLine() {
		return new Line2D.Double(
				origin,
				new Point2D.Double(origin.x + LINE_LENGTH * Math.cos(theta), origin.y + LINE_LENGTH * Math.sin(theta))
		);
	}
}