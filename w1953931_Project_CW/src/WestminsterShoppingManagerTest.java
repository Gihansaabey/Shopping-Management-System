import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class WestminsterShoppingManagerTest {
    @org.junit.jupiter.api.Test
    public void addProduct() {
        // Mock user input
        String inputString = "Product Name\nProduct ID\n10.5\n5\ne\nBrand\n2\nc\nM\nBlue\n";
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(inputStream);
        WestminsterShoppingManager productManager = new WestminsterShoppingManager();
        productManager.addProduct();

        assertEquals(1, WestminsterShoppingManager.products.size());
    }
    @org.junit.jupiter.api.Test
    public void deleteProduct() {
        // Mock user input
        String inputString = "ProductIDToDelete\nN\n";
        InputStream inputStream = new ByteArrayInputStream(inputString.getBytes());
        System.setIn(inputStream);

        WestminsterShoppingManager.products.add(new Electronics("ProductIDToDelete", "TestProduct", 10, 50.0, "Brand", 2));

        WestminsterShoppingManager productManager = new WestminsterShoppingManager();

        productManager.deleteProduct();

        assertTrue(WestminsterShoppingManager.products.isEmpty(), "Products list should be empty");
    }
    @org.junit.jupiter.api.Test
    public void testSaveToFile() throws IOException {
        WestminsterShoppingManager.products.add(new Electronics("P001", "TestProduct1", 5, 50.0, "Brand1", 2));
        WestminsterShoppingManager.products.add(new Clothing("P002", "TestProduct2", 10, 30.0, "SizeM", "Blue"));
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        shoppingManager.saveToFile();
        List<String> lines = Files.readAllLines(Path.of("products.txt"));
        System.out.println(lines.toString());
        String expectedOutput = "[Electronic: productId=P001,name= TestProduct1,available= 5,Price= 50.0,Brand= Brand1,Warranty= 2,"+" Clothing: productId=P002,name= TestProduct2,available= 10,Price= 30.0,Size= SizeM,Colour= Blue]";
        assertEquals(expectedOutput, lines.toString());
    }

}
