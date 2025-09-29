import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Settings window for editing application preferences.
 */
public class SettingsGUI {
    private final Stage stage;
    private final SettingsDataStorage storage;

    public SettingsGUI() {
        storage = SettingsDataStorage.getInstance();
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Settings");

        VBox root = new VBox(12);
        root.setPadding(new Insets(16));

        Label header = new Label("Application Settings");
        header.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(4));

        Label clinicLabel = new Label("Clinic name:");
        TextField clinicField = new TextField(storage.getClinicName());
        clinicField.setPrefColumnCount(20);

        Label autosaveLabel = new Label("Enable auto-save:");
        CheckBox autosaveBox = new CheckBox();
        autosaveBox.setSelected(storage.isAutoSaveEnabled());

        Label apptLabel = new Label("Default appointment (min):");
        TextField apptField = new TextField(Integer.toString(storage.getAppointmentDurationMinutes()));

    // theme selector removed; we'll apply a single light look to this dialog

        grid.add(clinicLabel, 0, 0);
        grid.add(clinicField, 1, 0);
        grid.add(autosaveLabel, 0, 1);
        grid.add(autosaveBox, 1, 1);
        grid.add(apptLabel, 0, 2);
        grid.add(apptField, 1, 2);
    // no theme selector cell

        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER_RIGHT);

    Button saveBtn = new Button("Save");
        Button resetBtn = new Button("Reset to defaults");
        Button closeBtn = new Button("Close");

        saveBtn.setOnAction(e -> {
            // Validate and save
            String clinicName = clinicField.getText();
            if (clinicName == null || clinicName.trim().isEmpty()) {
                clinicName = "HealthCare Pro";
            }
            storage.setClinicName(clinicName.trim());

            storage.setAutoSaveEnabled(autosaveBox.isSelected());

            try {
                int minutes = Integer.parseInt(apptField.getText().trim());
                if (minutes <= 0) minutes = storage.getAppointmentDurationMinutes();
                storage.setAppointmentDurationMinutes(minutes);
            } catch (NumberFormatException ex) {
                // ignore invalid entry and keep previous value
            }

            // single light theme - no theme saved

            stage.close();
        });

        resetBtn.setOnAction(e -> {
            storage.resetToDefaults();
            // update UI to reflect defaults
            clinicField.setText(storage.getClinicName());
            autosaveBox.setSelected(storage.isAutoSaveEnabled());
            apptField.setText(Integer.toString(storage.getAppointmentDurationMinutes()));
            // no theme to restore
        });

        closeBtn.setOnAction(e -> stage.close());

        buttons.getChildren().addAll(resetBtn, saveBtn, closeBtn);

        root.getChildren().addAll(header, grid, buttons);

    // Apply simple inline light styling for this settings dialog
    root.setStyle("-fx-background-color: linear-gradient(to bottom, #f7fff7, #f0f9f0); -fx-padding: 12;");
    header.setStyle("-fx-text-fill: #1b5e20;");
    clinicLabel.setStyle("-fx-text-fill: #2e7d32;");
    autosaveLabel.setStyle("-fx-text-fill: #2e7d32;");
    apptLabel.setStyle("-fx-text-fill: #2e7d32;");
    saveBtn.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-background-radius: 8;");
    resetBtn.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #2e7d32; -fx-border-color: #d4edda; -fx-background-radius: 8;");
    closeBtn.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-background-radius: 8;");

    Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(480);
        stage.setHeight(280);
    }

    public void show() {
        // show and wait so changes are applied before returning
        stage.showAndWait();
    }
}
