package com.randombot.mygame.screens;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
		
		Table stuffTable = new Table();
		stuffTable.setFillParent(true);
		stuffTable.defaults().expand().space(15f);
		stuffTable.pad(60f);
		
		final Button rblogo, twitter, fb, market;
		
		rblogo = new MenuButton("Daniel Nowendsztern\nDan Cristian Rotaru", skin, "icon-blitz");
		stuffTable.add(rblogo).colspan(3).fill();
		
		stuffTable.row();
		twitter = new TextButton("Twitter", skin);
		stuffTable.add(twitter);
		
		market = new TextButton("Market", skin);
		stuffTable.add(market);
		
		fb = new TextButton("Facebook", skin);
		stuffTable.add(fb);
		
		root.addActor(stuffTable);
		
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
