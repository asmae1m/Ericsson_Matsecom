package DataStore;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class JsonDataStore implements DataStore {

	private ArrayList<UserData> UserList = new ArrayList<>();

	public JsonDataStore() throws IOException {
		loadUser();
	}

	@Override
	public void saveUserData(UserData userData) {
		boolean exists = false;
		try {
			// Add the new UserData object to the existing userList
			for (int i = 0; i < UserList.size(); i++) {
				UserData elem = UserList.get(i);
				if (elem.getImsi().equals(userData.getImsi())) {
					UserList.set(i, userData);
					exists = true;
					break;
				}
			}
			if (!exists) {
				UserList.add(userData);
			}

			// Create a new instance of ObjectMapper
			ObjectMapper objectMapper = new ObjectMapper();

			// Write the updated userList to the JSON file
			objectMapper.writeValue(new File("userdata.json"), UserList);

		} catch (IOException e) {
			System.err.println("Error saving user data to JSON file: " + e.getMessage());
		}
	}

	@Override
	public void loadUser() {
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
			UserList.addAll(loadedUserData);

		} catch (IOException e) {
			System.err.println("Error loading user data from JSON file: " + e.getMessage());
		}
	}

}