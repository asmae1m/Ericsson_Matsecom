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
	        System.out.println("Select action (enter the number on the left):");
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
	            	printListHeader(false);
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

    private String addPadding(String str, int len, boolean alignLeft) {
    	if (alignLeft) {
    		while (str.length()<len) {
        		str += " ";
        	}
    	} else {
    		while (str.length()<len) {
        		str = " " + str;
        	}
    	}
    	
    	return str;
    }
    
    private void printListHeader(boolean hasIndex) {
    	String line = "";
    	if (hasIndex) {
    		line = "#    ";
    	}
    	line += "Forename            ";
    	line += "Surname             ";
    	line += "Subscription Type   ";
    	line += "Terminal Type       ";
    	line += "Voice use      ";
    	line += "Date use       ";
    	line += "Upgrades purchased";
    	
    	System.out.println(line);
    }
    
    private String getUserDisplay(UserData user) {
    	String line = addPadding(user.getForename(), 20, true);
    	line += addPadding(user.getSurname(), 20, true);
    	line += addPadding(user.getSubscriptionType(), 20, true);
    	line += addPadding(user.getTerminalType(), 20, true);
    	line += addPadding(String.valueOf(user.getVoiceMinutes()+ "m "), 15, false);
    	line += addPadding(String.valueOf(user.getDataUsed()+ " MB "), 15, false);
    	line += addPadding(String.valueOf(user.getDataVolumeUpgrades()), 5, false);
    	
    	return line;
    	
    	
    }
    
    private void displayInvoice(InvoiceInformation invoice) {
    	

        System.out.println("");
        System.out.println("Customer: " + invoice.forename + " " + invoice.surname);
        System.out.println("Your base Price is: " + invoice.basePrice / 100. + "€.");
        System.out.println("You used " + invoice.dataVolume + " mb.");
        System.out.println("Your calls lasted " + invoice.voiceMinutes + " minutes.");

        if (invoice.dataVolumeUpgrades > 0)
            System.out.println("You used " + invoice.dataVolumeUpgrades + ".");
        System.out.println("Your total charge is " + invoice.totalCharge / 100. + "€.");
    	
    }
    
    
	private void showUserList(boolean showIndex) {
        List<UserData> users = api.getUserList();
        for (int i=0; i<users.size(); i++) {
        	if (showIndex) {
        		System.out.println(addPadding(Integer.toString(i) + ": ", 5, true) + getUserDisplay(users.get(i)));
        	} else {
        		System.out.println(getUserDisplay(users.get(i)));
        	}
            
        }
	}
	
	private int getIntegerInput() {
		while(true) {
			String input = scanner.nextLine();
			try {
				return Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please enter a valid number");
			}
		}
	}
	
	private int selectUser() {
		while(true) {
			System.out.println();
			printListHeader(true);
			showUserList(true);
			System.out.println();
			System.out.println("Please select a user (enter the number on the left):");
			try {
		        int option = getIntegerInput();
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
        displayInvoice(invoice);
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
        while (!imsi.matches("^\\d{15}$")) {
        	System.out.println("Invalid IMSI, please enter a 15-digit number");
        	imsi=scanner.nextLine();
        }
        
        String subscriptionType = selectTypeFromList(config.getPossibleSubscriptionTypes(), "Select new subscription (enter the number on the left):", null);
        String terminalType = selectTypeFromList(config.getPossibleTerminalTypes(), "Select new terminal (enter the number on the left):", null);

        UserData newUser = new UserData(forname,surname, imsi, terminalType, subscriptionType);
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
    	System.out.println();
        System.out.println("New Session");
        String serviceType = selectTypeFromList(config.getPossibleServices(), "Select service (enter the number on the left):", null);
        System.out.println();
        System.out.print("Time in minutes: ");
        int time = getIntegerInput();
        while (0 > time || time > 1440) {
        	System.out.println("Sessions are limited to 24 hours (1440 minutes). Please enter a valid Time:");
        	time = getIntegerInput();
        }
        
        boolean keepTrying = true;
        while (keepTrying) {
        	try {
                api.newSession(userIndex, serviceType, time);
                System.out.println("Session created successfully.");
                keepTrying = false;
            } catch (NotEnoughDataVolumeException e) {
            	if (offerDataUpgrade()) {
            		api.upgradeDataVolume(userIndex);
            	} else {
            		System.out.println("Session was canceled.");
            		keepTrying = false;
            	}
            }
        }
    }
    
    private boolean offerDataUpgrade() {
    	System.out.println();
    	System.out.println("It appears that you do not have enough data volume left.");
    	System.out.println("You can upgrade you data volume by 1 GB RIGHT NOW for ONLY 10€!");
    	System.out.println();
    	System.out.println("Would you like to upgrade your data volume? (y/n)");
    	
    	while(true) {
    		String input = scanner.nextLine();
    		if ("y".equals(input.toLowerCase())) {
    			return true;
    		} else if ("n".equals(input.toLowerCase())) {
    			return false;
    		}
    	}
    }
    
    private void invoice() {
    	List<InvoiceInformation> invoices = api.invoice();
    	System.out.println("Invoices:");
    	for (InvoiceInformation invoice : invoices) {
    		System.out.println();
    		displayInvoice(invoice);
    	}
    }
    
    private static void displayStartupMessage() {
    	System.out.println("MATSECOM Session Management Simulator v1.1");
    	System.out.println("powered by ALRA");
    	System.out.println();
    }
    
}
