package functional.programming;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Order {

    private int date;
    private float price;

    @Getter
    private float discount;

    public Order(int date, float price) {
        this.date = date;
        this.price = price;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void addDiscount(float i){
        this.discount+=i;
    }
}
