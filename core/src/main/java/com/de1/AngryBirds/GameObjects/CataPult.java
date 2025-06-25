package com.de1.AngryBirds.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.de1.AngryBirds.GameObjects.Birds.Bird;
import com.de1.AngryBirds.Levels.Level;

import java.io.Serializable;

public class CataPult extends Actor implements Serializable {

    protected Texture texture;
    protected float xPos, yPos;
    protected Bird bird;
    protected Vector2 launchPoint;
    protected Vector2 dragPoint;
    protected boolean isDragging;
    protected ShapeRenderer sling;
    protected float maxDragDistance;
    protected Level level;
    protected ShapeRenderer trajectoryRenderer;
    protected Vector2[] trajectoryPoints;
    protected int trajectoryPointCount;


    public CataPult(float xPos, float yPos, Level level) {
        this.xPos = xPos;
        this.yPos = yPos;
        setPosition(xPos, yPos);

        this.texture = new Texture(Gdx.files.internal("Images\\Catapult1.png"));
        setSize(texture.getWidth(), texture.getHeight());
        this.setScale(0.3f);

        this.launchPoint = new Vector2(210, 395);
        this.dragPoint = new Vector2(launchPoint);
        this.isDragging = false;
        this.maxDragDistance = 50;
        this.sling = new ShapeRenderer();
        this.level = level;
        this.trajectoryPointCount = 100;
        this.trajectoryRenderer = new ShapeRenderer();
        this.trajectoryPoints = new Vector2[trajectoryPointCount];
        for (int i = 0; i < trajectoryPointCount; i++) {
            trajectoryPoints[i] = new Vector2();
        }
    }

    private void calculateTrajectory() {
        if (bird == null || dragPoint == null || !isDragging) {
            return;
        }

        Vector2 velocity = new Vector2(launchPoint.x - dragPoint.x, launchPoint.y - dragPoint.y);
        velocity.scl(5f);

        float g = -30.0f;
        float timeStep = 0.1f;

        for (int i = 0; i < trajectoryPointCount; i++) {
            float t = i * timeStep;
            float x = bird.getBody().getPosition().x + velocity.x * t;
            float y = bird.getBody().getPosition().y + velocity.y * t + 0.5f * g * t * t;
            trajectoryPoints[i].set(x, y);
        }
    }

    public boolean handleTouchDown(float screenX, float screenY) {
        if (bird != null) {

            Vector2 touchPoint = new Vector2(screenX, screenY);
            Vector2 birdPosition = new Vector2(bird.getBody().getPosition());
            float birdRadius = bird.getRadius();

            if (touchPoint.dst(birdPosition) <= birdRadius) {
                isDragging = true;
                return true;
            }
        }
        return false;
    }
    public void handleTouchDragged(float screenX, float screenY) {
        if (isDragging && bird != null) {

            dragPoint.set(new Vector2(screenX, screenY));
            if (launchPoint.dst(dragPoint) > maxDragDistance) {
                dragPoint.set(launchPoint.cpy().add(dragPoint.sub(launchPoint).nor().scl(maxDragDistance)));
            }
            bird.getBody().setTransform(dragPoint.x, dragPoint.y, 0);
        }
    }

    public void handleTouchUp(float screenX, float screenY) {
        if (isDragging && bird != null) {

            isDragging = false;
            Vector2 launchVelocity = new Vector2(launchPoint.x - dragPoint.x, launchPoint.y - dragPoint.y);
            if(launchVelocity.x > 10 || launchVelocity.y > 10){

                launchVelocity.scl(1000000);
                bird.launch(launchVelocity);
                bird.isshot = true;
                bird.isLoaded = false;
                bird = null;
            }
            else{
                bird.getBody().setTransform(launchPoint.x, launchPoint.y, 0);
            }
        }
    }

    public void loadBird(Bird bird) {
        this.bird = bird;
        bird.isLoaded = true;
        bird.getBody().setTransform(launchPoint.x, launchPoint.y, 0);
        bird.getBody().setAwake(false);
    }

    public void unloadBird(){

        this.bird.getBody().setAwake(true);
        this.bird.getBody().setTransform(this.bird.getxPos(), this.bird.getyPos(), 0);
        this.bird.isLoaded = false;
        this.bird = null;
    }

    public Bird getBird(){
        return this.bird;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(
            this.texture,
            getX(), getY(),
            getOriginX(), getOriginY(),
            getWidth(), getHeight(),
            getScaleX(), getScaleY(),
            getRotation(),
            0, 0, (int) getWidth(), (int) getHeight(),
            false, false
        );

        if (isDragging && bird != null) {
            batch.end();
            sling.setProjectionMatrix(batch.getProjectionMatrix());
            sling.begin(ShapeRenderer.ShapeType.Line);

            sling.setColor(Color.BLACK);
            sling.line(launchPoint.x, launchPoint.y, dragPoint.x, dragPoint.y);
            sling.line(launchPoint.x + 20, launchPoint.y , dragPoint.x, dragPoint.y);

            sling.end();

            trajectoryRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            trajectoryRenderer.begin(ShapeRenderer.ShapeType.Filled);
            trajectoryRenderer.setColor(Color.RED);

            calculateTrajectory();
            for (Vector2 point : trajectoryPoints) {
                trajectoryRenderer.circle(point.x, point.y, 2);
            }

            trajectoryRenderer.end();

            batch.begin();
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (isDragging && bird != null) {
            float distance = launchPoint.dst(dragPoint);
            if (distance > maxDragDistance) {
                dragPoint.set(launchPoint.cpy().add(dragPoint.sub(launchPoint).nor().scl(maxDragDistance)));
                bird.getBody().setTransform(dragPoint.x, dragPoint.y, 0);
            }
        }
        calculateTrajectory();
    }

    public void dispose() {
        this.texture.dispose();
        this.sling.dispose();
        this.trajectoryRenderer.dispose();
    }
}
