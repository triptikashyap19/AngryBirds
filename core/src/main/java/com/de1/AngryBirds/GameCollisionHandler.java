package com.de1.AngryBirds;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.de1.AngryBirds.GameObjects.Birds.Bird;
import com.de1.AngryBirds.GameObjects.Blocks.Block;
import com.de1.AngryBirds.GameObjects.Pigs.Pig;

import java.io.Serializable;

public class GameCollisionHandler implements ContactListener, Serializable {

    private MyGame game;
    private int lvlnum;

    public GameCollisionHandler(MyGame game, int lvlnum) {
        this.game = game;
        this.lvlnum = lvlnum;
    }

    @Override
    public void beginContact(Contact contact) {
        Object userDataA = contact.getFixtureA().getBody().getUserData();
        Object userDataB = contact.getFixtureB().getBody().getUserData();

        if (userDataA instanceof Bird && userDataB instanceof Block) {
            handleBirdBlockCollision((Bird) userDataA, (Block) userDataB);
        }
        else if (userDataA instanceof Block && userDataB instanceof Bird) {
            handleBirdBlockCollision((Bird) userDataB, (Block) userDataA);
        }
        else if (userDataA instanceof Bird && userDataB instanceof Pig) {
            handleBirdPigCollision((Bird) userDataA, (Pig) userDataB);
        }
        else if (userDataA instanceof Pig && userDataB instanceof Bird) {
            handleBirdPigCollision((Bird) userDataB, (Pig) userDataA);
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        Object userDataA = contact.getFixtureA().getBody().getUserData();
        Object userDataB = contact.getFixtureB().getBody().getUserData();

        if (userDataA instanceof Block && userDataB instanceof Pig) {
            handleBlockPigCollision((Block) userDataA, (Pig) userDataB, impulse);
        }
        else if (userDataA instanceof Pig && userDataB instanceof Block) {
            handleBlockPigCollision((Block) userDataB, (Pig) userDataA, impulse);
        }
        else if (userDataA instanceof Block && userDataB instanceof Block) {
            handleBlockBlockCollision((Block) userDataB, (Block) userDataA, impulse);
        }

    }

    private void handleBirdBlockCollision(Bird bird, Block block) {

        block.Damage(bird.getDamage());
        game.levels[lvlnum].score += bird.getDamage()*10;
        bird.makeDead();
    }
    private void handleBirdPigCollision(Bird bird, Pig pig) {

        pig.Damage(bird.getDamage());
        game.levels[lvlnum].score += bird.getDamage()*10;
        bird.makeDead();
    }

    private void handleBlockPigCollision(Block block, Pig pig, ContactImpulse impulse) {

        float damageThreshold = 20000.0f;

        float maxImpulse = 0.0f;
        for (float normalImpulse : impulse.getNormalImpulses()) {
            if (normalImpulse > maxImpulse) {
                maxImpulse = normalImpulse;
            }
        }

        if (maxImpulse > damageThreshold){
            int blockHealth = block.getHealth();
            int pigHealth = pig.getHealth();
            block.Damage(pigHealth);
            pig.Damage(blockHealth);
            game.levels[lvlnum].score += (blockHealth+pigHealth)*10;
        }
    }

    private void handleBlockBlockCollision(Block block1, Block block2,ContactImpulse impulse) {

        float damageThreshold = 100000.0f;

        float maxImpulse = 0.0f;
        for (float normalImpulse : impulse.getNormalImpulses()) {
            if (normalImpulse > maxImpulse) {
                maxImpulse = normalImpulse;
            }
        }

        if (maxImpulse > damageThreshold){
            int block1Health = block1.getHealth();
            int block2Health = block2.getHealth();
            block1.Damage(block1Health);
            block2.Damage(block1Health);
            game.levels[lvlnum].score += (block1Health+block2Health)*10;
        }
    }
}
