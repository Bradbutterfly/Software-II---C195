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

public class ModifyAppointmentMenu implements Initializable {
    Parent scene;
    Stage stage;
    /**
     * Labels
     */
    public Label IDLabel;
    public Label TypeLabel;
    public Label StartLabel;
    public Label EndLabel;
    public Label CustomerIDLabel;
    public Label UserIDLabel;
    public Label TitleLabel;
    public Label DescriptionLabel;
    public Label LocationLabel;
    public Label ContactLabel;
    public Label ModifyAppointmentLabel;
    /**
     * Date picker
     */
    public DatePicker StartCalender;
    /**
     * Combo boxes
     */
    public ComboBox<LocalTime> StartCombo;
    public ComboBox<LocalTime> EndCombo;
    public ComboBox<String> TypeCombo;
    public ComboBox<Customer> CustomerIDCombo;
    public ComboBox<Contact> ContactCombo;
    public ComboBox<User> UserIDCombo;
    /**
     * Buttons
     */
    public Button SaveButtonLabel;
    public Button CancelButtonLabel;
    /**
     * Text fields
     */
    public TextField IDText;
    public TextField TitleText;
    public TextField DescriptionText;
    public TextField LocationText;

    /**
     * Lambda Expression, Does not allow the user to set an appointment date in the past, Previously used on Add appointment menu
     * @param url            url
     * @param resourceBundle rb
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TypeCombo.setVisibleRowCount(5);
        TypeCombo.setItems(TypeList());
        StartCombo.setVisibleRowCount(5);
        StartCombo.setItems(timeRange());
        EndCombo.setVisibleRowCount(5);
        EndCombo.setItems(timeRange());
        ContactCombo.setVisibleRowCount(5);
        try {
            ContactCombo.setItems(DBContact.getAllContact());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CustomerIDCombo.setVisibleRowCount(5);
        CustomerIDCombo.setItems(DBCustomer.getAllCustomer());
        UserIDCombo.setVisibleRowCount(5);
        try {
            UserIDCombo.setItems(DBUser.getAllUser());
        } catch (SQLException e) {
            e.printStackTrace();
        }
         // Lambda Expression
        StartCalender.setDayCellFactory(calender -> new DateCell() {
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
     * @param event Saves modified data
     * @throws IOException io
     */
    public void SaveButtonLabel(ActionEvent event) throws IOException {
        boolean True = Validate (IDText.getText());
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
            String Appointment_ID = IDText.getText();

            LocalTime Open = LocalTime.of(8, 0);
            LocalTime Close = LocalTime.of(22, 0);

            ZoneId Time = ZoneId.of("America/New_York");
            ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());

            ZonedDateTime openZone = ZonedDateTime.of(LocalDate.now(), Open, Time);
            ZonedDateTime closeZone = ZonedDateTime.of(LocalDate.now(), Close, Time);

            ZonedDateTime open = openZone.withZoneSameInstant(localZone);
            ZonedDateTime close = closeZone.withZoneSameInstant(localZone);

            DBAppointment.updateAppointment(Title, Description, Location, Type, Start, End, Customer_ID.getCustomer_ID(), User_ID.getUser_ID(), Contact_ID.getContact_ID(), Appointment_ID);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AppointmentMenu.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    /**
     * Cancel button
     * @param event Sends user to appointment menu
     * @throws IOException io
     */
    public void CancelButtonAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AppointmentMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * Local date start
     */
    public LocalDateTime DateStart() {
        LocalTime Start = StartCombo.getSelectionModel().getSelectedItem();
        LocalDate AppointmentDate = StartCalender.getValue();
        return LocalDateTime.of(AppointmentDate, Start);
    }
    /**
     * This variable creates a DateEnd end object
     * @return returns DateEnd end object
     */
    public LocalDateTime DateEnd() {
        LocalTime End = EndCombo.getSelectionModel().getSelectedItem();
        LocalDate AppointmentDate = StartCalender.getValue();
        return LocalDateTime.of(AppointmentDate, End);
    }
    /**
     * This variable creates a list of appointment types
     * @return returns type list
     */
    public ObservableList<String> TypeList() {
        ObservableList<String> Type = FXCollections.observableArrayList();
        Type.addAll("Initial Intake", "Planning Session", "Follow Up", "Med Check", "Brain Dump", "Process Discussion", "Debriefing", "Termination");
        return Type;
    }
    /**
     * This variable creates hours
     * @return returns combo box with hours
     */
    public ObservableList<LocalTime> timeRange() {
        ObservableList<LocalTime> Time = FXCollections.observableArrayList();
        LocalTime LocalOpen = LocalTime.of(8, 0);
        LocalTime LocalClose = LocalTime.of(22, 0);

        ZoneId EST = ZoneId.of("America/New_York");
        ZoneId localTime = ZoneId.of(TimeZone.getDefault().getID());

        ZonedDateTime openTime = ZonedDateTime.of(LocalDate.now(), LocalOpen, EST);
        ZonedDateTime closeTime = ZonedDateTime.of(LocalDate.now(), LocalClose, EST);

        ZonedDateTime ZoneOpen = openTime.withZoneSameInstant(localTime);
        ZonedDateTime ZoneClose = closeTime.withZoneSameInstant(localTime);

        ZonedDateTime T = ZoneOpen.minusMinutes(30);

        boolean Range = T.isBefore(ZoneClose);
        while (Range) {
            T = T.plusMinutes(30);
            Time.add(LocalTime.from(T));
            if (!(!T.equals(ZoneClose) || T.isAfter(ZoneClose))) {
                break;
            }
        }
        return Time;
    }
    /**
     * This variable populates the text fields and combo boxes with an appointment
     */
    public void AppointmentSelected (Appointment appointment) {
        IDText.setText(String.valueOf(appointment.getAppointment_ID()));
        TitleText.setText(appointment.getTitle());
        DescriptionText.setText(appointment.getDescription());
        LocationText.setText(appointment.getLocation());
        for (Contact CO : ContactCombo.getItems()) {
            if (appointment.getContact_ID() == CO.getContact_ID()) {
                ContactCombo.setValue(CO);
                break;
            }
        }
        TypeCombo.setValue(appointment.getType());
        StartCalender.setValue(appointment.getStart().toLocalDate());
        StartCombo.setValue(appointment.getStart().toLocalTime());
        EndCombo.setValue(appointment.getEnd().toLocalTime());
        for (Customer CU : CustomerIDCombo.getItems()) {
            if (appointment.getCustomer_ID() == CU.getCustomer_ID()) {
                CustomerIDCombo.setValue(CU);
                break;
            }
        }
        for (User U : UserIDCombo.getItems()) {
            if (appointment.getUser_ID() == U.getUser_ID()){
                UserIDCombo.setValue(U);
                break;
            }
        }
    }
    /**
     * This variable checks for errors
     * @param Appointment_ID passes in appointment ID of selected appointment
     * @return returns true if no errors or false with error alert
     */
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
        if (TypeCombo.getSelectionModel().isEmpty())  {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank");
            alert.setHeaderText("Type is Blank");
            alert.setContentText("Select Appointment Type");
            alert.showAndWait();
            return false;
        }
        if (StartCalender.getChronology().equals(null)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank");
            alert.setHeaderText("Appointment Date is Blank");
            alert.setContentText("Select Appointment Date");
            alert.showAndWait();
            return false;
        }
        if (StartCombo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank");
            alert.setHeaderText("Appointment Start Time is Blank");
            alert.setContentText("Select Appointment Start Time");
            alert.showAndWait();
            return false;
        }
        if (EndCombo.getSelectionModel().isEmpty())  {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank");
            alert.setHeaderText("Appointment End Time is Blank");
            alert.setContentText("Select Appointment End Time");
            alert.showAndWait();
            return false;
        }
        if (CustomerIDCombo.getSelectionModel().isEmpty())  {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank");
            alert.setHeaderText("Customer ID is Blank");
            alert.setContentText("Select Customer ID for Appointment");
            alert.showAndWait();
            return false;
        }
        if (UserIDCombo.getSelectionModel().isEmpty())  {
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

            if (Start.isAfter(ZoneEnd)
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
