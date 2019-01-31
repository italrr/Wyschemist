package com.nite.wyschemist;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

class renderObjectRectangle extends renderObject {
    Vec2 position;
    Vec2 size;
    float angle;
    Color color = new Color();
    public renderObjectRectangle(float x, float y, float w, float h, float angle){
        this.position = new Vec2(x, y);
        this.size = new Vec2(w, h);
        this.angle = angle;
        this.depth = Game.getGraphics().getDepth();
        this.color.set(Game.getGraphics().getColor().r, Game.getGraphics().getColor().g, Game.getGraphics().getColor().b, Game.getGraphics().getColor().a);
    }
    @Override
    public void draw(){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        ShapeRenderer shapeRenderer = Game.getGraphics().shapeRenderer;
        shapeRenderer.setProjectionMatrix(Game.getGraphics().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(position.x, position.y, size.x/2f, size.y/2f, size.x, size.y, 1, 1, angle);
        shapeRenderer.end();
    }
}

public class Rectangle {

    public static void draw(float x, float y, float w, float h){
        Game.getGraphics().addRenderObject(new renderObjectRectangle(x, y, w, h, 0));
    }

    public static void draw(float x, float y, float w, float h, float angle){
        Game.getGraphics().addRenderObject(new renderObjectRectangle(x, y, w, h, angle));
    }
}
