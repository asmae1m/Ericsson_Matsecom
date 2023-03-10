package SessionSimulator;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import ApiModule.*;
import Configuration.*;
import DataStore.*;


public class SessionSimulator {
	
	private Configuration config;
	private SessionManager api;
	private Scanner scanner;
	
	public SessionSimulator() throws IOException {
		 config = new ConfigurationImp();
		 JsonDataStore dataStore = new JsonDataStore();
		 api = new ApiModule(config, dataStore);
		 scanner = new Scanner(System.in);
	}
	
	public void mainLoop() {
		boolean exit = false;
		while(!exit) {
			// Prompt user for which method to test
			System.out.println();
	        System.out.println("What do you want to do?");
	        System.out.println();
	        System.out.println("1. User List");
	        System.out.println("2. Edit User");
	        System.out.println("3. add User");
	        System.out.println("4. remove User");
	        System.out.println("5. new Session");
	        System.out.println("6. invoice");
	        System.out.println("7. exit");
	        
	        String input = scanner.nextLine();

	        switch(input) {
	            case "1":
	            	System.out.println();
	                showUserList(false);
	                break;
	            case "2":
	                editUser();
	                break;
	            case "3":
	                addUser();
	                break;
	            case "4":
	                removeUser();
	                break;
	            case "5":
	                newSession();
	                break;
	            case "6":
	            	invoice();
	                break;
	            case "7":
	            	api.saveData();
	            	exit=true;
	                break;
	            default:
	                System.out.println("Invalid option");
	                break;
	        }

		}
		
		scanner.close();
		
	}
	
    public static void main(String[] args) throws IOException {
        SessionSimulator sim = new SessionSimulator();
        displayStartupMessage();
        sim.mainLoop(); 
    }

    private String getUserDisplay(UserData user) {
    	return user.getForename() + " " + user.getSurname(); //TODO!!!
    }
    
    private String getInvoiceDisplay(InvoiceInformation invoice) {
    	return invoice.forename + " " + invoice.surname; //TODO!!!!
    }
    
    
	private void showUserList(boolean showIndex) {
        List<UserData> users = api.getUserList();
        for (int i=0; i<users.size(); i++) {
        	if (showIndex) {
        		System.out.println(Integer.toString(i) + ": " + getUserDisplay(users.get(i)));
        	} else {
        		System.out.println(getUserDisplay(users.get(i)));
        	}
            
        }
	}
	
	private int selectUser() {
		while(true) {
			System.out.println();
			System.out.println("Please select a user (enter the number on the left)");
			showUserList(true);
			try {
		        int option = scanner.nextInt();scanner.nextLine();
		        api.getUserList().get(option);
		        return option;
			} catch(RuntimeException e) {
				System.out.println("Invalid input, try again!");
			}
		}
	}
	
	private String selectTypeFromList(List<String> list, String prompt, String defaultValue) {
		while(true) {
			System.out.println(prompt);
			for (int i=0; i<list.size(); i++) {
				System.out.println(Integer.toString(i)+": "+list.get(i));
			}
			
	        String input = scanner.nextLine();
	        if (defaultValue != null && "".equals(input)) {
	        	return defaultValue;
	        }
			try {
		        return list.get(Integer.parseInt(input));
			} catch(RuntimeException e) {
				System.out.println("Invalid input, try again!");
			}
		}
	}
	
    private void editUser() {
    	
    	int userIndex = selectUser();
    	UserData user = api.getUserList().get(userIndex);
    	
        System.out.println("Enter new forename (leave empty to keep current value):");
        String forename = scanner.nextLine();
        if (forename.equals("")) {
        	forename = user.getForename();
        }
        System.out.println("Enter new surname (leave empty to keep current value):");
        String surname = scanner.nextLine();
        if (surname.equals("")) {
        	surname = user.getSurname();
        }
        
        String subscriptionType = selectTypeFromList(config.getPossibleSubscriptionTypes(), "Select new subscription:", user.getSubscriptionType());
        String terminalType = selectTypeFromList(config.getPossibleTerminalTypes(), "Select new terminal:", user.getTerminalType());

        InvoiceInformation invoice = api.updateUserData(userIndex, forename, surname, subscriptionType, terminalType);
        System.out.println("User edited successfully:");
        System.out.println(getUserDisplay(user));
        System.out.println("New Invoice:");
        System.out.println(getInvoiceDisplay(invoice));
    }
    
    private void addUser() {
    	System.out.println();
        System.out.println("Enter user information:");
        System.out.print("Forname: ");
        String forname = scanner.nextLine();
        System.out.print("Surname: ");
        String surname = scanner.nextLine();
        System.out.print("IMSI: ");
        String imsi = scanner.nextLine();
        
        String subscriptionType = selectTypeFromList(config.getPossibleSubscriptionTypes(), "Select new subscription:", null);
        String terminalType = selectTypeFromList(config.getPossibleTerminalTypes(), "Select new terminal:", null);

        UserData newUser = new UserData(forname,surname, imsi, subscriptionType, terminalType);
        try {
            api.addUser(newUser);
            System.out.println("User added successfully.");
        } catch (UserAlreadyExistsException e) {
            System.out.println("User already exists.");
        }
    }

    private void removeUser() {
        int userIndex = selectUser();

        try {
            api.removeUser(userIndex);
            System.out.println("User removed successfully.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid user index.");
        }
    }

    private void newSession() {
    	int userIndex = selectUser();
        System.out.println("Enter session information:");
        String serviceType = selectTypeFromList(config.getPossibleServices(), "Select service:", null);
        System.out.print("Time: ");
        int time = scanner.nextInt();scanner.nextLine();

        try {
            api.newSession(userIndex, serviceType, time);
            System.out.println("Session created successfully.");
        } catch (NotEnoughDataVolumeException e) {
            
        	//TODO
           
        }
    }
    
    private void invoice() {
    	List<InvoiceInformation> invoices = api.invoice();
    	System.out.println("Invoices:");
    	for (InvoiceInformation invoice : invoices) {
    		System.out.println(getInvoiceDisplay(invoice));
    	}
    }
    
    private static void displayStartupMessage() {
    	System.out.println("MATSECOM Session Management Simulator v1.1");
    	System.out.println("powered by ALRA");
    	System.out.println();
    }
    
}
