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
import android.widget.ImageView;

public class MazeGUI extends Activity {
	
	//These are only sample values.
	int m = 10, n = 10;
	
	private MazeGeneration mazeObject;
	private int[][] idArray;
	private GridLayout mazeImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_maze_gui);
			// Show the Up button in the action bar.
			setupActionBar();

			mazeObject = new MazeGeneration(m, n);
			idArray = new int[m][n];
		
			mazeImage = (GridLayout)findViewById(R.id.mazeImage);
			mazeImage.setColumnCount(m);
			mazeImage.setRowCount(n);
			mazeImage.setColumnOrderPreserved(true);
			mazeImage.setRowOrderPreserved(true);
			//Preliminary parameter setting. To be done- cell creation methods in graphicsMapping,
			//artwork for those cells, layering with Erika's movement UI. -Chris, 10/18/2013
			 
			this.graphicsMapping(mazeObject.maze);
		}
		catch(Exception e) {
			Log.v("MazeGUI", "Exception thown in onCreate: " + e.toString());
		}
		
		try {
			//Here is where  I altered the Button bindings. -Chris, 10/20/2013
			//GridLayout directionGrid = (GridLayout)findViewById(R.id.movementGridLayout);
			Button upButton = (Button) findViewById(R.id.leftButton);
			Button downButton = (Button) findViewById(R.id.rightButton);
			Button leftButton = (Button) findViewById(R.id.upButton);
			Button rightButton = (Button) findViewById(R.id.downButton);
			//button.Attributes.Add("OnClick", "button_Clicked");
		
			//Initialize up button on click listener
			upButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Log.v("MazeActivity", "North Button View");
					Button buttonView = (Button) v;
					System.out.println(buttonView.getText().toString());
					if(UserMovement.tryMoveNorth(mazeObject)) {
						mazeObject = UserMovement.movePlayerNorth(mazeObject, v, MazeGUI.this);
						
						//Testing Cell Updates - Chris
						System.out.println("Cur Coords- First: " + (mazeObject.user_coords.first) + " Second: " + mazeObject.user_coords.second);
						System.out.println("Last Coords- First: " + (mazeObject.user_coords.first) + " Second: " + (mazeObject.user_coords.second + 1));
						
						ImageView lastCell = (ImageView) findViewById(idArray[mazeObject.user_coords.first][mazeObject.user_coords.second + 1]);
						lastCell.setImageResource(0);
						
						ImageView currentCell = (ImageView) findViewById(idArray[mazeObject.user_coords.first][mazeObject.user_coords.second]);
						currentCell.setImageResource(R.drawable.player_graphic);
						//Testing End
						

						System.out.println("Move succesful");
					}
					else {
						System.out.println("Invalid move");
					}
					
					//TODO: call method to update UI?
					//buttonView.setText("U");
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
						
						//Added block -Chris
						System.out.println("Cur Coords- First: " + (mazeObject.user_coords.first) + " Second: " + mazeObject.user_coords.second);
						System.out.println("Last Coords- First: " + (mazeObject.user_coords.first) + " Second: " + (mazeObject.user_coords.second - 1));
						
						ImageView lastCell = (ImageView) findViewById(idArray[mazeObject.user_coords.first][mazeObject.user_coords.second - 1]);
						lastCell.setImageResource(0);
						
						ImageView currentCell = (ImageView) findViewById(idArray[mazeObject.user_coords.first][mazeObject.user_coords.second]);
						currentCell.setImageResource(R.drawable.player_graphic);
						//
						
						System.out.println("Move succesful");
					}
					else {
						System.out.println("Invalid move");
					}
					
					//TODO: call method to update UI?
					//buttonView.setText("D");				
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
						
						//Added block -Chris
						System.out.println("Cur Coords- First: " + (mazeObject.user_coords.first) + " Second: " + mazeObject.user_coords.second);
						System.out.println("Last Coords- First: " + (mazeObject.user_coords.first + 1) + " Second: " + (mazeObject.user_coords.second));
						
						ImageView lastCell = (ImageView) findViewById(idArray[mazeObject.user_coords.first + 1][mazeObject.user_coords.second]);
						lastCell.setImageResource(0);
						
						ImageView currentCell = (ImageView) findViewById(idArray[mazeObject.user_coords.first][mazeObject.user_coords.second]);
						currentCell.setImageResource(R.drawable.player_graphic);
						//
						
						System.out.println("Move succesful");
					}
					else {
						System.out.println("Invalid move");
					}
					
					//TODO: call method to update UI?
					//buttonView.setText("L");				
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
						
						//Added block -Chris
						System.out.println("Cur Coords- First: " + (mazeObject.user_coords.first) + " Second: " + mazeObject.user_coords.second);
						System.out.println("Last Coords- First: " + (mazeObject.user_coords.first - 1) + " Second: " + (mazeObject.user_coords.second));
						
						ImageView lastCell = (ImageView) findViewById(idArray[mazeObject.user_coords.first - 1][mazeObject.user_coords.second]);
						lastCell.setImageResource(0);
						
						
						ImageView currentCell = (ImageView) findViewById(idArray[mazeObject.user_coords.first][mazeObject.user_coords.second]);
						currentCell.setImageResource(R.drawable.player_graphic);
						//
						
						System.out.println("Move succesful");
					}
					else {
						System.out.println("Invalid move");
					}
					
					//TODO: call method to update UI?
					//buttonView.setText("R");				
				}
			});
		}
		catch(Exception e) {
			Log.v("MazeGUI", "Exception thown in onCreate onClickListeners: " + e.toString());
		}
		
		OnClickListener clickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.v("MazeActivity", "Button View");
				Button buttonView = (Button)v;
				System.out.println("Button " + buttonView.getText().toString() + " pressed");
				//TODO: change logic to check the direction is valid
				// update location in array 
				// call method to update UI? (need to know which direction button was pressed)
				//if (buttonView.getText().toString().isEmpty()) {
					buttonView.setText("X");
				//}
			}
		};
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
		int idCount = 0;
		
		for (int i = 0; i < m; i++)
		{
			for(int j = 0; j < n; j++)
			{
				idArray[i][j] = idCount;
				
				if(textArray[i][j] == 'X')
				{
					ImageView WallGraphic = new ImageView(this);
					WallGraphic.setImageResource(R.drawable.wall_graphic);
					WallGraphic.setId(idCount);

					mazeImage.addView(WallGraphic);
				}
				else if(textArray[i][j] == '.')
				{
					ImageView PathGraphic = new ImageView(this);
					PathGraphic.setBackgroundResource(R.drawable.path_graphic);
					PathGraphic.setId(idCount);
					
					mazeImage.addView(PathGraphic);
				}
				else if(textArray[i][j] == 'R')
				{
					ImageView RoadblockGraphic = new ImageView(this);
					RoadblockGraphic.setBackgroundResource(R.drawable.roadblock_graphic);
					RoadblockGraphic.setId(idCount);
					
					mazeImage.addView(RoadblockGraphic);
				}
				else if(textArray[i][j] == 'S')
				{
					ImageView StartGraphic = new ImageView(this);
					StartGraphic.setBackgroundResource(R.drawable.start_graphic);
					StartGraphic.setImageResource(R.drawable.player_graphic);
					StartGraphic.setId(idCount);
					
					mazeImage.addView(StartGraphic);
				}
				else if(textArray[i][j] == 'G')
				{
					ImageView GoalGraphic = new ImageView(this);
					GoalGraphic.setBackgroundResource(R.drawable.goal_graphic);
					GoalGraphic.setId(idCount);
					
					mazeImage.addView(GoalGraphic);
				}
				
				idCount++;
			}
		}
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
    	q = "What is the Capital of the USA?";
    	String cAns = "Washington DC";
    	String wAns1 = "Alabama";
    	String wAns2 = "California";
    	String wAns3 = "Michigan";
    			
    	
    	//Adding q to the intent
    	intent.putExtra("question", q);
    	intent.putExtra("cAns", cAns);
    	intent.putExtra("wAns1", wAns1);
    	intent.putExtra("wAns2", wAns2);
    	intent.putExtra("wAns3",wAns3);
    	
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


