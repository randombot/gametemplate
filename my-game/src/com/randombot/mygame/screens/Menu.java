package com.randombot.mygame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.randombot.mygame.view.MenuButton;
import com.randombot.mygame.view.ModalDialog;

public class Menu extends BaseScreen {

	private ModalDialog exitDialog;

	@Override
	public void create() {
		super.create();

		final float DEFAULT_DIALOG_PADDING_BOTTON_TOP = 10f;
		exitDialog = new ModalDialog("Exit", skin) {
			protected void result(Object object) {
				if ((Boolean) object) {
					Gdx.app.exit();
				}
			}
		};
		exitDialog.text("Are you sure?")
		.button("EXIT", true).key(Keys.ENTER, true)
		.button("Keep playing", false).key(Keys.BACK, false).key(Keys.BACKSPACE, false);

		Image logo = new Image(skin, "ic_logo");
		logo.setScaling(Scaling.fit);

		final Button play, help, credits, music, sound;
		play = new TextButton("Play", skin);
		help = new TextButton("Help", skin);
		credits = new TextButton("Credits", skin);
		music = new TextButton("Music", skin, "toggle");
		sound = new TextButton("Sound", skin, "toggle");

		Table stuffTable = new Table();
		stuffTable.setFillParent(true);
		stuffTable.defaults().expand().space(DEFAULT_DIALOG_PADDING_BOTTON_TOP*.5f);
		stuffTable.pad(DEFAULT_DIALOG_PADDING_BOTTON_TOP);

		stuffTable.add(logo).colspan(2).padRight(60f).padLeft(60f).padTop(60f);
		stuffTable.row();
		stuffTable.add(play).colspan(2).fill().expand().padRight(100f).padLeft(100f).padTop(100f);		
		stuffTable.row();
		
		Table miniTable = new Table();
		miniTable.pad(50f);
		miniTable.defaults().fill().expand().uniform().space(50f);
		miniTable.add(music);
		miniTable.add(sound);
		miniTable.row();
		miniTable.add(help);
		miniTable.add(credits);		
		stuffTable.add(miniTable).expand().fill();

		root.addActor(stuffTable);

		// Creating the transition listener
		ClickListener mTransitionListener = new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				final BaseScreen next = getNextScreen(event.getListenerActor());
				if (next == null) {
					return;
				}
				exitAnimation(next);
			}

			private BaseScreen getNextScreen(Actor target) {
				BaseScreen next = null;
				if (target == play) {
					next = Menu.play;
				} else if (target == help) {
					next = Menu.help;
				} else if (target == credits) {
					next = Menu.credits;
				}
				return next;
			}
		};
		play.addListener(mTransitionListener);
		help.addListener(mTransitionListener);
		credits.addListener(mTransitionListener);

		// Creating the music/sound listener
		ClickListener mMultimediaListener = new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				final Actor target = event.getListenerActor();
				if (target == music) {
					System.out.println("Change music state");
				} else if (target == sound) {
					System.out.println("Change sound state");
				}
			}
		};
		music.addListener(mMultimediaListener);
		sound.addListener(mMultimediaListener);
	}

	@Override
	public void onBackPressed() {
		if(exitDialog.isVisible()) return;
		exitDialog.show(stage);		
	}		

}
