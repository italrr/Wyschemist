package com.nite.wyschemist;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

class renderObjectCircle extends renderObject {
    Vec2 position;
    float radios = 1;
    Color color = new Color();
    float thick = 1;
    boolean filled = false;

    public renderObjectCircle(float x, float y, float radios, float thick, boolean filled){
        this.position = new Vec2(x, y);
        this.radios = radios;
        this.thick = thick;
        this.filled = filled;
        this.depth = Game.getGraphics().getDepth();
        this.color.set(Game.getGraphics().getColor().r, Game.getGraphics().getColor().g, Game.getGraphics().getColor().b, Game.getGraphics().getColor().a);
    }
    @Override
    public void draw(){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        ShapeRenderer shapeRenderer = Game.getGraphics().shapeRenderer;
        Gdx.gl.glLineWidth(thick);
        shapeRenderer.setProjectionMatrix(Game.getGraphics().getCamera().combined);
        shapeRenderer.begin(filled ? ShapeRenderer.ShapeType.Filled : ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(color);
        shapeRenderer.circle(position.x, position.y, radios);
        shapeRenderer.end();
        Gdx.gl.glLineWidth(1);
    }
}

public class Circle {

    public static void draw(float x, float y, float r, float thick, boolean filled){
        Game.getGraphics().addRenderObject(new renderObjectCircle(x, y, r, thick, filled));
    }
}