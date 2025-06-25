package com.de1.AngryBirds.GameObjects.Pigs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.io.Serializable;

public class Pig extends Actor implements Serializable {

    protected Texture texture;
    protected Body body;
    protected World world;
    protected float xPos, yPos;
    protected int health;
    protected float radius;

    public Pig(World world,float xPos, float yPos, int health) {
        this.world = world;
        this.health = health;
        this.xPos = xPos;
        this.yPos = yPos;

        setPosition(xPos, yPos);

    }

    public World getWorld() {
        return world;
    }

    public float getxPos() {
        return xPos;
    }

    public float getyPos() {
        return yPos;
    }

    public Body getBody() {
        return body;
    }

    public void Damage(int damage) {
        this.health -= damage;

    }
    public boolean isDestroyed() {
        return health <= 0;
    }

    public int getHealth() {

        return health;
    }

    public void testCreateBody(float x, float y) {
        createBody(x, y);
    }

    protected void createBody(float x, float y) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        this.body = world.createBody(bodyDef);
        float radius = this.radius;

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.6f;
        fixtureDef.restitution = 0.2f;
        body.createFixture(fixtureDef);
        body.setUserData(this);
        shape.dispose();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        setPosition(
            (body.getPosition().x) - getWidth() / 2,
            (body.getPosition().y) - getHeight() / 2
        );

        setRotation(MathUtils.radiansToDegrees * body.getAngle());

        batch.draw(
            this.texture,
            getX(), getY(),
            getOriginX(), getOriginY(),
            getWidth(), getHeight(),
            getScaleX(), getScaleY(),
            getRotation(),
            0, 0, (int)getWidth(), (int)getHeight(),
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
