package com.de1.AngryBirds.GameObjects.Blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.io.Serializable;

public class Block extends Actor implements Serializable {

    protected Texture texture;
    protected Body body;
    protected World world;
    protected float xPos, yPos;
    protected int health;

    public Block(World world, float xPos, float yPos) {
        this.world = world;
        this.xPos = xPos;
        this.yPos = yPos;
        setPosition(xPos, yPos);
    }

    public float getxPos() {
        return xPos;
    }

    public float getyPos() {
        return yPos;
    }

    public void Damage(int damage) {
        this.health -= damage;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public Body getBody() {
        return body;
    }

    public World getWorld() {
        return world;
    }

    public boolean isDestroyed() {
        return health <= 0;
    }

    protected void createBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        this.body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2 , getHeight() / 2 );

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.6f;
        fixtureDef.restitution = 0.0f;
        body.createFixture(fixtureDef);
        body.setUserData(this);
        shape.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        setPosition(
            (body.getPosition().x ) - getWidth() / 2,
            (body.getPosition().y ) - getHeight() / 2
        );
        setRotation((float) Math.toDegrees(body.getAngle()));

        batch.draw(
            texture,
            getX(), getY(),
            getOriginX(), getOriginY(),
            getWidth(), getHeight(),
            getScaleX(), getScaleY(),
            getRotation(),
            0, 0, (int) getWidth(), (int) getHeight(),
            false, false
        );
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void dispose() {
        this.texture.dispose();
        world.destroyBody(this.body);
    }
}
