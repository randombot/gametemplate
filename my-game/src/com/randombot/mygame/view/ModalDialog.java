package com.randombot.mygame.view;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ModalDialog extends Dialog {

	public ModalDialog (String title, Skin skin) {
		super(title, skin, "exit-dialog");
		initialize();
	}

	private void initialize() {
		setModal(true);
		setMovable(false);
		setResizable(false);
		final float DEFAULT_DIALOG_PADDING_BOTTON_TOP = 10f;
		padLeft(DEFAULT_DIALOG_PADDING_BOTTON_TOP);
		padRight(DEFAULT_DIALOG_PADDING_BOTTON_TOP);	
		padBottom(DEFAULT_DIALOG_PADDING_BOTTON_TOP);	
		getButtonTable()
		.defaults()
		.space(DEFAULT_DIALOG_PADDING_BOTTON_TOP*1.2f)
		.minWidth(getWidth());
	}
	
	public boolean isVisible(){
		return getParent() != null;
	}
}
