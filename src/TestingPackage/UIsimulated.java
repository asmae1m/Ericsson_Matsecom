package TestingPackage;

import java.util.Scanner;
import ApiModule.ApiModule;
import Configuration.Configuration;
import Configuration.ConfigurationImp;
import DataStore.DataStore;
import DataStore.JsonDataStore;
import DataStore.UserData;
import ApiModule.InvoiceInformation;
import ApiModule.UserAlreadyExistsException;
import ApiModule.NotEnoughDataVolumeException;


public class UIsimulated {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Setup
        ConfigurationImp config = new ConfigurationImp();
        JsonDataStore dataStore = new JsonDataStore();
        ApiModule api = new ApiModule(config, dataStore);

        // Prompt user for which method to test
        System.out.println("Welcome, what do you need?");
        System.out.println("1. getUserList");
        System.out.println("2. addUser");
        System.out.println("3. removeUser");
        System.out.println("4. newSession");
        System.out.println("5. invoice");
        System.out.println("6. saveData");
        int option = scanner.nextInt();

        switch(option) {
            case 1:
                testGetUserList(api);
                break;
            case 2:
                testAddUser(api);
                break;
            case 3:
                testRemoveUser(api);
                break;
            case 4:
                testNewSession(api);
                break;
            case 5:
                testInvoice(api);
                break;
            case 6:
                testSaveData(api);
                break;
            default:
                System.out.println("Invalid option");
                break;
        }

        scanner.close();
    }

    private static void testGetUserList(ApiModule api) {
        System.out.println("User List:");
        for (UserData user : api.getUserList()) {
            System.out.println(user);
        }
    }

    private static void testAddUser(ApiModule api) {
        System.out.println("Enter user information:");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("IMSI: ");
        String imsi = scanner.nextLine();
        System.out.print("Subscription Type: ");
        String subscriptionType = scanner.nextLine();
        System.out.print("Terminal Type: ");
        String terminalType = scanner.nextLine();
        scanner.close();

        UserData newUser = new UserData(name, imsi, subscriptionType, terminalType);
        try {
            api.addUser(newUser);
            System.out.println("User added successfully.");
        } catch (UserAlreadyExistsException e) {
            System.out.println("User already exists.");
        }
    }

    private static void testRemoveUser(ApiModule api) {
        System.out.println("Enter user index:");
        Scanner scanner = new Scanner(System.in);
        int userIndex = scanner.nextInt();
        scanner.close();

        try {
            api.removeUser(userIndex);
            System.out.println("User removed successfully.");
        } catch (UserIndexOutOfBoundsException e) {
            System.out.println("Invalid user index.");
        }
    }

    private static void testNewSession(ApiModule api) {
        System.out.println("Enter session information:");
        Scanner scanner = new Scanner(System.in);
        System.out.print("User index: ");
        int userIndex = scanner.nextInt();
        System.out.print("Service type: ");
        String serviceType = scanner.next();
        System.out.print("Time: ");
        int time = scanner.nextInt();
        scanner.close();

        try {
            api.newSession(userIndex, serviceType, time);
            System.out.println("Session created successfully.");
        } catch (NotEnoughDataVolumeException e) {
            System.out.println("Not enough data volume.");
        }
    }