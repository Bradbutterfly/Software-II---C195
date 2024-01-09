package Controller;

import Access.DBAppointment;
import Model.Appointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentMenu implements Initializable {
    Parent scene;
    Stage stage;
    /**
     * Label
     */
    public Label AppointmentLabel;
    /**
     * Tableview
     */
    public TableView<Appointment>AppointmentTable;
    /**
     * Table columns
     */
    public TableColumn<Appointment, String>TitleColumn;
    public TableColumn<Appointment, String>DescriptionColumn;
    public TableColumn<Appointment, String>LocationColumn;
    public TableColumn<Appointment, String>TypeColumn;
    public TableColumn<Appointment, String>StartColumn;
    public TableColumn<Appointment, String>EndColumn;
    public TableColumn<Appointment, Integer>AppointmentIDColumn;
    public TableColumn<Appointment, Integer>ContactColumn;
    public TableColumn<Appointment, Integer>CustomerColumn;
    public TableColumn<Appointment, Integer>UserColumn;
    /**
     * Buttons
     */
    public Button AddButtonLabel;
    public Button ModifyButtonLabel;
    public Button ReturnButtonLabel;
    public Button DeleteButtonLabel;
    /**
     * Radio buttons
     */
    public RadioButton AllRadioLabel;
    public RadioButton MonthRadioLabel;
    public RadioButton WeekRadioLabel;
    /**
     * Toggle
     */
    public ToggleGroup Group1;
    /**
     * Observable list
     */
    ObservableList<Appointment> appointments;
    /**
     * Override
     * @param url url
     * @param resourceBundle rb
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AllRadioLabel.setSelected(true);
        AllRadioLabel.requestFocus();
        AppointmentTable.setItems(DBAppointment.getAllAppointment());
        AppointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        TitleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        DescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        LocationColumn.setCellValueFactory(new PropertyValueFactory<>("Location"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        StartColumn.setCellValueFactory(new PropertyValueFactory<>("Start"));
        EndColumn.setCellValueFactory(new PropertyValueFactory<>("End"));
        CustomerColumn.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        UserColumn.setCellValueFactory(new PropertyValueFactory<>("User_ID"));
        ContactColumn.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
    }
    /**
     * Return button
     * @param event sends user back to main menu
     * @throws IOException io
     */
    public void ReturnButtonAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * Add button
     * @param event Sends user to add menu
     * @throws IOException io
     */
    public void AddButtonAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AddAppointmentMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * Modify button
     * @param event Sends user to modify menu
     * @throws IOException io
     */
    public void ModifyButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/ModifyAppointmentMenu.fxml"));
        loader.load();
        Appointment SelectAppointment = AppointmentTable.getSelectionModel().getSelectedItem();
        if (SelectAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Selection Error");
            alert.setContentText("No Appointment Selected");
            alert.showAndWait();
        } else {
            ModifyAppointmentMenu modifyAppointmentMenu = loader.getController();
            modifyAppointmentMenu.AppointmentSelected(AppointmentTable.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    /**
     * Week radio button
     */
    public void WeekRadioAction() {
        if (WeekRadioLabel.isSelected()) {
            AppointmentTable.setItems(DBAppointment.getAppointmentWeek());
        }
    }
    /**
     * Month radio button
     */
    public void MonthRadioAction() {
        if (MonthRadioLabel.isSelected()) {
            AppointmentTable.setItems(DBAppointment.getAppointmentMonth());
        }
    }
    /**
     * All radio button
     */
    public void AllRadioAction() {
        if (AllRadioLabel.isSelected()) {
            AppointmentTable.setItems(DBAppointment.getAllAppointment());
        }
    }
    /**
     * Delete button
     * @throws SQLException sql
     */
    public void DeleteButtonAction(ActionEvent event) throws SQLException, IOException {
        Appointment SelectAppointment = AppointmentTable.getSelectionModel().getSelectedItem();
        if (SelectAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Selection Error");
            alert.setContentText("No Appointment Selected");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error");
            alert.setContentText("Are you sure you want to delete this appointment? " + "\n" + "ID: " + SelectAppointment.getAppointment_ID() + " " + SelectAppointment.getType());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElseThrow() == ButtonType.OK) {
                DBAppointment.deleteAppointment(AppointmentTable.getSelectionModel().getSelectedItem().getAppointment_ID());
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AppointmentMenu.fxml")));
                stage.setScene(new Scene(scene));
                stage.show();

            }
        }
    }
}
