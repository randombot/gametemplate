package com.randombot.mygame.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class Help extends BaseScreen {
	
	final String instructionsText = "\n\nGame Instructions\n Lorem Ipsum es simplemente el texto de relleno de " +
			"las imprentas y archivos de texto. Lorem Ipsum ha sido el texto " +
			"de relleno estándar de las industrias desde el año 1500, cuando un " +
			"impresor (N. del T. persona que se dedica a la imprenta) desconocido." +
			
			
			"\n\nGame Objectives\n usó una galería de textos y los mezcló de tal manera que logró hacer " +
			"un libro de textos especimen. No sólo sobrevivió 500 años, sino que" +
			" tambien ingresó como texto de relleno en documentos electrónicos, " +
			"quedando esencialmente igual al original. Fue popularizado en los 60s " +
			"con la creación de las hojas.";
	
	@Override
	public void create() {
		super.create();
		previousScreen = menu;
		
		final Table stuffTable = new Table(skin);
		stuffTable.setFillParent(true);
		stuffTable.defaults().fill().expand().pad(30f);
		
		final Label instructions = new Label(instructionsText, skin);
		instructions.setFillParent(true);
		instructions.setWrap(true);
		
		final ScrollPane scroll = new ScrollPane(instructions, skin);
		scroll.setFillParent(true);	
		stuffTable.add(scroll);

		root.addActor(stuffTable);
	}
		
}
