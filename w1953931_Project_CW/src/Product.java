
public abstract class Product {
    private String  productID;
    private String  productName;
    private int noOfAvailableItems;
    private double price;
    private int quantity;


    public Product(String productID , String productName , int noOfAvailableItems,double price) {
        this.productID = productID;
        this.productName=productName;
        this.noOfAvailableItems=noOfAvailableItems;
        this.price=price;
        this.quantity = 0;
    }

    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public int getNoOfAvailableItems() {
        return noOfAvailableItems;
    }

    public double getPrice() {
        return price;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setNoOfAvailableItems(int noOfAvailableItems) {
        this.noOfAvailableItems = noOfAvailableItems;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity() {
        this.quantity = this.quantity + 1;
    }
    public void decreaseNoofAvailableItems() {
        this.noOfAvailableItems = this.noOfAvailableItems - 1;
    }

}
