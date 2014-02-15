package com.randombot.mygame;

public interface Resolver {
	
	String SHOW_INTENT = "";	
	
	void resolve(String which, Object... args);
}
