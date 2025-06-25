package com.de1.AngryBirds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.de1.AngryBirds.MyGame;

public class SplashScreen implements Screen {

    private MyGame game;
    private Sprite splashSprite;
    private Texture splashTexture;

    private float splashTime;
    private float timer;

    private OrthographicCamera camera;
    private Viewport viewport;

    public SplashScreen(MyGame game) {
        this.game = game;
        this.splashTexture = new Texture("Images\\Splashscreen.png");
        this.splashSprite = new Sprite(splashTexture);
        this.splashSprite.setSize(1920, 1080);

        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);

        this.splashTime = 1.5f;
        this.timer = 0f;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        timer += delta;

        if (timer >= splashTime) {

            game.setScreen(new HomeScreen(this.game));
        }

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        this.game.batch.begin();
        splashSprite.draw(this.game.batch);
        this.game.batch.end();
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
        splashTexture.dispose();
    }
}
