import javax.swing.*;
import java.util.*;
import java.io.*;

public class WestminsterShoppingManager implements ShoppingManager {

    public static ArrayList<Product> selectedProducts = new ArrayList<>(); //list to store selected products
    public static List<Product> cart = new ArrayList<>();//store products in the shopping cart

    public static List<Product> products=new ArrayList<>();  // list to store the available products

    public static  List<User> userList = new ArrayList<>();//list to store user details
    public static User currentUser; // adding a reference to the currently logged user
    public static ShoppingCart shoppingCart; //instance of the shopping cart class

    //method to add new products
    public void addProduct() {

        if(products.size()>=50){
            System.out.println("Product limit reached.Cannot add more products");
            return;
        }

        Scanner input = new Scanner(System.in);
        System.out.print("enter the product name:");
        String productName = input.next();
        while (productName.isEmpty()) {  //checking whether the product name empty or not
            System.out.print("This cant be empty , enter a product name:");
            productName = input.next();
        }
        System.out.print("enter the product id:");
        String productId=input.next();

        //checking whether the product id is duplicated or not
        while(isDuplicateProductId(productId)){
            System.out.println("Product with ID " + productId + " already exists. Please enter a different product ID.");
            System.out.print("Enter a different product id: ");
            productId = input.next();
        }


        System.out.print("enter the price:");
        double price = 0;
        while (true) {
            try {
                price = input.nextDouble();
                if (price < 0) {     //checking whether the entered price is negative or not
                    System.out.println("price cant be a negative , enter a valid product price");
                    price = input.nextDouble();
                } else {
                    break;
                }
            } catch (java.util.InputMismatchException e) { //handle if any other error happens
                System.out.println("Invalid input , please enter a valid price");
                input.next();
                System.out.println("enter a valid product price again");
            }
        }

        System.out.print("enter no of available items:");
        int noOfItems = 0;

        while (true) {
            try {
                noOfItems= input.nextInt();
                if (noOfItems < 0) {   //checking whether a valid quantity is entered
                    System.out.println("Quantity cannot be negative. Please enter a valid quantity.");
                    System.out.print("Enter the no of available items: ");
                } else {
                    break;
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for quantity.");
                input.next(); // clear the invalid input from the scanner
                System.out.print("Enter the no of available items: ");
            }
        }

        System.out.print("is it a electronic product or clothing product(e/c):"); //category of the product
        String ans = input.next();
        if (ans.toUpperCase().equals("E")) {  //if it is a electronic product
            System.out.print("enter the brand:");
            String brand = input.next();
            System.out.print("enter the warranty period (In weeks):");
            int warPeriod = 0;
            while (true) {
                try {
                    warPeriod = input.nextInt();
                    if (warPeriod < 0) {    //checking whether if valid warranty period is entered
                        System.out.println("warranty period cant be a negative value");
                        System.out.println("enter a valid warranty period");
                    } else {
                        break;
                    }
                } catch (java.util.InputMismatchException e) {
                    System.out.print("Invalid input , please enter a valid warranty period");
                    input.nextInt();
                }
            }
            products.add(new Electronics(productId, productName, noOfItems, price, brand, warPeriod));
            System.out.println("Electronic product added successfully");


        } else if(ans.toUpperCase().equals("C")){
            System.out.print("enter the size:");
            String size = input.next();
            System.out.print("enter the colour:");
            String col = input.next();
            products.add(new Clothing( productId,productName, noOfItems, price, size, col));
            System.out.println("Clothing product added successfully");
        }
        else {
            System.out.println("Invalid input. Please enter 'E' for electronic or 'C' for clothing.");
        }
    }

     // method to check if the product id is duplicated
    private boolean isDuplicateProductId(String productId) {
        for (Product product : products) {
            if (product.getProductID().equals(productId)) {
                return true;
            }
        }
        return false;
    }


    //method to delete the products
    public  void deleteProduct() {
        while(true){
            Scanner input = new Scanner(System.in);

            System.out.print("Enter the Product ID to delete: ");
            String productIdToDelete = input.next();
            boolean ifFound=false;


            for (Product product : products) {
                if (product.getProductID().equals(productIdToDelete)) {
                    products.remove(product);
                    System.out.println("Product with ID '" + productIdToDelete + "' deleted successfully.");
                    System.out.println("product name: "+product.getProductName());  //displaying the information of the deleted product
                    if (product instanceof Electronics){
                        System.out.println("Product type:Electronic ");
                    }
                    else{
                        System.out.println("Product type:Clothing ");
                    }
                    System.out.println("Number of products left:"+(products.size()));
                    ifFound=true;
                    break;
                }

            }if(ifFound==false){
                System.out.println("Invalid Product ID");  //if an invalid product id is entered

            }
            System.out.println("would you like to delete another product? (Y/N)"); //asking if they need to delete another product
            String yes_no= input.next();
            switch(yes_no.toUpperCase()) {
                case "Y":
                    break;
                case "N":
                    return;
                default:
                    while (true) {
                        System.out.println("wrong data type entered Y/N"); //handling a invalid input
                        String enter = input.next();
                        if (enter.equals("Y")) {
                            break;
                        } else if (enter.equals("N")) {
                            return;
                        } else {
                            System.out.println("wrong type");
                        }
                    }
            }
        }

    }

// method to display the available product list
    public void printProductList(){
        List<Product> newSortedProducts= new ArrayList<>(products);
        Collections.sort(newSortedProducts,Comparator.comparing(Product::getProductID));  //sorting the product list according to their id

        System.out.println("                    Product Information                ");
        System.out.println("----------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-20s | %-10s | %-10s | %-15s |\n",
                "ProductID", "ProductName", "Quantity", "Price", "Type");  //making a table like structure to print the list of products


        for(Product product:newSortedProducts){
            if(product instanceof Electronics) {
                Electronics electronicProduct = (Electronics) product;

                System.out.printf("| %-10s | %-20s | %-10d | %-10.2f | %-15s |\n",
                        electronicProduct.getProductID(), electronicProduct.getProductName(),
                        electronicProduct.getNoOfAvailableItems(), electronicProduct.getPrice(), "Electronic");
            }
            else if(product instanceof Clothing){
                Clothing clothProduct=(Clothing) product;

                System.out.printf("| %-10s | %-20s | %-10d | %-10.2f | %-15s |\n",
                        clothProduct.getProductID(), clothProduct.getProductName(),
                        clothProduct.getNoOfAvailableItems(), clothProduct.getPrice(), "Clothing");
            }
        }
        System.out.println("----------------------------------------------------------------------------------");
    }


//method to save the arraylist in a file
    public void saveToFile(){
        try {
            FileWriter myWriter = new FileWriter("products.txt"); // Creating a FileWriter to write to the "products.txt" file
            for (Product product : products) {
                if(product instanceof Electronics){  // checking whether the product is an instance of Electronics or Clothing
                    myWriter.write("Electronic: ");
                    myWriter.write("productId="+product.getProductID()+",");
                    myWriter.write("name= "+product.getProductName()+",");
                    myWriter.write("available= "+product.getNoOfAvailableItems()+",");
                    myWriter.write("Price= "+product.getPrice()+",");
                    myWriter.write("Brand= "+((Electronics) product).getBrand()+",");
                    myWriter.write("Warranty= "+((Electronics) product).getWarPeriod());

                    myWriter.write("\n"); //write the details in the file

                }
                else if(product instanceof Clothing){
                    myWriter.write("Clothing: ");
                    myWriter.write("productId="+product.getProductID()+",");
                    myWriter.write("name= "+product.getProductName()+",");
                    myWriter.write("available= "+product.getNoOfAvailableItems()+",");
                    myWriter.write("Price= "+product.getPrice()+",");
                    myWriter.write("Size= "+((Clothing) product).getSize()+",");
                    myWriter.write("Colour= "+((Clothing) product).getColour());

                    myWriter.write("\n");

                }
            }
            myWriter.close();
            System.out.println("Successfully saved to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // method to load back all the information saved in the file
    public void loadFile() {

        try {
            File myObj = new File("products.txt");

            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {

                String data = myReader.nextLine();
                if(data.contains("Clothing")){
                    String sub_string=data.substring(10);
                    String []clothArray=sub_string.split(",");

                    String prodId=clothArray[0].substring(10);
                    String prodNam=clothArray[1].substring(6);
                    int noItem=Integer.parseInt(clothArray[2].substring(clothArray[2].indexOf("=")+1).trim());
                    double price=Double.parseDouble(clothArray[3].substring(clothArray[3].indexOf("=")+1).trim());
                    String size=clothArray[4].substring(6);
                    String col=clothArray[5].substring(8);
                    products.add(new Clothing( prodId,prodNam, noItem, price, size, col));

                }
                else if(data.contains("Electronic")){
                    String sub_string=data.substring(12);
                    String []elecArray=sub_string.split(",");

                    String prodId=elecArray[0].substring(10);
                    String prodNam=elecArray[1].substring(6);
                    int noItem=Integer.parseInt(elecArray[2].substring(elecArray[2].indexOf("=")+1).trim());
                    double price=Double.parseDouble(elecArray[3].substring(elecArray[3].indexOf("=")+1).trim());
                    String brand=elecArray[4].substring(7);
                    int warPeriod=Integer.parseInt(elecArray[5].substring(elecArray[5].indexOf("=")+1).trim());
                    products.add(new Electronics(prodId, prodNam, noItem, price, brand, warPeriod));

                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //method to generate unique user ids
    public static String GenerateUniqueUserID(List<User> existingIds, String prefix) {
        // Find the maximum existing ID with the given prefix
        int maxId = 0;

        //iterating through the given user ids
        for (User existingId : existingIds) {
            if (existingId.getUserId().startsWith(prefix)) {  // Checking if the current existing ID starts with the prefix
                try {
                    //extracting the numerical part of the id
                    int id = Integer.parseInt(existingId.getUserId().substring(prefix.length()));
                    if (id > maxId) {
                        maxId = id;
                    }
                } catch (NumberFormatException ignored) { //if the numerical part is not a valid integer
                    System.out.println(ignored);
                }
            }
        }
        // Generate the new unique ID
        maxId++;
        String uniqueID = String.format("%s%03d", prefix, maxId);

        return uniqueID;
    }
    public boolean SignUp(String userName, String password) {
        String userId = GenerateUniqueUserID(userList,"USER");
        User newUser = new User(userId, userName, password);
        userList.add(newUser);
        JOptionPane.showMessageDialog(null, "User registered successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        saveToUserFile();
        return true;
    }
    public boolean Login(String userId, String password) {
        // Check if the user with the given userId exists
        for (User user : userList) {
            if (user.getUserName().equals(userId)) {


                // Check if the password matches
                if (user.getPassWord().equals(password)) {
                    currentUser = user;
                    shoppingCart = new ShoppingCart(cart);
                    if (currentUser.getPurchasedList() == null){
                        //System.out.println("empty");
                    }
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect password ,please try again", "Login Fail", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            }
        }

        // If userId not found
        JOptionPane.showMessageDialog(null, "User with the given UserName not found", "Login fail", JOptionPane.WARNING_MESSAGE);
        return false;
    }

    public static void saveToUserFile() { //saving user details to the file
        try {
            FileOutputStream file = new FileOutputStream("user.ser");
            ObjectOutputStream File = new ObjectOutputStream(file);
            File.writeObject(userList);
            file.close();
        }catch (Exception e){
            System.out.println(e);

        }
    }

    public void readFromUserFile() {  //load back from user file
        File newFile = new File("user.ser");
        if (newFile.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream("user.ser");
                ObjectInputStream FileIn = new ObjectInputStream(fileIn);
                userList = (ArrayList<User>) FileIn.readObject();
            } catch (Exception e) {
                System.out.println(e);
            }
        }else
            System.out.println("File Not exist");
    }

    //method for shopping gui class
    //get the available quantity of products to make the rows with less than 3 availability red

    public boolean isQuantityLessThanThree(String productId) {
        for (Product product : products) {
            if (product.getProductID().equals(productId)) {
                return product.getNoOfAvailableItems() < 3;//if availability is less than 3
            }
        }
        return false; // If product ID not found or quantity is equal or greater than 3
    }

}







