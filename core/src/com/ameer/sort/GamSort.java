package com.ameer.sort;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class GamSort extends Game {
	public OrthographicCamera camera;
	public StretchViewport viewport;
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		viewport = new StretchViewport(480, 800, camera);
		viewport.apply();
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		setScreen(new ScrGame(this));

	}

	@Override
	public void render () {
		super.render();
	}

	public void changeScreen(Screen screen){
		setScreen(screen);
	}

}
