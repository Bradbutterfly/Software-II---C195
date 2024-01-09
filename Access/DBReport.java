//NOT SURE WHAT THIS IS CONNECTED TO??

package Access;

import Model.Report;
import Utilities.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class provides user a path for connection to database
 */
public class DBReport {
    /**
     * Variables that can be called to get specific data
     */
    public static ObservableList<Report> ReportList = FXCollections.observableArrayList();
    public static ObservableList<Report> getAllReports() {
        return ReportList;
    }
    /**
     * Variable called to populate report with data from database
     */
    public static void getAllReport() throws SQLException {
        ReportList.clear();
        String SQL = "select CustomerId, ContactId, Type, month_name(start) as 'Month', count(*) as 'Total' From client_schedule.appointments group by Type, MONTH(start), CustomerId;";
        PreparedStatement PS = JDBC.getConnection().prepareStatement(SQL);
        ResultSet RS = PS.executeQuery();
        while (RS.next()) {
            String Month = RS.getString("Month");
            String Type = RS.getString("Type");
            String Total = RS.getString("Total");
            int Customer_ID = RS.getInt("CustomerId");
            int Contact_ID = RS.getInt("ContactId");
            Report report = new Report(Month, Type, Total, Contact_ID, Customer_ID);
            ReportList.add(report);
        }
    }
}
