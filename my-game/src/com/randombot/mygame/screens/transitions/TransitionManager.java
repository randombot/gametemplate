package com.randombot.mygame.screens.transitions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.randombot.mygame.screens.BaseScreen;

public class TransitionManager {

	private BaseScreen currScreen, nextScreen;
	private FrameBuffer currFbo, nextFbo;
	private float time;
	private Batch batch;
	private Transition screenTransition;

	public TransitionManager(){
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		currFbo = new FrameBuffer(Format.RGB888, w, h, false);
		nextFbo = new FrameBuffer(Format.RGB888, w, h, false);
		batch = new SpriteBatch();

	}

	public void setScreen (BaseScreen screen) {
		setScreen(screen, null);
	}

	public void setScreen (BaseScreen screen,
			Transition screenTransition) {

		// start new transition
		nextScreen = screen;
		nextScreen.show(); // activate next screen
		nextScreen.update(0); // let screen update() once
		if (currScreen != null) currScreen.pause();
		nextScreen.pause();
		Gdx.input.setInputProcessor(null); // disable input
		this.screenTransition = screenTransition;
		time = 0;
	}

	public void update(float delta){
		// get delta time and ensure an upper limit of one 60th second
		float deltaTime = Math.min(delta,
				1.0f / 60.0f);
		if (nextScreen == null) {
			// no ongoing transition
			if (currScreen != null) currScreen.update(deltaTime);
		} else {
			// ongoing transition
			float duration = 0;
			if (screenTransition != null)
				duration = screenTransition.getDuration();
			// update progress of ongoing transition
			time = Math.min(time + deltaTime, duration);
			if (screenTransition == null || time >= duration) {
				//no transition effect set or transition has just finished
				if (currScreen != null) currScreen.hide();
				nextScreen.resume();
				// enable input for next screen
				Gdx.input.setInputProcessor(
						nextScreen.getInputProcessor());
				// switch screens
				currScreen = nextScreen;
				nextScreen = null;
				screenTransition = null;
			} else {
				// render screens to FBOs
				currFbo.begin();
				if (currScreen != null) currScreen.update(deltaTime);
				currFbo.end();
				nextFbo.begin();
				nextScreen.update(deltaTime);
				nextFbo.end();
				// render transition effect to screen
				float alpha = time / duration;
				screenTransition.render(batch,
						currFbo.getColorBufferTexture(),
						nextFbo.getColorBufferTexture(),
						alpha);
			}
		}
	}
	
	public void dispose(){
		currFbo.dispose();
		currScreen = null;
		nextFbo.dispose();
		nextScreen = null;
		batch.dispose();
	}

	public interface Transition {
		float getDuration ();

		void render (Batch batch, Texture currScreen, Texture nextScreen, float alpha);
	}
}
