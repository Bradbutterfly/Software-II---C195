package Model;
/**
 * Variables for user
 */
public class User {
    private int User_ID;
    private String User_Name;
    private String Password;
    /**
     * Constructors for user
     * @param User_ID id
     * @param User_Name name
     * @param Password password
     */
    public User(int User_ID, String User_Name, String Password) {
        this.User_ID = User_ID;
        this.User_Name = User_Name;
        this.Password = Password;
    }
    public User() {
    }
    /**
     * Setters & Getters for user
     */
    public int getUser_ID() {
        return User_ID;
    }
    public String getUser_Name() {
        return User_Name;
    }
    public String getPassword() {
        return Password;
    }

    public void setUser_ID(int user_ID) {
        this.User_ID = user_ID;
    }
    public void setUser_Name(String user_Name) {
        this.User_Name = user_Name;
    }
    public void setPassword(String password) {
        this.Password = password;
    }
    /**
     * Converts data into readable content for combo boxes
     */
    @Override
    public String toString(){
        return getUser_ID() + " " + getUser_Name();
    }
}