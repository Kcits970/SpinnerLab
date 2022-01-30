package image;

import pattern.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageRenderer {
    ImageSettings imageSettings;
    PatternSettings patternSettings;

    public ImageRenderer(ImageSettings imageSettings, PatternSettings patternSettings) {
        this.imageSettings = imageSettings;
        this.patternSettings = patternSettings;
    }

    public void renderImage() throws IOException {
        BufferedImage image = new BufferedImage(imageSettings.imageWidth, imageSettings.imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();

        new PatternRenderer().drawPattern((Graphics2D) g, patternSettings, new Rectangle(imageSettings.imageWidth, imageSettings.imageHeight));
        g.dispose();

        javax.imageio.ImageIO.write(image, imageSettings.imageType, new File(imageSettings.filename));
    }
}
