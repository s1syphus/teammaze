package teammaize.android.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
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
    		
    		
    		Spinner spinner = (Spinner) findViewById(R.id.subjectSpinner);
    		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
    				R.array.subjectsArray, android.R.layout.simple_spinner_item);
    		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    		spinner.setAdapter(adapter);
    		
    		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    		        input_subject = parent.getItemAtPosition(pos).toString();
    		    }
    		    public void onNothingSelected(AdapterView<?> parent) {
    		    }
    		});
    		/*
    		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    	        // An item was selected. You can retrieve the selected item using
    	        input_subject = parent.getItemAtPosition(pos);
    			
    			}
    		});
    		*/
    		
    		Log.v("MainActivity", "Initialize start button click events");
    	}
    	catch (Exception e) {
    		Log.v("MainActivity", "Exception thown in onCreate: " + e.toString());
    	}
    }

/*
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
    */
    
    public void mazeStart (View view) {
    	try {
    		//Initiating the maze
    		Intent intent = new Intent(MainActivity.this, MazeGUI.class);
    		
    		if(input_subject.isEmpty()) {
    			intent.putExtra("subject", "All");
    		}
    		else {
    			intent.putExtra("subject", input_subject);
    		}
    		
    		startActivity(intent);
    	}
    	catch(Exception e) {
    		Log.v("MainActivity", "Exception thown in mazeStart: " + e.toString());
    	}
    }   

}
