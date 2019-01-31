package com.nite.wyschemist;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.*;

import java.util.ArrayList;

public class ParticleHydrogen extends Particle {

    @Override
    void create() {
        super.create();
        charge = 1;
        spawnCharge = 1;
        radiation = 5;
        stability = 10;
        mass = 25;
        unstableCharge = 5;
        unstableChargeMin = 0;
        radios = 30;
        symbol = "H";
//      thetaResonance = new Resonance(); // First
//      deltaResonance = new Resonance(); // Second
//      betaResonance = new Resonance(); // Background
        thetaColor = new Color(0, 0.8f, 0.8f, 1f); // Inner
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
