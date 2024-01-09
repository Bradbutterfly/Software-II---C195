package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainMenu {
    Parent scene;
    Stage stage;
    /**
     * Label
     */
    public Label MenuLabel;
    /**
     * Buttons
     */
    public Button CustomerButtonLabel;
    public Button AppointmentButtonLabel;
    public Button ReportButtonLabel;
    public Button ReturnButtonLabel;
    /**
     * Customer button
     * @param event Sends user to customer menu
     * @throws IOException io
     */
    public void CustomerButtonAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/CustomerMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * Appointment button
     * @param event Sends user to appointment menu
     * @throws IOException io
     */
    public void AppointmentButtonAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AppointmentMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * Report button
     * @param event Sends user to report menu
     * @throws IOException io
     */
    public void ReportButtonAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/ReportMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * Return button
     * @param event Sends user to log in menu
     * @throws IOException io
     */
    public void ReturnButtonAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/LoginMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
