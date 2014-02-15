package com.randombot.mygame;

import android.os.Bundle;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.randombot.mygame.MyGame;
import com.randombot.mygame.Resolver;

public class MainActivity extends AndroidApplication {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.hideStatusBar = true;
		cfg.useGL20 = true;
		cfg.useAccelerometer = false;
		cfg.useCompass = false;
		cfg.useGLSurfaceViewAPI18 = false;
		cfg.useImmersiveMode = true;
		cfg.useWakelock = false;

		initialize(new MyGame(new AndroidResolver()), cfg);
	}

	private class AndroidResolver implements Resolver {
		
		@Override
		public void resolve(int which, int ... args) {			
			switch (which){
				case ADMOB_ADS:{
					
					break;
				}
				case SWARM: {
					
					break;
				}
				case SHARE: {
					
					break;
				}
				case SHOW_URI: {
				
					break;
				}
				default: break;
			}
		}  		
		
	}
		
}