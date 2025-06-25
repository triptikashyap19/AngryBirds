package com.de1.AngryBirds;

import com.badlogic.gdx.math.Vector2;

public class BirdState {

    float x,y;
    float angle;
    Vector2 velocity;
    float AngularVelocity;
    int health;
    String type;
    int damage;
    boolean isshot;
    boolean isLoaded;

    public BirdState(float x, float y, float angle, Vector2 velocity,float AngularVelocity, int health, int damage, boolean isshot,String type, boolean isLoaded) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.velocity = velocity;
        this.health = health;
        this.type = type;
        this.damage = damage;
        this.isshot = isshot;
        this.isLoaded = isLoaded;
        this.AngularVelocity = AngularVelocity;
    }
}
