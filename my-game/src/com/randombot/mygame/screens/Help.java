package com.randombot.mygame.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Help extends BaseScreen {
	
	public static final String descriptionTitle = "Game instructions";
	public static final String descriptionText = "Lorem Ipsum es simplemente el texto de relleno de " +
			"las imprentas y archivos de texto. Lorem Ipsum ha sido el texto " +
			"de relleno est�ndar de las industrias desde el a�o 1500, cuando un " +
			" tambien ingres� como texto de relleno en documentos electr�nicos, " +
			"quedando esencialmente igual al original. Fue popularizado en los 60s " +
			"impresor (N. del T. persona que se dedica a la imprenta) desconocido.";
	
	public static final String objectivesTitle = "Game objectives";
	public static final String objectivesText = "Lorem Ipsum us� una galer�a de textos y los mezcl� de tal manera que logr� hacer " +
			"las imprentas y archivos de texto. Lorem Ipsum ha sido el texto " +
			"de relleno est�ndar de las industrias desde el a�o 1500, cuando un " +
			"un libro de textos especimen. No s�lo sobrevivi� 500 a�os, sino que" +
			" tambien ingres� como texto de relleno en documentos electr�nicos, " +
			"quedando esencialmente igual al original. Fue popularizado en los 60s " +
			"con la creaci�n de las hojas.";
	
	@Override
	public void create() {
		super.create();
		previousScreen = menu;
		
		final Label descriptionTitleLabel = new Label(descriptionTitle, skin);
		descriptionTitleLabel.setColor(Color.BLUE);	
		final Label descriptionTextLabel = new Label(descriptionText, skin);
		descriptionTextLabel.setWrap(true);
		
		final Label objectivesTitleLabel = new Label("\n" + objectivesTitle, skin);
		objectivesTitleLabel.setColor(Color.BLUE);	
		final Label objectivesTextLabel = new Label(objectivesText, skin);
		objectivesTextLabel.setWrap(true);

		final Table tableBody = new Table();
		tableBody.defaults().fill().expand();
		tableBody.add(descriptionTitleLabel);
		tableBody.row();
		tableBody.add(descriptionTextLabel);
		tableBody.row();
		tableBody.add(objectivesTitleLabel);
		tableBody.row();
		tableBody.add(objectivesTextLabel);
		
		final ScrollPane scroll = new ScrollPane(tableBody);		
		root.add(scroll).fill().expand();
		root.pad(0f, 30f, 0, 30f);
	}
		
}
