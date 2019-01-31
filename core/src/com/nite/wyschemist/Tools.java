package com.nite.wyschemist;

import java.util.Date;

public class Tools {
    public static long getTicks(){
        return System.currentTimeMillis();
    }
    public static void print(String string){
        Date date = new Date(getTicks());
        System.out.println("["+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds()+"] "+string);
    }
    public static float lerp(float a, float b, float f)
    {
        return (a * (1.0f - f)) + (b * f);
    }
    public static int random(int min, int max){
        return min + (int)(Math.random() * ((max - min) + 1));
    }
}
