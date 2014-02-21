package com.randombot.mygame;

import java.io.IOException;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.randombot.mygame.screens.BaseScreen;
import com.randombot.mygame.screens.LoadManager;
import com.randombot.mygame.screens.transitions.TransitionManager;
import com.randombot.mygame.screens.transitions.TransitionManager.Transition;

public class MyGame implements ApplicationListener {
	
	public BaseScreen showingScreen;
	private Resolver resolver;
	private LoadManager screenManager;
	private TransitionManager transitionManager;
	
	//////////////////////////////////////////////////////////////////////////////////////////
	private static final boolean PACKING = false;
	private static final boolean PRE_MARKET = true;
	public static final String GAME_NAME = "my-game";		
	//////////////////////////////////////////////////////////////////////////////////////////
	//// Comment added from iPad :)	

	public MyGame(Resolver res){
		this.resolver = res;
	}
	
	@Override
	public void create() {
		this.transitionManager = new TransitionManager();
		this.screenManager = new LoadManager(this, this.resolver, this.transitionManager);
		this.showingScreen = this.screenManager;
		this.showingScreen.create();
		if (PRE_MARKET){
			this.showingScreen.getStage().addListener(new InputListener(){
				@Override
				public boolean keyDown(InputEvent event, int keycode) {

					if (keycode == Keys.ENTER){
						try {
							MyGame.this.showingScreen.saveScreenshot();
						} catch (IOException e) { 
							e.printStackTrace();
						}
					} else if (keycode == Keys.SPACE){
						// copia descripcion
						
					}
					
					return super.keyDown(event, keycode);
				}
			});			
		}
	}

	@Override
	public void dispose() {
		this.screenManager.dispose();
		this.transitionManager.dispose();
		System.exit(0);
	}

	@Override
	public void render() {	
		this.showingScreen.update(Gdx.graphics.getDeltaTime());
		this.showingScreen.draw();
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
		changeScreen(next, null);
	}
	
	public void changeScreen(BaseScreen next, Transition screenTransition){		
		this.showingScreen = this.transitionManager.prepateTransition(next, screenTransition);;
	}
	
	public static void main(String[] args) {
		if (PACKING) { Packer.compile(); }		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "my-game";
		cfg.useGL20 = true;
		cfg.width = 480;
		cfg.height = 854;		
		new LwjglApplication(new MyGame(new DesktopResolver()), cfg);
		
	}
	
	private static class DesktopResolver implements Resolver {		
		@Override
		public void resolve(int which, int arg) {
			System.out.println("Resolve: " + which + ", Arg: " + arg);
		}
	}
	
	
}
