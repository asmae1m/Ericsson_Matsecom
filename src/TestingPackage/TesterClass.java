package TestingPackage;

import java.io.IOException;
import java.util.List;

import ApiModule.ApiModule;
import ApiModule.InvoiceInformation;
import Configuration.ConfigurationImp;
import DataStore.JsonDataStore;
import DataStore.UserData;

public class TesterClass {
	public static void main(String[] args) throws IOException {

		ConfigurationImp config = new ConfigurationImp();
		JsonDataStore dataStore = new JsonDataStore();

		ApiModule api = new ApiModule(config, dataStore);

		List<UserData> userList = api.getUserList();
		System.out.println("Initial User List: " + userList);

		UserData newUser = new UserData("Asmae", "Mahjoubi", "124565645", "PhairPhone", "Subscription A");
		api.addUser(newUser);
		System.out.println("we've added the user: " + newUser);
		System.out.println("The user List is : " + api.getUserList());

		api.removeUser(1);
		System.out.println("i've removed user: " + api.getUserList());

		api.addUser(newUser);
		System.out.println("User added: " + newUser);
		api.newSession(1, "Browsing and social networking", 10);
		System.out.println("Data session created for user: " + newUser);

		api.newSession(1, "Voice call", 30);
		System.out.println("Voice session created for user: " + newUser);

		List<InvoiceInformation> invoices = api.invoice();
		System.out.println("Invoices generated: " + invoices);

		// Test saveData method
		api.saveData();
		System.out.println("Data saved.");

	}

}
