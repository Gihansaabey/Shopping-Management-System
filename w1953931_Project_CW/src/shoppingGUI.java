import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class shoppingGUI extends JFrame {
    private JComboBox<String> productTypeComboBox;   //combo box to select the type of products to display
    private JTextArea productDetailsTextArea;
    private JButton addToCartButton; //add the selected product to the shopping cart
    private JButton viewCartButton;  //view the content of the shopping cart
    private JPanel centerPanel;  //panel to hold product list table
    private JTable table;  //JTable to display the product list
    JScrollPane scrollPane;

    private DefaultTableModel tableModel;

    String Column_name[] = {"Product Id", "Name", "Category", "Price", "Info"};
    WestminsterShoppingManager manager = new WestminsterShoppingManager();

    public shoppingGUI() {
        setTitle("Shopping GUI");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        // Initialize components
        productTypeComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothes"});
        addToCartButton = new JButton("Add to Cart");
        viewCartButton = new JButton("View Cart");



        setupProductTable((ArrayList<Product>) WestminsterShoppingManager.products);

        // Adding components to the layout using border layout
        // top panel to hold combo box and view cart button
        JPanel topPanel = new JPanel(new FlowLayout( FlowLayout.CENTER));
        topPanel.add(new JLabel("Select Product Type:"));
        topPanel.add(productTypeComboBox);
        topPanel.add(viewCartButton);

        //center panel to hold the scrollPane(table)
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        //adding padding to sides of the table
        JPanel paddedPanel = new JPanel(new BorderLayout());
        int horizontalPadding = 50;
        paddedPanel.setBorder(BorderFactory.createEmptyBorder(0, horizontalPadding, 0, horizontalPadding)); // Add padding to left and right
        paddedPanel.add(centerPanel, BorderLayout.CENTER);

        //adding top and center panels to the main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(paddedPanel, BorderLayout.CENTER);

        add(mainPanel);

        //panel for the textarea and add to cart button
        JPanel buttonPanel = new JPanel(new FlowLayout());

        productDetailsTextArea = new JTextArea();
        JScrollPane scroll = new JScrollPane(productDetailsTextArea);
        scroll.setPreferredSize(new Dimension(1200, 280));
        buttonPanel.add(scroll);
        buttonPanel.add(addToCartButton);
        add(buttonPanel);

        productTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //handling the category combo box
                updateProductTable();
            }
        });

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSelectedProductToCart();
            }
        });


        viewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewShoppingCart();
            }
        });



// Adding a ListSelectionListener to the table
        //displaying selected product details in text area

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // To avoid handling intermediate selections
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) { // If a row is selected
                        // Retrieve data from the selected row
                        String productId = table.getValueAt(selectedRow, 0).toString();
                        String productName = table.getValueAt(selectedRow, 1).toString();
                        String category = table.getValueAt(selectedRow, 2).toString();
                        double price = Double.parseDouble(table.getValueAt(selectedRow, 3).toString());
                        // Displaying the data in the productDetailsTextArea
                        String details = "\n" + "     " + "Selected Product Details" + "\n" + "\n" +
                                "     " + "Product ID: " + productId + "\n" + "\n" +
                                "     " + "Product Name: " + productName + "\n" + "\n" +
                                "     " + "Category: " + category + "\n" + "\n" +
                                "     " + "Price: " + price;

                        productDetailsTextArea.setText(details);
                    }
                }
            }
        });
    }
    private void updateProductTable() {
        //when electronics is selected from product category combo box
        if ("Electronics".equals(productTypeComboBox.getSelectedItem())) {
            DefaultTableModel electronicsTableModel = ElectronicTable.addElectronicsToTableModel((ArrayList<Product>) WestminsterShoppingManager.products);
            if (table != null) {
                table.setModel(electronicsTableModel);
            } else {
                setupProductTable((ArrayList<Product>) WestminsterShoppingManager.products);
            }
            //when Clothes is selected from combo box
        } else if ("Clothes".equals(productTypeComboBox.getSelectedItem())) {

            DefaultTableModel clothingTableModel=ClothingTable.addClothingToTableModel((ArrayList<Product>) WestminsterShoppingManager.products);
            if (table != null) {
                table.setModel(clothingTableModel);
            } else {
                setupProductTable((ArrayList<Product>) WestminsterShoppingManager.products);
            }
        } else {
            //when All is selected from combo box
            if (WestminsterShoppingManager.products != null && !WestminsterShoppingManager.products.isEmpty()) {
                //rebuilds the table model using all products
                DefaultTableModel allProductsTableModel = new DefaultTableModel(Column_name, 0);

                for (Product product : WestminsterShoppingManager.products) {
                    String Prod_ID = product.getProductID();
                    String Prod_name = product.getProductName();
                    double price = product.getPrice();
                    String Category;
                    String info;
                    if (product instanceof Clothing) {
                        Category = "Clothing";
                        info = ((Clothing) product).getSize() + "," + ((Clothing) product).getColour();
                    } else {
                        Category = "Electronics";
                        info = ((Electronics) product).getBrand() + "," + ((Electronics) product).getWarPeriod() + " weeks warranty";
                    }
                    Object[] data = {Prod_ID, Prod_name, Category, price, info};
                    allProductsTableModel.addRow(data);
                }
                //sets the updated model to the table
                table.setModel(allProductsTableModel);
            }
        }
    }


   //all products table (displays when the gui loads)
    public void setupProductTable(ArrayList<Product> allProduct) {

        // Sort the allProduct list by Product ID
        Collections.sort(allProduct, Comparator.comparing(Product::getProductID));  //sort the products according to the id
        DefaultTableModel tableModel1 = new DefaultTableModel(Column_name, 0);

        System.out.println(allProduct.get(0).getQuantity());
        for (Product product1 : allProduct) {
            String Prod_ID = product1.getProductID();
            String Prod_name = product1.getProductName();
            double price = product1.getPrice();
            if (product1 instanceof Clothing) {
                String Category = "Clothing";
                String info = ((Clothing) product1).getSize() + "," + ((Clothing) product1).getColour();
                Object[] data = {Prod_ID, Prod_name, Category, price, info};
                tableModel1.addRow(data);

            } else if (product1 instanceof Electronics) {
                String Category = "Electronics";
                String info = ((Electronics) product1).getBrand() + "," + ((Electronics) product1).getWarPeriod() + " weeks warranty";
                Object[] data = {Prod_ID, Prod_name, Category, price, info};
                tableModel1.addRow(data);
            }
        }


        table = new JTable(tableModel1);
        int rowHeight = table.getRowHeight();
        int visibleRows = 10;
        int preferredHeight = rowHeight * visibleRows;
        int preferredWidth = 800;

        Dimension preferredSize = new Dimension(preferredWidth, preferredHeight);
        table.setPreferredScrollableViewportSize(preferredSize);
        scrollPane = new JScrollPane(table);
        table.setRowHeight(30);

        table.setDefaultRenderer(Object.class, new CustomCellRenderer()); //rows with less availability turns red

    }



    private void addSelectedProductToCart() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String productId = table.getValueAt(selectedRow, 0).toString();

            boolean productExists = false;
            for (Product product : WestminsterShoppingManager.selectedProducts) {
                if (product.getProductID().equals(productId)) {
                    product.increaseQuantity(); // Increment the quantity of the selected product
                    product.decreaseNoofAvailableItems();
                    //System.out.println("Product Name" + product.getProductName() + "Product Quantity: "+ product.getQuantity());
                    productExists = true;

                    break;
                }
            }

            if (!productExists) {
                // Find the product in allProduct list
                for (Product product : WestminsterShoppingManager.products) {
                    if (product.getProductID().equals(productId)) {

                        product.increaseQuantity(); // Increment the quantity of the selected product

                        WestminsterShoppingManager.selectedProducts.add(product); // Add the product to the cart
                        product.decreaseNoofAvailableItems();
                        JOptionPane.showMessageDialog(null, "Product added to cart.", "Success", JOptionPane.INFORMATION_MESSAGE);

                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Product quantity updated in cart.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a product to add to the cart.", "No product selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void viewShoppingCart() {
        ShoppingCartGUI cartGUI = new ShoppingCartGUI(WestminsterShoppingManager.selectedProducts);
        cartGUI.showCart();
    }

    // method to make the rows with less than 3 available items red
    class CustomCellRenderer extends DefaultTableCellRenderer {
        WestminsterShoppingManager manager = new WestminsterShoppingManager();

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            String productId = table.getValueAt(row, 0).toString(); // as product ID is in the first column

            // Check if the quantity for the current product ID is less than 3 using the method in westminsterShoppingManager class
            if (manager.isQuantityLessThanThree(productId)) {
                cellComponent.setBackground(Color.RED); // set the background colour if the quantity < 3
            } else {
                cellComponent.setBackground(table.getBackground());
            }

            return cellComponent;
        }
    }
    public  void drive() {
        shoppingGUI gui = new shoppingGUI();
        gui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gui.setVisible(true);
    }

}

