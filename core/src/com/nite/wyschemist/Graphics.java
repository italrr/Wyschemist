package com.nite.wyschemist;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class renderObject {
    int depth = 0;
    private boolean added = false;
    public void draw(){

    }
}

public class Graphics {
    private ArrayList<renderObject> buffer = new ArrayList();
    private int depth = 0;
    private Color color = new Color();

    public ShapeRenderer shapeRenderer = new ShapeRenderer();
    public SpriteBatch spriteBatch = new SpriteBatch();

    public Color getColor(){
        return color;
    }
    public void setColor(Color color){
        this.color.set(color.r, color.g, color.b, color.a);
    }
    public void resetColor(){
        this.color.set(1, 1, 1, 1);
    }

    public int getDepth(){
        return depth;
    }
    public void setDepth(int depth){
        this.depth = depth;
    }
    public void resetDepth(){
        this.depth = 0;
    }

    private final float SCALE = 32f;
    private final float INV_SCALE = 1.f / SCALE;
    private float idealWidth = 720;
    private float idealHeight = 1280;
    private float VP_WIDTH = idealWidth * INV_SCALE;
    private float VP_HEIGHT = idealHeight * INV_SCALE;
    private float realWidth = 0;
    private float realHeight = 0;


    float getWidth(){
        return realWidth;
    }

    float getHeight(){
        return realHeight;
    }

    private OrthographicCamera camera;

    public OrthographicCamera getCamera(){
        return camera;
    }

    public Graphics(){

        float sW = Gdx.graphics.getWidth();
        float sH = Gdx.graphics.getHeight();
        while(true){
            if(sW < idealWidth*1.10 && sW > idealWidth*.90 && sH < idealHeight*1.10 && sH > idealHeight*.90) break;
            if(sW < idealWidth*0.90 || sH < idealHeight*.90){
                sW *= 1.10;
                sH *= 1.10;
                continue;
            }
            if(sW > idealWidth*1.10 || sH > idealHeight*1.10) {
                sW *= 0.90;
                sH *= 0.90;
                continue;
            }
        }
        realWidth = sW;
        realHeight = sH;
        VP_WIDTH = sW * INV_SCALE;
        VP_HEIGHT = sH * INV_SCALE;
        camera = new OrthographicCamera(realWidth, realHeight);
        camera.setToOrtho(false, realWidth, realHeight);
        Tools.print("Resolution "+String.valueOf(realWidth)+"x"+String.valueOf(realHeight));
    }

    public void addRenderObject(renderObject rObj){
        //buffer.add(rObj);
        for(int i = 0; i < buffer.size(); ++i){
            if(buffer.get(i).depth < depth){
                buffer.add(i, rObj);
                return;
            }
        }
        for(int i = buffer.size()-1; i >= 0; i--){
            if(buffer.get(i).depth == depth){
                buffer.add(i+1, rObj);
                return;
            }
        }
        buffer.add(buffer.size(), rObj);
    }
    void draw(){
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        for(int i = 0; i < buffer.size(); ++i){
            buffer.get(i).draw();
        }
        buffer.clear();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
