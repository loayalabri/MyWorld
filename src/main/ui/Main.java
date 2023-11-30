package ui;

import ui.gui.MyWorldGUI;
import ui.gui.StartScreen;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    private static StartScreen startScreen;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            startScreen = new StartScreen();
            startScreen.setVisible(true);
            java.util.Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                int progress = 0;
                @Override
                public void run() {
                    progress += 10;
                    startScreen.updateProgressBar(progress);
                    if (progress >= 100) {
                        startScreen.setVisible(false);
                        timer.cancel();
                        new MyWorldGUI();
                    }
                }
            },0,500);
        });
    }
}
