package com.de1.AngryBirds.GameObjects.Blocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class Stone extends Block{

    public Stone(World world, float xPos, float yPos, int health){
        super(world,xPos, yPos);
        this.health = health;
    }
}
