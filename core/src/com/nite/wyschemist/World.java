package com.nite.wyschemist;

import java.util.ArrayList;

public class World {
    public ArrayList<Entity> entities = new ArrayList<Entity>();
    public boolean isInWorld(Entity entity){
        for(int i = 0; i < entities.size(); ++i){
            if(entities.get(i) == entity) return true;
        }
        return false;
    }
    public void add(Entity entity){
        if(isInWorld(entity)) return;
        entity.world = this;
        entity.create();
        entities.add(entity);
    }
    ArrayList<Entity> toRemove = new ArrayList<Entity>();
    public void remove(Entity entity){
        if(entity.destroyed) return;
        entity.destroyed = true;
        toRemove.add(entity);
    }
    public void cleanUp(){
        while(toRemove.size() > 0){
            Entity entity = toRemove.get(0);
            entity.destroy();
            entities.remove(entity);
            toRemove.remove(0);
        }
    }
    public void update(){
        for(int i = 0; i < entities.size(); ++i){
            if(entities.get(i).destroyed) continue;
            entities.get(i).physicsUpdate();
            if(entities.get(i).destroyed) continue;
            entities.get(i).update();
        }
        for(int i = 0; i < entities.size(); ++i){
            entities.get(i).draw();
        }
        cleanUp();
    }
}
