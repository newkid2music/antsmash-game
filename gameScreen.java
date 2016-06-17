package com.example.hm5_lewis;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class gameScreen extends Activity {
	PrefsScreen v;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Disable the title
		requestWindowFeature (Window.FEATURE_NO_TITLE);
		// Make full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// Start the view
		v = new PrefsScreen(this);
		setContentView(v);
	}
	
	@Override
	protected void onPause () {
		super.onPause();
		v.pause();
	}

	@Override
	protected void onResume () {
		super.onResume();
		v.resume();
	}
}

