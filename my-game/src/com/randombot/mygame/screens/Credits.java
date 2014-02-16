package com.randombot.mygame.screens;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
