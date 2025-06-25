package com.de1.AngryBirds.GameObjects.Pigs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class KingPig extends Pig {

    public KingPig(World world, float xPos, float yPos,int health) {
        super(world,xPos,yPos,health);

        this.texture = new Texture(Gdx.files.internal("Images\\KingPig.png"));
        setSize(texture.getWidth(), texture.getHeight());
        setScale(0.7f);
        setOrigin(getWidth() / 2, getHeight() / 2);
        this.radius = Math.max(getWidth(), getHeight()) / 3.0f;
        createBody(xPos, yPos);

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
        fixtureDef.density = 0.2f;
        fixtureDef.friction = 0.6f;
        fixtureDef.restitution = 0.2f;
        body.createFixture(fixtureDef);
        body.setUserData(this);
        shape.dispose();
    }
}
