package com.randombot.mygame.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Background extends Image {
	
	public Background(Skin skin) {
		super(skin, "bg");
		setBounds(0, 0, BaseScreen.getStage().getWidth(), BaseScreen.getStage().getHeight());
	}
	
	
}
