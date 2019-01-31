package com.nite.wyschemist;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

import javax.tools.Tool;

public class Wizard extends Entity {

    float maxRadios = 300;
    Font font = null;

    @Override
    void create() {
        font = new Font("font/prefix.ttf", 36, 8);
    }

    @Override
    void destroy() {

    }

    @Override
    void draw() {
        // Distortion
        if(castRadios > 0){
            Game.getGraphics().setDepth(-1000);
            Vec2 touchPosition = Game.getInput().getTouchedPosition();

            float m = radiosAlpha - 0.4f;
            if(m < 0) m = 0;
            Game.getGraphics().setColor(new Color(0.016f, 0.369f, 0.592f, m));
            Circle.draw(touchPosition.x, touchPosition.y, castRadios + m * 80, 4, false);
            Game.getGraphics().resetColor();

            Game.getGraphics().setColor(new Color(1, 1, 1, 1));
            Circle.draw(touchPosition.x + ringDistortionTrans.x, touchPosition.y + ringDistortionTrans.y, castRadios, 2, false);

            Game.getGraphics().resetDepth();
        }

        // Board
        Game.getGraphics().setColor(new Color(0.7f, 1f, 0f, 1f));
        Game.getGraphics().setDepth(10000);
        font.draw("Rads "+String.valueOf(Game.getGameInfo().Rads), 32, Game.getGraphics().getHeight()-32);
        Game.getGraphics().resetColor();
        Game.getGraphics().resetDepth();
    }

    boolean pressed = false;
    long pressStart = 0;
    float castRadios = 0;
    float radiosAlpha = 0;
    Vec2 ringDistortion = new Vec2();
    Vec2 ringDistortionTrans = new Vec2();
    long distortionTimeout;
    int state = 0;
    boolean spawned = false;
    long spawnDelay = 0;
    Vec2 grabPosition = new Vec2();
    Vec2 grabReleasePosition = new Vec2();
    long grabTime = 0;
    Entity grabbed = null;
    long touchDelay = 0;
    void reset(){
        pressed = false;
        pressStart = 0;
        castRadios = 0;
        radiosAlpha = 0;
        ringDistortion = new Vec2();
        ringDistortionTrans = new Vec2();
        distortionTimeout = 0;
        state = 0;
        spawned = false;
        spawnDelay = 0;
        grabPosition = new Vec2();
        grabReleasePosition = new Vec2();
        grabTime = 0;
        grabbed = null;
        swingCount = 0;
        swingTimeout = 0;
        touchDelay = 0;
        swingUpCount = 0;
        swingUpTimeout = 0;

    }
    long restartGrabInertia = 0;

    int swingCount = 0;
    int swingUpCount = 0;
    long swingTimeout = 0;
    long swingUpTimeout = 0;
    Vec2 lastSwingStart = new Vec2();
    Vec2 lastSwingUpStart = new Vec2();

    @Override
    void update() {

        boolean pressedNow = Game.getInput().isTouchedDown();

        if(Tools.getTicks()-touchDelay < 600){
            reset();
            return;
        }


        // Drag
        if(grabbed != null && pressedNow) {
            grabbed.setAbsolutePosition(Game.getInput().getTouchedPosition());
            // Morph
            if(Game.getInput().getTouchedPosition().equals(grabPosition) && Tools.getTicks()-grabTime > 2000) {
                Game.getWorld().remove(grabbed);
                if(grabbed instanceof ParticleHydrogen) {
                    // Oxygen
                    Particle particle = new ParticleOxygen();
                    particle.setAbsolutePosition(Game.getInput().getTouchedPosition());
                    Game.getWorld().add(particle);
                    reset();
                    return;
                }
                if(grabbed instanceof ParticleOxygen) {
                    // Helium
                    Particle particle = new ParticleHelium();
                    particle.setAbsolutePosition(Game.getInput().getTouchedPosition());
                    Game.getWorld().add(particle);
                    reset();
                    return;
                }
                // Uranium
                if(grabbed instanceof ParticleHelium) {
                    // Helium
                    Particle particle = new ParticleUranium();
                    particle.setAbsolutePosition(Game.getInput().getTouchedPosition());
                    Game.getWorld().add(particle);
                    reset();
                    return;
                }
            }
            // Charge
            if(!lastSwingStart.equals(Game.getInput().getTouchedPosition())){
                Vec2 p = Game.getInput().getTouchedPosition();
                if(Math.abs(lastSwingStart.y-p.y) < 150 && Math.abs(lastSwingStart.x-p.x) > 50 && Math.abs(lastSwingStart.x-p.x)  < 400){
                    ++swingCount;
                    swingTimeout = Tools.getTicks();
                }
                lastSwingStart.set(Game.getInput().getTouchedPosition());
                if(Tools.getTicks()-swingTimeout < 1000){
                    if(swingCount > 10){
                        ((Particle)grabbed).chargeIt();
                        swingTimeout = 0;
                        swingCount = 0;
                    }
                }
            }
            // Discharge
            if(!lastSwingUpStart.equals(Game.getInput().getTouchedPosition())){
                Vec2 p = Game.getInput().getTouchedPosition();
                if(Math.abs(lastSwingUpStart.x-p.x) < 150 && Math.abs(lastSwingUpStart.y-p.y) > 50 && Math.abs(lastSwingUpStart.y-p.y) < 400){
                    ++swingUpCount;
                    swingUpTimeout = Tools.getTicks();
                }
                lastSwingUpStart.set(Game.getInput().getTouchedPosition());
                if(Tools.getTicks()-swingUpTimeout < 1000){
                    if(swingUpCount > 10){
                        ((Particle)grabbed).dischargeIt();
                        swingUpTimeout = 0;
                        swingUpCount = 0;
                    }
                }

            }

        }else
        if(grabbed != null){
            if(Tools.getTicks()-restartGrabInertia > 1200){
                grabPosition.set(Game.getInput().getTouchedPosition());
                restartGrabInertia = Tools.getTicks();
            }
            restartGrabInertia = Tools.getTicks();
            grabReleasePosition.set(Game.getInput().getTouchedPosition());
            float d = (float)Math.hypot(grabPosition.x-grabReleasePosition.x, grabPosition.y-grabReleasePosition.y);
            float t = (Tools.getTicks() - grabTime)/1000 + 1;
            float speed = d/t;
            float angle = (float)Math.atan2(grabReleasePosition.x-grabPosition.x, grabReleasePosition.y-grabPosition.y);
            grabbed.addInertia((float) (Math.sin(angle)*speed), (float) (Math.cos(angle)*speed));
            grabbed = null;
            grabTime = 0;
            state = 0;
            touchDelay = Tools.getTicks();
        }
//        if(grabbed != null && pressedNow && grabbed.hasInertia()){
//            grabbed.resetInertia();
//        }

        if(grabbed == null) {
            ArrayList<Entity> entities = Game.getWorld().entities;
            for (int i = 0; i < entities.size(); ++i) {
                Entity ent = entities.get(i);
                if (pressedNow && ent.isTouched(Game.getInput().getTouchedPosition()) && Math.hypot(ent.inertia.x, ent.inertia.y) < 10) {
                    grabbed = ent;
                    grabPosition.set(Game.getInput().getTouchedPosition());
                    grabTime = Tools.getTicks();
                    break;
                }
            }
        }

        // Cast Hydrogen
        if(state == 1){
            float t = (Tools.getTicks()-pressStart);
            if(t > 500f) t = 500f;
            float d = (t / 500f);
            float r = d * 80;
            radiosAlpha = d;
            castRadios = Tools.lerp(castRadios, r, 0.25f);
            if(Tools.getTicks()-distortionTimeout > 50f){
                distortionTimeout = Tools.getTicks();
                ringDistortion.set(Tools.random(-2, 2), Tools.random(-2, 2));
            }
            ringDistortionTrans.lerp(ringDistortion, 0.25f);
        }
        if(state == 2){
            if(castRadios > 0){
                radiosAlpha = Tools.lerp(radiosAlpha, 0, 0.15f);
                castRadios = Tools.lerp(castRadios, 0, 0.15f);
            }

            if(castRadios > 0 && castRadios < 0.10f){
                castRadios = 0;
            }
            if(castRadios == 0){
                pressed = false;
                pressStart = 0;
                castRadios = 0;
                radiosAlpha = 0;
                pressedNow = false;
                touchDelay = Tools.getTicks();
                state = 0;
                if(spawned){
                    state = 4;
                    spawnDelay = Tools.getTicks();
                }
            }

        }
        if(state == 4 && Tools.getTicks()-spawnDelay > 500){
            state = 0;
            spawned = false;
        }
        if(!pressedNow && pressed && Tools.getTicks()-pressStart < 1500){
            state = 2;
        }
        if(pressedNow && pressed && Tools.getTicks()-pressStart > 1500 && state == 1){
            Particle particle = new ParticleHydrogen();
            particle.setAbsolutePosition(Game.getInput().getTouchedPosition());
            Game.getWorld().add(particle);
            state = 2;
            spawned = true;
        }
        if(pressedNow && !pressed && state == 0 && grabbed == null){
            pressed = true;
            pressStart = Tools.getTicks();
            state = 1;
        }




    }
}
