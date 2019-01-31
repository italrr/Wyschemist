package com.nite.wyschemist;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Entity {
    Vec2 position = new Vec2();
    Vec2 positionTrans = new Vec2();
    Vec2 inertia = new Vec2();
    boolean solid = true;
    World world = null;
    int radios = 25;
    boolean destroyed = false;
    void create(){

    }

    void destroy(){

    }

    boolean isTouched(float x, float y){
        return Math.pow(x-position.x, 2) + Math.pow(y - position.y, 2) < Math.pow(radios, 2)*5f;
    }

    boolean isTouched(Vec2 p){
        return isTouched(p.x, p.y);
    }

    float getDistance(Entity entity){
        if(this == entity) return 0;
        return (float) Math.sqrt( Math.pow( entity.position.x - position.x, 2 ) + Math.pow( entity.position.y - position.y, 2 ) );
    }

//    long lastSnap = 0;

    boolean snapped = false;
    void snapEffect(float x, float y){
        analyzeSpeed(x, y);
//        if(Tools.getTicks()-lastSnap < 200)
//            return;
//        lastSnap = Tools.getTicks();
        if(snapped){
            return;
        }
        Entity t = new Entity();
        Entity j = new Entity();
        t.position.set(position);
        j.position.set(x, y);
        float d = t.getDistance(j);
        if(d > 50){
            if(world != null){
                snapped = true;
                snapEffect ef = new snapEffect();
                ef.position.set(t.position.x, t.position.y);
                world.add(ef);
                return;
            }
        }
    }

    long lastCheckSpeed = Tools.getTicks();
    float speed = 0;
    void analyzeSpeed(float x, float y){
        Entity t = new Entity();
        Entity j = new Entity();
        t.position.set(position);
        j.position.set(x, y);
        float d = t.getDistance(j);
        speed = d/(lastCheckSpeed/1000f + 1);
        lastCheckSpeed = Tools.getTicks();
        if(d <= 15){
            snapped = false;
        }
    }

    void setAbsolutePosition(float x, float y){
        snapEffect(x, y);
        positionTrans.set(x, y);
        position.set(x, y);
    }

    void setPosition(float x, float y){
        snapEffect(x, y);
        positionTrans.set(x, y);
    }

    void setPosition(Vec2 v){
        positionTrans.set(v.x, v.y);
    }

    void setAbsolutePosition(Vec2 p){
        setAbsolutePosition(p.x, p.y);
    }

    void addInertia(float x, float y){
        inertia.x += x;
        inertia.y += y;
    }

    void addInertia(Vec2 i){
        addInertia(i.x, i.y);
    }

    void resetInertia(){
        inertia.x = 0;
        inertia.y = 0;
    }

    boolean hasInertia(){
        return inertia.x > 0.5f || inertia.y > 0.5f;
    }

    void physicsUpdate(){

    }

    void draw(){

    }

    void update(){

    }


}
