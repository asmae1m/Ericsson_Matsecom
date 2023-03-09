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

	public JsonDataStore() throws IOException {
		loadUsers();
	}

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

	@Override
	public ArrayList<UserData> loadUsers() {
		ArrayList<UserData> Result = new ArrayList<UserData>();
		try {
			// Create a new instance of ObjectMapper
			ObjectMapper objectMapper = new ObjectMapper();

			// Read the contents of the JSON file into a string
			String jsonString = new String(Files.readAllBytes(Paths.get("userdata.json")));

			// Convert the JSON string to a list of UserData objects
			TypeReference<ArrayList<UserData>> typeRef = new TypeReference<ArrayList<UserData>>() {
			};
			ArrayList<UserData> loadedUserData = objectMapper.readValue(jsonString, typeRef);

			// Add the loaded UserData objects to the existing userList
			Result.addAll(loadedUserData);

		} catch (IOException e) {
			System.err.println("Error loading user data from JSON file: " + e.getMessage());
		}

		return Result;
	}

}