package com.de1.AngryBirds.GameObjects.Blocks.StoneBlocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.de1.AngryBirds.GameObjects.Blocks.Stone;

public class HollowSquareStone extends Stone {

    public HollowSquareStone(World world, float xPos, float yPos, int health) {

        super(world,xPos, yPos, health);

        this.texture = new Texture(Gdx.files.internal("Images\\Stone\\HollowSquareStone.png"));
        setSize(texture.getWidth(), texture.getHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);

        createBody(xPos , yPos );

    }
}
