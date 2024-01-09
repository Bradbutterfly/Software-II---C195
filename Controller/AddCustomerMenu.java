package Controller;

import Access.DBCountries;
import Access.DBCustomer;
import Access.DBDivision;
import Model.Countries;
import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import java.util.Objects;
import java.util.ResourceBundle;

public class AddCustomerMenu implements Initializable {
    Parent scene;
    Stage stage;
    /**
     * Buttons
     */
    public Button SaveButtonLabel;
    public Button CancelButtonLabel;
    /**
     * Labels
     */
    public Label IDLabel;
    public Label NameLabel;
    public Label AddressLabel;
    public Label PhoneNumberLabel;
    public Label StateLabel;
    public Label AddCustomerLabel;
    public Label CountryLabel;
    public Label PostalCodeLabel;
    /**
     * Text fields
     */
    public TextField IDText;
    public TextField NameText;
    public TextField AddressText;
    public TextField PostalText;
    public TextField PhoneText;
    /**
     * Combo boxes
     */
    public ComboBox<Countries>CountryCombo;
    public ComboBox<Division>StateCombo;
    /**
     * Observable lists
     */
    private final ObservableList<Countries> countries = FXCollections.observableArrayList();
    private final ObservableList<String> locations = FXCollections.observableArrayList();
    /**
     * Override
     * @param url url
     * @param resourceBundle rb
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CountryCombo.setVisibleRowCount(5);
        CountryCombo.setPromptText("Select Country...");
        CountryCombo.setItems(DBCountries.getAllCountries());
        StateCombo.setVisibleRowCount(5);StateCombo.setPromptText("Select Division");
        StateCombo.setItems(DBDivision.getAllDivision());
    }
    /**
     * This variable returns the filtered country list
     */
    public void CountryComboAction() {
        StateCombo.setItems(StateFilter());
    }
    /**
     * Save button
     * @param event Saves new customer data
     * @throws SQLException sql
     * @throws IOException io
     */
    public void SaveButtonAction(ActionEvent event) throws SQLException, IOException {
            String Customer_Name = NameText.getText();
            String Address = AddressText.getText();
            String Postal_Code = PostalText.getText();
            String Phone = PhoneText.getText();
            Division Division_ID = StateCombo.getValue();

        if (NameText.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank");
            alert.setHeaderText("Customer Name is Blank");
            alert.setContentText("Input Customer Name");
            alert.showAndWait();
        } else if (AddressText.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank");
            alert.setHeaderText("Address is Blank");
            alert.setContentText("Input Customer Address");
            alert.showAndWait();
        } else if (PostalText.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank");
            alert.setHeaderText("Postal Code is Blank");
            alert.setContentText("Input Customer Postal Code");
            alert.showAndWait();
        } else if (PhoneText.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank");
            alert.setHeaderText("Phone Number is Blank");
            alert.setContentText("Input Customer Phone Number");
            alert.showAndWait();
        } else if (CountryCombo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank");
            alert.setHeaderText("Country is Blank");
            alert.setContentText("Select Country");
            alert.showAndWait();
        } else if (StateCombo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Blank");
            alert.setHeaderText("Division is Blank");
            alert.setContentText("Select Division");
            alert.showAndWait();
        } else {
            DBCustomer.addCustomer(Customer_Name, Address, Postal_Code, Phone, Division_ID.getDivision_ID());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/CustomerMenu.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    /**
     * Cancel button
     * @param event Returns user back to customer menu
     * @throws IOException io
     */
    public void CancelButtonAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/CustomerMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * This variable filters the list of divisions
     * @return returns the division list filtered by country
     */
    public ObservableList<Division> StateFilter() {
        ObservableList<Division> allDivision = DBDivision.getAllDivision();
        return new FilteredList<>(allDivision, i -> i.getCountry_ID() == CountryCombo.getSelectionModel().getSelectedItem().getCountry_ID());
    }
}
