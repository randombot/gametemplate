package com.randombot.mygame.screens;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.randombot.mygame.screens.transitions.Slice;


public class Play extends BaseScreen {
	
	private Label score;
	private Dialog pauseMenu;
	private boolean pause;

	@Override
	public void create() {
		super.create();
		previousScreen = menu;

		root.defaults().fillX().expand().top();
		score = new Label("Score: 0000000000000", skin);
		score.setAlignment(Align.center);
		root.add(score);
		
		pauseMenu = new Dialog("Game Paused.", skin, "exit-dialog");
		pauseMenu.setResizable(false);
		pauseMenu.setMovable(false);
		pauseMenu.setModal(true);
		pauseMenu.debug();

		final TextButton resume = new TextButton("Continue", skin);
		resume.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				pauseMenu.hide();
				pause = false;
				return super.touchDown(event, x, y, pointer, button);
			}		
		});
		
		final TextButton exit = new TextButton("Exit", skin);	
		exit.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				pauseMenu.hide();	
				pauseMenu.setVisible(false);			
				game.changeScreen(menu, Slice.init(1f, MathUtils.randomBoolean(), Slice.RANDOM, 10, Interpolation.sineOut));	
				return super.touchDown(event, x, y, pointer, button);
			}		
		});
		
		Table buttonsTable = pauseMenu.getButtonTable();
		float width = stage.getWidth()*.75f, height = stage.getHeight()*.25f;
		buttonsTable.defaults().fill().expand();
		buttonsTable.add(resume).width(width).height(height);
		buttonsTable.row();
		buttonsTable.add(exit).width(width).height(height);
		
		Image pauseButton = new Image(skin, "ic_more");
		root.add(pauseButton).maxSize(50f).padTop(10f);
		pauseButton.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				pauseMenu.setVisible(true);
				pauseMenu.show(stage);		
				pause = true;
				return super.touchDown(event, x, y, pointer, button);
			}		
		});

		root.row();
		
		// Here start the game content 
		
		
	}
	
	@Override
	public void show() {
		super.show();
		pause = false;
	}
	
	@Override
	public void pause() {
		pause = true;
	}
	
	
	@Override
	public void resume() {
		pauseMenu.show(stage);
	}
}
