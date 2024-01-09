package Access;

import Model.Appointment;
import Utilities.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 * Class provides user a path for connection to database
 */
public class DBAppointment {
    /**
     * Variables called to get appointment list and all appointments from database
     */
    public static ObservableList<Appointment> AppointmentsList = FXCollections.observableArrayList();
    public static ObservableList<Appointment> getAllAppointments() {
        return AppointmentsList;
    }
    /**
     * Variable called to get all appointments from database
     */
    public static ObservableList<Appointment> getAllAppointment() {
        ObservableList<Appointment> AppointmentList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM appointments";
            PreparedStatement PS = JDBC.getConnection().prepareStatement(sql);
            ResultSet RS = PS.executeQuery();
            while (RS.next()) {
                int Appointment_ID = RS.getInt("Appointment_ID");
                String Title = RS.getString("Title");
                String Description = RS.getString("Description");
                String Location = RS.getString("Location");
                String Type = RS.getString("Type");
                LocalDateTime Start = RS.getTimestamp("Start").toLocalDateTime();
                LocalDateTime End = RS.getTimestamp("End").toLocalDateTime();
                int Customer_ID = RS.getInt("Customer_ID");
                int User_ID = RS.getInt("User_ID");
                int Contact_ID = RS.getInt("Contact_ID");
                Appointment A = new Appointment(Appointment_ID, Title, Description, Location, Type, Start, End, Contact_ID, Customer_ID, User_ID);
                AppointmentList.add(A);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return AppointmentList;
    }
    /**
     * Method called to add appointment to database
     * @param Title title
     * @param Description description
     * @param Location location
     * @param Type type
     * @param Start start
     * @param End end
     * @param Customer_ID customer id
     * @param User_ID user id
     * @param Contact_ID contact id
     * @throws SQLException sql
     */
    public static void addAppointment(String Title, String Description, String Location, String Type, LocalDateTime Start, LocalDateTime End,
                                      Integer Customer_ID, Integer User_ID, Integer Contact_ID) throws SQLException {
            String SQL = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement PS = JDBC.getConnection().prepareStatement(SQL);
            PS.setString(1, Title);
            PS.setString(2, Description);
            PS.setString(3, Location);
            PS.setString(4, Type);
            PS.setTimestamp(5, Timestamp.valueOf(Start));
            PS.setTimestamp(6, Timestamp.valueOf(End));
            PS.setInt(7, Customer_ID);
            PS.setInt(8, User_ID);
            PS.setInt(9, Contact_ID);
            PS.executeUpdate();
    }
    /**
     * Method to update appointment
     * @param Title title
     * @param Description description
     * @param Location location
     * @param Type type
     * @param Start start
     * @param End end
     * @param Customer_ID customer id
     * @param User_ID user id
     * @param Contact_ID contact id
     * @param Appointment_ID appointment id
     */
    public static void updateAppointment(String Title, String Description, String Location, String Type, LocalDateTime Start, LocalDateTime End, Integer Customer_ID, Integer User_ID, Integer Contact_ID, String Appointment_ID) {
        try {
            String SQL = "UPDATE appointments " +  "SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, " +  "Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID=?";
            PreparedStatement PS  = JDBC.getConnection().prepareStatement(SQL);
            PS.setString(1, Title);
            PS.setString(2, Description);
            PS.setString(3, Location);
            PS.setString(4, Type);
            PS.setTimestamp(5, Timestamp.valueOf(Start));
            PS.setTimestamp(6, Timestamp.valueOf(End));
            PS.setInt(7, Customer_ID);
            PS.setInt(8, User_ID);
            PS.setInt(9, Contact_ID);
            PS.setString(10, Appointment_ID);
            PS.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Deletes appointment data
     * @param Appointment_ID appointment id
     * @throws SQLException sql
     */
    public static void deleteAppointment(Integer Appointment_ID) throws SQLException {
            String SQL = "DELETE FROM appointments WHERE Appointment_ID = (?)";
            PreparedStatement PS = JDBC.getConnection().prepareStatement(SQL);
            PS.setInt(1, Appointment_ID);
            PS.executeUpdate();
    }
    /**
     * Appointment weekly
     * @return weekly appointment returned
     */
    public static ObservableList<Appointment> getAppointmentWeek() {
        ObservableList<Appointment> AppointmentList = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT * FROM appointments WHERE WEEK(Start) = WEEK(current_date())";
            PreparedStatement PS = JDBC.getConnection().prepareStatement(SQL);
            ResultSet RS = PS.executeQuery();
            while (RS.next()) {
                int Appointment_ID = RS.getInt("Appointment_ID");
                String Title = RS.getString("Title");
                String Description = RS.getString("Description");
                String Location = RS.getString("Location");
                String Type = RS.getString("Type");
                LocalDateTime Start = RS.getTimestamp("Start").toLocalDateTime();
                LocalDateTime End = RS.getTimestamp("End").toLocalDateTime();
                int Customer_ID = RS.getInt("Customer_ID");
                int User_ID = RS.getInt("User_ID");
                int Contact_ID = RS.getInt("Contact_ID");
                Appointment A = new Appointment(Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID);
                AppointmentList.add(A);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return AppointmentList;
    }
    /**
     * Appointment monthly
     * @return Monthly appointments are returned
     */
    public static ObservableList<Appointment> getAppointmentMonth() {
        ObservableList<Appointment> AppointmentList = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT * FROM appointments WHERE MONTH(Start) = MONTH(current_date())";
            PreparedStatement PS = JDBC.getConnection().prepareStatement(SQL);
            ResultSet RS = PS.executeQuery();
            while (RS.next()) {
                int Appointment_ID = RS.getInt("Appointment_ID");
                String Title = RS.getString("Title");
                String Description = RS.getString("Description");
                String Location = RS.getString("Location");
                String Type = RS.getString("Type");
                LocalDateTime Start = RS.getTimestamp("Start").toLocalDateTime();
                LocalDateTime End = RS.getTimestamp("End").toLocalDateTime();
                int Customer_ID = RS.getInt("Customer_ID");
                int User_ID = RS.getInt("User_ID");
                int Contact_ID = RS.getInt("Contact_ID");
                Appointment a = new Appointment(Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID);
                AppointmentList.add(a);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return AppointmentList;
    }
    /**
     * Variable to get number of appointments today.
     * @return number of appointments
     */
    public static Integer getAppointmentToday() {
        int count = 0;
        try {
            LocalDate LD = LocalDate.now();
            System.out.println(LD);
            String SQL = "SELECT Start FROM appointments ";
            PreparedStatement PS = JDBC.getConnection().prepareStatement(SQL);
            ResultSet RS = PS.executeQuery();
            while (RS.next()) {
                LocalDate d = RS.getTimestamp("Start").toLocalDateTime().toLocalDate();
                if (d.equals(LD)) {
                    count++;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count;
    }
    /**
     * Variable to get number of appointments by type and month.
     * @param Type type of appointment
     * @param Month month of appointment
     * @return number of appointments
     */
    public static Integer getAppointmentMonthAndType(Integer Month, String Type) {
        int count = 0;
        try {
            String SQL = "SELECT COUNT(*) FROM appointments WHERE Type = ? AND MONTH(Start) = ?";
            PreparedStatement PS = JDBC.getConnection().prepareStatement(SQL);
            PS.setString(1, Type);
            PS.setInt(2, Month);
            ResultSet RS = PS.executeQuery();
            while (RS.next()) {
                count = RS.getInt("COUNT(*)");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return count;
    }
}



