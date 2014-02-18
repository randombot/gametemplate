package com.randombot.mygame.screens.transitions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.randombot.mygame.screens.transitions.TransitionManager.Transition;

public class Fade implements Transition {
	private static final Fade instance = new Fade();	
	
	private float duration;	
	private boolean fadeCurrentScreen;
	
	public static Fade init (float duration, boolean fadeCurrentScreen) {
		instance.fadeCurrentScreen = fadeCurrentScreen;
		instance.duration = duration;
		return instance;
	}
	@Override
	public float getDuration () {
		return duration;
	}
	@Override
	public void render (Batch batch, TextureRegion currScreen,
			TextureRegion nextScreen, float alpha) {
		alpha = Interpolation.fade.apply(alpha);
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.setColor(1, 1, 1, fadeCurrentScreen ? 1-alpha : 1);
		batch.draw(currScreen, 0, 0);
		batch.setColor(1, 1, 1, alpha);
		batch.draw(nextScreen, 0, 0);
		batch.end();
	}
}
