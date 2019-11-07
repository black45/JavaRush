package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.ad.AdvertisementManager;
import com.javarush.task.task27.task2712.ad.NoVideoAvailableException;
import com.javarush.task.task27.task2712.kitchen.Order;

import java.io.IOException;
import java.util.IllegalFormatCodePointException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet extends Observable  {
     public static Logger logger = Logger.getLogger(Tablet.class.getName());
     final int number;
    private Order order;
    private Observer observer;
    public Tablet(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public Order createOrder(){
        Order order = new Order(this);

        try {

            if(order.isEmpty()) return null;
            ConsoleHelper.writeMessage(order.toString());
            setChanged();
            notifyObservers(order);
            AdvertisementManager advertisementManager = new AdvertisementManager(order.getTotalCookingTime()*60);
            advertisementManager.processVideos();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
            return null;
        }catch(NoVideoAvailableException e){
            logger.log(Level.INFO,"No video is available for the order " + order);
        }
        return order;
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }



    @Override
    public synchronized void deleteObserver(Observer o) {
        super.deleteObserver(o);
    }

    @Override
    public String toString() {
        return "Tablet{" +
                "number=" + number +
                '}';
    }
}
