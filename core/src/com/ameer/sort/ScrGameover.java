package com.ameer.sort;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by am44_000 on 2018-01-09.
 */

public class ScrGameover implements Screen, InputProcessor {
    GamSort game;
    SpriteBatch batch = new SpriteBatch();
    Button btnRestart;
    Sprite sprTest;
    public OrthographicCamera camera;
    public StretchViewport viewport;
    Vector3 vTouch = new Vector3();
    public ScrGameover(GamSort _game){
        game = _game;
        Gdx.input.setInputProcessor(this);
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        viewport = new StretchViewport(480, 800, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        resize(480, 800);
        //setCamera(480, 800);
        batch.setProjectionMatrix(camera.combined);
        btnRestart = new Button("backarrow.png", camera.viewportWidth/2, camera.viewportHeight/2);

    }
    @Override
    public void show() {
        btnRestart = new Button("backarrow.png", camera.viewportWidth/2, camera.viewportHeight/2);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(253 / 255f, 247 / 255f, 239 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        btnRestart.draw(batch);
        batch.end();
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        camera.unproject(vTouch.set(screenX,screenY,0));
        if (btnRestart.isHit(vTouch.x, vTouch.y)) {
            game.setScreen(new ScrGame(game));
            System.out.println("hit");
        }
        System.out.println(vTouch.x + "  " +  vTouch.y);
        return false;
    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }



    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
