package com.randombot.mygame.screens;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Background extends Image {
	
	private float width, speed;
	
	private int dir;
	static int LEFT = -1;
	static int RIGHT = 1;
	
	public Background(Skin skin) {
		super(skin, "bg");
		width = BaseScreen.getStage().getWidth()*2.5f;
		dir = LEFT;
		speed = MathUtils.random(width/24, width/18);
		setBounds(0, 0, width, BaseScreen.getStage().getHeight());
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if (dir == RIGHT){
			if (getX() > 1){
				dir = LEFT;
			}		
		} else if (dir == LEFT){
			if ((getX()-getStage().getWidth()) < (-getWidth()-1)){
				dir = RIGHT;
			}		
		}		
		
		setX(getX()+speed*dir*delta);		
	}
	
}
