public class Electronics extends Product{
    private String brand;
    private int warPeriod;

    public Electronics(String productId,String productName , int noOfAvailableItems, double price ,String brand,int warPeriod) {
        super(productId,productName,noOfAvailableItems,price);
        this.brand = brand;
        this.warPeriod=warPeriod;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarPeriod() {
        return warPeriod;
    }

    public void setWarPeriod(int warPeriod) {
        this.warPeriod = warPeriod;
    }
}
