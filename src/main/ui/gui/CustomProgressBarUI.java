package ui.gui;

import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;

// Represents a custom progress bar that extends BasicProgressBarUI and
// adds functionality to choose the color of the color of text of
// the progress bar
public class CustomProgressBarUI extends BasicProgressBarUI {

    @Override
    protected Color getSelectionBackground() {
        return Color.WHITE;  // Change the text color to red (customize as needed)
    }

    @Override
    protected Color getSelectionForeground() {
        return Color.WHITE;  // Change the text color to red (customize as needed)
    }
}
