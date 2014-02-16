package com.randombot.mygame.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class Help extends BaseScreen {
	
	final String instructionsText = "\n\nGame Instructions\n Lorem Ipsum es simplemente el texto de relleno de " +
			"las imprentas y archivos de texto. Lorem Ipsum ha sido el texto " +
			"de relleno est�ndar de las industrias desde el a�o 1500, cuando un " +
			"impresor (N. del T. persona que se dedica a la imprenta) desconocido." +
			
			
			"\n\nGame Objectives\n us� una galer�a de textos y los mezcl� de tal manera que logr� hacer " +
			"un libro de textos especimen. No s�lo sobrevivi� 500 a�os, sino que" +
			" tambien ingres� como texto de relleno en documentos electr�nicos, " +
			"quedando esencialmente igual al original. Fue popularizado en los 60s " +
			"con la creaci�n de las hojas.";
	
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
