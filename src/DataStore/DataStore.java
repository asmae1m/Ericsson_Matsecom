package DataStore;

import java.util.ArrayList;
public interface DataStore {
    
    public static final ArrayList<UserData> UserList = null;
    // Method to save user data in JSON format
    void saveUserData(UserData userData);
    
    // Method to load user data from JSON format
    public void loadUser();
    

}
