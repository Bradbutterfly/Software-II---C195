package Access;

import Model.Customer;
import Utilities.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Class provides user a path for connection to database
 */
public class DBCustomer {
    /**
     * Variable called to get customer list data from database
     */
    public static ObservableList<Customer> customerList = FXCollections.observableArrayList();
    /**
     * Variable called to get all customer data from database
     * @return All customer data
     */
    public static ObservableList<Customer> getAllCustomer() {
        ObservableList<Customer> CustomerList = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Division_ID, Country_ID FROM customers, first_level_divisions WHERE customers.Division_ID = first_level_divisions.Division_ID";
            PreparedStatement PS = JDBC.getConnection().prepareStatement(SQL);
            ResultSet RS = PS.executeQuery();
            while(RS.next()) {
                int Customer_ID = RS.getInt("Customer_ID");
                String Customer_Name = RS.getString("Customer_Name");
                String Address = RS.getString("Address");
                String Postal_Code = RS.getString("Postal_Code");
                String Phone = RS.getString("Phone");
                int Division_ID = RS.getInt("Division_ID");
                int Country_ID = RS.getInt("Country_ID");
                Customer C = new Customer(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID, Country_ID);
                CustomerList.add(C);
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return CustomerList;
    }
    /**
     * Adds customer data from database
     * @param Customer_Name name
     * @param Address address
     * @param Postal_Code postal
     * @param Phone phone
     * @param Division_ID division
     * @throws SQLException sql
     */
    public static void addCustomer(String Customer_Name, String Address, String Postal_Code, String Phone, int Division_ID) throws SQLException {
        String SQL = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (? , ?, ?, ?, ?)";
        PreparedStatement PS = JDBC.getConnection().prepareStatement(SQL);
        PS.setString(1, Customer_Name);
        PS.setString(2, Address);
        PS.setString(3, Postal_Code);
        PS.setString(4, Phone);
        PS.setInt(5, Division_ID);
        PS.executeUpdate();
    }
    /**
     * Updates customer data from database
     * @param Customer_Name name
     * @param Address address
     * @param Postal_Code postal
     * @param Phone phone
     * @param Customer_ID customer id
     */
    public static void updateCustomer(String Customer_Name, String Address, String Postal_Code, String Phone, Integer Division_ID, Integer Customer_ID)
    {
        try {
            String SQL = "UPDATE customers SET  Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=? WHERE Customer_ID=?";
            PreparedStatement PS = JDBC.getConnection().prepareStatement(SQL);
            PS.setString(1, Customer_Name);
            PS.setString(2, Address);
            PS.setString(3, Postal_Code);
            PS.setString(4, Phone);
            PS.setInt(5, Division_ID);
            PS.setInt(6, Customer_ID);
            PS.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Deletes Customer data from database
     * @param Customer_ID customer id
     * @throws SQLException sql
     */
    public static void deleteCustomer(Integer Customer_ID) throws SQLException {
        String SQL = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement PS = JDBC.getConnection().prepareStatement(SQL);
        PS.setInt(1, Customer_ID);
        PS.executeUpdate();
    }
}
