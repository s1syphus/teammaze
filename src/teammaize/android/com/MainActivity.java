package teammaize.android.com;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	try {
    		super.onCreate(savedInstanceState);
    		setContentView(R.layout.activity_main);
    	
    		Log.v("MainActivity", "Initialize start button click events");
    		Button startButton = (Button) findViewById(R.id.upButton);
    		startButton.setOnClickListener(new OnClickListener() {
    			public void onClick(View v) {
    				mazeStart(v);
    			}
    		});
    	}
    	catch (Exception e) {
    		Log.v("MainActivity", "Exception thown in onCreate: " + e.toString());
    	}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	try {
    		// Inflate the menu; this adds items to the action bar if it is present.
    		getMenuInflater().inflate(R.menu.main, menu);
    		return true;
    	}
    	catch(Exception e) {
    		Log.v("MainActivity", "Exception thrown onCreateOptionsMenu: " + e.toString());
    		return false;
    	}
    }
    
    public void mazeStart (View view) {
    	try {
    		//Initiating the maze
    		Intent intent = new Intent(MainActivity.this, MazeGUI.class);
    		//add extra to intent here
    		startActivity(intent);
    	}
    	catch(Exception e) {
    		Log.v("MainActivity", "Exception thown in mazeStart: " + e.toString());
    	}
    }
    
    
    
}
