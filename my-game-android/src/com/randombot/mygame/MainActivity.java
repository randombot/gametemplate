package com.randombot.mygame;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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

public class MainActivity extends AndroidApplication implements Resolver{

	private AdView adView;

	private String facebook;
	private String twitter;
	private String market;
	private String randombot;
	private String appName;
	private String googlePlay;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		this.facebook = getString(R.string.facebook);
		this.twitter = getString(R.string.twitter);
		this.market = getString(R.string.market);
		this.randombot = getString(R.string.randombot);
		this.appName = getString(R.string.app_name);
		this.googlePlay = getString(R.string.google_play);

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
		View gameView = initializeForView(new MyGame(this), cfg);

		this.adView = new AdView(this);
		this.adView.setAdUnitId(getString(R.string.ad_unit_id));
		this.adView.setAdSize(AdSize.BANNER);
		this.adView.loadAd(new AdRequest.Builder().build());
		/**
		 * Invoking { requestWindowFeature(Window.FEATURE_NO_TITLE); } will make the add view
		 * not to be shown until the next event (updating the ad every 60 seconds or pause/resume)
		 * By calling setBackgroundColor we force the add to show itself as soon as it's loaded.
		 */
		this.adView.setBackgroundColor(Color.BLACK);

		// Add the libgdx view
		layout.addView(gameView);

		// Add the AdMob view
		RelativeLayout.LayoutParams adParams = 
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,  
						RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

		layout.addView(this.adView, adParams);

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

	@Override
	public void onBackPressed() { }

	@Override
	public void resolve(int which, int arg) {
		switch (which){
		case RANKING: {

			break;
		}
		case SHARE: {
			Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND); 
			sharingIntent.setType("text/plain");
			String shareBody = "Let's play " + this.appName + "!\n" + this.googlePlay;
			sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
			startActivity(Intent.createChooser(sharingIntent, "Share via"));
			break;
		}
		case SHOW_URI: {
			String res = "";
			switch(arg){
				case SHOW_URI_FACEBOOK:
						res = this.facebook;
					break;
				case SHOW_URI_TWITTER:
						res = this.twitter;	
					break;
				case SHOW_URI_MARKET:
						res = this.market;	
					break;
				case SHOW_URI_RANDOMBOT:
						res = this.randombot;	
					break;
			}

			Uri myUri = Uri.parse(res);
			Intent intent = new Intent(Intent.ACTION_VIEW, myUri);
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
			startActivity(intent);
			break;
		}
		default: break;
		}
	}
}