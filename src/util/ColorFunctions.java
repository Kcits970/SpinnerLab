package util;

import java.awt.Color;

public class ColorFunctions {
    public static Color invert(Color c) {
        return new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());
    }
}
