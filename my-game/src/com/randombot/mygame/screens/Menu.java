package com.randombot.mygame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

public class Menu extends BaseScreen {

	private Dialog exitDialog;
	private Image logo;
	private ImageButton play, help, credits, music, sound;
	
	@Override
	public void create() {
		super.create();

		final float DEFAULT_DIALOG_PADDING_BOTTON_TOP = 20f;
		exitDialog = new Dialog("Exit", skin, "dialog") {
			protected void result(Object object) {
				if ((Boolean) object) {
					Gdx.app.exit();
				}
			}
		}.text("Are you sure?")
		.button("No!", false).key(Keys.BACK, false).key(Keys.BACKSPACE, false)
		.button("Yes", true).key(Keys.ENTER, true);
		exitDialog.setMovable(false);
		exitDialog.padLeft(DEFAULT_DIALOG_PADDING_BOTTON_TOP);
		exitDialog.padRight(DEFAULT_DIALOG_PADDING_BOTTON_TOP);
		
		
		logo = new Image(skin, "icon-blitz");
		play = new ImageButton(skin, "ic_playgame");
		help = new ImageButton(skin, "icon-blitz");
		credits = new ImageButton(skin, "icon-blitz");
		music = new ImageButton(skin, "icon-blitz");
		sound = new ImageButton(skin, "icon-blitz");
		
		stage.addActor(logo);
		
		
		
	}

	@Override
	public void onBackPressed() {
		if(exitDialog.getParent() != null) return;
		exitDialog.show(stage);		
	}	
	
	
	
}
