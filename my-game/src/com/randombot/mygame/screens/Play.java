package com.randombot.mygame.screens;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.randombot.mygame.screens.transitions.Slice;


public class Play extends BaseScreen {
	
	private Label score;

	@Override
	public void create() {
		super.create();
		previousScreen = menu;

		root.defaults().fillX().expand();
		score = new Label("Score: 0000000000000", skin);
		score.setAlignment(Align.center);
		root.add(score);
		
		final Window pauseMenu = new Window("Game Paused.", skin);
		pauseMenu.setBounds(stage.getWidth()*0.2f, stage.getHeight()*0.2f, stage.getWidth()*0.6f, stage.getHeight()*0.5f);
		pauseMenu.setResizable(false);
		pauseMenu.setMovable(false);
		pauseMenu.setVisible(false);		
		pauseMenu.debug();

		pauseMenu.defaults().fill().expand();
		final TextButton resume = new TextButton("Continue", skin);
		resume.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				pauseMenu.setVisible(false);
				return super.touchDown(event, x, y, pointer, button);
			}		
		});
		
		final TextButton exit = new TextButton("Exit", skin);	
		exit.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				pauseMenu.setVisible(false);		
				game.changeScreen(menu, Slice.init(1f, MathUtils.randomBoolean(), Slice.RANDOM, 10, Interpolation.sineOut));				
				return super.touchDown(event, x, y, pointer, button);
			}		
		});
		
		pauseMenu.add(resume);
		pauseMenu.row();
		pauseMenu.add(exit);
		
		Image pauseButton = new Image(skin, "ic_more");
		root.add(pauseButton).maxSize(50f);
		pauseButton.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				pauseMenu.setVisible(!pauseMenu.isVisible());				
				return super.touchDown(event, x, y, pointer, button);
			}		
		});

		root.row();
		stage.addActor(pauseMenu);
		
		// Here start the game content 
		
		
	}
	
	
	
}
