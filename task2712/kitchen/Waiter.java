package com.javarush.task.task27.task2712.kitchen;

import com.javarush.task.task27.task2712.ConsoleHelper;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class Waiter implements Observer {



    @Override
    public void update(Observable observable, Object o) {
        try {
            ConsoleHelper.writeMessage(o.toString() + " was cooked by " + observable.toString());
        }catch (IOException e){}
    }
}
