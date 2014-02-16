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

		final float DEFAULT_DIALOG_PADDING_BOTTON_TOP = 10f;
		rate = new Dialog("Rating", skin, "exit-dialog") {
			protected void result(Object object) {
				if ((Boolean) object) {
					Gdx.app.exit();
				}
			}
		};
		rate.getButtonTable()
		.defaults()
		.space(DEFAULT_DIALOG_PADDING_BOTTON_TOP)
		.width(DEFAULT_DIALOG_PADDING_BOTTON_TOP*15f);
		rate.text("Rate this game!")
		.button("No!", false).key(Keys.BACK, false).key(Keys.BACKSPACE, false)
		.button("Yes", true).key(Keys.ENTER, true);
		rate.setMovable(false);
		rate.padLeft(DEFAULT_DIALOG_PADDING_BOTTON_TOP);
		rate.padRight(DEFAULT_DIALOG_PADDING_BOTTON_TOP);	
		rate.padBottom(DEFAULT_DIALOG_PADDING_BOTTON_TOP);	
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
