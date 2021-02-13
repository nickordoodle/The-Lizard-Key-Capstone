package org.lizard.util;

import java.awt.*;

public class Screen {

    private Screen() {
    }

    // Get the screen size's dimensions
    public static final Dimension SCREEN_DIMENSIONS = Toolkit.getDefaultToolkit().getScreenSize();

    public static final int WIDTH = (int) SCREEN_DIMENSIONS.getWidth();

    public static final int HEIGHT = (int) SCREEN_DIMENSIONS.getHeight();

    public static final int CENTER_HORIZONTAL = WIDTH / 2;

    public static final int CENTER_VERTICAL = HEIGHT / 2;

    public static final int MAP_SQUARE_SIZE = 80;


}
