import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ElectronicTable {
    //class to display the electronic products when electronics from combo box is selected
    public static DefaultTableModel addElectronicsToTableModel( ArrayList<Product> allProducts) {
        String Column_name[]={"Product Id", "Name", "Category", "Price", "Info"};;
        DefaultTableModel tableModel2 = new DefaultTableModel(Column_name, 0);

        for (Product product : allProducts) {
            if (product instanceof Electronics) {
                String Prod_ID = product.getProductID();
                String Prod_name = product.getProductName();
                double price = product.getPrice();
                String Category = "Electronics";
                String info = ((Electronics) product).getBrand() + ", " +
                        ((Electronics) product).getWarPeriod() + " weeks warranty";

                Object[] data = {Prod_ID, Prod_name, Category, price, info};
                tableModel2.addRow(data);
            }
        }
    return tableModel2;

    }
}

