package com.randombot.mygame.screens;

import com.badlogic.gdx.Input.Keys;
import com.randombot.mygame.Resolver;
import com.randombot.mygame.view.ModalDialog;

public class Help extends BaseScreen {

	private ModalDialog rate;

	@Override
	public void create() {
		super.create();
		previousScreen = menu;

		rate = new ModalDialog("Rating", skin) {
			protected void result(Object object) {
				if ((Boolean) object) {
					resolver.resolve(Resolver.SHARE);
				}
			}
		};
		rate.text("Rate this game!")
		.button("No!", false).key(Keys.BACK, false).key(Keys.BACKSPACE, false)
		.button("Yes", true).key(Keys.ENTER, true);
		
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
