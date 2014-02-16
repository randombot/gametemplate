package com.randombot.mygame;

public interface Resolver {
	
	int SHOW_URI = 1, SHARE = 3, SWARM = 4;	
	int SHOW_URI_FACEBOOK = 10; //if it's not facebook then it's twitter.
	
	void resolve(int which, int args);
	
}
