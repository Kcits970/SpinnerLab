package pattern;

import java.awt.geom.Point2D;

public final class MyMath {
    public static double bisectorAngle(double angle1, double angle2) {
        return Math.min(angle1, angle2) + Math.abs(angle1 - angle2)/2 + ((Math.abs(angle1 - angle2) > Math.PI) ? Math.PI : 0);
    }

    public static Point2D.Double distancedPoint(Point2D.Double origin, double distance, double angle) {
        double xDist = distance * Math.cos(angle);
        double yDist = distance * Math.sin(angle);

        return new Point2D.Double(origin.getX() + xDist, origin.getY() + yDist);
    }

    public static double sec(double theta) {
        return 1 / Math.cos(theta);
    }

    public static double csc(double theta) {
        return 1 / Math.sin(theta);
    }

    public static double cot(double theta) {
        return 1 / Math.tan(theta);
    }

    public static int pow(int base, int exp) {
        int result = 1;

        for (int i = 0; i < exp; i++)
            result *= base;

        return result;
    }
}
