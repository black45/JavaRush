package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import com.javarush.task.task27.task2712.Tablet;

import java.io.DataInput;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

public class Order {
    private final Tablet tablet;
    protected List<Dish> dishes;

    public Order(Tablet tablet) {
        this.tablet = tablet;
        try {
            dishes = ConsoleHelper.getAllDishesForOrder();
        }catch (IOException e){}

    }

    public boolean isEmpty(){
        if(dishes == null || dishes.size() == 0) return true;
        return false;
    }

    public int getTotalCookingTime(){
        if(isEmpty()) return 0;
            int res = 0;
            for(Dish dish : dishes){
                res += dish.getDuration();
            }
            return res;
    }

    @Override
    public String toString() {
            if (dishes == null) return "";

            String createOrder = "";
            for (int i = 0; i < dishes.size(); i++) {
                createOrder += dishes.get(i).toString().trim() + ",";
            }
            return "Your order: "+dishes+" of Tablet{"+"number="+ tablet.getNumber() +", cooking time "+getTotalCookingTime()+"min}";
    }
}
