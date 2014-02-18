package com.randombot.mygame.screens.transitions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.randombot.mygame.screens.transitions.TransitionManager.Transition;

public class Slide implements Transition {
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;
	public static final int DOWN = 4;
	public static final int RANDOM = 0;
	private static final Slide instance =
			new Slide();
	private float duration;
	int direction;
	boolean slideOut;
	Interpolation easing;
	public static Slide init (float duration,
			int direction, boolean slideOut, Interpolation easing) {
		instance.duration = duration;
		instance.direction = direction == RANDOM ? MathUtils.random(LEFT, DOWN) : direction;
		instance.slideOut = slideOut;
		instance.easing = easing;
		return instance;
	}

	@Override
	public float getDuration () {
		return duration;
	}

	public void render (Batch batch, TextureRegion currScreen,
			TextureRegion nextScreen, float alpha) {
		float w = currScreen.getRegionWidth();
		float h = currScreen.getRegionHeight();
		float x = 0;
		float y = 0;
		alpha = easing.apply(alpha);
		// calculate position
		switch (direction) {
		case LEFT:
			x = -w * alpha;
			if (!slideOut) x += w;
			break;
		case RIGHT:
			x = w * alpha;
			if (!slideOut) x -= w;
			break;
		case UP:
			y = h * alpha;
			if (!slideOut) y -= h;
			break;
		case DOWN:
			y = -h * alpha;
			if (!slideOut) y += h;
			break;
		}
		// drawing order depends on slide type ('in' or 'out')
		TextureRegion texBottom = slideOut ? nextScreen : currScreen;
		TextureRegion texTop = slideOut ? currScreen : nextScreen;
		// finally, draw both screens
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(texBottom, 0, 0);
		batch.draw(texTop, x, y);
		batch.end();
	}
}