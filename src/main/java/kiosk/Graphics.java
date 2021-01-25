package kiosk;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import processing.core.PConstants;
import processing.core.PFont;

public class Graphics {

    private static final String GOTHIC_PATH = "assets/CenturyGothic.ttf";
    private static final String GOTHIC_BOLD_PATH = "assets/CenturyGothicBold.ttf";

    private static boolean fontsLoaded = false;
    private static PFont gothic = null;
    private static PFont gothicBold = null;
    private static float bubbleOffset = 0;

    private Graphics() {

    }

    /**
     * Loads fonts so they are ready for use.
     */
    public static void loadFonts() {
        if (Graphics.fontsLoaded) {
            System.err.println("FontManager.loadFonts should only be called once");
        } else {
            File gothicFile = new File(GOTHIC_PATH);
            File gothicBoldFile = new File(GOTHIC_BOLD_PATH);

            try {
                Graphics.gothic = new PFont(
                        Font.createFont(Font.TRUETYPE_FONT, gothicFile), true);
                Graphics.gothicBold = new PFont(
                        Font.createFont(Font.TRUETYPE_FONT, gothicBoldFile), true);
            } catch (FontFormatException | IOException exception) {
                throw new IllegalStateException("Could not load fonts");
            }

            Graphics.fontsLoaded = true;
        }
    }

    public static void useGothic(Kiosk sketch) {
        useGothic(sketch, 48, false);
    }

    public static void useGothic(Kiosk sketch, int fontSize) {
        useGothic(sketch, fontSize, false);
    }

    public static void useGothic(Kiosk sketch, int fontSize, boolean bold) {
        if (bold) {
            sketch.textFont(gothicBold, fontSize);
        } else {
            sketch.textFont(gothic, fontSize);
        }
    }

    /**
     * Draws a rounded rectangle.
     * @param sketch to draw to
     * @param x top left corner of the rectangle
     * @param y top left corner of the rectangle
     * @param w width of the rectangle
     * @param h height of the rectangle
     * @param r radius of the curve
     */
    public static void drawRoundedRectangle(Kiosk sketch, float x, float y,
                                            float w, float h, float r) {
        sketch.rectMode(PConstants.CORNER);

        // Draw the rounded rectangle
        sketch.rect(x, y, w, h, r);
    }

    /**
     * Draws a blue background with grey-blue circles
     * bubbling up from the bottom.
     * @param sketch to draw to
     */
    public static void drawBubbleBackground(Kiosk sketch) {
        final int width = sketch.width;
        final int height = sketch.height;

        float spacing = width / 50f;
        float radius = spacing / 2.05f;
        float radiusChipping = 0.2825f;

        sketch.ellipseMode(PConstants.CORNER);
        sketch.noStroke();
        sketch.background(44, 134, 194);
        sketch.fill(99, 144, 197);

        boolean stagger = false;
        float y = height - radius;
        float iterationNumber = ((radius - 1) / radiusChipping);
        for (int i = 0; i < iterationNumber; i++) {
            for (float x = stagger ? spacing / 2 - bubbleOffset : -bubbleOffset; x < width; x += spacing) {
                if (y > 0) {
                    sketch.ellipse(x, y, radius, radius);
                }
            }
            radius -= radiusChipping;
            y -= spacing;
            stagger = !stagger;
        }
        bubbleOffset = bubbleOffset + 0.125f;
    }
}
