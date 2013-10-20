package teammaize.android.com;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.support.v4.app.NavUtils;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.XmlResourceParser;
import android.view.*;
import android.widget.Button;
import android.widget.GridLayout;

public class MazeGUI extends Activity {
	
	int m, n;
	char[][] mazeTextArray;
	
	private MazeGeneration mazeObject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_maze_gui);
			// Show the Up button in the action bar.
			setupActionBar();

			mazeObject = new MazeGeneration(m, n);
		
		//mazeObject = new MazeGeneration(m, n);
		
		
			GridLayout mazeImage = (GridLayout)findViewById(R.id.mazeImage);
			mazeImage.setColumnCount(m);
			mazeImage.setRowCount(n);
			//Preliminary parameter setting. To be done- cell creation methods in graphicsMapping,
			//artwork for those cells, layering with Erika's movement UI. -Chris, 10/18/2013
		
			this.graphicsMapping(mazeTextArray);
		}
		catch(Exception e) {
			Log.v("MazeGUI", "Exception thown in onCreate: " + e.toString());
		}
		
		try {
			//GridLayout directionGrid = (GridLayout)findViewById(R.id.movementGridLayout);
			Button upButton = (Button) findViewById(R.id.upButton);
			Button downButton = (Button) findViewById(R.id.downButton);
			Button leftButton = (Button) findViewById(R.id.leftButton);
			Button rightButton = (Button) findViewById(R.id.rightButton);
			//button.Attributes.Add("OnClick", "button_Clicked");
		
			//Initialize up button on click listener
			upButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Log.v("MazeActivity", "North Button View");
					Button buttonView = (Button) v;
					System.out.println(buttonView.getText().toString());
					if(UserMovement.tryMoveNorth(mazeObject)) {
						mazeObject = UserMovement.movePlayerNorth(mazeObject, v, MazeGUI.this);
						System.out.println("Move succesful");
					}
					else {
						System.out.println("Invalid move");
					}
					//TODO: call method to update UI?
					buttonView.setText("U");
				}
			});
			
			// Initialize down button on click listener
			downButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Log.v("MazeActivity", "South Button View");
					Button buttonView = (Button) v;
					System.out.println(buttonView.getText().toString());
					if(UserMovement.tryMoveSouth(mazeObject)) {
						mazeObject = UserMovement.movePlayerSouth(mazeObject, v, MazeGUI.this);
						System.out.println("Move succesful");
					}
					else {
						System.out.println("Invalid move");
					}
					//TODO: call method to update UI?
					buttonView.setText("D");				
				}
			});
			
			// Initialize left button on click listener
			leftButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Log.v("MazeActivity", "West Button View");
					Button buttonView = (Button) v;
					System.out.println(buttonView.getText().toString());
					if(UserMovement.tryMoveWest(mazeObject)) {
						mazeObject = UserMovement.movePlayerWest(mazeObject, v, MazeGUI.this);
						System.out.println("Move succesful");
					}
					else {
						System.out.println("Invalid move");
					}
					//TODO: call method to update UI?
					buttonView.setText("L");				
				}
			});
			
			// Initialize right button on click listener
			rightButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Log.v("MazeActivity", "East Button View");
					Button buttonView = (Button) v;
					System.out.println(buttonView.getText().toString());
					if(UserMovement.tryMoveEast(mazeObject)) {
						mazeObject = UserMovement.movePlayerEast(mazeObject, v, MazeGUI.this);
						System.out.println("Move succesful");
					}
					else {
						System.out.println("Invalid move");
					}
					//TODO: call method to update UI?
					buttonView.setText("R");				
				}
			});
		}
		catch(Exception e) {
			Log.v("MazeGUI", "Exception thown in onCreate onClickListeners: " + e.toString());
		}
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maze_gui, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void graphicsMapping(char[][] textArray)
	{
		//Routine to be implemented -Chris
	}
	
	public void roadBlockEnc (View view) {
    	//Initiating the roadblock intent
    	Intent intent = new Intent(MazeGUI.this, RoadBlock.class);
    	String q = new String();
    	
    	/* Not working, no way to get the path
    	//getting questions
    	String fileL = new String();
    	fileL = "android.resource://" + getPackageName() + "/"+R.raw.sample;
    	QuestionParser qParser = new QuestionParser();
    	qParser.parseXML(fileL);
    	
    	//----Put Extra Testing----
    	//instantiating string variables (unnecessary but just for kicks)
    	String q, a, wa1, wa2, wa3;
    	q = new String();
    	ArrayList<dbEntry> qList = qParser.getQuestionList();
    	dbEntry dbE = qList.get(0);
    	
    	//assign q variable to the question text
    	q = dbE.getQ();    	
    	
    	//add questions class to intent here
    	//intent.putExtra("question", q);
    	 
    	*/
    	
    	
    	try { //This way works if we're using the assets folder
    		
    		String title = "Start Asset Method";
    		String deco = "--------------------";    		
    		System.out.println(deco + title + deco);
    		
    		//get the asset manager
    		AssetManager assetM = getAssets();
    		//String dirList[] = assetM.list(""); //files in list
    		
    		//open file
    		InputStream inputStream = null;
        	inputStream = assetM.open("sample.xml");
        	
        	//variable to store the contents of the file
            byte[] reader = new byte[inputStream.available()];
            
            //reads the whole file
            while (inputStream.read(reader) != -1) {}
            
            //stores the contents of the file into q
    		String ex = new String(reader);
    		System.out.println(ex);
    		
    		title = "End Assets Method";
    		System.out.println(deco + title + deco);    		
    		inputStream.close();
    		
    	} catch(Exception e) {
    		System.out.println("ERMEGERD");
    	}
    	
    	    	    	
    	//Method if we use the resource raw method
    	try {
    		String title = "Start Resource Method";
    		String deco = "--------------------";
    		String decoLine = deco + title + deco;
    		System.out.println(deco + title + deco);    		
    		
    		//opens the file into an input stream using the resource id
    		InputStream inputStream = null;
        	inputStream = getResources().openRawResource(R.raw.sample2);
        	
        	//variable to store the contents of the file
            byte[] reader = new byte[inputStream.available()];
            
            //reads the whole file
            while (inputStream.read(reader) != -1) {}
            
            //stores the contents of the file into q
    		String ex = new String(reader);
    		System.out.println(ex);
    		
    		title = "End Resource Method";
    		System.out.println(deco + title + deco);
    		inputStream.close();
    		
    	} catch(Exception e) {
    		Log.e("Eerrrrrr.....", e.getMessage());
    	}    	
    	
    	//Default
    	q = "Question";
    	
    	//Adding q to the intent
    	intent.putExtra("question", q);
    	
    	//switch to the roadblock activity
    	startActivityForResult(intent, 10); //10 is arbitrary, can be anything
    }
	
	//Closes activity
	public void closeActivity(View view) {
		//return to the previous activity with no results intent
		finish();
	}

	//Handles the results from the roadblock
	protected void onActivityResult(int requestCode, int resultCode,
	          Intent data) {
		//Request code 10 is arbitrary
		if (requestCode == 10){
			if (resultCode == RESULT_OK){
				//Doing something with the value
	            String ans = data.getStringExtra("answer"); 
	            
	            //Printing result to console
	            System.out.println(ans);
			}
		}
	}
}


