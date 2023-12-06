package ui.gui;

import model.Country;
import model.ListOfCountry;
import model.exceptions.CountryNotFoundException;
import model.exceptions.EmptyStringException;
import model.exceptions.RatingOutOfBoundException;
import model.log.Event;
import model.log.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;

// Represents a GUI for myWorld Application
public class MyWorldGUI {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 700;
    private ListOfCountry myWorld;
    private static final String STORAGE_FILE = "./data/myWorld.json";
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private final JFrame frame;

    private final JList<Country> countryList;
    private final DefaultListModel<Country> listModel;

    private JPanel topPanel;
    private JSplitPane midPanel;
    private JPanel botPanel;
    private JLabel nameLabel;
    private JLabel continentLabel;
    private JLabel ratingLabel;
    private JTextArea descLabel;

    // MODIFIES: this
    // EFFECTS: construct a JFrame for the application and initialize its fields.
    public MyWorldGUI() {
        frame = new JFrame("My World");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        countryList = new JList<>();
        listModel = new DefaultListModel<>();
        initializeFields();
        initializeGraphics();
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Event event : EventLog.getInstance()) {
                    System.out.println(event);
                }
                System.exit(0);
            }
        });
    }

    // EFFECTS: initialize myWorld, jsonReader and jsonWriter.
    private void initializeFields() {
        myWorld = new ListOfCountry();
        jsonReader = new JsonReader(STORAGE_FILE);
        jsonWriter = new JsonWriter(STORAGE_FILE);
    }

    // MODIFIES: this.
    // EFFECTS: initialize the graphical components in the frame
    private void initializeGraphics() {
        initializePanels();
        initializeMainText();
        initializeButtons();
        initializeMidPanelComponents();
    }

    // MODIFIES: this.
    // EFFECTS: initialize topPanel, midPanel, and botPanel.
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

    // MODIFIES: this.
    // EFFECTS: initialize the components of midPanel.
    private void initializeMidPanelComponents() {
        JPanel rightPanel = new JPanel(new GridLayout(5,1));
        nameLabel = new JLabel();
        continentLabel = new JLabel();
        ratingLabel = new JLabel();
        descLabel = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(descLabel);
        descLabel.setLineWrap(true);
        descLabel.setWrapStyleWord(true);
        descLabel.setEditable(false);
        rightPanel.add(nameLabel);
        rightPanel.add(continentLabel);
        rightPanel.add(ratingLabel);
        rightPanel.add(scrollPane);
        countryList.setModel(listModel);
        midPanel.setLeftComponent(new JScrollPane(countryList));
        midPanel.setRightComponent(rightPanel);
        frame.add(midPanel, BorderLayout.CENTER);
        midPanel.setEnabled(false);
        countryList.getSelectionModel().addListSelectionListener(e -> displayCountryInfo());
    }

    // EFFECTS: display info of selected country in the right component of midPanel.
    private void displayCountryInfo() {
        Country selectedCountry = countryList.getSelectedValue();
        if (selectedCountry != null) {
            nameLabel.setText("Name: " + selectedCountry.getCountryName());
            continentLabel.setText("Continent: " + selectedCountry.getContinent());
            ratingLabel.setText("Rating: " + selectedCountry.getRating());
            descLabel.setText("Description: " + selectedCountry.getDescription());
        }
    }

    // MODIFIES: this
    // EFFECTS: update listModel with visitedCountries in myWorld.
    public void updateCountryList() {
        listModel.clear();
        for (Country next : myWorld.getCountriesVisited()) {
            listModel.addElement(next);
        }
    }


    // MODIFIES: this.
    // EFFECTS: initialize the main logo on top center.
    private void initializeMainText() {
        ImageIcon imageIcon = scaleIcon(new ImageIcon("./data/WorldIcon.png"), 100, 100);
        JLabel iconLabel = new JLabel("My World!");
        iconLabel.setIcon(imageIcon);
        iconLabel.setHorizontalTextPosition(JLabel.CENTER);
        iconLabel.setOpaque(true);
        iconLabel.setForeground(Color.BLACK);
        iconLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 50));
        iconLabel.setVerticalAlignment(JLabel.TOP);
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        topPanel.add(iconLabel);
    }

    // MODIFIES: this.
    // EFFECTS: initialize buttons for add, remove, save, load.
    private void initializeButtons() {
        final int BUTTON_WIDTH = 150;
        final int BUTTON_HEIGHT = 75;
        Font textFont = new Font("Lucida Grande", Font.PLAIN, 16);
        JButton addButton = initializeAddButton(BUTTON_WIDTH, BUTTON_HEIGHT, textFont);
        JButton removeButton = initializeRemoveButton(BUTTON_WIDTH, BUTTON_HEIGHT, textFont);
        JButton loadButton = initializeLoadButton(BUTTON_WIDTH, BUTTON_HEIGHT, textFont);
        JButton saveButton = initializeSaveButton(BUTTON_WIDTH, BUTTON_HEIGHT, textFont);
        botPanel.add(addButton);
        botPanel.add(removeButton);
        botPanel.add(loadButton);
        botPanel.add(saveButton);
    }

    // MODIFIES: this.
    // EFFECTS: initialize saveButton and returns it.
    private JButton initializeSaveButton(int buttonWidth, int buttonHeight, Font textFont) {
        JButton saveButton = new JButton("Save Data");
        ImageIcon saveIcon = scaleIcon(new ImageIcon("./data/SaveIcon.png"), 50, 50);
        saveButton.addActionListener(e -> doSaveData());
        saveButton.setBounds(620, 0, buttonWidth, buttonHeight);
        saveButton.setIcon(saveIcon);
        saveButton.setHorizontalTextPosition(AbstractButton.LEFT);
        saveButton.setIconTextGap(30);
        saveButton.setFont(textFont);
        return saveButton;
    }

    // MODIFIES: this.
    // EFFECTS: initialize loadButton and returns it.
    private JButton initializeLoadButton(int buttonWidth, int buttonHeight, Font textFont) {
        JButton loadButton = new JButton("Load Data");
        ImageIcon loadIcon = scaleIcon(new ImageIcon("./data/LoadIcon.png"), 50, 50);
        loadButton.addActionListener(e -> doLoadData());
        loadButton.setBounds(420, 0, buttonWidth, buttonHeight);
        loadButton.setIcon(loadIcon);
        loadButton.setIconTextGap(30);
        loadButton.setFont(textFont);
        loadButton.setHorizontalTextPosition(AbstractButton.LEFT);
        return loadButton;
    }

    // MODIFIES: this.
    // EFFECTS: initialize removeButton and returns it.
    private JButton initializeRemoveButton(int buttonWidth, int buttonHeight, Font textFont) {
        JButton removeButton = new JButton("Remove Country");
        ImageIcon removeIcon = scaleIcon(new ImageIcon("./data/RemoveIcon.png"), 50, 50);
        removeButton.addActionListener(e -> doRemoveCountry());
        removeButton.setBounds(220, 0, buttonWidth, buttonHeight);
        removeButton.setIcon(removeIcon);
        removeButton.setIconTextGap(30);
        removeButton.setFont(textFont);
        removeButton.setHorizontalTextPosition(AbstractButton.LEFT);
        return removeButton;
    }

    // MODIFIES: this.
    // EFFECTS: initialize addButton and returns it.
    private JButton initializeAddButton(int buttonWidth, int buttonHeight, Font textFont) {
        JButton addButton = new JButton("Add Country");
        ImageIcon addIcon = scaleIcon(new ImageIcon("./data/AddIcon.png"),50,50);
        addButton.addActionListener(e -> doAddCountry());
        addButton.setBounds(20, 0, buttonWidth, buttonHeight);
        addButton.setIcon(addIcon);
        addButton.setIconTextGap(30);
        addButton.setFont(textFont);
        addButton.setHorizontalTextPosition(AbstractButton.LEFT);
        return addButton;
    }

    // MODIFIES: STORAGE_FILE
    // EFFECTS: save countryList to STORAGE_FILE.
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

    // MODIFIES: STORAGE_FILE
    // EFFECTS: load countryList from STORAGE_FILE.
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

    // MODIFIES: this.
    // EFFECTS: display dialog for removing a country.
    private void doRemoveCountry() {
//        new RemoveCountryDialog(this);
        int selectedIndex = countryList.getSelectedIndex();
        if (selectedIndex != -1) {
            Country selectedCountry = listModel.getElementAt(selectedIndex);
            try {
                myWorld.removeCountry(selectedCountry.getCountryName());
                updateCountryList();
                nameLabel.setText("");
                continentLabel.setText("");
                descLabel.setText("");
                ratingLabel.setText("");
                JOptionPane.showMessageDialog(frame, selectedCountry + " was removed successfully",
                        "Country Removed", JOptionPane.INFORMATION_MESSAGE);
            } catch (CountryNotFoundException e) {
                JOptionPane.showMessageDialog(frame,   " Select country to remove",
                        "Select Country to remove", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame,   " Select country to remove",
                    "Select Country to remove", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // MODIFIES: this.
    // EFFECTS: display dialog for adding a country.
    private void doAddCountry() {
        new AddCountryDialog(this);
    }

    // MODIFIES: imageIcon
    // EFFECTS: helper method to scale a given imageIcon with given width and height.
    private ImageIcon scaleIcon(ImageIcon imageIcon, int width, int height) {
        Image image = imageIcon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    // EFFECTS: return myWorld
    public ListOfCountry getCountries() {
        return myWorld;
    }

    public void setVisible() {
        frame.setVisible(true);
    }
}
