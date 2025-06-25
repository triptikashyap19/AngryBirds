package com.de1.AngryBirds.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.de1.AngryBirds.GameObjects.Birds.Bird;
import com.de1.AngryBirds.GameObjects.Birds.Red;
import com.de1.AngryBirds.GameObjects.Birds.Yellow;
import com.de1.AngryBirds.GameObjects.Blocks.Block;
import com.de1.AngryBirds.GameObjects.Blocks.GlassBlocks.*;
import com.de1.AngryBirds.GameObjects.Blocks.StoneBlocks.ThickRectStone;
import com.de1.AngryBirds.GameObjects.Blocks.StoneBlocks.ThinRectStone1;
import com.de1.AngryBirds.GameObjects.Blocks.WoodBlocks.SquareWood;
import com.de1.AngryBirds.GameObjects.Blocks.WoodBlocks.ThinRectWood1;
import com.de1.AngryBirds.GameObjects.Pigs.KingPig;
import com.de1.AngryBirds.GameObjects.Pigs.Pig;
import com.de1.AngryBirds.GameObjects.Pigs.SmallPig;
import com.de1.AngryBirds.MyGame;
import com.de1.AngryBirds.Screens.DefeatScreen;
import com.de1.AngryBirds.Screens.VictoryScreen;

public class Level2 extends Level {

    private Texture mushroom;
    private Sprite mushroomSprite;
    private Texture greenGrass;
    private Sprite greenGrassSprite;
    private float defeatTimer;

    public Level2(MyGame game, int levelnum) {
        super(game,levelnum);
        this.lvlnum = 2;
        this.defeatTimer = 0;
        mushroom = new Texture(Gdx.files.internal("Images\\Decors\\Mushroom.png"));
        mushroomSprite = new Sprite(mushroom);
        mushroomSprite.setCenter(1800,310);

        greenGrass = new Texture(Gdx.files.internal("Images\\Decors\\GreenGrass.png"));
        greenGrassSprite = new Sprite(greenGrass);
        greenGrassSprite.setCenter(1850,310);
        createLvL2();
    }

    private void createLvL2(){

        this.birds.add(new Red(this.world,120,295,100,100,cataPult));
        this.birds.add(new Yellow(this.world,60,295,200,200,cataPult));

        this.pigs.add(new KingPig(this.world,1398,716,300));
        this.pigs.add(new SmallPig(this.world,1325,433,100));
        this.pigs.add(new SmallPig(this.world,1475,433,100));

        this.blocks.add(new SquareWood(this.world,1250,356,200));
        this.blocks.add(new SquareWood(this.world,1550,356,200));
        this.blocks.add(new HollowSquareGlass(this.world,1400,356,100));
        this.blocks.add(new HollowSquareGlass(this.world,1259,593,100));
        this.blocks.add(new HollowSquareGlass(this.world,1540,593,100));
        this.blocks.add(new ThickRectStone(this.world,1200,296,0,300));
        this.blocks.add(new ThickRectGlass(this.world,1300,296,0,100));
        this.blocks.add(new ThickRectGlass(this.world,1400,296,0,100));
        this.blocks.add(new ThickRectGlass(this.world,1500,296,0,100));
        this.blocks.add(new ThickRectStone(this.world,1600,296,0,300));
        this.blocks.add(new ThinRectStone1(this.world,1400,410,0,300));
        this.blocks.add(new ThinRectWood1(this.world,1400,655,0,200));
        this.blocks.add(new ThinRectGlass3(this.world,1260,492,90,100));
        this.blocks.add(new ThinRectGlass3(this.world,1540,492,90,100));

        for(Bird bird: this.birds){
            stage.addActor(bird);
        }

        stage.addActor(cataPult);

        for(Pig pig: this.pigs){
            stage.addActor(pig);
        }

        for(Block block: this.blocks){
            stage.addActor(block);
        }
        this.stage.addActor(pauseWin);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!paused) {
            world.step(1 / 60f, 8, 3);
            removeDeadObjects();
            if(cataPult.getBird() == null && flag){
                Bird birdToLoad = getBirdtoLoad();
                if( birdToLoad != null){
                    cataPult.loadBird(birdToLoad);
                    flag = false;
                }
            }
            scoreval.setText(String.valueOf(score));

            if(score >= 20000){
                stars = 3;
            }
            else{
                stars = 2;
            }

            if(pigs.isEmpty()){
                this.game.setScreen(new VictoryScreen(this.game, this.lvlnum));
            }

            if(birds.isEmpty()){
                defeatTimer += delta;

                if (defeatTimer >= 15 && !pigs.isEmpty()) {
                    this.game.setScreen(new DefeatScreen(this.game, this.lvlnum));
                }
            }
        }

        this.cam.update();
        this.game.batch.setProjectionMatrix(cam.combined);

        this.game.batch.begin();
        this.LvlSprite.draw(this.game.batch);
        this.greenGrassSprite.draw(this.game.batch);
        this.mushroomSprite.draw(this.game.batch);

        this.game.batch.end();
//        debugRenderer.render(world, cam.combined);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show(){
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        this.stage.dispose();
        this.whitefont.dispose();
        this.blackfont.dispose();
        for (Bird bird : this.birds) {
            bird.dispose();
        }
        for(Pig pig : this.pigs){
            pig.dispose();
        }
        cataPult.dispose();
        for (Block block : this.blocks) {
            block.dispose();
        }
        LvlSprite.getTexture().dispose();
        mushroom.dispose();
        greenGrass.dispose();
        world.dispose();
        debugRenderer.dispose();

    }
}
