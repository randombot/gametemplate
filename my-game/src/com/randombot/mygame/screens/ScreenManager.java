package com.randombot.mygame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.randombot.mygame.MyGame;
import com.randombot.mygame.Resolver;

public class ScreenManager extends BaseScreen {

	private final String SKIN_SRC = "data/skin/holo-dark-xhdpi.json";
	
	private MyGame myGame;
	private Resolver resol;	
	
	private NinePatch loadingBar, loadingProgress;
	private TextureAtlas atlas;
	private float xBar, yBar, wBar, hBar;
	private Batch sb;
	
	public ScreenManager(MyGame myGame, Resolver resolver) {
		this.myGame = myGame;
		this.resol = resolver;
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
	
	@Override
	public void render(float delta) {
		act(delta);
		draw();
	}
	
	private void act(float delta) {
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

					menu = new Menu();
					menu.create();
					
					game.changeScreen(menu);
				}
			});			
		}
	}

	private void draw() {
		clearColor();
		this.sb.begin();
		this.loadingBar.draw(this.sb, this.xBar, this.yBar, this.wBar, this.hBar);
		this.loadingProgress.draw(this.sb, this.xBar, this.yBar, this.wBar * am.getProgress(), this.hBar);
		this.sb.end();
	}

	public void resize() { stage.setViewport(screenw, screenh, true); }
	
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
