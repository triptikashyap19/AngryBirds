package com.de1.AngryBirds.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.de1.AngryBirds.GameObjects.Birds.Bird;
import com.de1.AngryBirds.GameObjects.Birds.Black;
import com.de1.AngryBirds.GameObjects.Birds.Red;
import com.de1.AngryBirds.GameObjects.Blocks.Block;
import com.de1.AngryBirds.GameObjects.Blocks.GlassBlocks.*;
import com.de1.AngryBirds.GameObjects.Blocks.StoneBlocks.HollowSquareStone;
import com.de1.AngryBirds.GameObjects.Blocks.StoneBlocks.ThickRectStone;
import com.de1.AngryBirds.GameObjects.Blocks.StoneBlocks.ThinRectStone1;
import com.de1.AngryBirds.GameObjects.Blocks.WoodBlocks.ThickRectWood;
import com.de1.AngryBirds.GameObjects.Blocks.WoodBlocks.ThinRectWood1;
import com.de1.AngryBirds.GameObjects.Pigs.KingPig;
import com.de1.AngryBirds.GameObjects.Pigs.Pig;
import com.de1.AngryBirds.GameObjects.Pigs.SmallPig;
import com.de1.AngryBirds.MyGame;
import com.de1.AngryBirds.Screens.DefeatScreen;
import com.de1.AngryBirds.Screens.VictoryScreen;

public class Level3 extends Level {

    private Texture mushroom;
    private Sprite mushroomSprite;
    private Texture greenGrass;
    private Sprite greenGrassSprite;
    private float defeatTimer;

    public Level3(MyGame game, int levelnum) {
        super(game,levelnum);
        this.lvlnum = 3;
        this.defeatTimer = 0;
        mushroom = new Texture(Gdx.files.internal("Images\\Decors\\Mushroom.png"));
        mushroomSprite = new Sprite(mushroom);
        mushroomSprite.setCenter(1800,310);

        greenGrass = new Texture(Gdx.files.internal("Images\\Decors\\GreenGrass.png"));
        greenGrassSprite = new Sprite(greenGrass);
        greenGrassSprite.setCenter(1850,310);
        createLvL3();
    }

    private void createLvL3(){

        this.birds.add(new Red(this.world,110,375,100,100,cataPult));
        this.birds.add(new Black(this.world,40,375,300,300,cataPult));

        this.pigs.add(new SmallPig(this.world,1325,611,100));
        this.pigs.add(new SmallPig(this.world,1175,611,100));
        this.pigs.add(new SmallPig(this.world,1251,521,100));
        this.pigs.add(new KingPig(this.world,1583,510,300));

        this.blocks.add(new ThickRectWood(this.world,1583,295,0,200));
        this.blocks.add(new ThickRectWood(this.world,1500,295,0,200));
        this.blocks.add(new ThickRectWood(this.world,1417,295,0,200));
        this.blocks.add(new ThickRectWood(this.world,1334,295,0,200));
        this.blocks.add(new ThickRectWood(this.world,1251,295,0,200));
        this.blocks.add(new ThickRectWood(this.world,1168,295,0,200));

        this.blocks.add(new ThickRectStone(this.world,1583,336,0,300));
        this.blocks.add(new ThickRectStone(this.world,1334,336,0,300));
        this.blocks.add(new ThickRectStone(this.world,1168,336,0,300));

        this.blocks.add(new HollowSquareStone(this.world,1583,398,300));
        this.blocks.add(new HollowSquareGlass(this.world,1334,398,100));
        this.blocks.add(new HollowSquareGlass(this.world,1168,398,100));

        this.blocks.add(new ThickRectGlass(this.world,1334,460,0,100));
        this.blocks.add(new ThickRectGlass(this.world,1168,460,0,100));

        this.blocks.add(new ThinRectWood1(this.world,1251,490,0,200));
        this.blocks.add(new ThinRectGlass1(this.world,1583,449,0,100));

        this.blocks.add(new ThickRectGlass(this.world,1334,540,90,100));
        this.blocks.add(new ThickRectGlass(this.world,1168,540,90,100));
        this.blocks.add(new ThinRectStone1(this.world,1251,580,0,300));

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

            if(score >= 24000){
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
