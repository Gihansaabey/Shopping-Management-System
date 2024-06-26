import java.util.*;

public class ShoppingCart {

    private static double prodTotal;  // store the total cost of the products
    List<Product> products=new ArrayList<>();   //list to holds the products in the shopping cart
    public ShoppingCart(List<Product> products) {
        this.products = products;
    }

    public static void removeProduct() {
        int selectedRow = ShoppingCartGUI.cartTable.getSelectedRow();
        if (selectedRow != -1) {
            String productId = (String) ShoppingCartGUI.tableModel.getValueAt(selectedRow, 0);
            int currentQuantity = (int) ShoppingCartGUI.tableModel.getValueAt(selectedRow, 2);

            if (currentQuantity > 1) {
                // If the product has more than one quantity, decrease the quantity by 1
                ShoppingCartGUI.tableModel.setValueAt(currentQuantity - 1, selectedRow, 2);
                UpdateAvailableCount((ArrayList<Product>) WestminsterShoppingManager.products, productId);
            } else {
                // If the product has only one quantity, remove the entire row and update available count
                ShoppingCartGUI.tableModel.removeRow(selectedRow);
                UpdateAvailableCount((ArrayList<Product>) WestminsterShoppingManager.products, productId);

                // removing the product from the cartProducts list using an iterator
                Iterator<Product> iterator = ShoppingCartGUI.cartProducts.iterator();
                while (iterator.hasNext()) {
                    Product product = iterator.next();
                    if (product.getProductID().equals(productId)) {
                        iterator.remove(); // Remove the product from cartProducts
                        break; // Break the loop once the product is removed
                    }
                }
            }
        }
    }
 //update the available count of the main product list
    private static void UpdateAvailableCount(ArrayList<Product> products, String productId) {
    }

    public static double calculateTotal(ArrayList<Product> cartProducts) { //calculate the total cost before applying the discounts
        double total = 0;
        for (Product product : cartProducts) {
            total += product.getPrice()* product.getQuantity();

        }
        //System.out.println("Total is " + total);
        prodTotal = total;
        return total;
    }


    // calculating the discount on the cart
    public static double calculateTwentyDiscount(ArrayList<Product> cartProducts) {
        double discount = 0;

        // Create a map to store the count of Electronic and Clothing products
        Map<Class<? extends Product>, Integer> productCounts = new HashMap<>();

        // Populate the map with cart product counts
        for (Product product : cartProducts) {
            if (product instanceof Clothing || product instanceof Electronics) {
                Class<? extends Product> productClass = product.getClass();
                productCounts.put(productClass, productCounts.getOrDefault(productClass, 0) + product.getQuantity());
            }
        }

        // Calculate the discount based on the product counts
        for (int count : productCounts.values()) {
            if (count >= 3) {
                // Applying 20% discount for each type with at least three products
                discount += 0.2 * prodTotal;
            }
        }

        return discount;
    }

    public static double calculateFirstPurchaseDiscount(ArrayList<Product> cartProducts) {
        if (WestminsterShoppingManager.currentUser.getPurchasedList() == null) {
            // Apply a 10% discount for the first purchase
            return 0.1 * calculateTotal(cartProducts);
        }
        return 0; // No discount if it's not the first purchase
    }
    public static void calculateCategoryQuantities(ArrayList<Product> cartProducts) { //calculating the product quantity of each category
        for (Product product : cartProducts) {
            int categoryIndex = getCategoryIndex(product);
            if (categoryIndex != -1) {
                ShoppingCartGUI.categoryQuantities[categoryIndex]++;
            }
        }
    }

    public static int getCategoryIndex(Product product) {
        if (product instanceof Clothing) {
            return 0; // Category index for Clothing
        } else if (product instanceof Electronics) {
            return 1; // Category index for Electronics
        }
        return -1; // Return -1 for unknown categories
    }
}


