package com.randombot.mygame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.randombot.mygame.MyGame;
import com.randombot.mygame.Resolver;
import com.randombot.mygame.screens.transitions.Fade;

public class BaseScreen {	

	protected static final float screenw = 500;
	protected static final float screenh = 800;

	protected static Skin skin;
	protected static Stage stage;
	protected static MyGame game;
	protected static AssetManager am;
	protected static Resolver resolver;

	protected static Menu menu;
	protected static Help help;
	protected static Credits credits;
	protected static Play play;

	protected BaseScreen previousScreen;
	protected Table root;

	protected void setUpRoot(){
		root = new Table(skin).debug();
		root.setVisible(false);
		root.setFillParent(true);
		stage.addActor(root);
	}

	protected void clearColor(){
		GL20 gl20 = Gdx.gl20;
		gl20.glClearColor(.7f, .8f, .9f, 1f);
		gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	public void update(float delta) { 
		stage.act(delta);
	}

	public void draw(){
		clearColor();
		stage.draw();
		Table.drawDebug(stage);
	}

	public void create() {
		setUpRoot();
	}

	public void show() { 
		root.setVisible(true);
	}

	public void hide() { 
		root.setVisible(false);
	}	

	public InputProcessor getInputProcessor(){
		return stage;
	}

	public void pause() { }

	public void resume() { }	

	public void dispose() { }

	public void onBackPressed(){
		game.changeScreen(previousScreen, Fade.init(1f, MathUtils.randomBoolean()));
	}	
	
	public static Stage getStage(){
		return stage;
	}
}
