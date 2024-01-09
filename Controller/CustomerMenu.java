package Controller;

import Access.DBCustomer;
import Model.Customer;
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

public class CustomerMenu implements Initializable {
    Parent scene;
    Stage stage;
    /**
     * Label
     */
    public Label CustomerLabel;
    /**
     * Table view
     */
    public TableView<Customer> CustomerTable;
    /**
     * Table column
     */
    public TableColumn<Customer, Integer> DivisionColumn;
    public TableColumn<Customer, Integer> IDColumn;
    public TableColumn<Customer, String> NameColumn;
    public TableColumn<Customer, String> AddressColumn;
    public TableColumn<Customer, String> PostalColumn;
    public TableColumn<Customer, String> PhoneColumn;
    /**
     * Button
     */
    public Button AddButtonLabel;
    public Button ModifyButtonLabel;
    public Button ReturnButtonLabel;
    public Button DeleteButtonLabel;

    /**
     * Override
     *
     * @param url            url
     * @param resourceBundle rb
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CustomerTable.setItems(DBCustomer.getAllCustomer());
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
        AddressColumn.setCellValueFactory(new PropertyValueFactory<>("Address"));
        PostalColumn.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
        PhoneColumn.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        DivisionColumn.setCellValueFactory(new PropertyValueFactory<>("Division_ID"));
    }

    /**
     * Return button
     *
     * @param event Sends user back to main menu
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
     *
     * @param event Sends user to add menu
     * @throws IOException io
     */
    public void AddButtonAction(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AddCustomerMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Modify button
     *
     * @param event Sends user to modify menu
     * @throws IOException io
     */
    public void ModifyButtonAction(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyCustomerMenu.fxml"));
            loader.load();

            ModifyCustomerMenu modifyCustomerMenu = loader.getController();
            modifyCustomerMenu.CustomerSelected(CustomerTable.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Selection Error");
            alert.setContentText("No Customer Selected");
            alert.showAndWait();
        }
    }
    /**
     * Delete button
     * @param event Deletes selected data
     * @throws SQLException sql
     */
    public void DeleteButtonAction(ActionEvent event) throws SQLException, IOException {
        Customer SelectedCustomer = CustomerTable.getSelectionModel().getSelectedItem();
        if (SelectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Selection Error");
            alert.setContentText("No Customer Selected");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure?");
            alert.setHeaderText("Delete " + SelectedCustomer.getCustomer_Name() + " and any associated appointments.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElseThrow() == ButtonType.OK) {
                DBCustomer.deleteCustomer(CustomerTable.getSelectionModel().getSelectedItem().getCustomer_ID());
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/CustomerMenu.fxml")));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
    }
}
