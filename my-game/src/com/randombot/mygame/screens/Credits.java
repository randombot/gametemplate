package com.randombot.mygame.screens;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.randombot.mygame.Resolver;
import com.randombot.mygame.view.MenuButton;
import com.randombot.mygame.view.ModalDialog;

public class Credits extends BaseScreen {

	private ModalDialog rate;

	@Override
	public void create() {
		super.create();
		previousScreen = menu;

		rate = new ModalDialog("Rating", skin) {
			protected void result(Object object) {
				if ((Boolean) object) {
					resolver.resolve(Resolver.SHARE, 0);
				}
			}
		};
		rate.text("Rate this game!")
		.button("No!", false).key(Keys.BACK, false).key(Keys.BACKSPACE, false)
		.button("Yes", true).key(Keys.ENTER, true);	

		root.defaults().expand().space(15f);
		root.pad(60f);

		final Button rblogo, twitter, fb, market;

		rblogo = new MenuButton("Daniel Nowendsztern\nDan Cristian Rotaru", skin, "icon-blitz");
		root.add(rblogo).colspan(3).fill();

		root.row();
		twitter = new TextButton("Twitter", skin);
		root.add(twitter);

		market = new TextButton("Market", skin);
		root.add(market);

		fb = new TextButton("Facebook", skin);
		root.add(fb);

		// Creating the music/sound listener
		ClickListener mMultimediaListener = new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				final Actor target = event.getListenerActor();
				if (target == rblogo) {
					resolver.resolve(Resolver.SHOW_URI, Resolver.SHOW_URI_RANDOMBOT);
				} else if (target == twitter) {
					resolver.resolve(Resolver.SHOW_URI, Resolver.SHOW_URI_TWITTER);
				} else if (target == market) {
					resolver.resolve(Resolver.SHOW_URI, Resolver.SHOW_URI_MARKET);
				} else if (target == fb) {
					resolver.resolve(Resolver.SHOW_URI, Resolver.SHOW_URI_FACEBOOK);
				}
			}
		};
		rblogo.addListener(mMultimediaListener);
		twitter.addListener(mMultimediaListener);
		market.addListener(mMultimediaListener);
		fb.addListener(mMultimediaListener);
	}

	@Override
	public void show() {
		super.show();
		if(rate.isVisible()) return;
		rate.show(stage);		
	}

	@Override
	public void onBackPressed() {
		if(rate.isVisible()) return;
		super.onBackPressed();
	}

}
