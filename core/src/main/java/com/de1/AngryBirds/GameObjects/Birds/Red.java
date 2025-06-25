package com.de1.AngryBirds.GameObjects.Birds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.de1.AngryBirds.GameObjects.CataPult;

public class Red extends Bird {

    public Red(World world, float xPos, float yPos,int health,int damage, CataPult cataPult) {
        super(world, xPos, yPos, health, 100, damage, cataPult);
        this.texture = new Texture(Gdx.files.internal("Images/RedBird.png"));
        setSize(texture.getWidth(), texture.getHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);

        createBody(xPos, yPos);
    }

    protected void createBody(float x, float y) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x , y );

        this.body = world.createBody(bodyDef);
        float radius = Math.max(getWidth(), getHeight()) / 2f;
        this.radius = radius;
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.4f;
        body.createFixture(fixtureDef);
        body.setUserData(this);

        shape.dispose();
    }
}
