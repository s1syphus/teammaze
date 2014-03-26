package teammaize.android.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;
import ask.scanninglibrary.Settings;

//public class settingsMenu extends ASKActivity{
public class settingsMenu extends Activity{
	/*
	 * Subjects are in the following format
	 * boolean vector where:
	 * 0 = science
	 * 1 = math
	 * 2 = geography
	 * 3 = literature
	 * 
	 * 
	 * will add more later
	 * 
	 */
	
	int numSubjects = 4;
	boolean[] subjects = new boolean[numSubjects];
	int speed;
	protected void onCreate(Bundle savedInstanceState){
	 	try {
    		super.onCreate(savedInstanceState);
    		setContentView(R.layout.settings_menu);
    	
    	
    		
    		final Button mainButton = (Button) findViewById(R.id.doneSubjectButton);
    		mainButton.setOnClickListener(new OnClickListener(){
    			@Override
    			public void onClick(View v){
    				   				
    				Log.v("settingsMenu", "subjects selected = " + subjects[0] + subjects[1] + subjects[2] + subjects[3] );
    				Log.v("settingsMenu", "speed changed to: " + speed);
    				//make this work, keeps crashing, ask chris mcmeeking
    		//		Settings.setScanningSpeed(settingsMenu.this, speed);
    				
    				Intent data = new Intent();
    				data.putExtra("subjects",subjects);
    				setResult(RESULT_OK, data);
    				finish();
    			}
    			
    		});
    	
    		subjectsSelectionListenerOnButton();
    		askScanSpeedSelection();
    		
    		
    		
    		/*
    		subjectsSelectionListenerOnButton();
    		askScanSpeedSelection();
    		*/
    		/*
    		 * "Done Button"
    		 * This will send the data to the subject selection (when implemented)
    		 */

    		
    		//send all data back...not sure how to do this yet
    		Log.v("settingsMenu", "Initialize toggle buttons for selection");
    	}
    	catch (Exception e) {
    		Log.v("settingsMenu", "Exception thrown in onCreate: " + e.toString());
    	}
	}
	
	
	public void subjectsSelectionListenerOnButton(){
		
		/*
		 Subject toggle buttons
		 */
		
		ToggleButton scienceToggle = (ToggleButton) findViewById(R.id.scienceToggleButton);
		scienceToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					Log.v("settingsMenu", "science button has been checked");
					subjects[0] = true;
					//add to subject string
					
				}
				else{
					subjects[0] = false;
					Log.v("settingsMenu", "science button has not been checked");
				}
			}
		});
		
		ToggleButton mathToggle = (ToggleButton) findViewById(R.id.mathToggleButton);
		mathToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					//add to subject string
					subjects[1] = true;
					Log.v("settingsMenu", "math button has been checked");
				}
				else{
					subjects[1] = false;
					Log.v("settingsMenu", "math button has not been checked");
				}
			}
		});
		
		ToggleButton geographyToggle = (ToggleButton) findViewById(R.id.geographyToggleButton);
		geographyToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					subjects[2] = true;
					Log.v("settingsMenu", "geography button has been checked");
				}
				else{
					subjects[2] = false;
					Log.v("settingsMenu", "geography button has not been checked");
				}
			}
		});
		
		ToggleButton literatureToggle = (ToggleButton) findViewById(R.id.literatureToggleButton);
		literatureToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					subjects[3] = true;
					Log.v("settingsMenu", "literature button has been checked");
				}
				else{
					subjects[3] = false;
					Log.v("settingsMenu", "literature button has not been checked");
				}
			}
		});
		
		
		
		
	}
	
	public void askScanSpeedSelection(){
		SeekBar scanSpeedSeekBar = (SeekBar) findViewById(R.id.scanSpeedSeekBar);
		final TextView seekBarText = (TextView) findViewById(R.id.scanSpeedSeekBarText);
		
		scanSpeedSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
				seekBarText.setText("Speed = "+progress+"ms (or something)");
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar ){
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				speed = seekBar.getProgress()+500;
			}
			
		});
		
	}
	

	

	
	
	
}
