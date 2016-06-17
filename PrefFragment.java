package com.example.hm5_lewis;

import com.example.hm5_lewis.Bug.BugState;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.Toast;

public class PrefFragment extends Thread {
	private SurfaceHolder holder;
	private Handler handler;		// required for running code in the UI thread
	private boolean isRunning = false;
	Context context;
	Paint paint;
	int score = 0;
	int touchx, touchy;	// x,y of touch event
	boolean touched;	// true if touch happened
	private static final Object lock = new Object(); 

	public PrefFragment (SurfaceHolder surfaceHolder, Context context) {
	   holder = surfaceHolder;
	   this.context = context;
	   handler = new Handler();
	   touched = false;
	}
	
	public void setRunning(boolean b) {
		isRunning = b;	// no need to synchronize this since this is the only line of code to writes this variable
	}
	
	// Set the touch event x,y location and flag indicating a touch has happened
	public void setXY (int x, int y) {
		synchronized (lock) {
			touchx = x;
			touchy = y;
			this.touched = true;
		}
	}
	
	@Override
	public void run() {
		while (isRunning) {
			// Lock the canvas before drawing
			Canvas canvas = holder.lockCanvas();	
			if (canvas != null) {
				// Perform drawing operations on the canvas
				render(canvas);		
				// After drawing, unlock the canvas and display it
				holder.unlockCanvasAndPost (canvas);	
			}
		}
	}
	
	// Loads graphics, etc. used in game
	private void loadData (Canvas canvas) {
		Bitmap bmp;
		int newWidth, newHeight;
		float scaleFactor;
		
		// Create a paint object for drawing vector graphics
		paint = new Paint();
		
		// Load score bar
		// ADD CODE HERE
				bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.wood_texture);
				newHeight = (int)(canvas.getHeight() * 0.1f);
				Assets.scorebar = Bitmap.createScaledBitmap (bmp, canvas.getWidth(), newHeight, false);
				bmp = null;	
				
		// Load food bar
		bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.part_baked_bread);
		// Compute size of bitmap needed (suppose want height = 10% of screen height)
		newHeight = (int)(canvas.getHeight() * 0.1f);
		// Scale it to a new size
		Assets.foodbar = Bitmap.createScaledBitmap (bmp, canvas.getWidth(), newHeight, false);
		// Delete the original
		bmp = null;		
		
		// Load ant 1
		bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.ant);
		// Compute size of bitmap needed (suppose want width = 20% of screen width)
		newWidth = (int)(canvas.getWidth() * 0.2f);
		// What was the scaling factor to get to this?
		scaleFactor = (float)newWidth / bmp.getWidth();
		// Compute the new height
		newHeight = (int)(bmp.getHeight() * scaleFactor);
		// Scale it to a new size
		Assets.ant = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
		// Delete the original
		bmp = null;

		// Load the other bitmaps similarly
		// ...
		// Load ant 2
				bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.ant2);
				// Compute size of bitmap needed (suppose want width = 20% of screen width)
				newWidth = (int)(canvas.getWidth() * 0.2f);
				// What was the scaling factor to get to this?
				scaleFactor = (float)newWidth / bmp.getWidth();
				// Compute the new height
				newHeight = (int)(bmp.getHeight() * scaleFactor);
				// Scale it to a new size
				Assets.ant2 = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
				// Delete the original
				bmp = null;
				
				// Load ladybug
				bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.ladybug);
				// Compute size of bitmap needed (suppose want width = 20% of screen width)
				newWidth = (int)(canvas.getWidth() * 0.2f);
				// What was the scaling factor to get to this?
				scaleFactor = (float)newWidth / bmp.getWidth();
				// Compute the new height
				newHeight = (int)(bmp.getHeight() * scaleFactor);
				// Scale it to a new size
				Assets.lbug = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
				// Delete the original
				bmp = null;
				
		// Load roach3 (dead bug)
		bmp = BitmapFactory.decodeResource (context.getResources(), R.drawable.ant3);
		// Compute size of bitmap needed (suppose want width = 20% of screen width)
		newWidth = (int)(canvas.getWidth() * 0.2f);
		// What was the scaling factor to get to this?
		scaleFactor = (float)newWidth / bmp.getWidth();
		// Compute the new height
		newHeight = (int)(bmp.getHeight() * scaleFactor);
		// Scale it to a new size
		Assets.ant3 = Bitmap.createScaledBitmap (bmp, newWidth, newHeight, false);
		// Delete the original
		bmp = null;
		
		// Create a bug
		for (int n = 0; n<Assets.bug.length; n++) {
		Assets.bug[n] = new Bug();
		Assets.bugs[n] = new Ladybug();
		}
	}

	// Load specific background screen
	private void loadBackground (Canvas canvas, int resId) {
		// Load background
		Bitmap bmp = BitmapFactory.decodeResource (context.getResources(), resId);
		// Scale it to fill entire canvas
		Assets.background = Bitmap.createScaledBitmap (bmp, canvas.getWidth(), canvas.getHeight(), false);
		// Delete the original
		bmp = null;						
	}
	
	private void render (Canvas canvas) {
		int i, x, y;
		
		switch (Assets.state) {
			case GettingReady:
				// Load a special "getting ready screen"
				loadBackground (canvas, R.drawable.scaredperson);
				// Load data and other graphics needed by game
				loadData(canvas);
				// Draw the background screen
				canvas.drawBitmap (Assets.background, 0, 0, null);
				// Play a sound effect
				Assets.soundPool.play(Assets.sound_getready, 1, 1, 1, 0, 1);
				// Start a timer
				Assets.gameTimer = System.nanoTime() / 1000000000f;
				// Go to next state
				Assets.state = Assets.GameState.Starting;
				break;
			case Starting:
				// Draw the background screen
				canvas.drawBitmap (Assets.background, 0, 0, null);
				// Has 3 seconds elapsed?
				float currentTime = System.nanoTime() / 1000000000f;
				if (currentTime - Assets.gameTimer >= 3) {
					// Load game play background
					loadBackground (canvas, R.drawable.seamless_fine_wood_texture);
					// Goto next state
					Assets.state = Assets.GameState.Running;
				}
				break;
			case Running:
				// Draw the background screen
				canvas.drawBitmap (Assets.background, 0, 0, null);
				// Draw the score bar at top of screen
				canvas.drawBitmap (Assets.scorebar, 0, 0, null);
				// ADD CODE HERE
				// Draw the score on the score bar
				// canvas.drawText(....)
				paint.setColor(Color.GREEN);
				paint.setStyle(Style.FILL);
				paint.setTextSize(65);
				canvas.drawText("   " + score , 40, 70, paint);
				
				// Draw the foodbar at bottom of screen
				canvas.drawBitmap (Assets.foodbar, 0, canvas.getHeight()-Assets.foodbar.getHeight(), null);		
				// Draw one circle for each life at top right corner of screen
				// Let circle radius be 5% of width of screen
				int radius = (int)(canvas.getWidth() * 0.05f);
				int spacing = 8; // spacing in between circles
				x = canvas.getWidth() - radius - spacing;	// coordinates for rightmost circle to draw
				y = radius + spacing;
				for (i=0; i<Assets.livesLeft; i++) {
					paint.setColor(Color.GREEN);
					paint.setStyle(Style.FILL);
					canvas.drawCircle(x, y, radius, paint);
					paint.setColor(Color.BLACK);
					paint.setStyle(Style.STROKE);
					canvas.drawCircle(x, y, radius, paint);
					// Reposition to draw the next circle to the left
					x -= (radius*2 + spacing);
				}
				
				for (int n = 0; n<Assets.bug.length; n++) {
				//int n=0;
				// Process a touch
				if (touched) {
					// Set touch flag to false since we are processing this touch now
					touched = false;
					// See if this touch killed a bug
					boolean bugKilled = Assets.bug[n].touched(canvas, touchx, touchy);
					if (bugKilled)  {
						score++;
						Assets.soundPool.play(Assets.sound_squish, 1, 1, 1, 0, 1);
					}
					else {
						Assets.soundPool.play(Assets.sound_thump, 1, 1, 1, 0, 1);
					}
				}
				
					

				// Draw dead bugs on screen
				Assets.bug[n].drawDead(canvas);
				// Move bugs on screen
				Assets.bug[n].move(canvas);
				//Bring a dead bug to life?
				Assets.bug[n].birth(canvas);
				
				Assets.bugs[n].drawDead(canvas);
				// Move bugs on screen
				Assets.bugs[n].move(canvas);
				//Bring a dead bug to life?
				Assets.bugs[n].birth(canvas);
				}
				
				
				// ADD MORE CODE HERE TO PLAY GAME

				
				// Are no lives left?
				if (Assets.livesLeft == 0)
					// Goto next state
					Assets.state = Assets.GameState.GameEnding;
				break;
			case GameEnding:
				if (score > Assets.highscore) {
					Assets.highscore = score; 
					handler.post(new Runnable() {
						public void run() {	
							Toast.makeText(context, "New high score!", Toast.LENGTH_SHORT).show();  
							Assets.soundPool.play(Assets.celebrate, 1, 1, 1, 0, 1);
						}
					});
				}
				score = 0; 
				
				// Show a game over message
				handler.post(new Runnable() {
					public void run() {	
						Toast.makeText(context, "Game Over", Toast.LENGTH_SHORT).show();  	
					}
				});
				// Goto next state
				Assets.state = Assets.GameState.GameOver;
				break;
			case GameOver:
				// Fill the entire canvas' bitmap with 'black'
				canvas.drawColor(Color.BLACK);				
				break;
		}	
	}
}
