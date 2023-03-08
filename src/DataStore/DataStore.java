package DataStore;

import java.util.List;
public interface DataStore {
    
    public List<UserData> loadUsers();
    public void saveUsers(List<UserData> users);

}
