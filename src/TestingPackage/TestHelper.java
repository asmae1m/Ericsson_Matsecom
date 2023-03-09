package TestingPackage;

import java.io.IOException;
import Configuration.*;
import DataStore.*;

public class TestHelper {
	
	public static UserData getDefaultTestUser() {
		return new UserData("testForename", "testSurname", "123456789012345", "testTerminalType", "testSubscriptionType");
	}
	
	public static Configuration getDefaultTestConfiguration() {
		return new ConfigurationImp();
	}
	
	public static DataStore getDefaultTestDataStore() {
		try {
			return new JsonDataStore();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
