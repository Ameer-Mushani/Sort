package com.ameer.sort;

import com.badlogic.gdx.Game;

public class Sort extends Game {
	@Override
	public void create () {
		setScreen(new ScrGame(this));
	}

	@Override
	public void render () {
		super.render();
	}

}
