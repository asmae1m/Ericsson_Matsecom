import java.util.List;
public interface DataStore {
    
    List<UserData> UserData;
    // Method to save user data in JSON format
    void saveUserData(UserData userData);
    
    // Method to load user data from JSON format
    public UserData loadUser(String imsi);
    

}
