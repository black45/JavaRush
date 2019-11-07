package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;
import java.util.Observable;

import java.util.Observer;

public class Cook extends Observable implements Observer{
    private String name;
    public Cook(String name) {
        this.name = name;
    }

    @Override
    public void update(java.util.Observable observable, Object o) {
        try {
            ConsoleHelper.writeMessage("Start cooking - " + o.toString());
           setChanged();
           notifyObservers(o);
        }catch(Exception e){

        }
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
        return name;
    }
}

