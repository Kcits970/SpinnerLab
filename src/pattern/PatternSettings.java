package pattern;

import util.MyObservable;

import java.awt.*;

public class PatternSettings extends MyObservable {
    public double baseScale;
    public int rotation;
    public int numOfSides;
    public double altDistScale;
    public boolean antiAlias;
    public boolean removeBorder;
    public Color mountainColor = Color.RED;
    public Color valleyColor = Color.BLUE;
    public Color borderColor = Color.BLACK;
    public Color backgroundColor = Color.WHITE;

    public void setBaseScale(double baseScale) {this.baseScale = baseScale; notifyObservers();}
    public void setRotation(int rotation) {this.rotation = rotation; notifyObservers();}
    public void setNumOfSides(int numOfSides) {this.numOfSides = numOfSides; notifyObservers();}
    public void setAltDistScale(double altDistScale) {this.altDistScale = altDistScale; notifyObservers();}
    public void setAntiAlias(boolean antiAlias) {this.antiAlias = antiAlias; notifyObservers();}
    public void setRemoveBorder(boolean removeBorder) {this.removeBorder = removeBorder; notifyObservers();}

    public void setMountainColor(Color c) {mountainColor = c; notifyObservers();}
    public void setValleyColor(Color c) {valleyColor = c; notifyObservers();}
    public void setBorderColor(Color c) {borderColor = c; notifyObservers();}
    public void setBackgroundColor(Color c) {backgroundColor = c; notifyObservers();}

    public void setColors(Color mountain, Color valley, Color border, Color background) {
        mountainColor = mountain;
        valleyColor = valley;
        borderColor = border;
        backgroundColor = background;
        notifyObservers();
    }
    public void resetColors() {
        setColors(Color.RED, Color.BLUE, Color.BLACK, Color.WHITE);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(observer -> observer.update(null));
    }
}
