package com.randombot.mygame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

public class First extends BaseScreen {

	private Dialog exitDialog;

	@Override
	public void create() {
		super.create();

		final float DEFAULT_DIALOG_PADDING_BOTTON_TOP = 20f;
		exitDialog = new Dialog("¿Salir?", skin, "dialog") {
			protected void result(Object object) {
				if ((Boolean) object) {
					Gdx.app.exit();
				}
			}
		}.text("¿Estás seguro?")
		.button("¡No!", false).key(Keys.BACK, false).key(Keys.BACKSPACE, false)
		.button("Salir", true).key(Keys.ENTER, true);
		exitDialog.setMovable(false);
		exitDialog.padLeft(DEFAULT_DIALOG_PADDING_BOTTON_TOP);
		exitDialog.padRight(DEFAULT_DIALOG_PADDING_BOTTON_TOP);
	}

	@Override
	public void onBackPressed() {
		if(exitDialog.getParent() != null) return;
		exitDialog.show(stage);		
	}	
}
