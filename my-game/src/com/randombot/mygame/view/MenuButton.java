package com.randombot.mygame.view;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Scaling;

/**
 * A button displayed in the MainMenu Screen and maybe others.
 */
public class MenuButton extends Button {

	private final float PAD_TOP = 17f, PAD_LEFT = 17f, PAD_BOTTOM = 10f,
			PAD_RIGHT = 17f;
	private Label scene;

	public MenuButton(String name, Skin skin, String iconRegion) {
		super(skin);
		initialize(name, skin, iconRegion);
	}

	private void initialize(String name, Skin skin, String iconRegion) {
		Image sceneIcon = new Image(skin.getRegion(iconRegion));
		sceneIcon.setScaling(Scaling.fit);

		scene = new Label(name, skin);
		scene.setWrap(true);
		scene.setAlignment(Align.center);

		pad(PAD_TOP, PAD_LEFT, PAD_BOTTOM, PAD_RIGHT);
		add(sceneIcon).expand().fill();
		row();
		add(scene).fillX();
		debug();
	}

	@Override
	public float getPrefWidth() {
		// We make sure it's a square
		return Math.max(super.getPrefHeight(), super.getPrefHeight());
	}

	@Override
	public float getPrefHeight() {
		// We make sure it's a square
		return Math.max(super.getPrefHeight(), super.getPrefHeight());
	}
}
