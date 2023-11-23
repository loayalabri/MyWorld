package ui.gui;

import model.Country;
import model.ListOfCountry;
import model.exceptions.EmptyStringException;
import model.exceptions.RatingOutOfBoundException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class MyWorldGUI {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 700;
    private ListOfCountry myWorld;
    private static final String STORAGE_FILE = "./data/myWorld.json";
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private JFrame frame;

    private JList<Country> countryList;
    private DefaultListModel<Country> listModel;

    private JPanel topPanel;
    private JSplitPane midPanel;
    private JPanel botPanel;

    private JButton addButton;
    private JButton removeButton;
    private JButton loadButton;
    private JButton saveButton;
    
    public MyWorldGUI() {
        frame = new JFrame("My World");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        countryList = new JList<>();
        listModel = new DefaultListModel<>();
        initializeFields();
        initializeGraphics();
        frame.setVisible(true);
    }

    private void initializeFields() {
        myWorld = new ListOfCountry();
        jsonReader = new JsonReader(STORAGE_FILE);
        jsonWriter = new JsonWriter(STORAGE_FILE);
    }

    private void initializeGraphics() {
        initializePanels();
        initializeMainText();
        initializeButtons();
        initializeMidPanelComponents();
    }

    private void initializePanels() {
        topPanel = new JPanel();
        midPanel = new JSplitPane();
        botPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(WIDTH, 100));
        midPanel.setPreferredSize(new Dimension(WIDTH, 250));
        botPanel.setLayout(new GridLayout(2, 3, 10, 10));
        botPanel.setPreferredSize(new Dimension(WIDTH, 150));
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(botPanel, BorderLayout.SOUTH);

    }

    private void initializeMidPanelComponents() {
        JPanel panel = new JPanel(new GridLayout(5,1));
        JLabel nameLabel = new JLabel();
        JLabel continentLabel = new JLabel();
        JLabel ratingLabel = new JLabel();
        JLabel descLabel = new JLabel();
        panel.add(nameLabel);
        panel.add(continentLabel);
        panel.add(ratingLabel);
        panel.add(descLabel);
        countryList.setModel(listModel);
        midPanel.setLeftComponent(new JScrollPane(countryList));
        midPanel.setRightComponent(panel);
        frame.add(midPanel, BorderLayout.CENTER);
        updateCountryList();
        countryList.getSelectionModel().addListSelectionListener(e -> {
            Country selectedCountry = countryList.getSelectedValue();
            if (selectedCountry != null) {
                nameLabel.setText("Name: " + selectedCountry.getCountryName());
                continentLabel.setText("Continent: " + selectedCountry.getContinent());
                ratingLabel.setText("Rating: " + selectedCountry.getRating());
                descLabel.setText("Description: " + selectedCountry.getDescription());
            }
        });
    }

    public void updateCountryList() {
        listModel.clear();
        for (Country next : myWorld.getCountriesVisited()) {
            listModel.addElement(next);
        }
    }



    private void initializeMainText() {
        ImageIcon imageIcon = scaleIcon(new ImageIcon("./data/MyWorldIcon.png"));
        JLabel iconLabel = new JLabel(imageIcon);
        iconLabel.setOpaque(true);
        iconLabel.setHorizontalTextPosition(JLabel.CENTER);
        iconLabel.setVerticalTextPosition(JLabel.CENTER);
        iconLabel.setForeground(Color.BLUE);
        iconLabel.setFont(new Font("Times News Roman", Font.PLAIN, 50));
        iconLabel.setVerticalAlignment(JLabel.TOP);
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(iconLabel);
    }

    private void initializeButtons() {
        final int BUTTON_WIDTH = 150;
        final int BUTTON_HEIGHT = 75;
        addButton = new JButton();
        addButton.setText("Add Country");
        addButton.addActionListener(e -> doAddCountry());
        addButton.setBounds(20, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
        removeButton = new JButton();
        removeButton.setText("Remove country");
        removeButton.addActionListener(e -> doRemoveCountry());
        removeButton.setBounds(220, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
        loadButton = new JButton();
        loadButton.setText("Load Data");
        loadButton.addActionListener(e -> doLoadData());
        loadButton.setBounds(420, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
        saveButton = new JButton();
        saveButton.setText("Save Data");
        saveButton.addActionListener(e -> doSaveData());
        saveButton.setBounds(620, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
        botPanel.add(addButton);
        botPanel.add(removeButton);
        botPanel.add(loadButton);
        botPanel.add(saveButton);
    }

    private void doSaveData() {
        try {
            jsonWriter.open();
            jsonWriter.write(myWorld);
            jsonWriter.close();
            JOptionPane.showMessageDialog(frame, "Data saved successfully",
                    "Data Saved", JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(frame, "Unable to save to " + STORAGE_FILE,
                    "Save Data Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void doLoadData() {
        try {
            myWorld = jsonReader.read();
            JOptionPane.showMessageDialog(frame, "Data loaded successfully",
                    "Data Loaded", JOptionPane.INFORMATION_MESSAGE);
            updateCountryList();
        }  catch (RatingOutOfBoundException e) {
            JOptionPane.showMessageDialog(frame, "Invalid Rating",
                    "Rating Error", JOptionPane.ERROR_MESSAGE);
        } catch (EmptyStringException e) {
            JOptionPane.showMessageDialog(frame, "Invalid Country or Continent name",
                    "Empty Name Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Unable to load from " + STORAGE_FILE,
                    "Load Data Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void doRemoveCountry() {
        new RemoveCountryDialog(this);
    }

    private void doAddCountry() {
        new AddCountryDialog(this);
    }

    private ImageIcon scaleIcon(ImageIcon imageIcon) {
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(200, 100, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        return resizedIcon;
    }

    public ListOfCountry getCountries() {
        return myWorld;
    }

    public static void main(String[] args) {
        MyWorldGUI myWorldGUI = new MyWorldGUI();
    }
}
