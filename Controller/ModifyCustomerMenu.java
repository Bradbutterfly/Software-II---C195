package Controller;

import Access.DBCountries;
import Access.DBCustomer;
import Access.DBDivision;
import Model.Countries;
import Model.Customer;
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
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ModifyCustomerMenu implements Initializable {
    Parent scene;
    Stage stage;
    /**
     * Labels
     */
    public Label CountryLabel;
    public Label PhoneLabel;
    public Label StateLabel;
    public Label ModifyCustomerLabel;
    public Label IDLabel;
    public Label NameLabel;
    public Label AddressLabel;
    public Label PostalLabel;
    /**
     * Text fields
     */
    public TextField PostalText;
    public TextField PhoneText;
    public TextField IDText;
    public TextField NameText;
    public TextField AddressText;
    /**
     * Buttons
     */
    public Button SaveButtonLabel;
    public Button CancelButtonLabel;
    /**
     * Combo boxes
     */
    public ComboBox<Countries>CountryCombo;
    public ComboBox<Division>StateCombo;
    /**
     * Override
     * @param url url
     * @param resourceBundle rb
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CountryCombo.setVisibleRowCount(5);
        StateCombo.setVisibleRowCount(5);
        CountryCombo.setItems(DBCountries.getAllCountries());
        StateCombo.setItems(DBDivision.getAllDivision());
    }
    /**
     * This variable calls the division filter
     */
    public void CountryComboAction() {
        StateCombo.setItems(StateFilter());
    }
    /**
     * Save button
     * @param event Saves modified data
     * @throws IOException io
     */
    public void SaveButtonAction(ActionEvent event) throws IOException {
            String Customer_Name = NameText.getText();
            String Address = AddressText.getText();
            String Postal_Code = PostalText.getText();
            String Phone = PhoneText.getText();
            Division Division_ID = StateCombo.getValue();
            int Customer_ID = Integer.parseInt(IDText.getText());

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
            DBCustomer.updateCustomer(Customer_Name, Address, Postal_Code, Phone, Division_ID.getDivision_ID(), Customer_ID);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/CustomerMenu.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    /**
     * Cancel button
     * @param event Sends user to customer menu
     * @throws IOException io
     */
    public void CancelButtonAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/CustomerMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * Updates division list for current country selected
     * @param Country_ID id
     */
    public void UpdateDivisionList (int Country_ID) {
        StateCombo.setItems(DBDivision.getAllDivision().stream().filter(f -> f.getCountry_ID() == Country_ID).collect(Collectors.toCollection(FXCollections::observableArrayList)));
    }
    /**
     * This variable filters the list of divisions
     * @return returns filtered divisions
     */
    public ObservableList<Division> StateFilter() {
        ObservableList<Division> allDivision = DBDivision.getAllDivision();
        return new FilteredList<>(allDivision, i -> i.getCountry_ID() == CountryCombo.getSelectionModel().getSelectedItem().getCountry_ID());
    }
    /**
     * When customer is selected this variable will be used
     * @param customer customer
     */
    public void CustomerSelected (Customer customer) {
        IDText.setText(String.valueOf(customer.getCustomer_ID()));
        NameText.setText(customer.getCustomer_Name());
        AddressText.setText(customer.getAddress());
        PostalText.setText(customer.getPostal_Code());
        PhoneText.setText(customer.getPhone());
        for (Countries C : CountryCombo.getItems()) {
            if (customer.getCountry_ID() == C.getCountry_ID()) {
                CountryCombo.setValue(C);
                break;
            }
        }
        for (Division D : StateCombo.getItems()) {
            if (customer.getDivision_ID() == D.getDivision_ID()) {
                StateCombo.setValue(D);
                break;
            }
        }
    }
}
