package Controller;

import Access.DBAppointment;
import Access.DBUser;
import Model.Appointment;
import Utilities.Time_Zone_Menu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoginMenu implements Initializable {
    Parent scene;
    Stage stage;
    /**
     * Labels
     */
    public Label LogInLabel;
    public Label PasswordLabel;
    public Label Location;
    public Label TimeZone;
    public Label UserNameLabel;
    /**
     * Text fields
     */
    public TextField UserText;
    public TextField PasswordText;
    /**
     * Buttons
     */
    public Button ExitButtonLabel;
    public Button LogInButton;
    /**
     * RB language bundle
     */
    ResourceBundle RB = ResourceBundle.getBundle("Utilities/LanguageBundle");
    /**
     * Override
     * @param url url
     * @param resourceBundle rb
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LogInButton.setText(RB.getString("Log_In"));
        UserNameLabel.setText(RB.getString("User"));
        PasswordLabel.setText(RB.getString("Password"));
        LogInLabel.setText(RB.getString("Log_In"));
        Location.setText(RB.getString("Time_Zone"));
        setTimeZone();
    }
    /**
     * Login button
     * @param event User input is checked a provided with path
     * @throws IOException FXMLoader
     */
    public void LoginButtonAction(javafx.event.ActionEvent event) throws IOException {
        String  User_Name = UserText.getText();
        String Password = PasswordText.getText();
        boolean True = DBUser.validate_Login(User_Name, Password);
        if (True) {
            Successful();
            Appointment_Alert();
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainMenu.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else {
            Unsuccessful();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(RB.getString("Log_In_Error"));
            alert.setHeaderText(RB.getString("Error"));
            alert.setContentText(RB.getString("Retry"));
            alert.showAndWait();
        }
    }
    /**
     * Exit button press action
     */
    public void ExitButtonAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Do you wish to exit the program?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElseThrow() == ButtonType.OK) {
            System.exit(0);
        }
    }
    /**
     * This variable is called for a successful login
     */
    public void Successful() {
        Logger log = Logger.getLogger("login_activity.txt");
        try {
            FileHandler FH = new FileHandler("login_activity.txt", true);
            SimpleFormatter SF = new SimpleFormatter();
            FH.setFormatter(SF);
            log.addHandler(FH);
        } catch (IOException e) {
            Logger.getLogger(LoginMenu.class.getName()).log(Level.INFO, null, e);
            Logger.getLogger(LoginMenu.class.getName()).log(Level.SEVERE, null, e);}
        ZonedDateTime now = ZonedDateTime.now();
        log.info("User: " + UserText.getText() + " Successfully Logged In At " + now + " time zone");
    }
    /**
     * This variable is called for an unsuccessful login
     */
    public void Unsuccessful() {
        Logger log = Logger.getLogger("login_activity.txt");
        try {
            FileHandler FH = new FileHandler("login_activity.txt", true);
            SimpleFormatter SF = new SimpleFormatter();
            FH.setFormatter(SF);
            log.addHandler(FH);
        } catch (IOException e) {
            Logger.getLogger(LoginMenu.class.getName()).log(Level.INFO, null, e);
            Logger.getLogger(LoginMenu.class.getName()).log(Level.SEVERE, null, e);}
        ZonedDateTime now = ZonedDateTime.now();
        log.info("User: " + UserText.getText() + " Unsuccessfully Logged In At "  + now + " time zone");
    }
    /**
     * Lambda expression, it is used to display the time zone, This variable controls appointment alerts
     */
    public void Appointment_Alert() {
        boolean Validate = false;
        LocalDateTime logTime = LocalDateTime.now();
        int upcoming_ID = 0;
        LocalDateTime Start = LocalDateTime.now();
        ObservableList<Appointment> allAppointment = DBAppointment.getAllAppointment();
        ObservableList<Appointment> upcoming = FXCollections.observableArrayList();
        assert allAppointment != null;
        for (Appointment A : allAppointment) {
            LocalDateTime start = A.getStart();
            long timeDifference = ChronoUnit.MINUTES.between(logTime, start);
            if (timeDifference >= 0 && timeDifference <= 15) {
                upcoming.add(A);
                upcoming_ID = A.getAppointment_ID();
                Start = A.getStart();
                Validate = true;
            }
        }
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(RB.getString("Appointment_Alert"));
        if (Validate) {
            alert.setHeaderText((RB.getString("Upcoming")) + " Apt # " + upcoming_ID + " at " + Start);
        }
        else {
            alert.setHeaderText(RB.getString("No_Upcoming_Appointments"));
        }
        alert.setContentText(RB.getString("Check"));
        alert.showAndWait();
    }
    //Lambda Expression
    Time_Zone_Menu getTimeZone = () -> TimeZone.setText(ZoneId.systemDefault().toString());
    /**
     * Sets time zone
     */
    public void setTimeZone() {
        getTimeZone.time_Zone();
    }
}
