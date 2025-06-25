package com.de1.AngryBirds;

import com.badlogic.gdx.math.Vector2;

public class BlockState {

    float x, y;
    float angle;
    Vector2 velocity;
    float angularVelocity;
    int health;
    String type;

    public BlockState(float x, float y, float angle, Vector2 velocity, int health, String type,float angularVelocity) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.velocity = velocity;
        this.health = health;
        this.type = type;
        this.angularVelocity = angularVelocity;
    }
}
