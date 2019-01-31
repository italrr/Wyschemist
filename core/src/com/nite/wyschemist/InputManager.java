package com.nite.wyschemist;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class InputManager  implements InputProcessor {

    public InputManager(Camera camera){
        this.camera = camera;
        Gdx.input.setCatchBackKey(true);
    }

    Camera camera;
    Vector3 tp = new Vector3();
    boolean dragging;

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        camera.unproject(tp.set(screenX, screenY, 0));
        return false;
    }


    private boolean touched = false;
    private Vec2 touchedPosition = new Vec2();
    private boolean wasDragged = false;
    public Vec2 getTouchedPosition(){
        return new Vec2(touchedPosition);
    }
    public boolean isTouchedDown(){
        return touched;
    }
    private long lastDrag = 0;

    public boolean wasTouchDragged(){
        if(Tools.getTicks()-lastDrag > 1){
            wasDragged = false;
        }
        return wasDragged;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        Vector3 r = camera.unproject(tp.set(screenX, screenY, 0));
        dragging = true;
        touched = true;
        touchedPosition.set(r.x, r.y);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!dragging) return false;
        Vector3 r = camera.unproject(tp.set(screenX,  screenY, 0));
        touchedPosition.set(r.x, r.y);
        lastDrag = Tools.getTicks();
        wasDragged = true;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        camera.unproject(tp.set(screenX, screenY, 0));
        dragging = false;
        touched = false;
        wasDragged = false;
        return true;
    }


    @Override
    public boolean keyDown(int keycode) {
        if((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK)){
            Gdx.app.exit();
            //Wyschemist.clearSelf();
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}