package Model;

import java.time.LocalDateTime;
/**
 * Variables for Appointment
 */
public class Appointment {
    private int Appointment_ID;
    private String Title;
    private String Description;
    private String Location;
    private String Type;
    private LocalDateTime Start;
    private LocalDateTime End;
    private int Contact_ID;
    private int Division;
    private int Customer_ID;
    private int User_ID;
    /**
     * Constructors for appointment
     * @param Appointment_ID id
     * @param Title title
     * @param Description description
     * @param Location location
     * @param Type type
     * @param Start start
     * @param End end
     * @param Contact_ID contact
     * @param Customer_ID customer id
     */
    public Appointment(int Appointment_ID, String Title, String Description, String Location, String Type, LocalDateTime Start, LocalDateTime End, int Contact_ID, int Customer_ID, int User_ID) {
        this.Appointment_ID = Appointment_ID;
        this.Title = Title;
        this.Description = Description;
        this.Location = Location;
        this.Type = Type;
        this.Start = Start;
        this.End = End;
        this.Contact_ID = Contact_ID;
        this.Customer_ID = Customer_ID;
        this.User_ID = User_ID;
    }
    /**
     * Setters and Getters for appointment
     */
    public int getAppointment_ID() {
        return Appointment_ID;
    }
    public String getTitle() {
        return Title;
    }
    public String getDescription() {
        return Description;
    }
    public String getLocation() {
        return Location;
    }
    public String getType() {
        return Type;
    }
    public LocalDateTime getStart() {
        return Start;
    }
    public LocalDateTime getEnd() {
        return End;
    }
    public int getContact_ID() {
        return Contact_ID;
    }
    public int getCustomer_ID() {
        return Customer_ID;
    }
    public int getUser_ID() {
        return User_ID;
    }

    public void setAppointment_ID(int Appointment_ID) {
        this.Appointment_ID = Appointment_ID;
    }
    public void setTitle(String Title) {
        this.Title = Title;
    }
    public void setDescription(String Description) {
        this.Description = Description;
    }
    public void setLocation(String Location) {
        this.Location = Location;
    }
    public void setType(String Type) {
        this.Type = Type;
    }
    public void setStart(LocalDateTime Start) {
        this.Start = Start;
    }
    public void setEnd(LocalDateTime End) {
        this.End = End;
    }
    public void setContact_ID(int Contact_ID) {
        this.Contact_ID = Contact_ID;
    }
    public void setCustomer_ID(int Customer_ID) {
        this.Customer_ID = Customer_ID;
    }
    public void setUser_ID(int User_ID) {
        this.User_ID = User_ID;
    }
}
