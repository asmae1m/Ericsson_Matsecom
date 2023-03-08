package ApiModule;

import DataStore.UserData;
import java.util.List;

public interface SessionManager {
	List<UserData> getUserList();
	void addUser(UserData user);
	void removeUser(int userIndex);
    void newSession(int userIndex, String serviceType, int time);
    List<InvoiceInformation> invoice();
    void saveData();
    
}