package com.de1.AngryBirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.de1.AngryBirds.GameObjects.Birds.Bird;
import com.de1.AngryBirds.GameObjects.Birds.Black;
import com.de1.AngryBirds.GameObjects.Birds.Red;
import com.de1.AngryBirds.GameObjects.Birds.Yellow;
import com.de1.AngryBirds.GameObjects.Blocks.Block;
import com.de1.AngryBirds.GameObjects.Blocks.GlassBlocks.*;
import com.de1.AngryBirds.GameObjects.Blocks.StoneBlocks.*;
import com.de1.AngryBirds.GameObjects.Blocks.WoodBlocks.*;
import com.de1.AngryBirds.GameObjects.Pigs.BigPig;
import com.de1.AngryBirds.GameObjects.Pigs.KingPig;
import com.de1.AngryBirds.GameObjects.Pigs.Pig;
import com.de1.AngryBirds.GameObjects.Pigs.SmallPig;
import com.de1.AngryBirds.Levels.Level;
import com.de1.AngryBirds.Levels.Level1;
import com.de1.AngryBirds.Levels.Level2;
import com.de1.AngryBirds.Levels.Level3;
import com.de1.AngryBirds.Screens.SplashScreen;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Objects;

public class MyGame extends Game {

    public SpriteBatch batch;
    public int LevelsCleared;
    public int TotalLvLs;
    public Level[] levels;
    public int[] highScores;
    public Music bgMusic;

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.LevelsCleared = 1;
        this.TotalLvLs = 10;
        levels = new Level[this.TotalLvLs + 1];
        highScores = new int[this.TotalLvLs + 1];

        loadGameData("gameData.dat");

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("Sound\\Angry-Birds-Theme-Song.mp3"));
        bgMusic.setLooping(true);
        bgMusic.setVolume(0.05f);
        bgMusic.play();
        setScreen(new SplashScreen(this));

    }

    public void loadLevel(int i) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

            String filePath = "level" + i + ".json";
            if (Gdx.files.local(filePath).exists()) {
                try (FileReader reader = new FileReader(Gdx.files.local(filePath).file())) {
                    LevelState levelState = gson.fromJson(reader, LevelState.class);
                    switch (i) {
                        case 1:
                            levels[i] = new Level1(this, levelState.lvlnum);
                            break;
                        case 2:
                            levels[i] = new Level2(this, levelState.lvlnum);
                            break;
                        case 3:
                            levels[i] = new Level3(this, levelState.lvlnum);
                            break;
                        default:
                            levels[i] = null;
                            break;
                    }
                    levels[i].score = levelState.score;
                    levels[i].stars = levelState.stars;

                    Iterator<Block> blockIterator =levels[i].getBlocks().iterator();
                    while (blockIterator.hasNext()) {
                        Block block = blockIterator.next();

                        levels[i].getWorld().destroyBody(block.getBody());
                        block.remove();
                        blockIterator.remove();
                    }

                    Iterator<Pig> pigIterator = levels[i].getPigs().iterator();
                    while (pigIterator.hasNext()) {
                        Pig pig = pigIterator.next();

                        levels[i].getWorld().destroyBody(pig.getBody());
                        pig.remove();
                        pigIterator.remove();
                    }

                    Iterator<Bird> birdIterator = levels[i].getBirds().iterator();
                    while (birdIterator.hasNext()) {
                        Bird bird = birdIterator.next();

                        levels[i].getWorld().destroyBody(bird.getBody());
                        bird.remove();
                        birdIterator.remove();
                    }

                    levels[i].getCataPult().remove();
                    levels[i].getPauseWin().remove();

                    for (BirdState birdState : levelState.birds) {
                        if(Objects.equals(birdState.type, "Red")){
                            Red bird = new Red(levels[i].getWorld(), birdState.x, birdState.y, birdState.health, birdState.damage, levels[i].getCataPult());
                            bird.getBody().setTransform(birdState.x, birdState.y, birdState.angle);
                            bird.getBody().setLinearVelocity(birdState.velocity);
                            bird.getBody().setAngularVelocity(birdState.AngularVelocity);
                            bird.isshot = birdState.isshot;
                            bird.isLoaded = birdState.isLoaded;
                            levels[i].getBirds().add(bird);
                            levels[i].getStage().addActor(bird);
//                            if(bird.isLoaded && !bird.isshot) {
//                                levels[i].getCataPult().loadBird(bird);
//                            }
                        }
                        else if(Objects.equals(birdState.type, "Yellow")){
                            Yellow bird = new Yellow(levels[i].getWorld(), birdState.x, birdState.y, birdState.health, birdState.damage, levels[i].getCataPult());
                            bird.getBody().setTransform(birdState.x, birdState.y, birdState.angle);
                            bird.getBody().setLinearVelocity(birdState.velocity);
                            bird.getBody().setAngularVelocity(birdState.AngularVelocity);
                            bird.isshot = birdState.isshot;
                            levels[i].getBirds().add(bird);
                            levels[i].getStage().addActor(bird);
//                            if(bird.isLoaded && !bird.isshot) {
//                                levels[i].getCataPult().loadBird(bird);
//                            }
                        }
                        else if(Objects.equals(birdState.type, "Black")){
                            Black bird = new Black(levels[i].getWorld(), birdState.x, birdState.y, birdState.health, birdState.damage, levels[i].getCataPult());
                            bird.getBody().setTransform(birdState.x, birdState.y, birdState.angle);
                            bird.getBody().setLinearVelocity(birdState.velocity);
                            bird.getBody().setAngularVelocity(birdState.AngularVelocity);
                            bird.isshot = birdState.isshot;
                            levels[i].getBirds().add(bird);
                            levels[i].getStage().addActor(bird);
//                            if(bird.isLoaded && !bird.isshot) {
//                                levels[i].getCataPult().loadBird(bird);
//                            }
                        }
                    }
                    levels[i].getStage().addActor(levels[i].getCataPult());

                    for (PigState pigState : levelState.pigs) {
                        if(Objects.equals(pigState.type, "KingPig")){
                            KingPig pig = new KingPig(levels[i].getWorld(), pigState.x, pigState.y, pigState.health);
                            pig.getBody().setTransform(pigState.x, pigState.y, pigState.angle);
                            pig.getBody().setLinearVelocity(pigState.velocity);
                            pig.getBody().setAngularVelocity(pigState.AngularVelocity);
                            levels[i].getPigs().add(pig);
                            levels[i].getStage().addActor(pig);
                        }
                        else if(Objects.equals(pigState.type, "BigPig")){
                            BigPig pig = new BigPig(levels[i].getWorld(), pigState.x, pigState.y, pigState.health);
                            pig.getBody().setTransform(pigState.x, pigState.y, pigState.angle);
                            pig.getBody().setLinearVelocity(pigState.velocity);
                            pig.getBody().setAngularVelocity(pigState.AngularVelocity);
                            levels[i].getPigs().add(pig);
                            levels[i].getStage().addActor(pig);
                        }
                        else if(Objects.equals(pigState.type, "SmallPig")) {
                            SmallPig pig = new SmallPig(levels[i].getWorld(), pigState.x, pigState.y, pigState.health);
                            pig.getBody().setTransform(pigState.x, pigState.y, pigState.angle);
                            pig.getBody().setLinearVelocity(pigState.velocity);
                            pig.getBody().setAngularVelocity(pigState.AngularVelocity);
                            levels[i].getPigs().add(pig);
                            levels[i].getStage().addActor(pig);
                        }
                    }

                    for (BlockState blockState : levelState.blocks) {
                        if(Objects.equals(blockState.type,"HollowSquareGlass")) {
                            HollowSquareGlass block = new HollowSquareGlass(levels[i].getWorld(), blockState.x, blockState.y, blockState.health);
                            block.getBody().setTransform(blockState.x, blockState.y, blockState.angle* MathUtils.degreesToRadians);
                            block.getBody().setLinearVelocity(blockState.velocity);
                            block.getBody().setAngularVelocity(blockState.angularVelocity);
                            levels[i].getBlocks().add(block);
                            levels[i].getStage().addActor(block);
                        }
                        else if(Objects.equals(blockState.type,"HollowSquareStone")) {
                            HollowSquareStone block = new HollowSquareStone(levels[i].getWorld(), blockState.x, blockState.y, blockState.health);
                            block.getBody().setTransform(blockState.x, blockState.y, blockState.angle* MathUtils.degreesToRadians);
                            block.getBody().setLinearVelocity(blockState.velocity);
                            block.getBody().setAngularVelocity(blockState.angularVelocity);
                            levels[i].getBlocks().add(block);
                            levels[i].getStage().addActor(block);
                        }
                        else if(Objects.equals(blockState.type,"HollowSquareWood")) {
                            HollowSquareWood block = new HollowSquareWood(levels[i].getWorld(), blockState.x, blockState.y, blockState.health);
                            block.getBody().setTransform(blockState.x, blockState.y, blockState.angle* MathUtils.degreesToRadians);
                            block.getBody().setLinearVelocity(blockState.velocity);
                            block.getBody().setAngularVelocity(blockState.angularVelocity);
                            levels[i].getBlocks().add(block);
                            levels[i].getStage().addActor(block);
                        }
                        else if(Objects.equals(blockState.type,"SquareGlass")) {
                            SquareGlass block = new SquareGlass(levels[i].getWorld(), blockState.x, blockState.y, blockState.health);
                            block.getBody().setTransform(blockState.x, blockState.y, blockState.angle* MathUtils.degreesToRadians);
                            block.getBody().setLinearVelocity(blockState.velocity);
                            block.getBody().setAngularVelocity(blockState.angularVelocity);
                            levels[i].getBlocks().add(block);
                            levels[i].getStage().addActor(block);
                        }
                        else if(Objects.equals(blockState.type,"SquareWood")) {
                            SquareWood block = new SquareWood(levels[i].getWorld(), blockState.x, blockState.y, blockState.health);
                            block.getBody().setTransform(blockState.x, blockState.y, blockState.angle* MathUtils.degreesToRadians);
                            block.getBody().setLinearVelocity(blockState.velocity);
                            block.getBody().setAngularVelocity(blockState.angularVelocity);
                            levels[i].getBlocks().add(block);
                            levels[i].getStage().addActor(block);
                        }
                        else if(Objects.equals(blockState.type,"SquareStone")) {
                            SquareStone block = new SquareStone(levels[i].getWorld(), blockState.x, blockState.y, blockState.health);
                            block.getBody().setTransform(blockState.x, blockState.y, blockState.angle* MathUtils.degreesToRadians);
                            block.getBody().setLinearVelocity(blockState.velocity);
                            block.getBody().setAngularVelocity(blockState.angularVelocity);
                            levels[i].getBlocks().add(block);
                            levels[i].getStage().addActor(block);
                        }
                        else if(Objects.equals(blockState.type,"ThickRectGlass")) {
                            ThickRectGlass block = new ThickRectGlass(levels[i].getWorld(), blockState.x, blockState.y, blockState.angle, blockState.health);
//                            block.getBody().setTransform(blockState.x, blockState.y, blockState.angle);
                            block.getBody().setLinearVelocity(blockState.velocity);
                            block.getBody().setAngularVelocity(blockState.angularVelocity);
                            levels[i].getBlocks().add(block);
                            levels[i].getStage().addActor(block);
                        }
                        else if(Objects.equals(blockState.type,"ThickRectStone")) {
                            ThickRectStone block = new ThickRectStone(levels[i].getWorld(), blockState.x, blockState.y, blockState.angle, blockState.health);
//                            block.getBody().setTransform(blockState.x, blockState.y, blockState.angle);
                            block.getBody().setLinearVelocity(blockState.velocity);
                            block.getBody().setAngularVelocity(blockState.angularVelocity);
                            levels[i].getBlocks().add(block);
                            levels[i].getStage().addActor(block);
                        }
                        else if(Objects.equals(blockState.type,"ThickRectWood")) {
                            ThickRectWood block = new ThickRectWood(levels[i].getWorld(), blockState.x, blockState.y, blockState.angle, blockState.health);
//                            block.getBody().setTransform(blockState.x, blockState.y, blockState.angle);
                            block.getBody().setLinearVelocity(blockState.velocity);
                            block.getBody().setAngularVelocity(blockState.angularVelocity);
                            levels[i].getBlocks().add(block);
                            levels[i].getStage().addActor(block);
                        }
                        else if(Objects.equals(blockState.type,"ThinRectStone1")) {
                            ThinRectStone1 block = new ThinRectStone1(levels[i].getWorld(), blockState.x, blockState.y, blockState.angle, blockState.health);
//                            block.getBody().setTransform(blockState.x, blockState.y, blockState.angle);
                            block.getBody().setLinearVelocity(blockState.velocity);
                            block.getBody().setAngularVelocity(blockState.angularVelocity);
                            levels[i].getBlocks().add(block);
                            levels[i].getStage().addActor(block);
                        }
                        else if(Objects.equals(blockState.type,"ThinRectStone2")) {
                            ThinRectStone2 block = new ThinRectStone2(levels[i].getWorld(), blockState.x, blockState.y, blockState.angle, blockState.health);
//                            block.getBody().setTransform(blockState.x, blockState.y, blockState.angle);
                            block.getBody().setLinearVelocity(blockState.velocity);
                            block.getBody().setAngularVelocity(blockState.angularVelocity);
                            levels[i].getBlocks().add(block);
                            levels[i].getStage().addActor(block);
                        }
                        else if(Objects.equals(blockState.type,"ThinRectStone3")) {
                            ThinRectStone3 block = new ThinRectStone3(levels[i].getWorld(), blockState.x, blockState.y, blockState.angle, blockState.health);
//                            block.getBody().setTransform(blockState.x, blockState.y, blockState.angle);
                            block.getBody().setLinearVelocity(blockState.velocity);
                            block.getBody().setAngularVelocity(blockState.angularVelocity);
                            levels[i].getBlocks().add(block);
                            levels[i].getStage().addActor(block);
                        }
                        else if(Objects.equals(blockState.type,"ThinRectStone4")) {
                            ThinRectStone4 block = new ThinRectStone4(levels[i].getWorld(), blockState.x, blockState.y, blockState.angle, blockState.health);
//                            block.getBody().setTransform(blockState.x, blockState.y, blockState.angle);
                            block.getBody().setLinearVelocity(blockState.velocity);
                            block.getBody().setAngularVelocity(blockState.angularVelocity);
                            levels[i].getBlocks().add(block);
                            levels[i].getStage().addActor(block);
                        }
                        else if(Objects.equals(blockState.type,"ThinRectWood1")) {
                            ThinRectWood1 block = new ThinRectWood1(levels[i].getWorld(), blockState.x, blockState.y, blockState.angle, blockState.health);
//                            block.getBody().setTransform(blockState.x, blockState.y, blockState.angle);
                            block.getBody().setLinearVelocity(blockState.velocity);
                            block.getBody().setAngularVelocity(blockState.angularVelocity);
                            levels[i].getBlocks().add(block);
                            levels[i].getStage().addActor(block);
                        }
                        else if(Objects.equals(blockState.type,"ThinRectWood2")) {
                            ThinRectWood2 block = new ThinRectWood2(levels[i].getWorld(), blockState.x, blockState.y, blockState.angle, blockState.health);
//                            block.getBody().setTransform(blockState.x, blockState.y, blockState.angle);
                            block.getBody().setLinearVelocity(blockState.velocity);
                            block.getBody().setAngularVelocity(blockState.angularVelocity);
                            levels[i].getBlocks().add(block);
                            levels[i].getStage().addActor(block);
                        }
                        else if(Objects.equals(blockState.type,"ThinRectWood3")) {
                            ThinRectWood3 block = new ThinRectWood3(levels[i].getWorld(), blockState.x, blockState.y, blockState.angle, blockState.health);
//                            block.getBody().setTransform(blockState.x, blockState.y, blockState.angle);
                            block.getBody().setLinearVelocity(blockState.velocity);
                            block.getBody().setAngularVelocity(blockState.angularVelocity);
                            levels[i].getBlocks().add(block);
                            levels[i].getStage().addActor(block);
                        }
                        else if(Objects.equals(blockState.type,"ThinRectWood4")) {
                            ThinRectWood4 block = new ThinRectWood4(levels[i].getWorld(), blockState.x, blockState.y, blockState.angle, blockState.health);
//                            block.getBody().setTransform(blockState.x, blockState.y, blockState.angle);
                            block.getBody().setLinearVelocity(blockState.velocity);
                            block.getBody().setAngularVelocity(blockState.angularVelocity);
                            levels[i].getBlocks().add(block);
                            levels[i].getStage().addActor(block);
                        }
                        else if(Objects.equals(blockState.type,"ThinRectGlass1")) {
                            ThinRectGlass1 block = new ThinRectGlass1(levels[i].getWorld(), blockState.x, blockState.y, blockState.angle, blockState.health);
//                            block.getBody().setTransform(blockState.x, blockState.y, blockState.angle);
                            block.getBody().setLinearVelocity(blockState.velocity);
                            block.getBody().setAngularVelocity(blockState.angularVelocity);
                            levels[i].getBlocks().add(block);
                            levels[i].getStage().addActor(block);
                        }
                        else if(Objects.equals(blockState.type,"ThinRectGlass2")) {
                            ThinRectGlass2 block = new ThinRectGlass2(levels[i].getWorld(), blockState.x, blockState.y, blockState.angle, blockState.health);
//                            block.getBody().setTransform(blockState.x, blockState.y, blockState.angle);
                            block.getBody().setLinearVelocity(blockState.velocity);
                            block.getBody().setAngularVelocity(blockState.angularVelocity);
                            levels[i].getBlocks().add(block);
                            levels[i].getStage().addActor(block);
                        }
                        else if(Objects.equals(blockState.type,"ThinRectGlass3")) {
                            ThinRectGlass3 block = new ThinRectGlass3(levels[i].getWorld(), blockState.x, blockState.y, blockState.angle, blockState.health);
//                            block.getBody().setTransform(blockState.x, blockState.y, blockState.angle);
                            block.getBody().setLinearVelocity(blockState.velocity);
                            block.getBody().setAngularVelocity(blockState.angularVelocity);
                            levels[i].getBlocks().add(block);
                            levels[i].getStage().addActor(block);
                        }
                        else if(Objects.equals(blockState.type,"ThinRectGlass4")) {
                            ThinRectGlass4 block = new ThinRectGlass4(levels[i].getWorld(), blockState.x, blockState.y, blockState.angle, blockState.health);
//                            block.getBody().setTransform(blockState.x, blockState.y, blockState.angle);
                            block.getBody().setLinearVelocity(blockState.velocity);
                            block.getBody().setAngularVelocity(blockState.angularVelocity);
                            levels[i].getBlocks().add(block);
                            levels[i].getStage().addActor(block);
                        }
                    }
                    levels[i].getStage().addActor(levels[i].getPauseWin());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                catch (com.google.gson.JsonSyntaxException e) {
                    System.err.println("Error parsing JSON file: " + filePath);
                    e.printStackTrace();
                }
            }
//            else {
//                levels[i] = new ;
//            }
    }

    public void saveGameData(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(Gdx.files.local(filePath).write(false))) {
            GameData data = new GameData(this.LevelsCleared, this.highScores);
            oos.writeObject(data);

        } catch (IOException e) {
            System.err.println("Failed to save game data: " + e.getMessage());
        }
    }

    public void loadGameData(String filePath) {
        if (!Gdx.files.local(filePath).exists()) {

            this.LevelsCleared = 1;
            this.highScores = new int[this.TotalLvLs + 1];
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(Gdx.files.local(filePath).read())) {
            GameData data = (GameData) ois.readObject();
            this.LevelsCleared = data.levelsCleared;
            this.highScores = data.highScores;

        } catch (IOException | ClassNotFoundException e) {

            this.LevelsCleared = 1;
            this.highScores = new int[this.TotalLvLs + 1];
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        bgMusic.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        saveGameData("gameData.dat");
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }
}
