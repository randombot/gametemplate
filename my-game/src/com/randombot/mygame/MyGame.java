package com.randombot.mygame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.randombot.mygame.screens.BaseScreen;
import com.randombot.mygame.screens.ScreenManager;

public class MyGame implements ApplicationListener {
	
	public BaseScreen showingScreen;
	private Resolver resolver;
	private ScreenManager screenManager;
	
	public MyGame(Resolver res){
		this.resolver = res;
	}
	
	@Override
	public void create() {
		this.screenManager = new ScreenManager(this, this.resolver);
		this.showingScreen = this.screenManager;
		this.showingScreen.create();
	}

	@Override
	public void dispose() {
		this.screenManager.dispose();
		System.exit(0);
	}

	@Override
	public void render() {	
		this.showingScreen.render(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {
		this.screenManager.resize();
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
	
	private static class DesktopResolver implements Resolver {		
		@Override
		public void resolve(int which, int ... args) {
			System.out.println("Resolve: " + which);
		}
	}
	
}
