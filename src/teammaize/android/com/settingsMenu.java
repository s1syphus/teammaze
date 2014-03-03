package teammaize.android.com;

import java.util.Vector;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ToggleButton;
import ask.scanninglibrary.ASKActivity;

public class settingsMenu extends ASKActivity{

	protected void onCreate(Bundle savedInstanceState){
	 	try {
    		super.onCreate(savedInstanceState);
    		setContentView(R.layout.settings_menu);
    		
    		//"Up" button should not appear on the home page (cannot navigate 'up' to the home screen
    		getActionBar().hide();
   
    		
    		
    		
    		addListenerOnButton();
    		
    		Log.v("MainActivity", "Initialize start button click events");
    	}
    	catch (Exception e) {
    		Log.v("MainActivity", "Exception thown in onCreate: " + e.toString());
    	}
	};
	
	public void addListenerOnButton(){
		
		/*
		 Subject toggle buttons
		 */
		
		Vector<ToggleButton> toggleButtons = new Vector<ToggleButton>();
		toggleButtons.add((ToggleButton) findViewById(R.id.mathToggleButton));
		toggleButtons.add((ToggleButton) findViewById(R.id.scienceToggleButton));
		toggleButtons.add((ToggleButton) findViewById(R.id.geographyToggleButton));
		toggleButtons.add((ToggleButton) findViewById(R.id.literatureToggleButton));
		
		
		
		
		
	}
	
	
	
	
	
}
