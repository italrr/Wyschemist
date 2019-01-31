package com.nite.wyschemist;


import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

public class particleSplit extends Entity {
    Color bodyColor = new Color();
    int type = Tools.random(1, 5); // 1 = neutron, 2 = electron
    @Override
    void create(){

    }

    @Override
    void destroy(){

    }

    @Override
    void update(){
        position.x += inertia.x;
        position.y += inertia.y;
        // out of bounds
        if(position.x > Game.getGraphics().getWidth() || position.y > Game.getGraphics().getHeight() || position.x < 0  || position.y < 0){
            world.remove(this);
            Tools.print("destroyed splitted");
        }

        // shock-wave
        ArrayList<Entity> entities = Game.getWorld().entities;
        for(int i = 0; i < entities.size(); ++i){
            Entity ent = entities.get(i);
            if(ent == this) continue;
            if(!(ent instanceof Particle)) continue;
            if(ent.getDistance(this) < 250){
                if(type == 1 && Tools.random(1, 10) >= 9){
                    ((Particle) ent).split();
                    world.remove(this);
                    break;
                }else
                if(type == 2){
                    ((Particle) ent).chargeIt();
                    world.remove(this);
                    break;
                }
                float a = (float)Math.atan2(ent.position.y - position.y, ent.position.x - position.x);
                ent.addInertia((float)Math.cos(a)*10, (float)Math.sin(a)*10);
            }
        }
    }

    @Override
    void draw(){
        Game.getGraphics().setColor(bodyColor);
        Circle.draw(position.x, position.y, 8, 1, true);
    }
}
