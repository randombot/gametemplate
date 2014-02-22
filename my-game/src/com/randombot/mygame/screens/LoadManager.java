package com.randombot.mygame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.randombot.mygame.MyGame;
import com.randombot.mygame.screens.transitions.Slide;
import com.randombot.mygame.screens.transitions.TransitionManager;
import com.randombot.mygame.utils.Resolver;

public class LoadManager extends BaseScreen {

	private final String SKIN_SRC = "data/skin/skin.json";
	
	private MyGame myGame;
	private Resolver resol;	
	
	private NinePatch loadingBar, loadingProgress;
	private TextureAtlas atlas;
	private float xBar, yBar, wBar, hBar;
	private Batch sb;

	private TransitionManager transitionManager;
	
	public LoadManager(MyGame myGame, Resolver resolver, TransitionManager transitionManager) {
		this.myGame = myGame;
		this.resol = resolver;
		this.transitionManager = transitionManager;
	}
	
	@Override
	public void create() {
		float hh = Gdx.graphics.getHeight() / 2f, hw = Gdx.graphics.getWidth() / 2f;
		this.wBar = hw * 1.5f;
		this.hBar = hw / 7f;
		this.xBar = hw - this.wBar / 2f;
		this.yBar = hh / 2f - hBar / 2f;
		this.atlas = new TextureAtlas("data/ninepatch/ninepatch.atlas");
		this.loadingBar = new NinePatch(atlas.findRegion("2"), 4, 4, 4, 4);
		this.loadingProgress = new NinePatch(atlas.findRegion("3"), 4, 4, 4, 4);

		stage = new Stage(screenw,screenh, true);
		this.sb = stage.getSpriteBatch();	

		am = new AssetManager();
		/*-QUEUE here loading assets-*/
		am.load(SKIN_SRC, Skin.class);	
	}
	
	public void update(float delta) {
		if (am.update()) {
			Gdx.app.postRunnable(new Runnable(){
				@Override
				public void run() {
					game = myGame;
					resolver = resol;
					
					stage.getRoot().addCaptureListener(new InputListener() {			
						public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
							if (!(event.getTarget() instanceof TextField)) {
								stage.setKeyboardFocus(null);
								Gdx.input.setOnscreenKeyboardVisible(false);
							}
							return false;
						}			
						@Override
						public boolean keyDown(InputEvent event, int keycode) {
							if(keycode == Keys.BACK || (keycode == Keys.BACKSPACE && !(event.getTarget() instanceof TextField))){					
								game.showingScreen.onBackPressed();
								return true;
							}
							return false;
						}
					});
					
					Gdx.input.setInputProcessor(stage);
					
					skin = am.get(SKIN_SRC, Skin.class);

					bg = new Background(skin);
					stage.addActor(bg);
					
					menu = new Menu();
					menu.create();
					
					help = new Help();
					help.create();
					
					credits = new Credits();
					credits.create();
					
					play = new Play();
					play.create();					
					
					transitionManager.initialize();
					
					game.changeScreen(menu, Slide.init(1f, Slide.DOWN, MathUtils.randomBoolean(), Interpolation.circleOut));
				}
			});			
		}
	}

	public void draw() {
		clearColor();
		this.sb.begin();
		this.loadingBar.draw(this.sb, this.xBar, this.yBar, this.wBar, this.hBar);
		this.loadingProgress.draw(this.sb, this.xBar, this.yBar, this.wBar * am.getProgress(), this.hBar);
		this.sb.end();
	}

	public void resize() { }
	
	@Override
	public void hide() {
		this.atlas.dispose();
	}
	
	@Override
	public void dispose() {
		stage.dispose();
		menu.dispose();
		help.dispose();
		credits.dispose();
		play.dispose();
		am.dispose();
		am = null;
		stage = null;
		game = null;
		resolver = null;
	}
}
