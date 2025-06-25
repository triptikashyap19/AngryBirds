package com.de1.AngryBirds.GameObjects.Birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.de1.AngryBirds.GameObjects.CataPult;

import java.io.Serializable;

public class Bird extends Actor implements Serializable {
    protected Texture texture;
    protected float xPos;
    protected float yPos;
    protected Body body;
    protected World world;
    protected int health;
    protected int speed;
    protected int damage;
    protected float radius;
    public boolean isshot;
    protected CataPult cataPult;
    public boolean isLoaded;

    public Bird(World world, float xPos, float yPos, int health, int speed, int damage, CataPult cataPult) {
        this.world = world;
        this.health = health;
        this.speed = speed;
        this.damage = damage;
        this.isshot = false;
        this.cataPult = cataPult;
        this.xPos = xPos;
        this.yPos = yPos;
        this.isLoaded = false;

        setPosition(xPos, yPos);

    }

    protected void createBody(float x, float y) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x , y);

        this.body = world.createBody(bodyDef);
        float radius = Math.max(getWidth(), getHeight()) / 2f;
        this.radius = radius;
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.3f;
        body.createFixture(fixtureDef);
        body.setUserData(this);

        shape.dispose();
    }

    public void launch(Vector2 impulse) {
        if (body != null) {
            body.applyLinearImpulse(impulse , body.getWorldCenter(), true);
        }
    }
    public float getxPos(){
        return xPos;
    }
    public float getyPos(){
        return yPos;
    }

    public Body getBody(){
        return body;
    }
    public float getRadius(){
        return radius;
    }

    public int getDamage(){
        return damage;
    }

    public int getHealth(){
        return health;
    }
    public void makeDead(){
        this.health = 0;
    }
    public boolean isDestroyed() {
        return health <= 0;
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
