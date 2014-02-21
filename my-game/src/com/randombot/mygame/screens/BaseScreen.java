package com.randombot.mygame.screens;

import java.awt.Point;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.PixelInterleavedSampleModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
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
	
	public Stage getStage(){
		return stage;
	}

	public void pause() { }

	public void resume() { }	

	public void dispose() { }

	public void onBackPressed(){
		game.changeScreen(previousScreen, Fade.init(1f, MathUtils.randomBoolean()));
	}

	private final int[] RGBA_OFFSETS = { 0, 1, 2, 3 };
	private final int[] RGB_OFFSETS = { 0, 1, 2 };
	private String androidDir = null;
	private int photoNumber = 0;

	public void saveScreenshot() throws IOException {
		if (androidDir == null){
			String ppath = System.getProperty("user.dir");
			androidDir = ppath.replace("\\", "/").replace(MyGame.GAME_NAME, MyGame.GAME_NAME+"-android");			
			androidDir += "/market/";
		}
		++photoNumber;
		new File(androidDir).mkdirs();
		File createTempFile = new File(androidDir+photoNumber+".png");
		saveScreenshot(createTempFile);
	}

	private void saveScreenshot(File file) throws IOException {
		saveScreenshot(file, false);
	}

	private void saveScreenshot(File file, boolean hasAlpha) throws IOException {
		if (Gdx.app.getType() == ApplicationType.Android) return;

		byte[] screenshotPixels = ScreenUtils.getFrameBufferPixels(true);

		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

		saveScreenshot(file, screenshotPixels, width, height, hasAlpha);
	}

	private void saveScreenshot(File file, byte[] pixels, int width, int height, boolean hasAlpha) throws IOException {
		DataBufferByte dataBuffer = new DataBufferByte(pixels, pixels.length);
		PixelInterleavedSampleModel sampleModel = new PixelInterleavedSampleModel(
				DataBuffer.TYPE_BYTE, width, height, 4, 4 * width, getOffsets(hasAlpha));
		WritableRaster raster = Raster.createWritableRaster(sampleModel, dataBuffer, new Point(0, 0));
		BufferedImage img = new BufferedImage(getColorModel(hasAlpha), raster, false, null);
		ImageIO.write(img, "png", file);
	}

	private ColorModel getColorModel(boolean alpha) {
		if (alpha)
			return new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8, 8 }, true, false, ComponentColorModel.TRANSLUCENT, DataBuffer.TYPE_BYTE);
		return new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] { 8, 8, 8 }, false, false, ComponentColorModel.OPAQUE, DataBuffer.TYPE_BYTE);
	}

	private int[] getOffsets(boolean alpha) {
		if (alpha)
			return RGBA_OFFSETS;
		return RGB_OFFSETS;
	}

}
