package Access;

import Model.Division;
import Utilities.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Class provides user a path for connection to database
 */
public class DBDivision {
    public static ObservableList<Division> getAllDivision() {
        ObservableList<Division> divisionList = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT * FROM first_level_divisions";
            PreparedStatement PS = JDBC.getConnection().prepareStatement(SQL);
            ResultSet RS = PS.executeQuery();
            while (RS.next()) {
                int Division_ID = RS.getInt("Division_ID");
                String Division = RS.getString("Division");
                int Country_ID = RS.getInt("Country_ID");
                Division D = new Division(Division_ID, Division, Country_ID);
                divisionList.add(D);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return divisionList;
    }
}