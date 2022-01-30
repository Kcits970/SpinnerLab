package image;

public class ImageSettings {
    public int imageWidth;
    public int imageHeight;
    public String filename;
    public String imageType;

    public ImageSettings(int width, int height, String name, String type) {
        imageWidth = width;
        imageHeight = height;
        filename = (name.endsWith("." + type.toLowerCase())) ? name : name + "." + type.toLowerCase();
        imageType = type;
    }
}
