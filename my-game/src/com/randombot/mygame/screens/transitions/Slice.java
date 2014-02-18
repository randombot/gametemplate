package com.randombot.mygame.screens.transitions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.randombot.mygame.screens.transitions.TransitionManager.Transition;

public class Slice implements Transition{
	public static final int RANDOM = 0;
	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int UP_DOWN = 3;
	private static final Slice instance = new Slice();

	private boolean horizontal = true;
	private float duration;
	private int direction;
	private Interpolation easing;
	private Array<Integer> sliceIndex = new Array<Integer>();

	public static Slice init (float duration, boolean horizontal, int direction, int numSlices, Interpolation easing) {
		instance.duration = duration;
		instance.horizontal = horizontal;
		instance.direction = direction == RANDOM ? MathUtils.random(UP, UP_DOWN) : direction;;
		instance.easing = easing;
		// create shuffled list of slice indices which determines
		// the order of slice animation
		instance.sliceIndex.clear();
		for (int i = 0; i < numSlices; i++)
			instance.sliceIndex.add(i);
		instance.sliceIndex.shuffle();
		return instance;
	}

	@Override
	public float getDuration () {
		return duration;
	}

	@Override
	public void render (Batch batch, TextureRegion currScreen, TextureRegion nextScreen, float alpha) {

		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(currScreen, 0, 0);
		float w = currScreen.getRegionWidth();
		float h = currScreen.getRegionHeight();
		float x = 0;
		float y = 0;

		if(horizontal){
			int nextScreenW = currScreen.getRegionWidth();
			int sliceHeight = (int)(h / sliceIndex.size);
			Texture nextTex = nextScreen.getTexture();
			alpha = easing.apply(alpha);
			for (int i = 0; i < sliceIndex.size; ++i) {
				// current slice/column
				y = i * sliceHeight;
				// vertical displacement using randomized
				// list of slice indices
				float offsetX = w * (1 + sliceIndex.get(i) / (float)sliceIndex.size);
				switch (direction) {
				case UP:
					x = -offsetX + offsetX * alpha;
					break;
				case DOWN:
					x = offsetX - offsetX * alpha;
					break;
				case UP_DOWN:
					if (i % 2 == 0) {
						x = -offsetX + offsetX * alpha;
					} else {
						x = offsetX - offsetX * alpha;
					}
					break;
				}
				batch.draw(nextTex, x, y, 0, 0, w, sliceHeight, 1, 1, 0,
						0, i * sliceHeight, nextScreenW, sliceHeight,
						false, true);
			}
		} else {

			int nextScreenH = currScreen.getRegionHeight();
			int sliceWidth = (int)(w / sliceIndex.size);
			Texture nextTex = nextScreen.getTexture();
			alpha = easing.apply(alpha);
			for (int i = 0; i < sliceIndex.size; ++i) {
				// current slice/column
				x = i * sliceWidth;
				// vertical displacement using randomized
				// list of slice indices
				float offsetY = h * (1 + sliceIndex.get(i) / (float)sliceIndex.size);
				switch (direction) {
				case UP:
					y = -offsetY + offsetY * alpha;
					break;
				case DOWN:
					y = offsetY - offsetY * alpha;
					break;
				case UP_DOWN:
					if (i % 2 == 0) {
						y = -offsetY + offsetY * alpha;
					} else {
						y = offsetY - offsetY * alpha;
					}
					break;
				}
				batch.draw(nextTex, x, y, 0, 0, sliceWidth, h, 1, 1, 0,
						i * sliceWidth, 0, sliceWidth, nextScreenH,
						false, true);
			}
		}
		batch.end();
	}

}
