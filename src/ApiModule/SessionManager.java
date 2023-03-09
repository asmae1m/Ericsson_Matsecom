/**
 * SessionManager Interface
 * 
 * Use this to integrate our software into your system.
 * The default implementation is ApiModule.
 * 
 * @author ALARA
 * 
 * @since 1.0
 */

package ApiModule;

import DataStore.UserData;
import java.util.List;

public interface SessionManager {
	/**
	 * Get list of all users.
	 * 
	 * @return List of UserData of all subscribers.
	 * 
	 * @since 1.0
	 */
	List<UserData> getUserList();
	
	/**
	 * add a new user
	 * 
	 * @param data of user to be added.
	 * 
	 * @throws UserAlreadyExistsException if a User with the same IMSI already exists.
	 * 
	 * @since 1.0
	 */
	void addUser(UserData user) throws UserAlreadyExistsException;
	
	/**
	 * remove a user
	 * 
	 * @param userIndex: Index of subscriber to be removed.
	 * 
	 * @throws UserIndexOutOfBoundsException if an invalid userIndex is entered.
	 * 
	 * @since 1.0
	 */
	void removeUser(int userIndex) throws UserIndexOutOfBoundsException;
	
	/**
	 * updates subscriber data based on session:
	 * - voice minutes used, taking free minutes into account
	 * - data used
	 * 
	 * @param userIndex: index of subscriber who has the session
	 * @param serviceType: name of the service to be used
	 * @param time: time spent using the service in minutes
	 * 
	 * @throws NotEnoughDataVolumeException if there is not enough data volume left for the subscriber
	 * IMPORTANT: This depends on latency and therefore may happen inconsistently!
	 * 
	 * @since 1.0
	 */
    void newSession(int userIndex, String serviceType, int time) throws NotEnoughDataVolumeException;
    
    /**
	 * updates subscriber data based on session data:
	 * - voice minutes used, taking free minutes into account
	 * - data used
	 * 
	 * @param userIndex: index of subscriber who has the session
	 * @param serviceType: name of the service to be used
	 * @param time: time spent using the service in minutes
	 * 
	 * @throws UserIndexOutOfBoundsException if an invalid userIndex is entered.
	 * @throws NotEnoughDataVolumeException if there is not enough data volume left for the subscriber
	 * IMPORTANT: This depends on latency and therefore may happen inconsistently!
	 * 
	 * @since 1.0
	 */
    List<InvoiceInformation> invoice();
    
    /**
     * save the current data
     * - First creates a backup of the existing data.
     * - Then updates the subscriber data.
     * 
     * This should always be called before exiting.
     * 
     * @since 1.0
     */
    void saveData();
    
}