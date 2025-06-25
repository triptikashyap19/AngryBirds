package com.de1.AngryBirds.GameObjects.Blocks;

import com.badlogic.gdx.physics.box2d.World;

public class Glass extends Block {

    public Glass(World world, float xPos, float yPos, int health){
        super(world,xPos, yPos);
        this.health = health;
    }
}
