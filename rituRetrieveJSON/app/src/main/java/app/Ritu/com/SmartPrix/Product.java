package app.Ritu.com.SmartPrix;

import java.io.Serializable;

/**
 * Created by ritu on 12/11/15.
 */
public class Product  implements Serializable {

    String Title;
    String Category;
    int Price;
    String brand;
    String ImageURL;
    String Product_ID;


    public String get_Title(){
        return this.Title;
    }

    public void set_Title(String title){
        this.Title=title;
    }

    public String get_Brand(){
        return this.brand;
    }

    public void set_Brand(String brand){
        this.brand=brand;
    }

    public String get_ImageURL(){
        return this.ImageURL;
    }

    public String get_ProductID(){
        return this.Product_ID;
    }

    public String get_Category(){
        return this.Category;
    }

    public void set_Category(String category){
        this.Category=category;
    }

    public int get_Price(){
        return this.Price;
    }

    public void set_Price(int price){
        this.Price=price;
    }

    public void set_ProductID_and_ImageURL(String product_id, String imgurl){
        this.Product_ID=product_id;
        this.ImageURL=imgurl;

    }
}