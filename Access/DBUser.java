package Access;

import Model.User;
import Utilities.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class provides user a path for connection to database
 */
public class DBUser {
    public static ObservableList<User> getAllUser() throws SQLException {
        ObservableList<User> UserList = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM Users";
        PreparedStatement PS = JDBC.getConnection().prepareStatement(SQL);
        ResultSet RS = PS.executeQuery();
        while (RS.next()) {
            int User_ID = RS.getInt("User_ID");
            String User_Name = RS.getString("User_Name");
            String Password = RS.getString("Password");
            User U = new User(User_ID, User_Name, Password);
            UserList.add(U);
        }
        return UserList;
    }
    /**
     * Validates log in
     * @param User_Name user
     * @param Password password
     * @return Log in credentials
     */
    public static Boolean validate_Login(String User_Name, String Password) {
        int count = 0;
        try {
            String SQL = "SELECT COUNT(*) FROM users WHERE User_Name = ? AND Password = ?";
            PreparedStatement PS = JDBC.getConnection().prepareStatement(SQL);
            PS.setString(1, User_Name);
            PS.setString(2, Password);
            ResultSet RS = PS.executeQuery();
            while (RS.next()) {
                count = RS.getInt("COUNT(*)");
            }
            if (count != 0) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return false;
    }
}
