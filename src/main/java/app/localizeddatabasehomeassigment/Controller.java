package app.localizeddatabasehomeassigment;

import javafx.fxml.FXML; // Import for @FXML annotation
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class Controller {
    @FXML
    private Label selectLanguageLabel;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private Button saveButton;
    @FXML
    private ComboBox<String> languageComboBox;

    private Map<String, String> translations = new HashMap<>();
    private Stage stage;

    public void initialize() {
        languageComboBox.setValue("English");
        String selectedLanguage = "en";
        loadTranslations(selectedLanguage);
        updateUI();
    }

    public void setStage(Stage stage) {
        this.stage = stage; // Set the stage reference
    }

    public void handleLanguageChange() {
        String selectedLanguage = languageComboBox.getValue();
        String languageCode = "en";

        switch (selectedLanguage) {
            case "Farsi":
                languageCode = "fa";
                break;
            case "Japanese":
                languageCode = "ja";
                break;
            default:
                languageCode = "en";
                break;
        }

        loadTranslations(languageCode);
        updateUI();
    }

    private void loadTranslations(String language) {
        String query = "SELECT element_id, " + language + "_text AS text FROM ui_translations";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/localizeddatabase", "root", "1234");
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String elementId = rs.getString("element_id");
                String text = rs.getString("text");
                translations.put(elementId, text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateUI() {
        String stageTitle = translations.get("title");
        if (stageTitle != null && stage != null) {
            stage.setTitle(stageTitle); // Update the stage title
        }

        selectLanguageLabel.setText(translations.get("label_language"));
        firstNameLabel.setText(translations.get("label_first_name"));
        lastNameLabel.setText(translations.get("label_last_name"));
        emailLabel.setText(translations.get("label_email"));
        saveButton.setText(translations.get("button_save"));
    }

    public void handleSave() {
        // Get user input from text fields
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String selectedLanguage = languageComboBox.getValue();

        // Determine the table name based on the selected language
        String tableName = "employee_en"; // Default to English
        switch (selectedLanguage) {
            case "Farsi":
                tableName = "employee_fa";
                break;
            case "Japanese":
                tableName = "employee_ja";
                break;
        }

        // Insert data into the appropriate table
        String insertQuery = "INSERT INTO " + tableName + " (first_name, last_name, email) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/localizeddatabase", "root", "1234");
             PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            // Set parameters for the PreparedStatement
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);

            // Execute the update
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Data saved successfully.");
            } else {
                System.out.println("Failed to save data.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Clear the input fields
            firstNameField.clear();
            lastNameField.clear();
            emailField.clear();
        }
    }
}
