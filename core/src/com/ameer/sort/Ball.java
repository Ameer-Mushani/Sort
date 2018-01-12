package com.ameer.sort;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by am44_000 on 2017-10-21.
 */
/*
TODO: Random ball spawn locations, change ball colour when picked up, score, ball movement?? maybe.
 */
public class Ball {
    static final int RADIUS = 28;
    final int COLOUR;
    Color clrShape, clrRed, clrRed2, clrBlue, clrBlue2;
    Color[] arColors;// first colour is the normal colour of the ball and the second is when the ball is picked up by user
    //Color[] arRedClr = {clrRed, clrRed2}, arBlueClr = {clrBlue, clrBlue2};
    ShapeRenderer shape;
    Vector2 vBall;
    Vector3 vTouch;
    float fVelX, fVelY;
    OrthographicCamera camera;
    Circle hitCircle;
    boolean isMoveable;
    Timer timer;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    BitmapFont font;
    SpriteBatch batch;
    int nTime, nPointer;
    boolean isOn, isAlive, bCauseGameOver;


    public Ball(float X, float Y, int nColour, OrthographicCamera _camera) {
        shape = new ShapeRenderer();
         COLOUR = nColour;
        if(COLOUR == ScrGame.RED){
            arColors = new Color[]{clrRed = new Color(ScrGame.clrRED), clrRed2  = new Color(Color.valueOf("8e1a1aff"))};
        }
        else if(COLOUR == ScrGame.BLUE){
            arColors = new Color[]{clrBlue = new Color(ScrGame.clrBLUE), clrBlue2 = new Color(Color.valueOf("315c7ff"))} ;
        }
        clrShape = arColors[0];

        vBall = new Vector2(X, Y);
        vTouch = new Vector3();
        fVelX = 0;
        fVelY = 0;
        camera = _camera;
        shape.setProjectionMatrix(camera.combined);
        hitCircle = new Circle(vBall, RADIUS);
        //clrShape = new Color(99 / 255f, 184 / 255f, 1, 1);

        timer = new Timer();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("Pokemon GB.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        font = generator.generateFont(parameter);
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        nTime = 10;
        nPointer = -1;
        isOn = false;
        isAlive = true;
        bCauseGameOver = false;
    }

    public void render() {
        update();
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(clrShape);
        shape.circle(vBall.x, vBall.y, RADIUS, 300);
        shape.end();
        batch.begin();
        font.draw(batch, Integer.toString(nTime), vBall.x - parameter.size * Integer.toString(nTime).length() / 2, vBall.y + 10);
        batch.end();
        if (nTime <= 0) {
            if (isAlive) {
                isAlive = false;
                bCauseGameOver =true;
                this.dispose();
            }
        }
        if (!isOn) {
            isOn = true;
            countdown();
        }

    }
    public void draw(){
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(clrShape);
        shape.circle(vBall.x, vBall.y, RADIUS, 300);
        shape.end();
        batch.begin();
        font.draw(batch, Integer.toString(nTime), vBall.x - parameter.size * Integer.toString(nTime).length() / 2, vBall.y + 10);
        batch.end();
    }
    private void update() {
        vBall.x += fVelX;
        vBall.y += fVelY;
        if(!isMoveable){
            nPointer = -1;
            clrShape = arColors[0];
        }
        //onTouch();
    }

    void countdown() {
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                nTime--;
            }
        }, 1, 1);
    }
    Circle getCircle(){
        Circle circle = new Circle(vBall, RADIUS);
        return circle;
    }

    public void onTouch(int _nPointer) {
        //use one pointer value, compare it to new pointer. If equal allow movement else not. **COMPLETE**
        hitCircle.set(getCircle());
        camera.unproject(vTouch.set(Gdx.input.getX(_nPointer), Gdx.input.getY(_nPointer), 0));
        if (hitCircle.contains(vTouch.x, vTouch.y)) {
            isMoveable = true;
            shape.setColor(Color.ROYAL);
            nPointer = _nPointer;
        }
        if (isMoveable) {
            if(nPointer == _nPointer) {
                vBall.x = vTouch.x;
                vBall.y = vTouch.y;
                clrShape = arColors[1];
            }
        }
        else {

        }
    }

    void dispose() {
        shape.dispose();
        font.dispose();

    }
}