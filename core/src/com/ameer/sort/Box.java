package com.ameer.sort;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/**
 * Created by am44_000 on 2017-11-02.
 */

public class Box extends Sprite {
    OrthographicCamera camera;
    SpriteBatch batch;
    final int COLOUR;
    Box(float fX, float fY, int nColour, OrthographicCamera _camera){
        super(new Texture("rect_white.png"));
        camera = _camera;
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        setX(fX);
        setY(fY);
        setSize(getWidth(), 250);
        COLOUR = nColour;
        if(COLOUR == ScrGame.RED){
            this.setColor(ScrGame.clrRED);
        }
        if(COLOUR == ScrGame.BLUE){
            this.setColor(ScrGame.clrBLUE);
        }
    }
    public void render(){
        batch.begin();
        draw(batch);
        batch.end();
    }
}
