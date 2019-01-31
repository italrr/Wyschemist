package com.nite.wyschemist;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

class renderObjectText extends renderObject {
    Vec2 position;
    Vec2 size;
    String text;
    float angle;
    Color color = new Color();
    BitmapFont font;
    public renderObjectText(BitmapFont font, String text, float x, float y){
        this.text = text;
        this.font = font;
        this.position = new Vec2(x, y);
        ///this.size = new Vec2(w, h);
        this.angle = angle;
        this.depth = Game.getGraphics().getDepth();
        this.color.set(Game.getGraphics().getColor().r, Game.getGraphics().getColor().g, Game.getGraphics().getColor().b, Game.getGraphics().getColor().a);
    }
    @Override
    public void draw(){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        SpriteBatch spriteBatch = Game.getGraphics().spriteBatch;
        spriteBatch.setProjectionMatrix(Game.getGraphics().getCamera().combined);
        spriteBatch.begin();
        font.setColor(color);
        font.draw(spriteBatch, text, position.x, position.y);
        spriteBatch.end();
    }
}


public class Font extends Resource {
    private BitmapFont font = null;

    public Font(String Filename, int size, int boderThick){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(Filename));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.borderColor = new Color(0.10f, 0.10f, 0.10f, 1f);
        parameter.color = new Color(1f, 1f, 1f, 1f);
        parameter.borderWidth = boderThick;
        parameter.size = size;
        font = generator.generateFont(parameter);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        generator.dispose();
    }

    float getWidth(String input){
        final GlyphLayout layout = new GlyphLayout(font, input);
        return layout.width;
    }
    float getHeight(String input){
        final GlyphLayout layout = new GlyphLayout(font, input);
        return layout.height;
    }

    void draw(String text, float x, float y){
        Game.getGraphics().addRenderObject(new renderObjectText(font, text, x, y));
    }

    @Override
    void dispose(){
        font.dispose();
        super.dispose();
    }
}
