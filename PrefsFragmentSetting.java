/*___________________________________________________________________
|
| File: PrefsFragmentSettings.java
|
| Functions:	PrefsFragmentSettings (constructor)
|				onCreate
|				onResume
|
| Copyright 2015 Abonvita Software LLC.
|__________________________________________________________________*/
package com.example.hm5_lewis;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnClickListener;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.Preference.OnPreferenceClickListener;
 
/*___________________________________________________________________
|
| Class: PrefsFragmentSettings
|__________________________________________________________________*/
public class PrefsFragmentSetting extends PreferenceFragment implements OnSharedPreferenceChangeListener 
{
    final static String TAG = "PrefsFragmentSettings";
    
	/*___________________________________________________________________
	|
	| Function: PrefsFragmentSettings (constructor) 
	|__________________________________________________________________*/
    public PrefsFragmentSetting () 
    { 
    }
    
	/*___________________________________________________________________
	|
	| Function: onCreate 
	|__________________________________________________________________*/
	@Override
    public void onCreate (Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);   	
    	// Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.prefs_fragment_settings);
    }
	
	//@Override
	//public void onDestroyView() {
	//    super.onDestroyView();
	//    Intent myIntent = new Intent(getActivity(), MainActivity.class);
    //    getActivity().startActivity(myIntent);
	//}
	
	/*___________________________________________________________________
	|
	| Function: onResume 
	|__________________________________________________________________*/
    @Override
    public void onResume() 
    {
    	super.onResume();
    	
    	getView().setFocusableInTouchMode(true);
        getView().requestFocus();
       // getView().setOnKeyListener(new View.OnKeyListener() {
         //   @Override
          //  public boolean onKey(View v, int keyCode, KeyEvent event) {

           //     if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    // handle back button's click listener
            //        return true;
            //    }
            //    return false;
       //     }  
       // });

	    // Set up a listener whenever a key changes 
	    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this); 

    	// Set up a click listener
    	Preference pref;
    	pref = getPreferenceScreen().findPreference("key_company_info");
    	pref.setSummary("                  " + Assets.highscore);
	    /*pref.setOnPreferenceClickListener(new OnPreferenceClickListener() { 
	        public boolean onPreferenceClick (Preference preference) { 
	        	// Handle action on click here 
	        	try {
	   				Uri site = Uri.parse("http://education.nationalgeographic.com/education/encyclopedia/zoo/?ar_a=1");
	   		        Intent myIntent = new Intent(Intent.ACTION_VIEW, site); 
	   		        startActivity(myIntent);
	   			}
	   			catch (Exception e) {
//	   				Log.e (TAG, "Browser failed", e);
	   			}
	   			return (true);
	        }
	    });    	*/	
    }

	/*___________________________________________________________________
	|
	| Function: onSharedPreferenceChanged 
	|__________________________________________________________________*/
	public void onSharedPreferenceChanged (SharedPreferences sharedPreferences, String key)  
	{ 	
		if (key.equals("prefs_music_enabled")) {		
			boolean b = sharedPreferences.getBoolean("prefs_music_enabled", true);
			if (b == false) {
				if (Assets.mp != null)
					Assets.mp.setVolume(0, 0);
			}
			else {
				if (Assets.mp != null)
					Assets.mp.setVolume(1, 1);				
			}
		}
	}
}
