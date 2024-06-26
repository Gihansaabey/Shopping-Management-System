import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class ShoppingCartGUI extends JFrame {

    public static JTable cartTable;
    public static JLabel totalLabel;
    public static JLabel firstDiscountLabel;
    public static JLabel secondDiscountLabel;
    public static JLabel finalTotalLabel;

    public static int[] categoryQuantities; // Array to store category quantities
    public static final int NUM_CATEGORIES = 2; // Assuming two categories: Clothing and Electronics
    public static DefaultTableModel tableModel;
    public static ArrayList<Product> cartProducts;
    WestminsterShoppingManager man=new WestminsterShoppingManager();


        public ShoppingCartGUI ( ArrayList<Product> cartProducts){

            setTitle("Shopping Cart");
            setSize(600,400);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            //creating table model for the cart
            DefaultTableModel tableModel=new DefaultTableModel();
            tableModel.addColumn("Product Id");
            tableModel.addColumn("Product Name");
            tableModel.addColumn("Quantity");
            tableModel.addColumn("Price");

            categoryQuantities = new int[NUM_CATEGORIES]; // Initialize array for category quantities

            WestminsterShoppingManager.shoppingCart.calculateCategoryQuantities(cartProducts);

            //Populating the table with cart products
            for (Product product : cartProducts) {
                tableModel.addRow(new Object[]{
                        product.getProductID(),
                        product.getProductName(),
                        product.getQuantity(),
                        product.getPrice()*product.getQuantity()
                });
            }


            //creating the cart table
            cartTable=new JTable(tableModel);
            JScrollPane cartScrollPane=new JScrollPane(cartTable);

            totalLabel= new JLabel("Total= ");
            firstDiscountLabel=new JLabel("First Purchased Discount =");
            secondDiscountLabel=new JLabel("Three Items in the same category Discount = ");
            finalTotalLabel=new JLabel("Final total= ");

            // Create a panel for labels
            JPanel labelsPanel = new JPanel(new GridLayout(5, 1));
            labelsPanel.add(totalLabel);
            labelsPanel.add(firstDiscountLabel);
            labelsPanel.add(secondDiscountLabel);
            labelsPanel.add(finalTotalLabel);

            JButton remove=new JButton("remove product");
            JButton buy= new JButton("CheckOut");


            // Creating a panel for the bottom part of the frame
            JPanel bottomPanel = new JPanel(new BorderLayout());
            bottomPanel.add(labelsPanel, BorderLayout.NORTH);
            bottomPanel.add(remove,BorderLayout.SOUTH);
            bottomPanel.add(buy);

            // Adding components to the main frame
            add(cartScrollPane, BorderLayout.CENTER);
            add(bottomPanel, BorderLayout.SOUTH);

            // Calculate and setting the values for Total, Discount, and Final Total
            double totalValue = WestminsterShoppingManager.shoppingCart.calculateTotal(cartProducts);
            double secondDiscount = WestminsterShoppingManager.shoppingCart.calculateTwentyDiscount(cartProducts);
            double firstDiscount = WestminsterShoppingManager.shoppingCart.calculateFirstPurchaseDiscount(cartProducts);
            double finalTotalValue = totalValue - (firstDiscount+secondDiscount);

            totalLabel.setText("Total = " + totalValue);
            firstDiscountLabel.setText("First Purchase Discount= "+firstDiscount);
            secondDiscountLabel.setText("Three items in the same category Discount="+secondDiscount);
            finalTotalLabel.setText("Final Total = " + finalTotalValue);

            this.cartProducts=cartProducts;
            this.tableModel=(DefaultTableModel)cartTable.getModel();

            remove.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    WestminsterShoppingManager.shoppingCart.removeProduct();
                    System.out.println("Called");
                }
            });

            buy.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {handleCheckout();}
            });

            }

    private void handleCheckout() {
        WestminsterShoppingManager.currentUser.setPurchasedList((ArrayList<Product>) WestminsterShoppingManager.cart);
        JOptionPane.showMessageDialog(this, "Payment Successful!", "Payment Confirmation", JOptionPane.INFORMATION_MESSAGE);
        WestminsterShoppingManager.saveToUserFile();
        WestminsterShoppingManager.cart.clear();
        man.saveToFile(); //calling save to file method in westminsterShoppingManager class so that everything happend in the cart get saved

    }


    public void showCart() {
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }


}
