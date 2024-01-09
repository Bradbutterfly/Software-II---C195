package Controller;

import Access.DBAppointment;
import Access.DBContact;
import Access.DBCustomer;
import Access.DBUser;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class AddAppointmentMenu implements Initializable {
    Parent scene;
    Stage stage;
    /**
     * Buttons
     */
    public Button SaveButtonLabel;
    public Button CancelButtonLabel;
    /**
     * Date picker
     */
    public DatePicker StartDateCalender;
    /**
     * Label
     */
    public Label TypeLabel;
    public Label StartLabel;
    public Label EndLabel;
    public Label CustomerIDLabel;
    public Label UserIDLabel;
    public Label IDLabel;
    public Label TitleLabel;
    public Label DescriptionLabel;
    public Label LocationLabel;
    public Label ContactLabel;
    public Label AddAppointmentLabel;
    /**
     * Text fields
     */
    public TextField IDText;
    public TextField TitleText;
    public TextField DescriptionText;
    public TextField LocationText;
    /**
     * Combo boxes
     */
    public ComboBox<Contact>ContactCombo;
    public ComboBox<User>UserIDCombo;
    public ComboBox<Customer>CustomerIDCombo;
    public ComboBox<String>TypeCombo;
    public ComboBox<LocalTime>EndTimeCombo;
    public ComboBox<LocalTime>StartTimeCombo;
    /**
     * Lambda Expression, Does not allow the user to set an appointment date in the past, Override initializes and populates combo boxes.
     * @param url url
     * @param rb rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert false;
        TypeCombo.setVisibleRowCount(5);
        TypeCombo.setItems(TypeList());
        StartTimeCombo.setVisibleRowCount(5);
        StartTimeCombo.setItems(timeRange());
        EndTimeCombo.setVisibleRowCount(5);
        EndTimeCombo.setItems(timeRange());
        ContactCombo.setVisibleRowCount(5);
        CustomerIDCombo.setVisibleRowCount(5);
        UserIDCombo.setVisibleRowCount(5);
        CustomerIDCombo.setItems(DBCustomer.getAllCustomer());
        try {
            ContactCombo.setItems(DBContact.getAllContact());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            UserIDCombo.setItems(DBUser.getAllUser());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Lambda Expression
        StartDateCalender.setDayCellFactory(calender -> new DateCell() {
            @Override
            public void updateItem(LocalDate StartDateCalender, boolean empty) {
                super.updateItem(StartDateCalender, empty);
                setDisable(empty || StartDateCalender.isBefore(LocalDate.now()));
            }
        }
        );
    }
    /**
     * Save button
     * @param event Saves new appointments
     * @throws IOException FXML loader
     * @throws SQLException sql
     */
    public void SaveButtonAction(ActionEvent event) throws IOException, SQLException {
        Boolean True = Validate (IDText.getText());
        if (True) {
            String Title = TitleText.getText();
            String Description = DescriptionText.getText();
            String Location = LocationText.getText();
            String Type = TypeCombo.getValue();
            LocalDateTime Start = DateStart();
            LocalDateTime End = DateEnd();
            Contact Contact_ID = ContactCombo.getValue();
            Customer Customer_ID = CustomerIDCombo.getValue();
            User User_ID = UserIDCombo.getValue();

            DBAppointment.addAppointment(Title, Description, Location, Type, Start, End, Customer_ID.getCustomer_ID(), User_ID.getUser_ID(), Contact_ID.getContact_ID());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AppointmentMenu.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    /**
     * Cancel button
     * @param event Sends user back to appointment menu
     * @throws IOException io
     */
    public void CancelButtonAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AppointmentMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * This variable creates a DateStart Start object
     * @return returns Date object of start date and time
     */
    public LocalDateTime DateStart() {
        LocalTime Start = StartTimeCombo.getSelectionModel().getSelectedItem();
        LocalDate Appointment = StartDateCalender.getValue();
        return LocalDateTime.of(Appointment, Start);
    }
    /**
     * This variable creates a DateEnd End object
     * @return returns Date object of end date and time
     */
    public LocalDateTime DateEnd() {
        LocalTime End = EndTimeCombo.getSelectionModel().getSelectedItem();
        LocalDate Appointment = StartDateCalender.getValue();
        return LocalDateTime.of(Appointment, End);
    }
    /**
     * This variable creates a list of appointment types
     * @return returns observable list of appointment types
     */
    public ObservableList<String> TypeList() {
        ObservableList<String> Type = FXCollections.observableArrayList();
        Type.addAll("Initial Intake", "Planning Session", "Follow Up", "Med Check", "Brain Dump", "Process Discussion", "Debriefing", "Termination");
        return Type;
    }
    /**
     * this variable creates a business hour range
     * @return returns time ranges for business hours
     */
    public ObservableList<LocalTime> timeRange() {
        ObservableList<LocalTime> Time = FXCollections.observableArrayList();
        LocalTime localOpen = LocalTime.of(8, 0);
        LocalTime localClose = LocalTime.of(22, 0);

        ZoneId EST = ZoneId.of("America/New_York");
        ZoneId localTime = ZoneId.of(TimeZone.getDefault().getID());

        ZonedDateTime openTime = ZonedDateTime.of(LocalDate.now(), localOpen, EST);
        ZonedDateTime closeTime = ZonedDateTime.of(LocalDate.now(), localClose, EST);

        ZonedDateTime zoneOpen = openTime.withZoneSameInstant(localTime);
        ZonedDateTime zoneClose = closeTime.withZoneSameInstant(localTime);

        ZonedDateTime T = zoneOpen.minusMinutes(30);

        boolean Range = T.isBefore(zoneClose);
        while (Range) {
            T = T.plusMinutes(30);
            Time.add(LocalTime.from(T));
            if ((T.equals(zoneClose) || T.isAfter(zoneClose))) {
                break;
            }
        }
        return Time;
    }
    /**
     * This variable checks multiple errors in setting an appointment
     * @param Appointment_ID the auto-generated appointment ID
     * @return returns error for false and nothing for true
     **/
    public Boolean Validate (String Appointment_ID) {
        ObservableList<Appointment> allAppointment = DBAppointment.getAllAppointment();
        if (TitleText.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank");
            alert.setHeaderText("Title is Blank");
            alert.setContentText("Input Appointment Title");
            alert.showAndWait();
            return false;
        }
        if (DescriptionText.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank");
            alert.setHeaderText("Description is Blank");
            alert.setContentText("Input Appointment Description");
            alert.showAndWait();
            return false;
        }
        if (LocationText.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank");
            alert.setHeaderText("Location is Blank");
            alert.setContentText("Input Appointment Location");
            alert.showAndWait();
            return false;
        }
        if (ContactCombo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank");
            alert.setHeaderText("Contact is Blank");
            alert.setContentText("Select Appointment Contact");
            alert.showAndWait();
            return false;
        }
        if (TypeCombo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank");
            alert.setHeaderText("Type is Blank");
            alert.setContentText("Select Appointment Type");
            alert.showAndWait();
            return false;
        }
        if (StartDateCalender.getChronology().equals(null)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank");
            alert.setHeaderText("Appointment Date is Blank");
            alert.setContentText("Select Appointment Date");
            alert.showAndWait();
            return false;
        }
        if (StartTimeCombo.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank");
            alert.setHeaderText("Appointment Start Time is Blank");
            alert.setContentText("Select Appointment Start Time");
            alert.showAndWait();
            return false;
        }
        if (EndTimeCombo.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank");
            alert.setHeaderText("Appointment End Time is Blank");
            alert.setContentText("Select Appointment End Time");
            alert.showAndWait();
            return false;
        }
        if (CustomerIDCombo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank");
            alert.setHeaderText("Customer ID is Blank");
            alert.setContentText("Select Customer ID for Appointment");
            alert.showAndWait();
            return false;
        }
        if (UserIDCombo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank");
            alert.setHeaderText("User ID is Blank");
            alert.setContentText("Select User ID");
            alert.showAndWait();
            return false;
        }
        if (DateStart().isAfter(DateEnd())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("Start Time Cannot Be Before End Time");
            alert.setContentText("Select New Time");
            alert.showAndWait();
            return false;
        }
        if (DateEnd().isBefore(DateStart())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning");
            alert.setHeaderText("End Time Cannot Be Before Start Time");
            alert.setContentText("Select New Time");
            alert.showAndWait();
            return false;
        }
        for (Appointment A : allAppointment) {
            LocalDateTime Start = A.getStart();
            LocalDateTime ZoneStart = DateStart();
            LocalDateTime End = A.getEnd();
            LocalDateTime ZoneEnd = DateEnd();

            if (Start.isAfter(ZoneStart)
                    && Start.isBefore(ZoneEnd)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setHeaderText("Appointment Conflicts With Another Appointment");
                alert.setContentText("Select New Time");
                alert.showAndWait();
                return false;
            }
            if (Start.isEqual(ZoneStart)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setHeaderText("Appointment Conflicts With Another Appointment");
                alert.setContentText("Select New Time");
                alert.showAndWait();
                return false;
            }
            if (End.isAfter(ZoneStart)
                    && End.isBefore(ZoneEnd)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setHeaderText("Appointment Conflicts With Another Appointment");
                alert.setContentText("Select New Time");
                alert.showAndWait();
                return false;
            }
            if (End.isEqual(ZoneEnd)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setHeaderText("Appointment Conflicts With Another Appointment");
                alert.setContentText("Select New Time");
                alert.showAndWait();
                return false;
            }
            if (Start.isBefore(ZoneStart)
                    && End.isAfter(ZoneEnd)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Warning");
                alert.setHeaderText("Appointment Conflicts With Another Appointment");
                alert.setContentText("Select New Time");
                alert.showAndWait();
                return false;
            }
        }
        return true;
    }
}
