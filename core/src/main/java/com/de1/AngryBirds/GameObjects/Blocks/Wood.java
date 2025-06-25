package com.de1.AngryBirds.GameObjects.Blocks;

import com.badlogic.gdx.physics.box2d.World;

public class Wood extends Block{

    public Wood(World world ,float xPos, float yPos, int health){
        super(world,xPos, yPos);
        this.health = health;
    }
}
