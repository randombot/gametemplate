package com.randombot.mygame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.randombot.mygame.MyGame;
import com.randombot.mygame.Resolver;

public class BaseScreen {

	public static final String TAG = "MyGame";
	
	public static final float screenw = 720;
	public static final float screenh = 1280;

	private static MyGame game;

	public static Stage stage;

	protected static Skin skin;
	protected static Resolver resolver;
	
	public static First first;

	protected BaseScreen previousScreen;
	protected Table root;

	protected void setUpRoot(){
		root = new Table(skin);
		root.setFillParent(true);
		root.setVisible(false);
		stage.addActor(root);
	}
	
	protected void clearColor(){
		GL20 gl20 = Gdx.gl20;
		gl20.glClearColor(0f, 0f, 0f, 0f);
		gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	protected void exitAnimation(final BaseScreen next) {
		stage.addAction(Actions.sequence(
				Actions.fadeOut(.25f, Interpolation.fade),
				Actions.run(new Runnable() {
					@Override
					public void run() {
						game.changeScreen(next);
					}
				})));
	}

	public void render(float delta) { 
		clearColor();
		stage.act(delta);
		stage.draw();
	}

	public void create() {
		setUpRoot();
	}

	public void show() { 
		root.setVisible(true);
		stage.addAction(Actions.sequence(Actions.fadeIn(.25f, Interpolation.fade)));
	}

	public void hide() { 
		root.setVisible(false);
	}	

	public void pause() { }

	public void resume() { }	
	
	public void dispose() { }
	
	public void onBackPressed(){
		exitAnimation(previousScreen);
	}

	public static void initStatics(MyGame myGame, Resolver res){
		skin = new Skin(Gdx.files.internal("data/skin/holo-dark-xhdpi.json"));		
		stage = new Stage(screenw, screenh, true, null);	
		Gdx.input.setInputProcessor(stage);
		resolver = res;
		game = myGame;
		first = new First();
		first.create();
	}

	public static void disposeStatics(){
		stage.dispose();
		first.dispose();
		stage = null;
		game = null;
		resolver = null;
	}
}
