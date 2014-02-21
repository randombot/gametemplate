package com.randombot.mygame.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;


public class Play extends BaseScreen {

	@Override
	public void create() {
		super.create();
		previousScreen = menu;

		root.defaults().fillX().expand();
		final Label score = new Label("Score: 0000000000000", skin);
		score.setAlignment(Align.center);
		root.add(score);
		
		Image pauseButton = new Image(skin, "ic_more");
		root.add(pauseButton).maxSize(50f);

		root.row();
		
		// Here start the game content 
		
		
	}
	
	
	
}
