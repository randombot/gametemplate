package com.randombot.mygame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;

public class First extends BaseScreen {

	private Dialog exitDialog;
	
	@Override
	public void create() {
		super.create();
		
		exitDialog = new Dialog("¿Salir?", skin, "dialog") {
			protected void result(Object object) {
				if ((Boolean) object) {
					Gdx.app.exit();
				} else {
					exitDialog.setVisible(false);
				}
			}
		}.text("¿Estás seguro?").button("¡No!", false)
		.button("Salir", true).key(Keys.ENTER, true);
		exitDialog.setMovable(false);
		exitDialog.setVisible(false);
	}

	@Override
	public void onBackPressed() {
		if(!exitDialog.isVisible()){
			exitDialog.setVisible(true);
			exitDialog.show(stage);		
		}
	}	
}
