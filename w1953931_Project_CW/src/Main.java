import java.util.Scanner;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class Main {
    public static void main(String[] args) {
        WestminsterShoppingManager management =new WestminsterShoppingManager();
        management.loadFile(); //loading the product list to the system
        management.readFromUserFile();
        System.out.println("Welcome to Westminster Shopping Manager");
        while(true){
            try{
                Scanner sc=new Scanner(System.in);
                System.out.println("----------------------------------------");
                System.out.println("1. Add a new product");
                System.out.println("2. Delete a product");
                System.out.println("3. Print the list of products ");
                System.out.println("4. Save in a file");
                System.out.println("5. GUI ");
                System.out.println("6. exit");
                System.out.println("----------------------------------------");
                System.out.print("Please select an option:");
                int choice = sc.nextInt();
                sc.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        management.addProduct();
                        break;
                    case 2:
                        management.deleteProduct();
                        break;
                    case 3:
                        management.printProductList();
                        break;
                    case 4:
                        management.saveToFile();
                        break;

                    case 5:
                        LoginScreen login = new LoginScreen();
                        login.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                        login.setVisible(true);
                        break;
                    case 6:
                        System.out.println("Exiting the program. Goodbye!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
            catch (Exception e){
                System.out.println("Invalid input please try again");
                System.out.println();
            }
        }
    }

}
