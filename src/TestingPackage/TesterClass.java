package TestingPackage;


import java.io.IOException;
import java.util.List;

import ApiModule.ApiModule;
import ApiModule.InvoiceInformation;
import Configuration.ConfigurationImp;
import Configuration.Ran;
import Configuration.SessionType;
import DataStore.JsonDataStore;
import DataStore.UserData;

public class TesterClass {
	public static void main(String args[]) throws IOException {
		
	
	ConfigurationImp config = new ConfigurationImp();
    JsonDataStore dataStore = new JsonDataStore();
    
    
    ApiModule api = new ApiModule(config, dataStore);
    
    List<UserData> userList = api.getUserList();
    System.out.println("Initial User List: " + userList);
    System.out.println("the string value is : "+Ran.twoG.getStringValue());
    
    
    UserData newUser = new UserData("Asmae", "Mahjoubi", "114565645",Ran.twoG.getStringValue(), "Subscription A");
    api.addUser(newUser);
    System.out.println("we've added the user: " + newUser.getImsi());
    System.out.println("The user List is : " + api.getUserList());
    
    /*api.removeUser(1);
    System.out.println("i've removed user: " + api.getUserList());*/
    
    UserData newUser1 = new UserData("Person", "Good", "114465645",Ran.twoG.getStringValue(), "Subscription B");
    api.addUser(newUser1);
    
    System.out.println("User added: " + newUser1);
    
    api.newSession(1, "Voice call", 20);
    System.out.println("Data session created for user: " + newUser);
    
    
    
    List<InvoiceInformation> invoices = api.invoice();
    System.out.println("Invoices generated: " + invoices);
    
    // Test saveData method
    api.saveData();
    System.out.println("Data saved.");
    
    
    
    
    
    
  
	}

}
