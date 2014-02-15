package com.randombot.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.randombot.mygame.screens.BaseScreen;

public class Main {
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
