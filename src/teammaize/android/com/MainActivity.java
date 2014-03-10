package teammaize.android.com;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import ask.scanninglibrary.ASKActivity;

public class MainActivity extends ASKActivity {

	private String input_subject;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	try {
    		super.onCreate(savedInstanceState);
    		setContentView(R.layout.activity_main);
    		
    		//Up button should not appear on the home page (cannot navigate 'up' to the home screen
    		getActionBar().hide();
    		
    		Button settingsButton = (Button) findViewById(R.id.settingsButton);
    		settingsButton.setOnClickListener(new View.OnClickListener() {
    			public void onClick (View v){
    			Intent settingsIntent = new Intent(MainActivity.this, settingsMenu.class);
    			startActivity(settingsIntent);
    			}
    		});
    		
    		Button startButton = (Button) findViewById(R.id.startButton);
    		
    		startButton.setOnClickListener(new View.OnClickListener() {
    			public void onClick (View p){
    			
    				//Initiating the maze
    	    		Intent intent = new Intent(MainActivity.this, MazeGUI.class);
    	    		
    	    		/*if(input_subject.isEmpty()) {
    	    			intent.putExtra("subject", "All");
    	    		}
    	    		else {
    	    			intent.putExtra("subject", input_subject);
    	    		}*/
    	    		
    	    		intent.putExtra("subject", "All");
    	    		
    	    		startActivity(intent);
    			}
    		});
    		  		  		
    		
    		
    		Log.v("MainActivity", "Initialize start button click events");
    	}
    	catch (Exception e) {
    		Log.v("MainActivity", "Exception thown in onCreate: " + e.toString());
    	}
    }

}
