package com.randombot.mygame;

public interface Resolver {
	
	int SHOW_URI = 1, SHARE = 3, SWARM = 4;	
	
	void resolve(int which, int ... args);
	
}
