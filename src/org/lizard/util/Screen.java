package org.lizard.util;

import java.awt.*;

public class Screen {

    private Screen() {
    }

    // Get the screen size's dimensions
    public static final Dimension SCREEN_DIMENSIONS = Toolkit.getDefaultToolkit().getScreenSize();

    public static final int SCREEN_WIDTH = (int) SCREEN_DIMENSIONS.getWidth();

    public static final int SCREEN_HEIGHT = (int) SCREEN_DIMENSIONS.getHeight();

    public static final int SPECIFIED_WIDTH = 1600;

    public static final int SPECIFIED_HEIGHT = 1060;

    public static final int CENTER_HORIZONTAL = SPECIFIED_WIDTH / 2;

    public static final int CENTER_VERTICAL = SPECIFIED_HEIGHT / 2;

    public static final int MAP_SQUARE_SIZE = 110;

    public static int getLeftXCoordinateForElement(int elementWidth){
        return (CENTER_HORIZONTAL - (elementWidth / 2));
    }

    public static int getTopYCoordinateForElement(int elementHeight){
        return (CENTER_VERTICAL - (elementHeight / 2));
    }


}
