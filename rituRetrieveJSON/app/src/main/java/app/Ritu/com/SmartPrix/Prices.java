package app.Ritu.com.SmartPrix;

import java.io.Serializable;

/**
 * Created by ritu on 12/11/15.
 */
public class Prices  implements Serializable {

    String Title, store_url, store_rating, store_delivery, store_name, link, stock, delivery, shipping_cost, pos, logo;
    String Category;
    int Price;


    public String get_Title(){
        return this.Title;
    }

    public void set_Title(String title){
        this.Title=title;
    }

    public String get_Url(){
        return this.store_url;
    }

    public void set_Url(String surl){
        this.store_url=surl;
    }

    /*public String get_StoreDelivery(){
        return this.store_delivery;
    }

    public void set_StoreDelivery(String del){
        this.store_delivery = del;
    }*/

    public String get_link(){
        return this.link;
    }

    public void set_link(String lnk){
        this.link=lnk;
    }

    public String getLogo(){
        return this.logo;
    }

    public void setLogo(String logourl){
        this.logo = logourl;
    }

    public String get_Rating(){
        return this.store_rating;
    }

    public void set_Rating(String rating){
        this.store_rating=rating;
    }

    public int get_Price(){
        return this.Price;
    }

    public void set_Price(int price){
        this.Price=price;
    }


}