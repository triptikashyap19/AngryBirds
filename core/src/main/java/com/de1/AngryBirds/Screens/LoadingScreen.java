package com.de1.AngryBirds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.de1.AngryBirds.Levels.Level1;
import com.de1.AngryBirds.Levels.Level2;
import com.de1.AngryBirds.Levels.Level3;
import com.de1.AngryBirds.MyGame;

public class LoadingScreen implements Screen {

    private MyGame game;
    private Texture loadTex;
    private Sprite loadingScreenSprite;
    private OrthographicCamera cam;
    private Viewport viewport;

    private int lvl;
    private ShapeRenderer loadBar;

    private float loadTime;
    private float timer;

    public LoadingScreen(MyGame game, int lvl) {
        this.game = game;
        this.game.bgMusic.pause();
        this.lvl = lvl;
        cam = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, cam);
        loadTex = new Texture("Images\\Splashscreen.png");
        loadingScreenSprite = new Sprite(loadTex);
        loadingScreenSprite.setSize(1920,1080);

        this.loadTime = 1.5f;
        this.timer = 0f;
        this.loadBar = new ShapeRenderer();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        timer += delta;

        float barlength = Math.min(timer/loadTime,1.0f);

        if (timer >= loadTime) {
            this.game.bgMusic.play();
            renderLvl(this.lvl);
        }

        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        this.game.batch.begin();
        loadingScreenSprite.draw(this.game.batch);
        this.game.batch.end();

        loadBar.setProjectionMatrix(cam.combined);
        loadBar.begin(ShapeRenderer.ShapeType.Filled);
        loadBar.setColor(0, 0f, 0.9f, 1);
        loadBar.rect(560,70,800*barlength,30);
        loadBar.end();

    }

    private void renderLvl(int lvl) {
        switch(lvl){
            case 1:
                Level1 newlvl1 = new Level1(this.game,lvl);
                this.game.levels[lvl] = newlvl1;
                this.game.setScreen(newlvl1);
                break;

            case 2:
                Level2 newLvl2 = new Level2(this.game,lvl);
                this.game.levels[lvl] = newLvl2;
                this.game.setScreen(newLvl2);
                break;

            case 3:
                Level3 newLvl3 = new Level3(this.game,lvl);
                this.game.levels[lvl] = newLvl3;
                this.game.setScreen(newLvl3);
                break;

            default:
                this.game.setScreen(new LevelScreen(this.game));

        }
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

        loadTex.dispose();
        loadBar.dispose();

    }
}
