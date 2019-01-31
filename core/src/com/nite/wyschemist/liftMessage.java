package com.nite.wyschemist;

import com.badlogic.gdx.graphics.Color;

public class liftMessage extends Entity {
    Vec2 targetPosition = new Vec2();
    String message = "";
    float alpha = 1500;
    Color color = new Color();
    void calculateTarget(){
        font = Game.getSelf().liftMessageFont;
        float w = font.getWidth(message);
        float h = font.getHeight(message);
        float dirX = 35f;
        float dirY = 200f;
        if(position.x + dirX + w > Game.getGraphics().getWidth()){
            dirX *= -1;
        }
        if(position.y + dirY + h > Game.getGraphics().getHeight()){
            dirY *= -1;
        }
        targetPosition.set(position.x+dirX, position.y+dirY);
    }
    Font font = null;

    @Override
    void create(){
        font = Game.getSelf().liftMessageFont;
    }

    @Override
    void destroy(){

    }

    @Override
    void update(){
        position.lerp(targetPosition, 0.25f);
        alpha = Tools.lerp(alpha, 0, 0.10f);
        if(alpha <= 5){
            Game.getWorld().remove(this);
        }
    }

    @Override
    void draw(){
        Game.getGraphics().setColor(new Color(color.r, color.g, color.b, alpha/100f));
        Game.getGraphics().setDepth(10000);
        font.draw(message, position.x, position.y);
        Game.getGraphics().resetColor();
        Game.getGraphics().resetDepth();
    }

}

