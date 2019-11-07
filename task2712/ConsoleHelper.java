package com.javarush.task.task27.task2712;

import com.javarush.task.task27.task2712.kitchen.Dish;

import java.awt.*;
import java.io.BufferedReader;
import java.io.DataInput;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConsoleHelper {
public static void writeMessage(String message)throws IOException{
    System.out.println(message);
}

public static String readString() throws IOException{
 String result = "";
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    result = reader.readLine();
        reader.close();
    return result;
}

public static List<Dish> getAllDishesForOrder()throws IOException{
    List<Dish> dishes = new ArrayList<Dish>();
    String  s = readString();
    while(!s.equals("exit")){
        System.out.println(Dish.allDishesToString());
        if(Dish.allDishesToString().contains(s)) dishes.add(Dish.valueOf(s.substring(0, 1).toUpperCase() + s.substring(1)));
        else System.out.println("There is no such dish");
        s = readString();
    }
    return dishes;
}


}
