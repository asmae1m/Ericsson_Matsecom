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
	
	public SessionSimulator() throws IOException {
		 config = new ConfigurationImp();
		 JsonDataStore dataStore = new JsonDataStore();
		 api = new ApiModule(config, dataStore);
	}
	
	public void mainLoop() {
		boolean exit = false;
		while(!exit) {
			// Prompt user for which method to test
	        System.out.println("Welcome, what do you need?");
	        System.out.println("1. User List");
	        System.out.println("2. Edit User");
	        System.out.println("3. add User");
	        System.out.println("4. remove User");
	        System.out.println("5. new Session");
	        System.out.println("6. invoice");
	        System.out.println("7. exit");
	        
	        Scanner scanner = new Scanner(System.in);
	        int option = scanner.nextInt(); //TODO: sanazize input
	        scanner.close();

	        switch(option) {
	            case 1:
	                showUserList(false);
	                break;
	            case 2:
	                editUser();
	                break;
	            case 3:
	                addUser();
	                break;
	            case 4:
	                removeUser();
	                break;
	            case 5:
	                newSession();
	                break;
	            case 6:
	            	invoice();
	                break;
	            case 7:
	            	api.saveData();
	            	exit=true;
	                break;
	            default:
	                System.out.println("Invalid option");
	                break;
	        }

	        scanner.close();
			
			
		}
		
		
		
	}
	
    public static void main(String[] args) throws IOException {
        SessionSimulator sim = new SessionSimulator();
        sim.mainLoop(); 
    }

    private String getUserDisplay(UserData user) {
    	return user.getForename() + " " + user.getSurname(); //TODO!!!
    }
    
    private String getInvoiceDisplay(InvoiceInformation invoice) {
    	return invoice.forename + " " + invoice.surname; //TODO!!!!
    }
    
    
	private void showUserList(boolean showIndex) {
        System.out.println("User List:");
        List<UserData> users = api.getUserList();
        for (int i=0; i<users.size(); i++) {
        	if (showIndex) {
        		System.out.println(Integer.toString(i) + ": " + getUserDisplay(users.get(i)));
        	} else {
        		System.out.println(users.get(i));
        	}
            
        }
        
	}
	
	private int selectUser() {
		while(true) {
			showUserList(true);
			try {
				Scanner scanner = new Scanner(System.in);
		        int option = scanner.nextInt();
		        scanner.close();
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
			Scanner scanner = new Scanner(System.in);
	        String input = scanner.next();
	        scanner.close();
	        if (defaultValue != null && input=="") {
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
    	
    	Scanner scanner = new Scanner(System.in);
        System.out.println("Enter new forename (leave empty to keep current value):");
        String forename = scanner.next();
        if (forename.equals("")) {
        	forename = user.getForename();
        }
        System.out.println("Enter new surname (leave empty to keep current value):");
        String surname = scanner.next();
        if (surname.equals("")) {
        	surname = user.getSurname();
        }
        scanner.close();
        
        String subscriptionType = selectTypeFromList(config.getPossibleSubscriptionTypes(), "Select new subscription:", user.getSubscriptionType());
        String terminalType = selectTypeFromList(config.getPossibleTerminalTypes(), "Select new terminal:", user.getTerminalType());
        
        
        InvoiceInformation invoice = api.updateUserData(userIndex, forename, surname, subscriptionType, terminalType);
        System.out.println("User edited successfully:");
        System.out.println(getUserDisplay(user));
        System.out.println("New Invoice:");
        System.out.println(getInvoiceDisplay(invoice));
    }
    
    private void addUser() {
        System.out.println("Enter user information:");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Forname: ");
        String forname = scanner.nextLine();
        System.out.print("Surname: ");
        String surname = scanner.nextLine();
        System.out.print("IMSI: ");
        String imsi = scanner.nextLine();
        System.out.print("Subscription Type: ");
        String subscriptionType = scanner.nextLine();
        System.out.print("Terminal Type: ");
        String terminalType = scanner.nextLine();
        scanner.close();

        UserData newUser = new UserData(forname,surname, imsi, subscriptionType, terminalType);
        try {
            api.addUser(newUser);
            System.out.println("User added successfully.");
        } catch (UserAlreadyExistsException e) {
            System.out.println("User already exists.");
        }
    }

    private void removeUser() {
        System.out.println("Enter user index:");
        Scanner scanner = new Scanner(System.in);
        int userIndex = scanner.nextInt();
        scanner.close();

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
        Scanner scanner = new Scanner(System.in);
        String serviceType = selectTypeFromList(config.getPossibleServices(), "Select service:", null);
        System.out.print("Time: ");
        int time = scanner.nextInt();
        scanner.close();

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
    
}
