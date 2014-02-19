package com.randombot.mygame.screens.transitions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.randombot.mygame.screens.transitions.TransitionManager.Transition;

public class FadeSlices implements Transition{
	private static final FadeSlices instance = new FadeSlices();

	private boolean horizontal = true;
	private float duration;
	private int numSlices;
	private Array<Integer> sliceIndex = new Array<Integer>();
	private Array<Float> sliceTimes = new Array<Float>();

	public static FadeSlices init (float duration, boolean horizontal, int numSlices) {
		instance.duration = duration;
		instance.horizontal = horizontal;
		// create shuffled list of slice indices which determines
		// the order of slice animation
		instance.numSlices = numSlices;
		instance.sliceIndex.clear();
		float startTime = 0f;
		float stepTime = 1f / numSlices;
		for (int i = 0; i < numSlices; i++){
			instance.sliceIndex.add(i);
			instance.sliceTimes.add(startTime);
			startTime += stepTime;
		}
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

		if(!horizontal){
			int nextScreenW = currScreen.getRegionWidth();
			int sliceHeight = (int)(h / sliceIndex.size);
			Texture nextTex = nextScreen.getTexture();
			int curr = 0;
			for (int i = 0; i < sliceIndex.size; ++i) {
				// current slice/column
				y = i * sliceHeight;
				// vertical displacement using randomized
				// list of slice indices
				if(i == curr && alpha > sliceTimes.get(curr)){
					float value = (alpha - sliceTimes.get(curr)) * numSlices;
					if(value >= 1f){
						value = 1f;
						curr++;
					}
					batch.setColor(1f, 1f, 1f, value);
				} else {
					batch.setColor(1f, 1f, 1f, 0);
					
				}
				batch.draw(nextTex, x, y, 0, 0, w, sliceHeight, 1, 1, 0,
						0, i * sliceHeight, nextScreenW, sliceHeight,
						false, true);
			}
		} else {

			int nextScreenH = currScreen.getRegionHeight();
			int sliceWidth = (int)(w / sliceIndex.size);
			Texture nextTex = nextScreen.getTexture();
			int curr = 0;
			for (int i = 0; i < sliceIndex.size; ++i) {
				// current slice/column
				x = i * sliceWidth;
				// vertical displacement using randomized
				// list of slice indices
				if(i == curr && alpha > sliceTimes.get(curr)){
					float value = (alpha - sliceTimes.get(curr)) * numSlices;
					if(value >= 1f){
						value = 1f;
						curr++;
					}
					batch.setColor(1f, 1f, 1f, value);
				} else {
					batch.setColor(1f, 1f, 1f, 0);
					
				}
				batch.draw(nextTex, x, y, 0, 0, sliceWidth, h, 1, 1, 0,
						i * sliceWidth, 0, sliceWidth, nextScreenH,
						false, true);
			}
		}
		batch.end();
	}

}
