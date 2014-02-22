package com.randombot.mygame.screens;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.randombot.mygame.MyGame;

public class MarketUtils {
	
	private static String androidDir = null;
	private static int photoNumber = 0;
	
	public static void setUp(){
		BaseScreen.getStage().addListener(new InputListener(){
			@Override
			public boolean keyDown(InputEvent event, int keycode) {

				if (keycode == Keys.ENTER){
					try {
						saveScreenshot();
					} catch (IOException e) { 
						e.printStackTrace();
					}
				} else if (keycode == Keys.SPACE){
					
					
					
					
				}				
				return true;
			}
		});		
	}

	private static void saveScreenshot() throws IOException {
		if (androidDir == null){
			String ppath = System.getProperty("user.dir");
			androidDir = ppath.replace("\\", "/").replace(MyGame.GAME_NAME, MyGame.GAME_NAME+"-android");			
			androidDir += "/market/";
		}
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();				
		final Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Format.RGBA8888);
		ByteBuffer pixels = pixmap.getPixels();
		Gdx.gl.glReadPixels(0, 0, w, h, GL20.GL_RGBA, GL20.GL_UNSIGNED_BYTE, pixels);
		final int numBytes = w * h * 4;
		byte[] lines = new byte[numBytes];
		final int numBytesPerLine = w * 4;
		for (int i = 0; i < h; i++) {
			pixels.position((h - i - 1) * numBytesPerLine);
			pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
		}
		pixels.clear();
		pixels.put(lines);
		System.out.println("Captured.");
		PixmapIO.writePNG(new FileHandle(androidDir+(++photoNumber)+".png"), pixmap);
	}
	
}
