package com.randombot.mygame.screens.transitions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public interface Transition {
	float getDuration ();
	
	void render (Batch batch, Texture currScreen, Texture nextScreen, float alpha);
}
