package com.de1.AngryBirds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.de1.AngryBirds.MyGame;
import org.w3c.dom.Text;

public class HomeScreen implements Screen {

    private MyGame game;
    private Sprite homeSprite;
    private Texture homeTexture;
    private Sprite gameLogoSprite;
    private Texture piggies1;
    private Sprite pigs1Sprite;
    private Texture grass;
    private Sprite grassSprite;
    private Texture piggies2;
    private Sprite pigs2Sprite;
    private Texture redFlies;
    private Sprite redFliesSprite;
    private Texture birdsFly;
    private Sprite birdsFlySprite;

    private Texture lantern1;
    private Sprite lantern1Sprite;

    private Texture lantern2;
    private Sprite lantern2Sprite;

    private Texture lantern3;
    private Sprite lantern3Sprite;

    private Texture lantern4;
    private Sprite lantern4Sprite;

    private Texture spookybomb;
    private Sprite spookybombSprite;

    private Texture fatBird;
    private Sprite fatBirdSprite;

    private Texture tntBlock;
    private Sprite tntBlockSprite;

    private Texture spookyPig;
    private Sprite spookyPigSprite;

    private Texture skull;
    private Sprite skullSprite;

    private Texture egg1;
    private Sprite egg1Sprite;

    private Stage stage;
    private Table table;
    private BitmapFont whiteFont;
    private Label heading;
    private ImageButton playButton, exitButton;

    private OrthographicCamera camera;
    private Viewport viewport;

    public HomeScreen(MyGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        homeTexture = new Texture("Images\\angryBird_poachedEggbg.png");
        homeSprite = new Sprite(this.homeTexture);

        gameLogoSprite = new Sprite(new Texture("Images\\AngryBirdsTEXT.png"));
        gameLogoSprite.setCenter(960, 900);

        piggies1 = new Texture(Gdx.files.internal("Images\\Decors\\Piggies1.png"));
        pigs1Sprite = new Sprite(piggies1);
        pigs1Sprite.setCenter(1800,370);

        grass = new Texture("Images\\Decors\\GreenGrass.png");
        grassSprite = new Sprite(grass);
        grassSprite.setCenter(1710,300);

        piggies2 = new Texture("Images\\Decors\\Piggies2.png");
        pigs2Sprite = new Sprite(piggies2);
        pigs2Sprite.setCenter(1500,370);

        redFlies = new Texture(Gdx.files.internal("Images\\Decors\\RedFlies.png"));
        redFliesSprite = new Sprite(redFlies);
        redFliesSprite.setCenter(150,340);

        birdsFly = new Texture("Images\\Decors\\BirdsFly.png");
        birdsFlySprite = new Sprite(birdsFly);
        birdsFlySprite.setCenter(110,650);
        birdsFlySprite.rotate(-30);

        lantern1 = new Texture("Images\\Decors\\redLantern.png");
        lantern1Sprite = new Sprite(lantern1);
        lantern1Sprite.setCenter(1500,800);

        lantern2 = new Texture("Images\\Decors\\blueLantern.png");
        lantern2Sprite = new Sprite(lantern2);
        lantern2Sprite.setCenter(320,850);

        lantern3 = new Texture("Images\\Decors\\purpleLantern.png");
        lantern3Sprite = new Sprite(lantern3);
        lantern3Sprite.setCenter(1350,900);

        lantern4 = new Texture("Images\\Decors\\pinkLantern.png");
        lantern4Sprite = new Sprite(lantern4);
        lantern4Sprite.setCenter(600,1000);

        spookybomb = new Texture("Images\\Decors\\Spookybomb.png");
        spookybombSprite = new Sprite(spookybomb);
        spookybombSprite.setCenter(900,670);
        spookybombSprite.rotate(30);
        spookybombSprite.setSize(200,200);

        fatBird = new Texture("Images\\Decors\\FatBirdOr.png");
        fatBirdSprite = new Sprite(fatBird);
        fatBirdSprite.setCenter(600,500);

        tntBlock = new Texture("Images\\Decors\\TNTBlock.png");
        tntBlockSprite = new Sprite(tntBlock);
        tntBlockSprite.setCenter(1300,300);
        tntBlockSprite.setSize(100,100);

        spookyPig = new Texture("Images\\Decors\\SpookyPig.png");
        spookyPigSprite = new Sprite(spookyPig);
        spookyPigSprite.setCenter(1300,500);

        skull = new Texture("Images\\Decors\\skull.png");
        skullSprite = new Sprite(skull);
        skullSprite.setSize(200,200);
        skullSprite.setCenter(1800,700);

        egg1 = new Texture("Images\\Decors\\GoldEgg.png");
        egg1Sprite = new Sprite(egg1);
        egg1Sprite.setCenter(900,150);
        egg1Sprite.scale(1.2f);
        egg1Sprite.rotate(20);

        whiteFont = new BitmapFont(Gdx.files.internal("Fonts\\white.fnt"), false);

        table = new Table();
        table.top();
        table.setFillParent(true);

        heading = new Label("Let's Rumble!", new Label.LabelStyle(whiteFont, Color.WHITE));
        heading.setFontScale(3);
        heading.setAlignment(Align.center);
        Texture playbuttonTexture = new Texture("Images\\PlayButton2.png");
        Texture exitbuttonTexture = new Texture("Images\\ExitButton.png");

        ImageButton.ImageButtonStyle playButtonStyle = new ImageButton.ImageButtonStyle();
        playButtonStyle.up = new TextureRegionDrawable(playbuttonTexture);
        playButtonStyle.down = new TextureRegionDrawable(playbuttonTexture);
        playButton = new ImageButton(playButtonStyle);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                HomeScreen.this.game.setScreen(new LevelScreen(HomeScreen.this.game));
            }
        });

        ImageButton.ImageButtonStyle exitButtonStyle = new ImageButton.ImageButtonStyle();
        exitButtonStyle.up = new TextureRegionDrawable(exitbuttonTexture);
        exitButtonStyle.down = new TextureRegionDrawable(exitbuttonTexture);
        exitButton = new ImageButton(exitButtonStyle);

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        table.add(exitButton).width(100).height(100).expandX().align(Align.topRight).padTop(20).padRight(20).spaceBottom(300).row();  // Add Exit button to top right
        table.add(heading).spaceBottom(150).row();
        table.add(playButton).width(300).height(150).spaceBottom(50).row();

        stage.addActor(table);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        homeSprite.draw(game.batch);
        gameLogoSprite.draw(game.batch);
        pigs1Sprite.draw(game.batch);
        grassSprite.draw(game.batch);
        pigs2Sprite.draw(game.batch);
        redFliesSprite.draw(game.batch);
        birdsFlySprite.draw(game.batch);
        lantern1Sprite.draw(game.batch);
        lantern2Sprite.draw(game.batch);
        lantern3Sprite.draw(game.batch);
        lantern4Sprite.draw(game.batch);
        spookybombSprite.draw(game.batch);
        fatBirdSprite.draw(game.batch);
        tntBlockSprite.draw(game.batch);
        spookyPigSprite.draw(game.batch);
        skullSprite.draw(game.batch);
        egg1Sprite.draw(game.batch);

        game.batch.end();

        stage.act(delta);
        stage.draw();
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
        homeTexture.dispose();
        whiteFont.dispose();
        stage.dispose();
        piggies1.dispose();
        grass.dispose();
        piggies2.dispose();
        redFlies.dispose();
        birdsFly.dispose();
        lantern1.dispose();
        lantern2.dispose();
        lantern3.dispose();
        lantern4.dispose();
        spookybomb.dispose();
        fatBird.dispose();
        tntBlock.dispose();
        spookyPig.dispose();
        skull.dispose();
        egg1.dispose();
    }
}
