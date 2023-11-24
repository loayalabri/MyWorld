package ui.gui;

import javax.swing.*;
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

    public StartScreen() {
        this.setLayout(null);
        this.setSize(WIDTH,HEIGHT);
        initializeWelcomePanel();
        initializeProgressPanel();
        this.add(progressPanel);
        this.add(welcomePanel);
        this.setLocationRelativeTo(null);
    }

    private void initializeProgressPanel() {
        progressPanel = new JPanel();
        progressPanel.setBounds(0, 200, WIDTH,200);
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setUI(new CustomProgressBarUI());
        progressBar.setForeground(new Color(201, 80, 199, 194));
        progressBar.setPreferredSize(new Dimension(300, 30));
        progressPanel.setBackground(background);
        progressPanel.add(progressBar);
    }

    private void initializeWelcomePanel() {
        welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBounds(0, 0,400,200);
        JLabel welcome = new JLabel("Welcome!!");
        welcome.setFont(new Font("Monospaced", Font.BOLD, 60));
        welcome.setVerticalAlignment(JLabel.CENTER);
        welcome.setHorizontalAlignment(JLabel.CENTER);
        welcome.setForeground(new Color(245, 0, 0));
        welcomePanel.setBackground(background);
        welcomePanel.add(welcome);
    }

    public void updateProgressBar(int progress) {
        SwingUtilities.invokeLater(() -> progressBar.setValue(progress));
    }
}
