/**
 * The JsonDataStore class implements the DataStore interface to provide methods for loading
 * and saving a list of UserData objects to a JSON file.
 */
package DataStore;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonDataStore implements DataStore {
	
	private String fileName;

    /**
     * Creates a new JsonDataStore instance and loads the list of users from the specified JSON file.
     *
     * @throws IOException if there is an error reading the JSON file
     */
    public JsonDataStore(String fileName) throws IOException {
    	this.fileName = fileName;
        loadUsers();
    }
    
    /**
     * Creates a new JsonDataStore instance and loads the list of users from the default JSON file.
     *
     * @throws IOException if there is an error reading the JSON file
     */
    public JsonDataStore() throws IOException {
    	this.fileName = "userdata.json";
        loadUsers();
    }

    /**
     * Saves the specified list of UserData objects to the default JSON file.
     *
     * @param users the list of UserData objects to be saved to the JSON file
     */
    @Override
    public void saveUsers(List<UserData> users) {
        try {
            // Create a new instance of ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Write the updated userList to the JSON file
            objectMapper.writeValue(new File("userdata.json"), users);

        } catch (IOException e) {
            System.err.println("Error saving user data to JSON file: " + e.getMessage());
        }
    }

    /**
     * Loads the list of users from a JSON file.
     *
     * @return the list of UserData objects loaded from the JSON file
     */
    @Override
    public ArrayList<UserData> loadUsers() {
        ArrayList<UserData> Result = new ArrayList<UserData>();
        try {
            // Create a new instance of ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Read the contents of the JSON file into a string
            String jsonString = new String(Files.readAllBytes(Paths.get(fileName)));

            // Convert the JSON string to a list of UserData objects
            TypeReference<ArrayList<UserData>> typeRef = new TypeReference<ArrayList<UserData>>() {};
            ArrayList<UserData> loadedUserData = objectMapper.readValue(jsonString, typeRef);

            // Add the loaded UserData objects to the existing userList
            Result.addAll(loadedUserData);

        } catch (IOException e) {
            System.err.println("Error loading user data from JSON file: " + e.getMessage());
        }

        return Result;
    }
}
