package com.ameer.sort;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;
import java.util.ListIterator;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by am44_000 on 2017-10-20.
 */
/* TODO
*   Implement Multitouch
* */
public class ScrGame implements Screen, InputProcessor {
    static final int BLUE = 1, RED = 2;
    static final Color clrRED = new Color(Color.valueOf("ee2c2cff")), clrBLUE = new Color(Color.valueOf("63b8ffff"));

    private GamSort game;
    public OrthographicCamera camera;
    public StretchViewport viewport;

    ArrayList<Ball> arBalls, arBallsDragged;
    ListIterator<Ball> iterator;
    Ball ballDragged;
    ArrayList<Integer> arRemove = new ArrayList<Integer>();
    Vector3 vTouch, vRect;
    boolean isTouched;
    Timer timer;
    boolean isStarted;
    ArrayList<Box> arBoxes = new ArrayList<Box>();
    Box boxRoof, boxFloor;
    SpriteBatch batch;
    public ScrGame(GamSort _game) {
        game = _game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        viewport = new StretchViewport(480, 800, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        resize(480, 800);
        arBalls = new ArrayList<Ball>();
        arBallsDragged = new ArrayList<Ball>();
        vTouch = new Vector3();
        vRect = new Vector3(0,0,0);


        timer = new Timer();
        isStarted = false;
        Gdx.input.setInputProcessor(this);
        boxFloor = new Box(0, 0,BLUE, camera);
        boxRoof = new Box(0, viewport.getScreenHeight() - 250, RED, camera);
        arBoxes.add(boxFloor);
        arBoxes.add(boxRoof);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        spawnBalls(2); //DO NOT SET TO 0!!
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(253 / 255f, 247 / 255f, 239 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        boxFloor.render();
        boxRoof.render();
        iterator = arBalls.listIterator();
        while(iterator.hasNext()){
            Ball ball = iterator.next();
            if(ball.isAlive) {
                ballDraw(ball);
            }
            else{
                if(ball.bCauseGameOver){
                    gameOver();
                }
                else{
                    iterator.remove();
                }
            }
        }

//        if(ballDragged != null) {
//            ballDragged.render();
//            if(!ballDragged.isAlive){
//                ballDragged = null;
//            }
//        }
//        iterator = arBallsDragged.listIterator();
//        while(iterator.hasNext()){
//            Ball ballDragged = iterator.next();
//            ballDragged.render();
//            if(ballDragged.isAlive) {
//                ballDragged.render();
//            }
//            else{
//                iterator.remove();
//            }
//        }
        for (Ball ballDragged: arBallsDragged){
            ballDragged.render();
        }
        arBallsDragged.clear();

        //disposeBall();

    }
    private void ballDraw(Ball ball){
        ball.render();

        ballInBox(ball);
    }

    void ballInBox(Ball ball){
        for(Box box: arBoxes){
            if(box.getBoundingRectangle().contains(ball.getCircle())){
                if(ball.COLOUR == box.COLOUR){
                    if(!ball.isMoveable)
                    ball.isAlive = false;
                }
                else {
                    gameOver();
                }
            }
        }
    }
    public void spawnBalls(int nSeconds){
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                Ball b = new Ball(randomSpawnLocation().x,randomSpawnLocation().y, random(1,2), camera);
                arBalls.add(b);
            }
        },1,nSeconds);
    }
    public Vector2 randomSpawnLocation(){
        Vector2 vPos = new Vector2();
        vPos.x = random(Ball.RADIUS, viewport.getWorldWidth()- Ball.RADIUS);
        vPos.y = random(boxFloor.getHeight()+ Ball.RADIUS, boxRoof.getY()- Ball.RADIUS);
        return vPos;
    }
    public void disposeBall(){
        if(!arBalls.isEmpty()) {
//            for (Ball b : arBalls) {
//                b.render();
//            }
            for (Ball b : arBalls) {

                if (!b.isAlive) {
                    arRemove.add(arBalls.indexOf(b));
                }
            }
        }
        for(int i = 0; i< arRemove.size(); i++){
            arBalls.get(arRemove.get(i)).dispose();
           // arBalls.remove(arBalls.get(arRemove.get(i)));
        }
        arRemove.clear();
    }
    private Ball getMovingBall(int nPointer){
        for(Ball b: arBalls){
            if(b.isMoveable && b.nPointer == nPointer){
                return b;
            }
        }
        return null;
    }

    //implemented methods
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isTouched = false;
        for (Ball b: arBalls){
            if(pointer == b.nPointer) {
                b.isMoveable = false;
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        for(Ball ball: arBalls) {
            if (getMovingBall(pointer) == ball || getMovingBall(pointer) == null && ball.isAlive) {
                ball.onTouch(pointer);
//                ballDragged = ball;
                arBallsDragged.add(ball);

            }
        }return false;
    }
    public  void gameOver(){
        game.setScreen(new ScrGameover(game));

    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        return true;
    }
    @Override
    public void show() {

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
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
