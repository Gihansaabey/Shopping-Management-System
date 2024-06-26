import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ClothingTable {
    //class to display the clothing products when clothing from combo box is selected
    public static DefaultTableModel addClothingToTableModel(ArrayList<Product> allProducts) {
        String Column_name[]={"Product Id", "Name", "Category", "Price", "Info"};;
        DefaultTableModel tableModel2 = new DefaultTableModel(Column_name, 0);

        for (Product product : allProducts) {
            if (product instanceof Clothing) {
                String Prod_ID = product.getProductID();
                String Prod_name = product.getProductName();
                double price = product.getPrice();
                String Category = "Clothing";
                String info = ((Clothing) product).getSize() + ", " +
                        ((Clothing) product).getColour();

                Object[] data = {Prod_ID, Prod_name, Category, price, info};
                tableModel2.addRow(data);
            }
        }
        return tableModel2;

    }
}
