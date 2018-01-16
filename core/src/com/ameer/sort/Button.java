package com.ameer.sort;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by am44_000 on 2018-01-11.
 */

public class Button extends Sprite {
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    public Button(String sImg, float fX, float fY){
        super(new Texture(sImg));
        setPosition(fX, fY);
        //setSize(100,100);

    }
    public void drawRect(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(getX(),getY(),getWidth(),getHeight());
        shapeRenderer.end();
    }
    public boolean isHit(float fX, float fY){
        if(getBoundingRectangle().contains(fX,fY)){
            return true;
        }
        return false;
    }
}
