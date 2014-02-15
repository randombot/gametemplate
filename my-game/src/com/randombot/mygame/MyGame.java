package com.randombot.mygame;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.randombot.mygame.screens.BaseScreen;

public class MyGame implements ApplicationListener {
	
	private BaseScreen showingScreen;
	private Resolver resolver;
	
	public MyGame(Resolver res){
		this.resolver = res;
	}
	
	@Override
	public void create() {
		BaseScreen.initStatics(this, this.resolver);
		BaseScreen.stage.getRoot().addCaptureListener(new InputListener() {			
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if (!(event.getTarget() instanceof TextField)) {
					BaseScreen.stage.setKeyboardFocus(null);
					Gdx.input.setOnscreenKeyboardVisible(false);
				}
				return false;
			}			
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				if(keycode == Keys.BACK || (keycode == Keys.BACKSPACE && !(event.getTarget() instanceof TextField))){					
					MyGame.this.showingScreen.onBackPressed();
				}
				return true;
			}
		});
		this.showingScreen = BaseScreen.first;
		this.showingScreen.show();
	}

	@Override
	public void dispose() {
		BaseScreen.disposeStatics();
		System.exit(0);
	}

	@Override
	public void render() {	
		this.showingScreen.render(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {
		BaseScreen.stage.setViewport(BaseScreen.screenw, BaseScreen.screenh, true);
	}

	@Override
	public void pause() {
		this.showingScreen.pause();
	}

	@Override
	public void resume() {
		this.showingScreen.resume();
	}
	
	public void changeScreen(BaseScreen next){
		this.showingScreen.hide();
		this.showingScreen = next;
		this.showingScreen.show();
	}
	
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "my-game";
		cfg.useGL20 = true;
		cfg.width = 480;
		cfg.height = 854;

		new LwjglApplication(new MyGame(new DesktopResolver()), cfg);
	}
	
	private static class DesktopResolver implements Resolver{
		
		@Override
		public void resolve(String which, Object... args) {
			if(which.equals(SHOW_INTENT)){
				//Show intent...
				Gdx.app.log(BaseScreen.TAG, "Showing intent");
			} else {
				
			}
		}
	}
	
}
