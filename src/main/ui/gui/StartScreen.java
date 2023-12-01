package ui.gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;

// Represents the startScreen of the application that has a progress bar and
// welcome message
public class StartScreen extends JWindow {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private static final Color background = new Color(38, 38, 38);
    private JProgressBar progressBar;
    private JPanel progressPanel;
    private JPanel welcomePanel;

    // MODIFIES: this.
    // EFFECTS: constructs a window for the startScreen.
    public StartScreen() {
        this.setLayout(null);
        this.setSize(WIDTH,HEIGHT);
        initializeWelcomePanel();
        initializeProgressPanel();
        this.add(progressPanel);
        this.add(welcomePanel);
        this.setLocationRelativeTo(null);
    }

    // MODIFIES: this
    // EFFECTS: initialize the progress bar.
    private void initializeProgressPanel() {
        progressPanel = new JPanel();
        progressPanel.setBounds(0, 200, WIDTH,200);
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setUI(new BasicProgressBarUI() {
            @Override
            protected Color getSelectionBackground() {
                return Color.WHITE;
            }

            @Override
            protected Color getSelectionForeground() {
                return Color.WHITE;
            }
        });
        progressBar.setForeground(new Color(201, 80, 199, 194));
        progressBar.setPreferredSize(new Dimension(300, 30));
        progressPanel.setBackground(background);
        progressPanel.add(progressBar);
    }

    // MODIFIES: this
    // EFFECTS: initialize welcomePanel with welcome text.
    private void initializeWelcomePanel() {
        welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBounds(0, 0,400,200);
        JLabel welcome = new JLabel("WELCOME!!");
        welcome.setFont(new Font("Monospaced", Font.BOLD, 60));
        welcome.setVerticalAlignment(JLabel.CENTER);
        welcome.setHorizontalAlignment(JLabel.CENTER);
        welcome.setForeground(new Color(245, 0, 0));
        welcomePanel.setBackground(background);
        welcomePanel.add(welcome);
    }

    // MODIFIES: this
    // EFFECTS: update the progress bar by progress.
    // **this method is inspired from several posts in StackOverFlow.
    public void updateProgressBar(int progress) {
        SwingUtilities.invokeLater(() -> progressBar.setValue(progress));
    }
}
