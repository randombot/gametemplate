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
	
	public void initialize(){
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		nextFbo = new FrameBuffer(Format.RGB565, w, h, false);
		currFbo = new FrameBuffer(Format.RGB565, w, h, false);
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

	public BaseScreen prepateTransition (BaseScreen nextScr, Transition screenTransition) {
		currScreen = game.showingScreen;
		nextScreen = nextScr;
		if(screenTransition == null){
			currScreen.hide();
			nextScreen.show();			
			endTransition();
			return nextScr;
		}
		
		// start new transition
		batch = stage.getSpriteBatch();
		batch.setColor(1f, 1f, 1f, 1f);
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
		
		return this;
	}

	public void update(float delta){		
		// get delta time and ensure an upper limit of one 60th second
		if(delta > FIXED_TIMESTEP_LIMIT) delta = FIXED_TIMESTEP_LIMIT;

		// ongoing transition
		float duration = screenTransition.getDuration();
		// update progress of ongoing transition
		time += delta;
		if (time > duration) {
			endTransition();
		} else {
			// render transition effect to screen
			float alpha = time / duration;
			batch.setColor(1f, 1f, 1f, 1f);
			batch.setProjectionMatrix(originalViewport);
			screenTransition.render(batch, currTex, nexTex, alpha);
		}
	}
	
	private void endTransition(){
		//no transition effect set or transition has just finished
		// enable input for next screen
		Gdx.input.setInputProcessor(nextScreen.getInputProcessor());
		// switch screens
		game.showingScreen = nextScreen;
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
