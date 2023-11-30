package ui.gui;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class GuiMain {
    private static StartScreen startScreen;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            startScreen = new StartScreen();
            startScreen.setVisible(true);
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                int progress = 0;
                @Override
                public void run() {
                    progress += 10;
                    startScreen.updateProgressBar(progress);
                    if (progress >= 100) {
                        startScreen.setVisible(false);
                        createMainGui();
                        timer.cancel();
                    }
                }
            },0,500);
        });
    }

    private static void createMainGui() {
        new MyWorldGUI() {

        };
    }
}
