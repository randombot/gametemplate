package com.randombot.mygame;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AndroidApplication {
	
	private AdView adView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Create the layout
		RelativeLayout layout = new RelativeLayout(this);

		// Do the stuff that initialize() would do for you
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		// Create the libgdx View
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.hideStatusBar = true;
		cfg.useGL20 = true;
		cfg.useAccelerometer = false;
		cfg.useCompass = false;
		cfg.useGLSurfaceViewAPI18 = false;
		cfg.useImmersiveMode = true;
		cfg.useWakelock = false;
		View gameView = initializeForView(new MyGame(new AndroidResolver()), cfg);

		adView = new AdView(this);
		adView.setAdUnitId(getResources().getString(R.string.ad_unit_id));
		adView.setAdSize(AdSize.BANNER);
		adView.loadAd(new AdRequest.Builder().build());
		/**
		 * Invoking { requestWindowFeature(Window.FEATURE_NO_TITLE); } will make the add view
		 * not to be shown until the next event (updating the ad every 60 seconds or pause/resume)
		 * By calling setBackgroundColor we force the add to show itself as soon as it's loaded.
		 */
		adView.setBackgroundColor(Color.BLACK);
		
		// Add the libgdx view
		layout.addView(gameView);

		// Add the AdMob view
		RelativeLayout.LayoutParams adParams = 
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,  
						RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

		layout.addView(adView, adParams);

		// Hook it all up		
		setContentView(layout);		
	}

	@Override
	protected void onPause() {
		adView.pause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		adView.resume();
	}

	@Override
	protected void onDestroy() {
		adView.destroy();
		super.onDestroy();
	}

	private class AndroidResolver implements Resolver {

		@Override
		public void resolve(int which, int ... args) {			
			switch (which){
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

	@Override
	public void onBackPressed() { }
}