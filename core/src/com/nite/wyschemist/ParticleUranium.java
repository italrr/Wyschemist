package com.nite.wyschemist;

import com.badlogic.gdx.graphics.Color;

public class ParticleUranium extends Particle {

    @Override
    void create() {
        super.create();
        charge = 92;
        spawnCharge = 92;
        radiation = 5;
        stability = 10;
        mass = 4;
        unstableCharge = 93;
        unstableChargeMin = 1;
        radios = 46;
        symbol = "U";
//      thetaResonance = new Resonance(); // First
//      deltaResonance = new Resonance(); // Second
//      betaResonance = new Resonance(); // Background
        thetaColor = new Color(0.9f, 0.6f, 0f, 1f); // Inner
        //deltaColor = new Color(1, 1, 1, 1); // Outer
    }

    @Override
    void destroy() {

    }

    @Override
    void draw() {
        drawBody();
    }

    @Override
    void update() {
        orbiting();
    }

}
