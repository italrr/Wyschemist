package com.nite.wyschemist;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.*;

import java.util.ArrayList;

public class ParticleHelium extends Particle {

    @Override
    void create() {
        super.create();
        charge = 2;
        spawnCharge = 2;
        radiation = 5;
        stability = 10;
        mass = 4;
        unstableCharge = 15;
        unstableChargeMin = 1;
        radios = 30;
        symbol = "He";
//      thetaResonance = new Resonance(); // First
//      deltaResonance = new Resonance(); // Second
//      betaResonance = new Resonance(); // Background
        thetaColor = new Color(0, 0.6f, 0.9f, 1f); // Inner
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
