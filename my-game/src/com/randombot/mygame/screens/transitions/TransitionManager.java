package com.randombot.mygame.screens.transitions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.randombot.mygame.screens.BaseScreen;

public class TransitionManager extends BaseScreen {

	private static final float FIXED_TIMESTEP_LIMIT =  1.0f / 60.0f;
	
	private BaseScreen currScreen, nextScreen;
	private FrameBuffer currFbo, nextFbo;
	private Transition screenTransition;
	private Matrix4 originalViewport;
	private TextureRegion currTex;
	private TextureRegion nexTex;
	private Batch batch;
	private float time;

	public TransitionManager(){
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		nextFbo = new FrameBuffer(Format.RGB888, w, h, false);
		currFbo = new FrameBuffer(Format.RGB888, w, h, false);
		setUpNormalProjection();
		nexTex = new TextureRegion(nextFbo.getColorBufferTexture());
		currTex = new TextureRegion(currFbo.getColorBufferTexture());
		nexTex.flip(false, true);
		currTex.flip(false, true);
	}

	private void setUpNormalProjection(){
		OrthographicCamera cam = new OrthographicCamera();
		cam.setToOrtho(false);
		originalViewport = cam.combined;
	}

	public void prepateTransition (BaseScreen screen,
			Transition screenTransition) {
		batch = stage.getSpriteBatch();
		currScreen = game.showingScreen;
		// start new transition
		nextScreen = screen;
		if (currScreen != null) currScreen.pause();
		nextScreen.pause();
		Gdx.input.setInputProcessor(null); // disable input
		this.screenTransition = screenTransition;
		time = 0;

		currFbo.begin();
		currScreen.draw();			
		currFbo.end();	
		currScreen.hide();

		nextScreen.show();
		nextFbo.begin();
		nextScreen.draw();
		nextFbo.end();	
	}

	public void update(float delta){
		if(screenTransition == null){
			endTransition();
		}
		
		// get delta time and ensure an upper limit of one 60th second
		float deltaTime = Math.min(delta, FIXED_TIMESTEP_LIMIT);

		// ongoing transition
		float duration = screenTransition.getDuration();
		// update progress of ongoing transition
		time = Math.min(time + deltaTime, duration);
		if (time >= duration) {
			endTransition();
		} else {
			// render transition effect to screen
			batch.setProjectionMatrix(originalViewport);
			float alpha = time / duration;
			screenTransition.render(batch, currTex, nexTex, alpha);
		}
	}
	
	private void endTransition(){
		//no transition effect set or transition has just finished
		// enable input for next screen
		Gdx.input.setInputProcessor(nextScreen.getInputProcessor());
		// switch screens
		game.showingScreen = nextScreen;
		nextScreen = null;
		screenTransition = null;
	}

	@Override
	public void draw() { }

	public void dispose(){
		currFbo.dispose();
		currScreen = null;
		nextFbo.dispose();
		nextScreen = null;
	}

	public interface Transition {
		
		float getDuration ();

		void render (Batch batch, TextureRegion currScreen, TextureRegion nextScreen, float alpha);
	}
}
