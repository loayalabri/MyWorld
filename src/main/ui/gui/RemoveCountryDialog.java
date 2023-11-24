package ui.gui;

import model.ListOfCountry;
import model.exceptions.CountryNotFoundException;

import javax.swing.*;
import java.awt.*;

// Represents a Dialog for removing a country
public class RemoveCountryDialog extends JDialog {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 200;
    private final MyWorldGUI myWorldGUI;
    JTextField countryToRemove;
    JPanel topPanel;
    JPanel botPanel;

    // MODIFIES: this.
    // EFFECTS: Constructs a dialog for removing a country.
    public RemoveCountryDialog(MyWorldGUI myWorldGUI) {
        this.myWorldGUI = myWorldGUI;
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLayout(null);
        JPanel topBorderPanel = new JPanel();
        topBorderPanel.setBounds(0,0,400, 30);
        this.add(topBorderPanel);
        this.setSize(WIDTH, HEIGHT);
        initializePanels();
        initializeComponents();
        this.setVisible(true);
    }

    // MODIFIES: this.
    // EFFECTS: initialize top and bottom panel.
    private void initializePanels() {
        topPanel = new JPanel(new FlowLayout());
        botPanel = new JPanel(new FlowLayout());
        topPanel.setBounds(0,30,400, 80);
        botPanel.setBounds(0, 110, 400, 90);
        this.add(topPanel);
        this.add(botPanel);
    }

    // MODIFIES: this.
    // EFFECTS: initialize components for top and bottom panel.
    private void initializeComponents() {
        countryToRemove = new JTextField();
        countryToRemove.setPreferredSize(new Dimension(150,40));
        JLabel removeLabel = new JLabel("Name of Country to Remove");
        topPanel.add(removeLabel);
        topPanel.add(countryToRemove);
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> doRemove());
        botPanel.add(removeButton);
    }

    // MODIFIES: this.
    // EFFECTS: remove country given in countryToRemove field and
    //          updates countryList in myWorldGUI.
    private void doRemove() {
        String nameToRemove = countryToRemove.getText();
        ListOfCountry myWorld = myWorldGUI.getCountries();
        try {
            myWorld.removeCountry(nameToRemove);
            this.dispose();
            myWorldGUI.updateCountryList();
        } catch (CountryNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Could not find country with name " + nameToRemove,
                    "Country Not Found", JOptionPane.ERROR_MESSAGE);
        }
    }
}
