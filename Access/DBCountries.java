package Access;

import Model.Countries;
import Utilities.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Class provides user a path for connection to database
 */
public class DBCountries {
    public static ObservableList<Countries> getAllCountries() {
        ObservableList<Countries> CountryList = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT * FROM countries";
            PreparedStatement PS = JDBC.getConnection().prepareStatement(SQL);
            ResultSet RS = PS.executeQuery();
            while (RS.next()) {
                int Country_ID = RS.getInt("Country_ID");
                String Country = RS.getString("Country");
                Countries C = new Countries(Country_ID, Country);
                CountryList.add(C);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return CountryList;
    }
}