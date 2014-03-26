package teammaize.android.com;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import ask.scanninglibrary.ASKActivity;

public class MainActivity extends ASKActivity {

//	private String input_subject;
	
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
    			    			
    			
    			//startActivity(settingsIntent);
    			startActivityForResult(settingsIntent,2);
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
    	    		
    	    		//need to send data from settings menu to here
    	    		
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

    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	if (resultCode == RESULT_OK && requestCode == 2) {
            if (data.hasExtra("subjects")) {
            	boolean[] subjects = data.getExtras().getBooleanArray("subjects");
            	Log.v("MainActivity", "subjects selected = " + subjects[0] + subjects[1] + subjects[2] + subjects[3] );
            }
        }
    	else{
    		Log.v("MainActivity","subjects not returned correctly" );
    	}
	}
    
    
}

