package com.de1.AngryBirds.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.de1.AngryBirds.*;
import com.de1.AngryBirds.GameObjects.Birds.Bird;
import com.de1.AngryBirds.GameObjects.Blocks.Block;
import com.de1.AngryBirds.GameObjects.CataPult;
import com.de1.AngryBirds.GameObjects.Pigs.Pig;
import com.de1.AngryBirds.Screens.DefeatScreen;
import com.de1.AngryBirds.Screens.LevelScreen;
import com.de1.AngryBirds.Screens.LoadingScreen;
import com.de1.AngryBirds.Screens.VictoryScreen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class Level implements Screen, Serializable {

    protected MyGame game;
    protected World world;
    protected Box2DDebugRenderer debugRenderer;
    protected int lvlnum;
    public int score;
    public int stars;
    protected Viewport viewport;
    protected OrthographicCamera cam;
    protected ArrayList<Bird> birds;
    protected ArrayList<Pig> pigs;
    protected ArrayList<Block> blocks;
    protected CataPult cataPult;
    protected Sprite LvlSprite;
    protected Stage stage;
    protected BitmapFont whitefont;
    protected BitmapFont blackfont;
    protected Table table;
    protected boolean paused;
    protected Window pauseWin;
    protected Label scorelabel;
    protected Label scoreval;
    protected Label HSlabel;
    protected Label HSval;
    public boolean flag;


    public Level(MyGame game, int levelnum){
        this.game = game;
        this.lvlnum = levelnum;
        this.score = 0;
        this.stars = 0;
        this.blackfont = new BitmapFont(Gdx.files.internal("Fonts\\black.fnt"));
        this.whitefont = new BitmapFont(Gdx.files.internal("Fonts\\white.fnt"));
        this.cam = new OrthographicCamera();
        this.viewport = new FitViewport(1920,820,cam);
        this.birds = new ArrayList<>();
        this.pigs = new ArrayList<>();
        this.blocks = new ArrayList<>();
        this.cataPult = new CataPult(180,275,this);
        this.LvlSprite = new Sprite(new Texture(Gdx.files.internal("Images\\angryBird_poachedEggbg.png")));
        this.stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        this.table = new Table();
        this.table.setFillParent(true);
        this.paused = false;

        this.flag = true;
        this.scorelabel = new Label("Score",new Label.LabelStyle(this.blackfont,Color.BLACK));
        scorelabel.setFontScale(2.0f);
        scorelabel.setPosition(1600,770);
        this.stage.addActor(this.scorelabel);

        this.scoreval = new Label(this.score+"",new Label.LabelStyle(this.whitefont,Color.WHITE));
        scoreval.setFontScale(2.0f);
        scoreval.setPosition(1790,770);
        this.stage.addActor(this.scoreval);

        this.HSlabel = new Label("H.S",new Label.LabelStyle(this.blackfont,Color.BLACK));
        HSlabel.setFontScale(1.2f);
        HSlabel.setPosition(1600,720);
        this.stage.addActor(this.HSlabel);

        this.HSval = new Label(this.game.highScores[levelnum]+"",new Label.LabelStyle(this.whitefont,Color.BLACK));
        HSval.setFontScale(1.2f);
        HSval.setPosition(1700,720);
        this.stage.addActor(this.HSval);

        world = new World(new Vector2(0,-7f),true);
        world.setContactListener(new GameCollisionHandler(this.game, this.lvlnum));
        debugRenderer = new Box2DDebugRenderer();
        debugRenderer.SHAPE_STATIC.set(1,0,0,1);

        createPauseMenu();

        createGround();

        TextButton.TextButtonStyle pauseButtonStyle = new TextButton.TextButtonStyle();
        pauseButtonStyle.font = whitefont;
        pauseButtonStyle.fontColor = Color.WHITE;
        pauseButtonStyle.up = new TextureRegionDrawable(new Texture(Gdx.files.internal("Images\\PauseButton.png")));

        TextButton pauseButton = new TextButton("", pauseButtonStyle);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Level.this.game.bgMusic.pause();
                showPauseMenu();
            }
        });

        TextButton.TextButtonStyle replayButtonStyle = new TextButton.TextButtonStyle();
        replayButtonStyle.font = whitefont;
        replayButtonStyle.fontColor = Color.WHITE;
        replayButtonStyle.up = new TextureRegionDrawable(new Texture(Gdx.files.internal("Images\\replayButton.png")));

        TextButton replayButton = new TextButton("", replayButtonStyle);
        replayButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Level.this.game.setScreen(new LoadingScreen(Level.this.game,Level.this.lvlnum));
            }
        });

        this.table.top().left().padTop(30).padLeft(30);
        this.table.add(pauseButton).padRight(10);
        this.table.add(replayButton).padRight(30);

        this.stage.addActor(this.table);
        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float screenX, float screenY, int pointer, int button) {
                return cataPult.handleTouchDown(screenX, screenY);
            }

            @Override
            public void touchDragged(InputEvent event, float screenX, float screenY, int pointer) {
                cataPult.handleTouchDragged(screenX, screenY);
            }

            @Override
            public void touchUp(InputEvent event, float screenX, float screenY, int pointer, int button) {
                cataPult.handleTouchUp(screenX, screenY);
            }

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.S) {
                    for(Bird bird: birds){
                        if(bird.isshot){
                            bird.getBody().applyLinearImpulse(new Vector2(0, -1000000), bird.getBody().getWorldCenter(),true);
                        }
                    }
                    return true;
                }
                if(keycode == Input.Keys.D){
                    for(Bird bird: birds){
                        if(bird.isshot){
                            bird.getBody().setLinearVelocity(new Vector2(100000,0));
                        }
                    }
                    return true;
                }
                if(keycode == Input.Keys.A){
                    for(Bird bird: birds){
                        if(bird.isshot){
                            bird.getBody().applyLinearImpulse(new Vector2(-100000, 0),bird.getBody().getWorldCenter(),true);
                        }
                    }
                    return true;
                }
                return false;
            }

        });

    }

    public Window getPauseWin() {
        return pauseWin;
    }

    public World getWorld() {
        return world;
    }

    public ArrayList<Bird> getBirds() {
        return birds;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public ArrayList<Pig> getPigs() {
        return pigs;
    }

    public CataPult getCataPult() {
        return cataPult;
    }

    public Stage getStage() {
        return stage;
    }

    protected void createPauseMenu() {
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = whitefont;
        windowStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Images\\WinBG.png"))));

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = whitefont;
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Images\\MenuButtonTex.png"))));

        pauseWin = new Window("Paused", windowStyle);
        pauseWin.setSize(300, 400);
        pauseWin.setPosition(820 , 310 );
        pauseWin.setMovable(false);

        TextButton resumeButton = new TextButton("Resume", textButtonStyle);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Level.this.game.bgMusic.play();
                hidePauseMenu();
            }
        });

        TextButton exitButton = new TextButton("Quit", textButtonStyle);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                game.levels[Level.this.lvlnum] = null;
                game.setScreen(new DefeatScreen(game,Level.this.lvlnum));
            }
        });

        TextButton saveGameButton = new TextButton("Save and Exit", textButtonStyle);
        saveGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Level.this.game.bgMusic.play();
                Level.this.saveLevelState();
                hidePauseMenu();
                game.setScreen(new LevelScreen(game));
            }
        });

        pauseWin.add(resumeButton).pad(10).row();
        pauseWin.add(saveGameButton).pad(10).row();
        pauseWin.add(exitButton).pad(10).row();

        pauseWin.setVisible(false);

    }

    protected void showPauseMenu() {
        paused = true;
        pauseWin.setVisible(true);
    }

    protected void hidePauseMenu() {
        paused = false;
        pauseWin.setVisible(false);
    }

    public Viewport getViewport() {
        return viewport;
    }

    @Override
    public void show() {

    }

    protected void removeDeadObjects() {

        Iterator<Block> blockIterator = blocks.iterator();
        while (blockIterator.hasNext()) {
            Block block = blockIterator.next();
            if (block.isDestroyed()) {
                world.destroyBody(block.getBody());
                block.remove();
                blockIterator.remove();
            }
        }

        Iterator<Pig> pigIterator = pigs.iterator();
        while (pigIterator.hasNext()) {
            Pig pig = pigIterator.next();
            if (pig.isDestroyed() || pig.getBody().getPosition().x > 1920 || pig.getBody().getPosition().x < 250) {
                world.destroyBody(pig.getBody());
                pig.remove();
                pigIterator.remove();
            }
        }

        Iterator<Bird> birdIterator = birds.iterator();
        while (birdIterator.hasNext()) {
            Bird bird = birdIterator.next();
            if (bird.isDestroyed() || bird.getBody().getPosition().x > 1920 || bird.getBody().getPosition().x < 0) {
                world.destroyBody(bird.getBody());
                bird.remove();
                birdIterator.remove();
                if(cataPult.getBird() == null){
                    Bird birdToLoad = getBirdtoLoad();
                    if( birdToLoad != null){
                        cataPult.loadBird(birdToLoad);
                    }
                }
            }
        }
    }

    protected Bird getBirdtoLoad(){
        float xcoor = 0;
        Bird birdtoload = null;
        for(Bird bird: birds){
            if(!bird.isshot) {
                if (bird.getBody().getPosition().x > xcoor) {
                    xcoor = bird.getBody().getPosition().x;
                    birdtoload = bird;
                }
            }
        }
        return birdtoload;
    }

    private void createGround() {

        BodyDef groundBodyDef = new BodyDef();

        groundBodyDef.position.set(1100, 95);
        groundBodyDef.type = BodyDef.BodyType.StaticBody;

        Body groundBody = world.createBody(groundBodyDef);

        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox((1920f) , (1080 / 6.0f));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundShape;
        fixtureDef.friction = 0.8f;
        fixtureDef.restitution = 0.0f;

        groundBody.createFixture(fixtureDef);
        groundShape.dispose();
    }

    public void saveLevelState() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        LevelState levelState = new LevelState();

        levelState.lvlnum = this.lvlnum;
        levelState.score = this.score;
        levelState.stars = this.stars;
//        levelState.flag = this.flag;

        levelState.birds = new ArrayList<>();
        for (Bird bird : birds) {
            Vector2 position = bird.getBody().getPosition();
            Vector2 velocity = bird.getBody().getLinearVelocity();
            String type = bird.getClass().getSimpleName();
            levelState.birds.add(new BirdState(position.x, position.y, bird.getBody().getAngle(), velocity, bird.getBody().getAngularVelocity(), bird.getHealth(), bird.getDamage(), bird.isshot, type, bird.isLoaded));
        }

        levelState.pigs = new ArrayList<>();
        for (Pig pig : pigs) {
            Vector2 position = pig.getBody().getPosition();
            Vector2 velocity = pig.getBody().getLinearVelocity();
            String type = pig.getClass().getSimpleName();
            levelState.pigs.add(new PigState(position.x, position.y, pig.getBody().getAngle(), velocity,pig.getHealth(), type, pig.getBody().getAngularVelocity()));
        }

        levelState.blocks = new ArrayList<>();
        for (Block block : blocks) {
            Vector2 position = block.getBody().getPosition();
            Vector2 velocity = block.getBody().getLinearVelocity();
            String type = block.getClass().getSimpleName();
            levelState.blocks.add(new BlockState(position.x, position.y, block.getBody().getAngle()* MathUtils.radiansToDegrees,velocity ,block.getHealth(), type, block.getBody().getAngularVelocity()));
        }

        try (FileWriter writer = new FileWriter("level" + lvlnum + ".json")) {
            gson.toJson(levelState, writer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

        viewport.update(width, height, true);
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

    }
}
