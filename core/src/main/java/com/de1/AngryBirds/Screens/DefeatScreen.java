package com.de1.AngryBirds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.de1.AngryBirds.Levels.Level;
import com.de1.AngryBirds.Levels.Level1;
import com.de1.AngryBirds.MyGame;

public class DefeatScreen implements Screen {

    private MyGame game;
    private Viewport viewport;
    private OrthographicCamera cam;
    private Sprite defeatScreenSprite;
    private Stage stage;
    private BitmapFont font;
    private Texture pigCelebTex;
    private int Levelnum;

    public DefeatScreen(MyGame game, int levelnum) {

        this.game = game;
        this.Levelnum = levelnum;
        cam = new OrthographicCamera();
        viewport = new FitViewport(1920, 820, cam);
        defeatScreenSprite = new Sprite(new Texture(Gdx.files.internal("Images\\angryBird_poachedEggbg.png")));

        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont(Gdx.files.internal("Fonts\\white.fnt"));
        pigCelebTex = new Texture(Gdx.files.internal("Images\\PigsCelebrating.png"));
        Image pigCelebrating = new Image(pigCelebTex);

        Table table = new Table();
        table.setFillParent(false);
        table.setSize(500,650);
        table.setPosition(680, 80);

        TextureRegionDrawable tableBg = new TextureRegionDrawable(new Texture(Gdx.files.internal("Images\\DefeatScreenbg.png")));
        table.setBackground(tableBg);

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.RED);
        Label youLostLabel = new Label("You Lost...", labelStyle);
        youLostLabel.setFontScale(3f);

        TextButton.TextButtonStyle replaybuttonStyle = new TextButton.TextButtonStyle();
        replaybuttonStyle.font = font;
        replaybuttonStyle.fontColor = Color.WHITE;
        replaybuttonStyle.up = new TextureRegionDrawable(new Texture(Gdx.files.internal("Images\\replayButton.png")));

        TextButton replayButton = new TextButton("", replaybuttonStyle);
        replayButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                DefeatScreen.this.game.bgMusic.play();
                DefeatScreen.this.game.setScreen(new LoadingScreen(DefeatScreen.this.game,DefeatScreen.this.Levelnum));
            }
        });

        TextButton.TextButtonStyle levelmenubuttonStyle = new TextButton.TextButtonStyle();
        levelmenubuttonStyle.font = font;
        levelmenubuttonStyle.fontColor = Color.WHITE;
        levelmenubuttonStyle.up = new TextureRegionDrawable(new Texture(Gdx.files.internal("Images\\LevelMenuButton.png")));

        TextButton levelMenuButton = new TextButton("", levelmenubuttonStyle);
        levelMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                DefeatScreen.this.game.bgMusic.play();
                game.setScreen(new LevelScreen(game));
            }
        });

        TextButton.TextButtonStyle homebuttonStyle = new TextButton.TextButtonStyle();
        homebuttonStyle.font = font;
        homebuttonStyle.fontColor = Color.WHITE;
        homebuttonStyle.up = new TextureRegionDrawable(new Texture(Gdx.files.internal("Images\\HomeButton.png")));

        TextButton homeButton = new TextButton("", homebuttonStyle);
        homeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                DefeatScreen.this.game.bgMusic.play();
                game.setScreen(new HomeScreen(game));
            }
        });

        table.add(youLostLabel).expandX().pad(20f);
        table.row();
        table.add(pigCelebrating).pad(20f).row();
        Table buttonTable = new Table();
        buttonTable.add(replayButton).pad(20f);
        buttonTable.add(levelMenuButton).pad(20f);
        buttonTable.add(homeButton).pad(20f);
        table.add(buttonTable).pad(20f).row();

        stage.addActor(table);
        this.game.levels[Levelnum] = null;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();
        defeatScreenSprite.draw(game.batch);
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
        defeatScreenSprite.getTexture().dispose();
        stage.dispose();
        font.dispose();
        pigCelebTex.dispose();
    }
}
