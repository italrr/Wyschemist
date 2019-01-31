package com.nite.wyschemist;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

class gameInfo {
    int Rads = 0;
    int mRads = 0;
    boolean added = false;
    void update(){
        added = !added;
        if(added) return;
        if(Rads < mRads){
            if(Math.abs(Rads-mRads) > 100){
                Rads += 100;
            }else
            if(Math.abs(Rads-mRads) > 10){
                Rads += 10;
            }else
            if(Math.abs(Rads-mRads) > 5){
                Rads += 5;
            }else
            if(Math.abs(Rads-mRads) > 1){
                Rads += 1;
            }
        }
    }
}

public class Game extends World {
    private static World world;
    private static Graphics graphics;
    private static InputManager input;
    private static gameInfo gameInfo;

    public static Graphics getGraphics(){
        return graphics;
    }

    public static World getWorld(){
        return world;
    }

    public static InputManager getInput(){
        return input;
    }
    public static gameInfo getGameInfo(){
        return gameInfo;
    }

    ArrayList<Resource> localResources = new ArrayList<Resource>();

    private static Game Self = null;

    // Fonts
    Font liftMessageFont = null;
    Font particleFont = null;

    public static Game getSelf(){
        return Self;
    }

    public Game(){
        Self = this;
    }

    public void init(){
        Tools.print("Wyschemist");
        world = new World();
        Tools.print("Init World");
        graphics = new Graphics();
        Tools.print("Init Graphics");
        input = new InputManager(graphics.getCamera());
        Tools.print("Init Input");
        world.add(new Wizard());
        graphics.resetColor();
        gameInfo = new gameInfo();
        Gdx.input.setInputProcessor(input);
        // local resources
        liftMessageFont = new Font("font/prefix.ttf", 32, 8);
        localResources.add(liftMessageFont);
        particleFont = new Font("font/prefix.ttf", 28, 5);
        localResources.add(particleFont);
    }

    public void end(){
        for(int i = 0; i < localResources.size(); ++i){
            localResources.get(i).dispose();
        }
        world = null;
        graphics = null;
        input = null;
        gameInfo = null;
        Tools.print("Disposed");
        Self = null;
    }

    public void update(){
        gameInfo.update();
        world.update();
    }

    public void draw(){
        graphics.draw();
    }
}
