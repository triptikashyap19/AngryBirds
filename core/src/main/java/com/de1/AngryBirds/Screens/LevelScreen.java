package com.de1.AngryBirds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.de1.AngryBirds.Levels.Level;
import com.de1.AngryBirds.Levels.Level1;
import com.de1.AngryBirds.MyGame;

import java.awt.*;

public class LevelScreen implements Screen {

    private MyGame game;
    private Sprite levelscreenSprite;
    private Sprite selectLvLSprite;
    private Stage stage;
    private Viewport viewport;
    private Table table;
    private BitmapFont font;
    private Texture levelTexture;
    private OrthographicCamera camera;

    public LevelScreen(MyGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        levelscreenSprite = new Sprite(new Texture("Images\\angryBird_poachedEggbg.png"));
        selectLvLSprite = new Sprite(new Texture("Images\\SelectLevelTXT.png"));
        selectLvLSprite.setCenter(960, 900);

        font = new BitmapFont(Gdx.files.internal("Fonts\\white.fnt"), false);
        font.getData().setScale(2.3f);

        table = new Table();
        table.setFillParent(true);

        levelTexture = new Texture(Gdx.files.internal("Images\\UnlockedLvl(1).png"));
        for (int levelNumber = 1; levelNumber <= this.game.LevelsCleared; levelNumber++) {

            TextButtonStyle buttonStyle = new TextButtonStyle();
            buttonStyle.font = font;
            buttonStyle.fontColor = Color.WHITE;
            buttonStyle.up = new TextureRegionDrawable(levelTexture);

            TextButton levelButton = new TextButton(levelNumber + "", buttonStyle);

            final int lvl = levelNumber;

            levelButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if (hasSavedState(lvl)) {
                        createRestoreMenu(lvl);
//                        LevelScreen.this.game.setScreen(LevelScreen.this.game.levels[lvl]);
                    }
                    else {
                        LevelScreen.this.game.setScreen(new LoadingScreen(LevelScreen.this.game,lvl));
                    }

                }
            });

            table.add(levelButton).size(140, 140).pad(30).expandX();
            if (levelNumber % 5 == 0) {
                table.row();
            }
        }

        levelTexture = new Texture(Gdx.files.internal("Images\\UnlockedLvl(1).png"));
        for (int levelNumber = this.game.LevelsCleared+1; levelNumber <= this.game.TotalLvLs; levelNumber++) {

            TextButtonStyle buttonStyle = new TextButtonStyle();
            buttonStyle.font = font;
            buttonStyle.fontColor = Color.WHITE;
            buttonStyle.up = new TextureRegionDrawable(new Texture(Gdx.files.internal("Images\\LockedLvl.png")));

            TextButton levelButton = new TextButton("", buttonStyle);

            table.add(levelButton).size(140, 140).pad(30).expandX();
            if (levelNumber % 5 == 0) {
                table.row();
            }
        }

        TextButtonStyle backButtonStyle = new TextButtonStyle();
        backButtonStyle.font = font;
        backButtonStyle.fontColor = Color.WHITE;
        backButtonStyle.up = new TextureRegionDrawable(new Texture(Gdx.files.internal("Images\\BackButton.png")));

        TextButton backButton = new TextButton("", backButtonStyle);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new HomeScreen(LevelScreen.this.game));
            }
        });

        table.row();
        table.add(backButton).expandX().align(Align.left).padBottom(30).padLeft(30);

        stage.addActor(table);
    }

    private boolean hasSavedState(int levelNumber) {
        String filePath = "level" + levelNumber + ".json";
        if(Gdx.files.local(filePath).exists()) {
            return true;
        }
        return false;
    }

    private void createRestoreMenu(int levelNumber) {
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        BitmapFont font1 = this.font;
        font1.getData().setScale(0.9f);
        windowStyle.titleFont = font;
        windowStyle.background = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Images\\WinBG.png"))));

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Images\\MenuButtonTex.png"))));

        Window restoreWin = new Window("Restore Menu", windowStyle);
        restoreWin.setSize(500, 500);
        restoreWin.setPosition(650, 300);
        restoreWin.setMovable(false);

        TextButton newGameButton = new TextButton("New Game", textButtonStyle);
        newGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LevelScreen.this.game.setScreen(new LoadingScreen(LevelScreen.this.game, levelNumber));
                restoreWin.setVisible(false);
            }
        });

        TextButton restoreGameButton = new TextButton("Restore Saved Game", textButtonStyle);
        restoreGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LevelScreen.this.game.loadLevel(levelNumber);
                LevelScreen.this.game.setScreen(LevelScreen.this.game.levels[levelNumber]);
                restoreWin.setVisible(false);
            }
        });

        restoreWin.add(newGameButton).pad(10).row();
        restoreWin.add(restoreGameButton).pad(10).row();

        stage.addActor(restoreWin);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        levelscreenSprite.draw(game.batch);
        selectLvLSprite.draw(game.batch);
        game.batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void show() {

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
        stage.dispose();
        font.dispose();
        levelTexture.dispose();
    }
}
