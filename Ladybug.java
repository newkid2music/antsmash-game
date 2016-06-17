package com.example.hm5_lewis;

import android.graphics.Canvas;

public class Ladybug {

	// States of a Bug
	enum LadyBugState {
	  Dead,	 
	  ComingBackToLife,		
	  Alive, 			// in the game
	  DrawDead,			// draw dead body on screen
	};
	
	LadyBugState state;			// current state of bug	
	int x,y;				// location on screen (in screen coordinates)
	double speed;			// speed of bug (in pixels per second)
	// All times are in seconds
	float timeToBirth;		// # seconds till birth
	float startBirthTimer;	// starting timestamp when decide to be born
	float deathTime;		// time of death
	float animateTimer;		// used to move and animate the bug
	
	// Bug starts not alive
	public Ladybug () {
		state = LadyBugState.Dead;
	}
	
	// Bug birth processing
	public void birth (Canvas canvas) {
		// Bring a bug to life?
		if (state == LadyBugState.Dead) {
			// Set it to coming alive
			state = LadyBugState.ComingBackToLife;
			// Set a random number of seconds before it comes to life
			timeToBirth = (float)Math.random () * 5;
			// Note the current time
			startBirthTimer = System.nanoTime() / 1000000000f;
		}
		// Check if bug is alive yet
		else if (state == LadyBugState.ComingBackToLife) {
			float curTime = System.nanoTime() / 1000000000f;
			// Has birth timer expired?
			if (curTime - startBirthTimer >= timeToBirth) {
				// If so, then bring bug to life
				state = LadyBugState.Alive;
				// Set bug starting location at top of screen
				x = (int)(Math.random() * canvas.getWidth());
				// Keep entire bug on screen
				if (x < Assets.lbug.getWidth()/2)
					x = Assets.lbug.getWidth()/2;
				else if (x > canvas.getWidth() - Assets.lbug.getWidth()/2)
					x = canvas.getWidth() - Assets.lbug.getWidth()/2;
				y = 0;
				// Set speed of this bug
				speed = canvas.getHeight() / 4; // no faster than 1/4 a screen per second
				// subtract a random amount off of this so some bugs are a little slower
				// ADD CODE HERE
				// Record timestamp of this bug being born
				animateTimer = curTime;
			}
		}	
	}
	
	// Bug movement processing
	public void move (Canvas canvas) {
		// Make sure this bug is alive
		if (state == LadyBugState.Alive) {
			// Get elapsed time since last call here
			float curTime = System.nanoTime() / 1000000000f;
			float elapsedTime = curTime - animateTimer;
			animateTimer = curTime;
			// Compute the amount of pixels to move (vertically down the screen)
			y += (speed * elapsedTime);
			// Draw bug on screen
			canvas.drawBitmap(Assets.ant, x,  y, null);
			// Has it reached the bottom of the screen?
			if (y >= canvas.getHeight()) {
				// Kill the bug
				state = LadyBugState.Dead;
				// Subtract 1 life
				Assets.livesLeft--;
			}
		}
	}
	
	// Process touch to see if kills bug - return true if bug killed
	public boolean touched (Canvas canvas, int touchx, int touchy) {
		boolean touched = false;
		// Make sure this bug is alive
		if (state == LadyBugState.Alive) {
			// Compute distance between touch and center of bug
			float dis = (float)(Math.sqrt ((touchx - x) * (touchx - x) + (touchy - y) * (touchy - y)));
			// Is this close enough for a kill?
			if (dis <= Assets.ant.getWidth()*0.75f) {
				state = LadyBugState.DrawDead;	// need to draw dead body on screen for a while
				touched = true;		
				// Record time of death
				deathTime = System.nanoTime() / 1000000000f;
				
			}
		}	
		return (touched);
	}
	
	// Draw dead bug body on screen, if needed
	public void drawDead (Canvas canvas) {
		if (state == LadyBugState.DrawDead) {
			canvas.drawBitmap(Assets.ant3, x,  y, null);			
			// Get time since death
			float curTime = System.nanoTime() / 1000000000f;
			float timeSinceDeath = curTime - deathTime;
			// Drawn dead body long enough (4 seconds) ?
			if (timeSinceDeath > 4)
				state = LadyBugState.Dead;
		}
	}
	
}
