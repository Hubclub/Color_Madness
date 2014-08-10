package com.hubclub.color_madness;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ColorGame extends com.badlogic.gdx.Game {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		
		super.render();
	
	}
}
