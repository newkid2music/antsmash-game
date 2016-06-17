package com.example.hm5_lewis;

import com.example.hm5_lewis.R;

import com.example.hm5_lewis.IncomingCall;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class titleScreen extends Activity implements View.OnClickListener{
	
	ImageButton button;
	ImageButton button2;
	boolean switchToGameScreen;
	
	@Override	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(new MyView(this)); 
		setContentView(R.layout.activity_manifest);
		button =  (ImageButton) findViewById(R.id.imageButton1);
		button2=  (ImageButton) findViewById(R.id.imageButton2);
		button.setOnClickListener(this); 
		button2.setOnClickListener(this);  
	}

	public void onClick(View v) {
  	  if (v == button2) {
  		  //change the image onscreen    
  		startActivity(new Intent(titleScreen.this, gameScreen.class));   
  	  }
	
  	if (v == button) {
		  //change the image onscreen    
		startActivity(new Intent(titleScreen.this, PrefsActivity.class));   
	  }
  	
	  //public class MyView extends View {
		/*Bitmap bmp;
		boolean switchToGameScreen;
		public MyView(Context context) {
			super(context); 
			bmp = BitmapFactory.decodeResource (getResources(), R.drawable.ant_smasher_the_best_free_game);
			switchToGameScreen = false;
		}

		@Override
		protected void onDraw (Canvas canvas) {  
			// Draw the title full screen
			Rect dstRect = new Rect();
            canvas.getClipBounds(dstRect);
            canvas.drawBitmap(bmp, null, dstRect, null);
            // On click switch to main (game) activity
            if (switchToGameScreen) {
            	switchToGameScreen = false;
            	startActivity(new Intent(titleScreen.this, gameScreen.class));
            }
            else
            	invalidate();
        }*/
		
     	/*@Override
    	public boolean onTouchEvent(MotionEvent event) 
    	{
     		// On click set flag to switch to main (game) activity
    		if (event.getAction() == MotionEvent.ACTION_UP)
    			switchToGameScreen = true;	
    		return true; // to indicate we have handled this event
    	}*/
     }
	
	
}
