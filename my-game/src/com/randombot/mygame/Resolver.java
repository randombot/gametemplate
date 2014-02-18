package com.randombot.mygame;

public interface Resolver {
	
	int SHOW_URI = 1, SHARE = 3, SWARM = 4;	
	int SHOW_URI_FACEBOOK = 10; 
	int SHOW_URI_TWITTER = 11; 
	int SHOW_URI_RANDOMBOT = 12; 
	int SHOW_URI_MARKET = 13; 
	
	void resolve(int which, int args);
	
}
