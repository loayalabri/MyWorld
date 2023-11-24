package ui.gui;

import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;

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
