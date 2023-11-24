package ui.gui;

import model.Country;
import model.ListOfCountry;
import model.exceptions.EmptyStringException;
import model.exceptions.RatingOutOfBoundException;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;

// Represents a dialog for adding a country
public class AddCountryDialog extends JDialog {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 500;
    private final MyWorldGUI myWorldGUI;
    private JPanel topPanel;
    private JPanel botPanel;

    private JTextField nameField;
    private JTextField continentField;
    private JTextField ratingField;
    private JTextArea descField;

    public AddCountryDialog(MyWorldGUI myWorldGUI) {
        this.myWorldGUI = myWorldGUI;
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLayout(null);
        initializePanels();
        initializeButton();
        initializeTextFields();
        this.setVisible(true);
    }


    private void initializePanels() {
        topPanel = new JPanel();
        topPanel.setBounds(0, 0, 400, 400);
        topPanel.setLayout(null);
        botPanel = new JPanel();
        botPanel.setBounds(0, 400, 400, 100);
        this.add(topPanel);
        this.add(botPanel);
    }

    private void initializeButton() {
        JButton addButton = new JButton();
        addButton.setText("Add");
        addButton.setPreferredSize(new Dimension(100, 40));
        addButton.addActionListener(e -> doAddCountry());
        botPanel.add(addButton);
    }

    private void initializeTextFields() {
        initializeLabels();
        nameField = new JTextField();
        nameField.setBounds(175, 50, 150, 30);
        topPanel.add(nameField);
        continentField = new JTextField();
        continentField.setBounds(175, 100, 150, 30);
        topPanel.add(continentField);
        ratingField = new JTextField();
        ratingField.setBounds(175, 150, 150, 30);
        ((AbstractDocument) ratingField.getDocument()).setDocumentFilter(new IntegerDocumentFilter());
        topPanel.add(ratingField);
        descField = new JTextArea();
        descField.setBounds(175, 200, 200, 200);
        descField.setLineWrap(true);
        descField.setWrapStyleWord(true);
        topPanel.add(descField);
    }

    private void initializeLabels() {
        JLabel nameLabel = new JLabel("Country Name");
        nameLabel.setBounds(50, 50, 100, 30);
        topPanel.add(nameLabel);
        JLabel continentLabel = new JLabel("Continent");
        continentLabel.setBounds(50, 100, 100, 30);
        topPanel.add(continentLabel);
        JLabel ratingLabel = new JLabel("Rating [0-10]");
        ratingLabel.setBounds(50, 150, 100, 30);
        topPanel.add(ratingLabel);
        JLabel descLabel = new JLabel("Description");
        descLabel.setBounds(50, 200, 100, 30);
        topPanel.add(descLabel);
    }

    private void doAddCountry() {
        String countryName = nameField.getText();
        String continentName = continentField.getText();
        int rating = toInteger(ratingField.getText());
        String description = descField.getText();
        ListOfCountry myWorld = myWorldGUI.getCountries();
        try {
            myWorld.addCountry(new Country(countryName, continentName, rating, description));
            this.dispose();
            myWorldGUI.updateCountryList();
        } catch (RatingOutOfBoundException e) {
            JOptionPane.showMessageDialog(this, "Invalid Rating",
                    "Rating Error", JOptionPane.ERROR_MESSAGE);
        } catch (EmptyStringException e) {
            JOptionPane.showMessageDialog(this, "Invalid Country or Continent name",
                    "Empty Name Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int toInteger(String text) {
        if (text.isEmpty()) {
            return -1;
        } else {
            return Integer.parseInt(text);
        }
    }
}
