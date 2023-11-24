package ui.gui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import javax.swing.plaf.metal.MetalProgressBarUI;
import javax.swing.plaf.multi.MultiProgressBarUI;
import javax.swing.plaf.synth.SynthProgressBarUI;
import java.awt.*;

public class StartScreen extends JWindow {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
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
        progressPanel.setBackground(new Color(38, 38, 38));
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
        welcomePanel.setBackground(new Color(38, 38, 38));
        welcomePanel.add(welcome);
    }

    public void updateProgressBar(int progress) {
        SwingUtilities.invokeLater(() -> progressBar.setValue(progress));
    }
}
