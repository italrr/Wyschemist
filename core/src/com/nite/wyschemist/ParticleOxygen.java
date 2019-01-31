package com.nite.wyschemist;

import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.*;

import java.util.ArrayList;

public class ParticleOxygen extends Particle {
    @Override
    void create() {
        super.create();
        charge = 8;
        spawnCharge = 8;
        radiation = 3;
        stability = 10;
        mass = 3;
        unstableCharge = 10;
        unstableChargeMin = 5;
        radios = 40;
        symbol = "O";
//      thetaResonance = new Resonance(); // First
//      deltaResonance = new Resonance(); // Second
//      betaResonance = new Resonance(); // Background
        thetaColor = new Color(0.9f, 0f, 0.1f, 1f); // Inner
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
