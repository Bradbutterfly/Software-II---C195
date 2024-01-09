package Controller;

import Access.DBAppointment;
import Access.DBContact;
import Model.Appointment;
import Model.Contact;
import Utilities.Appointment_Today_Menu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import java.time.Month;
import java.util.Objects;
import java.util.ResourceBundle;

public class ReportMenu implements Initializable {
    Parent scene;
    Stage stage;
    /**
     * Tab
     */
    public Tab TypeAndMonthTab;
    public Tab ContactsScheduleTab;
    public Tab AppointmentsTodayTab;
    /**
     * Buttons
     */
    public Button ReturnLabel1;
    public Button ReturnButtonLabel2;
    public Button GenerateReportLabel;
    public Button ReturnButtonLabel3;
    /**
     * Combo boxes
     */
    public ComboBox<Month> AppointmentMonth;
    public ComboBox<String> AppointmentType;
    public ComboBox<Contact> ContactsCombo;
    /**
     * Labels
     */
    public Label totalCount;
    public Label AppointmentTodayLabel;
    public Label AppointmentToday;
    /**
     * Table view
     */
    public TableView<Appointment> ContactAppointmentTable;
    /**
     * Table column
     */
    public TableColumn<Appointment, String> TitleColumn;
    public TableColumn<Appointment, String> DescriptionColumn;
    public TableColumn<Appointment, String> LocationColumn;
    public TableColumn<Appointment, String> StartColumn;
    public TableColumn<Appointment, String> EndColumn;
    public TableColumn<Appointment, String> TypeColumn;
    public TableColumn<Appointment, Integer> AppointmentIDColumn;
    public TableColumn<Appointment, Integer> ContactColumn;
    public TableColumn<Appointment, Integer> CustomerColumn;
    public TableColumn<Appointment, Integer> UserColumn;
    /**
     * Override
     * @param url            url
     * @param resourceBundle rb
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppointmentType.setVisibleRowCount(5);
        AppointmentType.setItems(TypeList());
        AppointmentMonth.setVisibleRowCount(5);
        AppointmentMonth.setItems(MonthList());
        ContactsCombo.setVisibleRowCount(5);
        try {
            ContactsCombo.setItems(DBContact.getAllContact());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ContactAppointmentTable.setItems(DBAppointment.getAllAppointment());
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
        appointmentsToday();
    }
    /**
     * Return button
     * @param event Sends user back to log in menu
     * @throws IOException io
     */
    public void ReturnButtonAction1(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * Return button 2
     * @param event Sends user back to log in menu
     * @throws IOException io
     */
    public void ReturnButtonAction2(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * Return button 3
     * @param event Sends user back to log in menu
     * @throws IOException io
     */
    public void ReturnButtonAction3(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * Generate report
     */
    public void GenerateReportAction() {
        try {
            Month M = AppointmentMonth.getValue();
            Integer month = M.getValue();
            String type = AppointmentType.getValue();
            if (type == null || type.isBlank()) {
                throw new Exception();
            }
            Integer count = DBAppointment.getAppointmentMonthAndType(month, type);
            totalCount.setText(String.valueOf(count));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Month or Type is Blank");
            alert.setContentText("Select Type and Month");
            alert.showAndWait();
        }
    }
    /**
     * Contact combo
     */
    public void ContactsComboAction() {
        ContactAppointmentTable.setItems(contactFilter());
    }

    /**
     * This variable creates a list of appointment types
     * @return lists of available appointment types
     */
    public ObservableList<String> TypeList() {
        ObservableList<String> Type = FXCollections.observableArrayList();
        Type.addAll("Initial Intake", "Planning Session", "Follow Up", "Med Check", "Brain Dump", "Process Discussion", "Debriefing", "Termination");
        return Type;
    }
    /**
     * This variable creates a list of months
     * @return list of all months of the year
     */
    public ObservableList<Month> MonthList() {
        ObservableList<Month> month = FXCollections.observableArrayList();
        month.addAll( Month.JANUARY, Month.FEBRUARY, Month.MARCH, Month.APRIL, Month.MAY, Month.JUNE, Month.JULY, Month.AUGUST, Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER);
        return month;
    }
    /**
     * This variable filters appointments by contact
     * @return returns filtered list dependent on contact selected
     */
    public ObservableList<Appointment> contactFilter() {
        ObservableList<Appointment> allAppointments= DBAppointment.getAllAppointment();
        FilteredList<Appointment> filteredContactList = new FilteredList<>(allAppointments, i -> i.getContact_ID() == ContactsCombo.getSelectionModel().getSelectedItem().getContact_ID());
        return filteredContactList;
    }

    Integer AppointmentCount = DBAppointment.getAppointmentToday();
    Appointment_Today_Menu getAppointmentCount = () -> AppointmentToday.setText(String.valueOf(AppointmentCount));
    /**
     * Current day appointments
     */
    public void appointmentsToday() {
        getAppointmentCount.Appointment_Today();
    }
}