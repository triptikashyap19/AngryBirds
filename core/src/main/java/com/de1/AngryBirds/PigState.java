package com.de1.AngryBirds;

import com.badlogic.gdx.math.Vector2;

public class PigState {

    float x, y;
    float angle;
    Vector2 velocity;
    float AngularVelocity;
    int health;
    String type;

    public PigState(float x, float y, float angle, Vector2 velocity, int health, String type, float AngularVelocity) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.velocity = velocity;
        this.health = health;
        this.type = type;
        this.AngularVelocity = AngularVelocity;
    }
}
