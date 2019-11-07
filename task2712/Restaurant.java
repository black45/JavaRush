package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Cook;
import com.javarush.task.task27.task2712.kitchen.Order;
import com.javarush.task.task27.task2712.kitchen.Waiter;
import javafx.beans.Observable;
import javafx.scene.control.Tab;

import java.util.Observer;

public class Restaurant {
    public static void main(String[] args) {
       Tablet tablet = new Tablet(0);
      tablet.createOrder();
      tablet.createOrder();
      tablet.createOrder();
      tablet.createOrder();

        Tablet observable = new Tablet(5);
        Cook observer = new Cook("coock");
        Waiter waiter = new Waiter();
        observable.addObserver(observer);
        observer.addObserver(waiter);

    }

}
