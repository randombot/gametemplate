package com.randombot.mygame.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.randombot.mygame.MyGame;

public class Packer {

	static String fontName = "dfpop-xhdpi";

	/**
	 * Packs the images and prepares the skin to be used...
	 */
	public static void compile() {

		String ppath = System.getProperty("user.dir");
		ppath = ppath.replace("\\", "/");
		System.out.println("Project path: " + ppath);

		// Auxiliar paths
		File createdDir = new File(ppath+"/design/created");
		File stuffDir = new File(ppath+"/design/created/stuff");
		File skinDir = new File(ppath+"/data/skin");

		// empty tmp folder
		File tmpDir = new File(ppath+"/design/tmp");
		if(!tmpDir.exists())tmpDir.mkdirs();
		System.out.println("Empty tmp folder in " + tmpDir.getAbsolutePath());
		//boolean res = tmpDir.mkdir();

		// copy created files
		File[] files = createdDir.listFiles();
		for (File f : files){
			File newfile = new File(f.getAbsolutePath().replace("created", "tmp"));
			try { copyFileUsingStream(f, newfile); } catch (IOException e) {}
		}		

		// copy JSON file updating content
		System.out.println("Copy json file in " + tmpDir.getAbsolutePath());
		try {
			File skinSourceFile = new File(stuffDir.getCanonicalPath()+"/holo.json");
			Reader r = new FileReader(skinSourceFile);
			File skinDestFile = new File(skinDir.getAbsolutePath()+"/skin.json");
			Writer w = new FileWriter(skinDestFile);
			BufferedReader br = new BufferedReader(r);		

			ArrayList<String> icNames = new ArrayList<String>();
			String name;
			System.out.println("Reading icon files from " + tmpDir.getAbsolutePath());			
			File[] files2 = tmpDir.listFiles();
			for (File f : files2){
				File newfile = new File(f.getAbsolutePath());
				name = newfile.getName().replace(".png", "");
				if (name.contains("ic_")){
					icNames.add(name);
				}
			}

			System.out.println("Modifyng json file in " + skinDir.getAbsolutePath()+"/skin.json");
			try {
				String line = br.readLine();
				while (line != null) {
					if (line.contains("<icon>")){		
						for (int i = 0; i < icNames.size(); ++i){
							String aux = line.replace("<icon>", icNames.get(i));						
							w.append(aux + "\n");	
						}
					} else {
						w.append(line + "\n");	
					}
					line = br.readLine();		
				}
				br.close();
				w.close();
			} catch (IOException e) {
				e.printStackTrace();
			}			

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			System.out.println("Copy fonts");

			File fontSourceFile = new File(stuffDir.getCanonicalPath()+"/"+fontName+".fnt");
			File fontDestFile = new File(skinDir.getCanonicalPath()+"/"+fontName+".fnt");
			copyFileUsingStream(fontSourceFile, fontDestFile);

			fontSourceFile = new File(stuffDir.getCanonicalPath()+"/"+fontName+".png");
			fontDestFile = new File(tmpDir.getCanonicalPath()+"/"+fontName+".png");
			copyFileUsingStream(fontSourceFile, fontDestFile);

		} catch (IOException e) { }


		Settings set = new TexturePacker.Settings();
		set.filterMag = TextureFilter.Linear;
		set.filterMin = TextureFilter.Linear;
		set.pot = false;
		set.maxHeight = 1024;
		set.maxWidth = 1024;
		set.paddingX = 2;
		set.paddingY = 2;		

		System.out.println("TexturePacker");
		try {
			TexturePacker.process(set, tmpDir.getCanonicalPath(), skinDir.getCanonicalPath()+"/", "skin");
		} catch (IOException e) { }

		// Copy result to Android Project ?		
		File[] finalfiles = skinDir.listFiles();
		for (File f : finalfiles){
			File newfile = new File(f.getAbsolutePath().replace(MyGame.GAME_NAME, MyGame.GAME_NAME+"-android/assets/"));
			try { copyFileUsingStream(f, newfile); } catch (IOException e) {}
		}

		// Remove tmp directory
		System.out.println("Removing Tmp");
		removeDirectory(tmpDir);
	}

	private static void copyFileUsingStream(File source, File dest) throws IOException {
		System.out.println("Copy from " + source.getAbsolutePath() + " to " + dest.getAbsolutePath());
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} finally {
			if (is != null) is.close();
			if (os != null) os.close();
		}
	}

	public static boolean removeDirectory(File directory) {
		if (directory == null)
			return false;
		if (!directory.exists())
			return true;
		if (!directory.isDirectory())
			return false;

		String[] list = directory.list();
		if (list != null) {
			for (int i = 0; i < list.length; i++) {
				File entry = new File(directory, list[i]);
				if (entry.isDirectory())
				{
					if (!removeDirectory(entry))
						return false;
				}
				else
				{
					if (!entry.delete())
						return false;
				}
			}
		}

		return directory.delete();
	}

}
