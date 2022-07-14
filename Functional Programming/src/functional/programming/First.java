package functional.programming;

import org.javatuples.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class First {
    static List<Order>  orders = new ArrayList<Order>();

    public static void main(String[] args) {
        orders.add(new Order(2015,100.2f));
        orders.add(new Order(2017,150.2f));
        orders.add(new Order(2020,10.2f));
        orders.add(new Order(2018,50.2f));
        orders.add(new Order(2016,50.2f));
        orders.forEach(System.out::println);
        System.out.println("=============");
        orders.stream()
                .map((x) -> getOrderwithDiscount(x, getDiscountRule()))
                .forEach(System.out::println);
    }
    public static Order getOrderwithDiscount(Order r,List<Pair<Predicate<Order>, Supplier<Float>>> rules){
        rules.stream()
             .filter((x) -> x.getValue0().test(r))
                .limit(2)
             .forEach((x) -> r.addDiscount(x.getValue1().get()));
        return r;
    }
    public static List<Pair<Predicate<Order>,Supplier<Float>>> getDiscountRule(){

        Predicate<Order> p1 = (order)->order.getPrice()>100;
        Supplier<Float> f1 = ()-> 0.1f;
        Pair<Predicate<Order>,Supplier<Float>> pair1 = new Pair<>(p1,f1);

        Predicate<Order> p2 = (order)->order.getDate()<2017;
        Supplier<Float> f2 = ()-> 0.2f;
        Pair<Predicate<Order>,Supplier<Float>> pair2 = new Pair<>(p2,f2);

        return List.of(pair1,pair2);
    }
}

