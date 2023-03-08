import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonDataStore implements DataStore {

    private List<UserData> userDataList = new ArrayList<>();

    public JsonDataStore() {
        try {
            loadUserData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUserData(UserData userData) {
        // Implement the code to save user data in JSON format
    }

    @Override
    public UserData loadUser() {
        // Implement the code to load user data in JSON format
        return null;
    }

    private void loadUserData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("userdata.json");
        if (!file.exists()) {
            return;
        }

        userDataList = mapper.readValue(file, new TypeReference<List<UserData>>() {});
    }
}