package com.nite.wyschemist;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

class renderObjectLine extends renderObject {
    Vec2 position;
    Vec2 position2;
    float thick;
    Color color = new Color();
    public renderObjectLine(float x, float y, float x2, float y2, int thick){
        this.position = new Vec2(x, y);
        this.position2 = new Vec2(x2, y2);
        this.thick = thick;
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
        shapeRenderer.rectLine(position.x, position.y, position2.x, position2.y, thick);
        shapeRenderer.end();
    }
}

public class Line {

    public static void draw(float x, float y, float x2, float y2, int thick){
        Game.getGraphics().addRenderObject(new renderObjectLine(x, y, x2, y2, thick));
    }
}
