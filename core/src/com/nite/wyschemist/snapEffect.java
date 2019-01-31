package com.nite.wyschemist;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

public class snapEffect extends Entity{

    int rays = 12;
    float stretch = 2;
    float initialRadius = 32;
    float stretchMax = 12;
    float alpha = 60f;

    void create(){

    }

    void destroy(){

    }

    void update(){
        stretch = Tools.lerp(stretch, stretchMax, 0.35f);
        alpha = Tools.lerp(alpha, 0, 0.35f);
        if(alpha <= 0.01){
            world.remove(this);
        }
        ArrayList<Entity> entities = Game.getWorld().entities;
        for(int i = 0; i < entities.size(); ++i) {
            Entity ent = entities.get(i);
            if (ent == this) continue;
            if(ent.getDistance(this) < 25){
                alpha /= 2;
            }
        }
    }

    void draw(){
        Game.getGraphics().setColor(new Color(1, 1, 1, alpha));
        float u = 360 / rays;
        for(int i = 0; i < rays; ++i){
            float dx = (float) (position.x + Math.cos(Math.toRadians(u*(float)i))*initialRadius);
            float dy = (float) (position.y + Math.sin(Math.toRadians(u*(float)i))*initialRadius);
            float dx2 = (float) (position.x + Math.cos(Math.toRadians(u*(float)i))*(initialRadius + stretch));
            float dy2 = (float) (position.y + Math.sin(Math.toRadians(u*(float)i))*(initialRadius + stretch));
            Line.draw(dx, dy, dx2, dy2, 2);
        }
    }
}
