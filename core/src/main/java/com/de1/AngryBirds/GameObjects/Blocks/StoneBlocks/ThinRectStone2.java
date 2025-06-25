package com.de1.AngryBirds.GameObjects.Blocks.StoneBlocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.de1.AngryBirds.GameObjects.Blocks.Stone;

public class ThinRectStone2 extends Stone {

    public ThinRectStone2(World world, float xPos, float yPos, float angle, int health) {

        super(world,xPos, yPos, health);

        this.texture = new Texture(Gdx.files.internal("Images\\Stone\\ThinRectStone2.png"));
        setSize(texture.getWidth(), texture.getHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);

        createBody(xPos, yPos , angle);

    }

    protected void createBody(float x, float y, float angle) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.angle = angle* MathUtils.degreesToRadians;
        this.body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2 , getHeight() / 2 );

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.0f;
        body.createFixture(fixtureDef);
        body.setUserData(this);
        shape.dispose();
    }
}
