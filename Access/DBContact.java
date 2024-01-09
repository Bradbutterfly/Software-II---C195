package Access;

import Model.Contact;
import Utilities.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Class provides user a path for connection to database
 */
public class DBContact {
        public static ObservableList<Contact> getAllContact() throws SQLException {
            ObservableList<Contact> ContactList = FXCollections.observableArrayList();
                String SQL = "SELECT * from contacts";
                PreparedStatement PS = JDBC.getConnection().prepareStatement(SQL);
                ResultSet RS = PS.executeQuery();
                while(RS.next()) {
                    int Contact_ID = RS.getInt("Contact_ID");
                    String Contact_Name = RS.getString("Contact_Name");
                    String Email = RS.getString("Email");
                    Contact contact = new Contact(Contact_ID, Contact_Name, Email);
                    ContactList.add(contact);
                }
            return ContactList;
        }
    }
