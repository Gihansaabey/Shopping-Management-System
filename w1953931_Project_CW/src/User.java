import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String userId;
    private String userName;

    public ArrayList<Product> getPurchasedList() {
        return purchasedList;
    }

    public void setPurchasedList(ArrayList<Product> purchasedList) {
        this.purchasedList = purchasedList;
    }

    private String passWord;

    private ArrayList<Product> purchasedList;

    public User(String userId,String userName, String passWord) {
        this.userId=userId;
        this.userName = userName;
        this.passWord=passWord;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }




}
