package com.example.hm5_lewis;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class Assets extends Activity{
	public static MediaPlayer mp;
	static Bitmap background;
	static Bitmap foodbar;
	static Bitmap scorebar;
	static Bitmap ant;
	static Bitmap ant2;
	static Bitmap ant3;
	static int highscore; 
	static Bitmap lbug;

	// States of the Game Screen
	enum GameState {
	  GettingReady,	// play "get ready" sound and start timer, goto next state 
	  Starting,		// when 3 seconds have elapsed, goto next state
	  Running, 		// play the game, when livesLeft == 0 goto next state
	  GameEnding,	// show game over message
	  GameOver,		// game is over, wait for any Touch and go back to title activity screen
	};
	static GameState state;		// current state of the game
	static float gameTimer;	// in seconds
	static int livesLeft;		// 0-3
	
	static SoundPool soundPool;
	static int sound_getready;
	static int sound_squish;
	static int sound_thump;
	static int backgroundmusic;
	static int celebrate;
	
	static Bug[] bug = new Bug[10]; 
	static Ladybug[]  bugs = new Ladybug[10];
}
