package com.randombot.mygame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

public class Help extends BaseScreen {

	private Dialog rate;

	@Override
	public void create() {
		super.create();
		previousScreen = menu;

		final float DEFAULT_DIALOG_PADDING_BOTTON_TOP = 30f;
		rate = new Dialog("Rating", skin, "dialog") {
			protected void result(Object object) {
				if ((Boolean) object) {
					Gdx.app.exit();
				}
			}
		}
		.text("Rate this game!")
		.button("No!", false).key(Keys.BACK, false).key(Keys.BACKSPACE, false)
		.button("Yes", true).key(Keys.ENTER, true);
		rate.setMovable(false);
		rate.padLeft(DEFAULT_DIALOG_PADDING_BOTTON_TOP);
		rate.padRight(DEFAULT_DIALOG_PADDING_BOTTON_TOP);		
	}
	
	@Override
	public void show() {
		super.show();
		if(!isVisibleDialog()) return;
		rate.show(stage);		
	}
	
	@Override
	public void onBackPressed() {
		if(!isVisibleDialog()) return;
		super.onBackPressed();
	}
	
	private boolean isVisibleDialog(){
		return rate.getParent() == null;
	}
		
}
