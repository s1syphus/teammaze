package teammaize.android.com;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.support.v4.app.NavUtils;
import android.content.Intent;
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
		
			GridLayout mazeImage = (GridLayout)findViewById(R.id.mazeImage);
			mazeImage.setColumnCount(m);
			mazeImage.setRowCount(n);
			//Preliminary parameter setting. To be done- cell creation methods in graphicsMapping,
			//artwork for those cells, layering with Erika's movement UI. -Chris, 10/18/2013
		
			this.graphicsMapping(mazeTextArray);
		}
		catch(Exception e) {
			System.out.println("Exception thown in MazeActivity onCreate: " + e.toString());
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
					Log.v("MazeActivity", "Up Button View");
					Button buttonView = (Button) v;
					System.out.println(buttonView.getText().toString());
					if(UserMovement.tryMoveUp(mazeObject)) {
						System.out.println("Move succesful");
					}
					else {
						System.out.println("Invalid move");
					}
					//TODO: change logic to check the direction is valid
					// update location in array 
					// call method to update UI?
					buttonView.setText("U");
				}
			});
			
			// Initialize down button on click listener
			downButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Log.v("MazeActivity", "Down Button View");
					Button buttonView = (Button) v;
					System.out.println(buttonView.getText().toString());
					if(UserMovement.tryMoveDown(mazeObject)) {
						System.out.println("Move succesful");
					}
					else {
						System.out.println("Invalid move");
					}
					//TODO: change logic to check the direction is valid
					// update location in array 
					// call method to update UI?
					buttonView.setText("D");				
				}
			});
			
			// Initialize left button on click listener
			leftButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Log.v("MazeActivity", "Left Button View");
					Button buttonView = (Button) v;
					System.out.println(buttonView.getText().toString());
					if(UserMovement.tryMoveLeft(mazeObject)) {
						System.out.println("Move succesful");
					}
					else {
						System.out.println("Invalid move");
					}
					//TODO: change logic to check the direction is valid
					// update location in array 
					// call method to update UI?
					buttonView.setText("L");				
				}
			});
			
			// Initialize right button on click listener
			rightButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Log.v("MazeActivity", "Right Button View");
					Button buttonView = (Button) v;
					System.out.println(buttonView.getText().toString());
					if(UserMovement.tryMoveRight(mazeObject)) {
						System.out.println("Move succesful");
					}
					else {
						System.out.println("Invalid move");
					}
					//TODO: change logic to check the direction is valid
					// update location in array 
					// call method to update UI?
					buttonView.setText("R");				
				}
			});
		}
		catch(Exception e) {
			System.out.println("Exception thown in MazeActivity onCreate onClickListeners: " + e.toString());
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
    	Intent intent = new Intent(this, RoadBlock.class);
    	
    	//getting questions
    	String fileL = new String();
    	fileL = "sample.xml";
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
    	intent.putExtra("question", q);
    	
    	//switch to the roadblock activity
    	startActivity(intent);
    }
	
	public void closeActivity(View view){
		//return to the previous activity with no results intent
		finish();
	}

}
