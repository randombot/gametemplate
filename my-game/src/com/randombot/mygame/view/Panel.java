package com.randombot.mygame.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Panel is a generic lightweight container with methods show and hide. Sizes
 * and positions children using table constraints.
 */
public class Panel extends Table {

	private static float FADE_DURATION = .4f;
	private static final String STAGE_BACKGROUND_DEFAULT_DRAWABLE = "dialogDimMediumAlpha";
	
	private Vector2 temp;
	private boolean isModal;
	private Drawable stageBackground;
	private boolean hideOnOutterTouch;

	public Panel(Skin skin) {
		super(skin);
		setBackground("blueBlackMedium");
		this.stageBackground = skin.getDrawable(STAGE_BACKGROUND_DEFAULT_DRAWABLE);
		initialize();
	}
	
	public Panel(Skin skin, String drawableName) {
		super(skin);
		setBackground(drawableName);
		this.stageBackground = skin.getDrawable(STAGE_BACKGROUND_DEFAULT_DRAWABLE);
		initialize();
	}
	
	private void initialize(){
		this.temp = new Vector2();
		hideOnOutterTouch = true;
		isModal = true;
		setTouchable(Touchable.enabled);

		addListener(new InputListener() {
			Rectangle rtmp = new Rectangle();

			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				localToStageCoordinates(/* in/out */temp.set(x, y));
				rtmp.set(getX(), getY(), getWidth(), getHeight());
				if (!rtmp.contains(temp.x, temp.y)) {
					if (hideOnOutterTouch)
						hide();
				}
				return isModal;
			}

			public boolean mouseMoved(InputEvent event, float x, float y) {
				return isModal;
			}

			public boolean scrolled(InputEvent event, float x, float y,
					int amount) {
				return isModal;
			}

			public boolean keyDown(InputEvent event, int keycode) {
				return isModal;
			}

			public boolean keyUp(InputEvent event, int keycode) {
				return isModal;
			}

			public boolean keyTyped(InputEvent event, char character) {
				return isModal;
			}
		});
	}

	public void show() {
		setVisible(true);
		if (FADE_DURATION > 0) {
			getColor().a = 0f;
			addAction(Actions.fadeIn(FADE_DURATION, Interpolation.fade));
		}
	}

	public void hide() {
		if (FADE_DURATION > 0) {
			addAction(Actions.sequence(
					Actions.fadeOut(FADE_DURATION, Interpolation.fade),
					Actions.run(hideRunnable)));
		} else {
			hideRunnable.run();			
		}
	}

	@Override
	protected void drawBackground(Batch batch, float parentAlpha, float x,
			float y) {
		if (stageBackground != null) {
			Color color = getColor();
			batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
			Stage stage = getStage();
			stageBackground.draw(batch, 0, 0, stage.getWidth(),
					stage.getHeight());

		}
		super.drawBackground(batch, parentAlpha, x, y);
	}
	
	public Actor hit(float x, float y, boolean touchable) {
		Actor hit = super.hit(x, y, touchable);
		if ((hit == null && isModal && (!touchable || getTouchable() == Touchable.enabled))) {
			return this;
		}
		return hit;
	}

	public void setModal(boolean isModal) {
		this.isModal = isModal;
	}

	public boolean isModal() {
		return this.isModal;
	}
	
	public void setHideOnOutterTouch(boolean hideOnOutterTouch) {
		this.hideOnOutterTouch = hideOnOutterTouch;
	}	
	
	public void setStageBackground(Drawable stageBackground) {
		this.stageBackground = stageBackground;
	}

	private final Runnable hideRunnable = new Runnable() {
		@Override
		public void run() {
			setVisible(false);
		}
	};
}
