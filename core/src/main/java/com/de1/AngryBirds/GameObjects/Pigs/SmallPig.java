package com.de1.AngryBirds.GameObjects.Pigs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class SmallPig extends Pig{
    public SmallPig(World world, float xPos, float yPos, int health) {
        super(world,xPos,yPos,health);

        this.texture = new Texture(Gdx.files.internal("Images\\SmallPig.png"));
        setSize(texture.getWidth(), texture.getHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);
        this.radius = Math.max(getWidth(), getHeight()) / 2.0f;
        createBody(xPos, yPos);
    }
}
