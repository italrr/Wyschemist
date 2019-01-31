package com.nite.wyschemist;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

import javafx.geometry.Pos;

public class Particle extends Entity {
    boolean rotation = Tools.random(1, 2) == 1;
    float outerRingAlpha = Tools.random(1, 360);
    float innerRingAlpha =  Tools.random(1, 360);
    int charge = 0;
    int spawnCharge = 0;
    String symbol = "A";
    int radiation = 0;
    int stability = 0;
    int mass = 0;
    float eL = 0;
    int unstableCharge = 0;
    int unstableChargeMin = 1;
    Resonance thetaResonance = new Resonance(); // First
    Resonance deltaResonance = new Resonance(); // Second
    Resonance betaResonance = new Resonance(); // Background
    Color thetaColor = new Color(1, 1, 1, 1); // Inner
    Color deltaColor = new Color(1, 1, 1, 1); // Outer
    long lastBorrow = 0;
    Particle whoBorrow = null;
    Font symbolFont = null;

    @Override
    void create(){
        super.create();
        eL = 2f * (float)Math.pow(charge, 2);
        symbolFont = Game.getSelf().particleFont;
        Game.getGameInfo().mRads += Tools.random(5, 8);
    }

    public float getMass(){
        return mass + charge;
    }

    public void chargeIt(){
        liftMessage msg = new liftMessage();
        msg.message = "+e";
        msg.color.set(0f, 0.7f, 1f, 1f);
        Vec2 dposition = new Vec2(position);
        dposition.y -= 48;
        msg.position.set(dposition);
        msg.calculateTarget();
        Game.getWorld().add(msg);
        ++charge;
        eL = 2f * (float)Math.pow(charge, 2);
    }
    public void dischargeIt(){
        liftMessage msg = new liftMessage();
        msg.color.set(0f, 0.7f, 1f, 1f);
        Vec2 dposition = new Vec2(position);
        dposition.y -= 48;
        msg.position.set(dposition);
        msg.calculateTarget();
        if(charge > 0) {
            --charge;
            eL = 2f * (float)Math.pow(charge, 2);
            msg.message = "-e";
        }else{
            msg.message = "-0";
        }
        Game.getWorld().add(msg);
    }

    int rotationRing = 0;
    int rotators = 0;

    void orbiting(){
        if(whoBorrow != null && Tools.getTicks() - lastBorrow > 500){
           whoBorrow = null;
        }
        if(whoBorrow != null) return;
        ArrayList<Entity> entities = Game.getWorld().entities;
        rotators = 0;
        for(int i = 0; i < entities.size(); ++i){
            Entity ent = entities.get(i);
            if(ent == this) continue;
            if(ent.destroyed) continue;
            if(!(ent instanceof Particle)) continue;
            if(charge <= ((Particle)ent).charge) continue;
            if(rotators > 2) continue;
            if(ent.getDistance(this) < 200){
                Particle p = (Particle)ent;
                if(p.whoBorrow != this){
                    if(Tools.getTicks() - p.lastBorrow < 500){
                        continue;
                    }
                    p.whoBorrow = this;
                    p.lastBorrow = Tools.getTicks();
                }

                ++rotators;
                float dx = position.x + (float)Math.cos(Math.toRadians(rotationRing + 360/rotators))*150;
                float dy = position.y + (float)Math.sin(Math.toRadians(rotationRing + 360/rotators))*150;
                ent.setAbsolutePosition(dx, dy);
            }
        }
        rotationRing += 2 * rotators;
    }

    void drawBody(){

        float currentRadios = radios + 1.5f * (charge/spawnCharge);
        //float energyInertia = Math.abs((float)Math.hypot(inertia.x, inertia.y));
        outerRingAlpha += 5;
        innerRingAlpha += 5;
        float dx = (float)Math.cos((float)Math.toRadians(outerRingAlpha) * (rotation ? 1 : -1)) * 5f;
        float dy = (float)Math.sin((float)Math.toRadians(outerRingAlpha) * (rotation ? -1 : 1)) * 5f;
        //Game.getGraphics().setColor(new Color(1f, 1f, 0f, energyInertia*5));
        //Circle.draw(position.x+dx, position.y+dy, radios + 5*(energyInertia > 0 ? 1 : 0), 1, true);

        Game.getGraphics().resetDepth();
        Game.getGraphics().setColor(new Color(thetaColor.r, thetaColor.g, thetaColor.b, 0.10f));
        Circle.draw(position.x+dx, position.y+dy, currentRadios*1.5f, 1, true);
        Game.getGraphics().setColor(new Color(thetaColor.r, thetaColor.g, thetaColor.b, 0.30f));
        Circle.draw(position.x+dx, position.y+dy, currentRadios*1.3f, 1, true);
        Game.getGraphics().setColor(new Color(thetaColor.r, thetaColor.g, thetaColor.b, 0.60f));
        Circle.draw(position.x+dx, position.y+dy, currentRadios*1.1f, 1, true);

        Game.getGraphics().setColor(new Color(thetaColor.r, thetaColor.g, thetaColor.b, 0.80f));
        Circle.draw(position.x+dx, position.y+dy, currentRadios, 1, true);

        Game.getGraphics().setColor(new Color(1-thetaColor.r, 1-thetaColor.g, 1-thetaColor.b, 1f));
        symbolFont.draw(symbol, position.x + dx - symbolFont.getWidth(symbol)/2f, position.y + dy + symbolFont.getHeight(symbol)/2);

        Game.getGraphics().setDepth(500);
        float ring = 0;
        float[] ringn = new float[6];
        for(int i = 1; i <= charge; i += 1){
            if(i >= 1 && i <= 2){
                ring = 1;
                ++ringn[(int)ring-1];
            }
            if(i >= 3 && i <= 8){
                ring = 2;
                ++ringn[(int)ring-1];
            }
            if(i >= 9 && i <= 18){
                ring = 3;
                ++ringn[(int)ring-1];
            }
            if(i >= 19 && i <= 32){
                ring = 4;
                ++ringn[(int)ring-1];
            }
            if(i >= 33 && i <= 50){
                ring = 5;
                ++ringn[(int)ring-1];
            }
            if(i >= 51 && i <= 72){
                ring = 6;
                ++ringn[(int)ring-1];
            }
        }
        for(int i = 1; i <= charge; i += 1){
            if(i >= 1 && i <= 2){
                ring = 1;
            }
            if(i >= 3 && i <= 8){
                ring = 2;
            }
            if(i >= 9 && i <= 18){
                ring = 3;
            }
            if(i >= 19 && i <= 32){
                ring = 4;
            }
            if(i >= 33 && i <= 50){
                ring = 5;
            }
            if(i >= 51 && i <= 72){
                ring = 6;
            }
            float d = 360/ringn[(int)ring-1];
            float r = currentRadios + 30f + 10f*(float)Math.pow(ring,2);
            float cr = 5;
            float ag = ring % 2 != 0 ? innerRingAlpha + ring*30f : innerRingAlpha/2f + ring*30f;
            float diX = (position.x+dx) + (float)Math.cos((float)Math.toRadians(ag + d*i))*r;
            float diY = (position.y+dy) + (float)Math.sin((float)Math.toRadians(ag + d*i))*r;
            Game.getGraphics().setColor(new Color(0f, 0.9f, 1f, 0.6f));
            Circle.draw(diX, diY, cr*1.2f, 1, true);
            Game.getGraphics().setColor(new Color(0f, 0.7f, 1f, 1f));
            Circle.draw(diX, diY, cr, 1, true);
        }
        Game.getGraphics().resetDepth();
    }

    long stableCheck = 0;

    void split(){
        int nM = 0;
        int chr = charge;
        if(this instanceof ParticleUranium){
            chr *= 0.20f;
        }
        // charges
        for(int i = 0; i < chr; ++i){
            particleSplit split = new particleSplit();
            split.bodyColor.set(new Color(0, 0, 1, 0.5f));
            split.inertia.set(Tools.random(-100, 100), Tools.random(-100, 100));
            split.position.set(position);
            Game.getGameInfo().mRads += 25;
            nM += 25;
            Game.getWorld().add(split);
        }
        int whole = (int) (getMass() / 10);
        if(this instanceof ParticleUranium){
            whole = 0;
        }
        // everything else
        for(int i = 0; i < whole; ++i){
            particleSplit split = new particleSplit();
            split.bodyColor.set(new Color(((float)Tools.random(1, 255)/255f), ((float)Tools.random(1, 255)/255f), ((float)Tools.random(1, 255)/255f), 0.75f));
            split.inertia.set(Tools.random(-100, 100), Tools.random(-100, 100));
            split.position.set(position);
            Game.getGameInfo().mRads += 10;
            nM += 10;
            Game.getWorld().add(split);
        }

        liftMessage msg = new liftMessage();
        msg.message = "split";
        msg.color.set(1f, 0f, 0.2f, 1f);
        msg.position.set(position);
        msg.calculateTarget();
        Game.getWorld().add(msg);

        liftMessage msg2= new liftMessage();
        msg2.message = "+"+String.valueOf(nM);
        msg2.color.set(new Color(0.7f, 1f, 0f, 1f));
        Vec2 dposition = new Vec2(position);
        dposition.y += 48;
        msg2.position.set(dposition);
        msg2.calculateTarget();
        Game.getWorld().add(msg2);

        snapEffect ef = new snapEffect();
        ef.position.set(position.x, position.y);
        ef.stretch = 12;
        ef.stretchMax = 48;
        ef.radios += 40;
        world.add(ef);
        Game.getWorld().remove(this);
    }

    @Override
    void update(){
        super.update();
        // out of bounds

    }

    @Override
    void physicsUpdate(){
        if(Tools.getTicks()-stableCheck > 3000){
            if(charge >= unstableCharge){
                split();
                return;
            }
            if(charge <= unstableChargeMin){
                split();
                return;
            }
            stableCheck = Tools.getTicks();
        }
        positionTrans.x += inertia.x;
        positionTrans.y += inertia.y;
        position.lerp(positionTrans, 0.25f);
        // friction
        inertia.x *= 0.25f;
        inertia.y *= 0.25f;
        // out of bounds
        if(position.x > Game.getGraphics().getWidth()*2 || position.y > Game.getGraphics().getHeight()*2 || position.x < -Game.getGraphics().getWidth()  || position.y < -Game.getGraphics().getHeight()){
            world.remove(this);
            Tools.print("destroyed entity");
            return;
        }
        if(position.x > Game.getGraphics().getWidth() || position.y > Game.getGraphics().getHeight() || position.x < 0 || position.y < 0){
            float a = (float)Math.atan2(Game.getGraphics().getHeight()/2f - position.y, Game.getGraphics().getWidth()/2f - position.x);
            addInertia((float)Math.cos(a)*10, (float)Math.sin(a)*10);
        }
        // repulsion & fusion & fission
        ArrayList<Entity> entities = Game.getWorld().entities;
        for(int i = 0; i < entities.size(); ++i){
            Entity ent = entities.get(i);
            if(ent == this) continue;
            if(ent.destroyed) continue;
            if(!(ent instanceof Particle)) continue;
            if(ent.getDistance(this) < 100){
                float a = (float)Math.atan2(ent.position.y - position.y, ent.position.x - position.x);
                ent.addInertia((float)Math.cos(a)*50, (float)Math.sin(a)*50);
            }
        }
    }
}
