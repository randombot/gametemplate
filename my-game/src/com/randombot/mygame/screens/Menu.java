package com.randombot.mygame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.randombot.mygame.view.MenuButton;

public class Menu extends BaseScreen {

	private Dialog exitDialog;
	private Image logo;
	private Button play, help, credits, music, sound;
	
	@Override
	public void create() {
		super.create();

		final float DEFAULT_DIALOG_PADDING_BOTTON_TOP = 30f;
		exitDialog = new Dialog("Exit", skin, "dialog") {
			protected void result(Object object) {
				if ((Boolean) object) {
					Gdx.app.exit();
				}
			}
		}
		.text("Are you sure?")
		.button("No!", false).key(Keys.BACK, false).key(Keys.BACKSPACE, false)
		.button("Yes", true).key(Keys.ENTER, true);
		exitDialog.setMovable(false);
		exitDialog.padLeft(DEFAULT_DIALOG_PADDING_BOTTON_TOP);
		exitDialog.padRight(DEFAULT_DIALOG_PADDING_BOTTON_TOP);		
		
		logo = new Image(skin, "ic_logo");
		logo.setScaling(Scaling.fit);
		
		play = new MenuButton("Play", skin, "ic_playgame");
		help = new MenuButton("Help", skin, "icon-blitz");
		credits = new MenuButton("Credits", skin, "icon-blitz");
		music = new MenuButton("Music", skin, "icon-blitz");
		sound = new MenuButton("Sound",skin, "icon-blitz");
		
		Table stuff = new Table();
		stuff.setFillParent(true);
		stuff.defaults().expand().space(DEFAULT_DIALOG_PADDING_BOTTON_TOP*.5f);
		stuff.pad(DEFAULT_DIALOG_PADDING_BOTTON_TOP);

		stuff.add(logo).colspan(2);
		stuff.row();
		stuff.add(play).colspan(2);
		stuff.row();
		stuff.add(help);
		stuff.add(credits);
		stuff.row();
		stuff.add(music);
		stuff.add(sound);
		
		root.addActor(stuff);
	}

	@Override
	public void onBackPressed() {
		if(exitDialog.getParent() != null) return;
		exitDialog.show(stage);		
	}		
}
