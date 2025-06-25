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
import com.de1.AngryBirds.MyGame;

public class VictoryScreen implements Screen {

    private MyGame game;
    private Viewport viewport;
    private OrthographicCamera cam;
    private Sprite victoryScreenSprite;
    private Stage stage;
    private BitmapFont font;
    private int Levelnum;


    public VictoryScreen(MyGame game, int Levelnum) {

        this.game = game;
        this.Levelnum = Levelnum;

        unlockLvl();

        cam = new OrthographicCamera();
        viewport = new FitViewport(1920, 820, cam);
        victoryScreenSprite = new Sprite(new Texture(Gdx.files.internal("Images\\angryBird_poachedEggbg.png")));

        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);

        font = new BitmapFont(Gdx.files.internal("Fonts\\white.fnt"));

        Table table = new Table();
        table.setFillParent(false);
        table.setSize(500, 650);
        table.setPosition(680, 80);

        TextureRegionDrawable tableBG = new TextureRegionDrawable(new Texture(Gdx.files.internal("Images\\DefeatScreenbg.png")));
        table.setBackground(tableBG);

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);
        Label victoryLabel = new Label("Level Cleared!", labelStyle);
        victoryLabel.setFontScale(1.5f);


        Image starimg1 = new Image(new Texture(Gdx.files.internal("Images\\1star.png")));
        Image starimg2 = new Image(new Texture(Gdx.files.internal("Images\\2star.png")));
        Image starimg3 = new Image(new Texture(Gdx.files.internal("Images\\3star.png")));

        Label scoreLabel = new Label("Score: "+game.levels[Levelnum].score, labelStyle);
        if(game.levels[Levelnum].score > game.highScores[Levelnum]){
            game.highScores[Levelnum] = game.levels[Levelnum].score;
            table.add(new Label("New HighScore!",labelStyle)).row();
        }
        Label highScoreLabel = new Label("Highest Score: "+game.highScores[Levelnum], labelStyle);

        TextButton.TextButtonStyle nextLevelButtonStyle = new TextButton.TextButtonStyle();
        nextLevelButtonStyle.font = font;
        nextLevelButtonStyle.fontColor = Color.WHITE;
        nextLevelButtonStyle.up = new TextureRegionDrawable(new Texture(Gdx.files.internal("Images\\NextLevelButton.png")));

        TextButton nextLevelButton = new TextButton("", nextLevelButtonStyle);
        nextLevelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                VictoryScreen.this.game.bgMusic.play();
                game.setScreen(new LoadingScreen(game,VictoryScreen.this.Levelnum+1));
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
                VictoryScreen.this.game.bgMusic.play();
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
                VictoryScreen.this.game.bgMusic.play();
                game.setScreen(new HomeScreen(game));
            }
        });

        table.add(victoryLabel).expandX().pad(30f).row();

        Table starTable = new Table();

        if(game.levels[Levelnum].stars >=3){
            starTable.add(starimg3).pad(7f).expandX().align(Align.center);
        }
        else if(game.levels[Levelnum].stars >=2){
            starTable.add(starimg2).pad(7f).expandX().align(Align.center);
        }
        else if(game.levels[Levelnum].stars >=1){
            starTable.add(starimg1).pad(7f).expandX().align(Align.center);
        }
        table.add(starTable).pad(20f).row();

        table.add(scoreLabel).pad(10f).row();
        table.add(highScoreLabel).pad(10f).row();

        Table buttonTable = new Table();
        buttonTable.add(homeButton).pad(20f);
        buttonTable.add(levelMenuButton).pad(20f);
        buttonTable.add(nextLevelButton).pad(20f);
        table.add(buttonTable).pad(20f).row();

        stage.addActor(table);

        game.levels[Levelnum] = null;
    }

    @Override
    public void show() {

    }

    private void unlockLvl(){
        if(this.game.LevelsCleared <= this.Levelnum) {
            this.game.LevelsCleared++;
        }
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();
        victoryScreenSprite.draw(game.batch);
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
        victoryScreenSprite.getTexture().dispose();
        stage.dispose();
        font.dispose();

    }
}
