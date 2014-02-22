package com.randombot.mygame.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Background extends Image {
	
	private float width;
	private float x;
	
	private int dir;
	static int LEFT = -1;
	static int RIGHT = 1;
	
	public Background(Skin skin) {
		super(skin, "bg");
		width = BaseScreen.getStage().getWidth()*2f;
		x = 0;
		dir = RIGHT;
		setBounds(x, 0, width, BaseScreen.getStage().getHeight());
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if (dir == RIGHT){
			
			
		} else if (dir == LEFT){
			
		}
		
		
		
		
	}
	
}
