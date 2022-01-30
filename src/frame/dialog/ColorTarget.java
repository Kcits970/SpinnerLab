package frame.dialog;

import java.awt.*;
import java.util.function.*;

public class ColorTarget {
    Supplier<Color> origin;
    Consumer<Color> targetSetter;
    String targetName;

    public ColorTarget(Supplier<Color> origin, Consumer<Color> targetSetter, String targetName) {
        this.origin = origin;
        this.targetSetter = targetSetter;
        this.targetName = targetName;
    }

    public void modifyTarget(Color modifiedColor) {
        targetSetter.accept(modifiedColor);
    }

    public Color getColor() {return origin.get();}

    @Override
    public String toString() {return targetName;}
}