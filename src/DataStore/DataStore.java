/**
 * The DataStore interface provides methods for loading and saving a list of
 * UserData objects.
 */
package DataStore;

import java.util.List;

public interface DataStore {
    /**
     * Returns a List of UserData objects representing the users stored in the data store.
     *
     * @return a List of UserData objects representing the users stored in the data store
     */
    public List<UserData> loadUsers();

    /**
     * Saves the specified list of UserData objects to the data store.
     *
     * @param users the list of UserData objects to be saved to the data store
     */
    public void saveUsers(List<UserData> users);
}
