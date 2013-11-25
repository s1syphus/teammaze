package teammaize.android.com;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;

import java.io.InputStream;
import java.util.Vector;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import android.support.v4.app.NavUtils;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.XmlResourceParser;
import android.database.SQLException;
import android.view.*;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

public class MazeGUI extends ASKActivity {
	
	//These are only sample values.
	int x = 10, y = 10;
	
	private MazeGeneration mazeObject;
	private int[][] idArray;
	private GridLayout mazeImage;

	private QuestionsDataSource database;
	private ConnectionDetector connection;

	private UserMovement player;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_maze_gui);
			// Show the Up button in the action bar.
			setupActionBar();

			mazeObject = new MazeGeneration(x, y);
			idArray = new int[x][y];
		
			mazeImage = (GridLayout)findViewById(R.id.mazeImage);
			mazeImage.setColumnCount(x);
			mazeImage.setRowCount(y);
			mazeImage.setColumnOrderPreserved(true);
			mazeImage.setRowOrderPreserved(true);
			//Preliminary parameter setting. To be done- cell creation methods in graphicsMapping,
			//artwork for those cells, layering with Erika's movement UI. -Chris, 10/18/2013
			 
			this.graphicsMapping(mazeObject.maze);
			this.player = new UserMovement(new Pair<Integer, Integer>(0, 0));
		}
		catch(Exception e) {
			Log.v("MazeGUI", "Exception thown in onCreate: " + e.toString());
		}
		
		// This is where I request the Connection and parse data
		
		database = new QuestionsDataSource(this);
		database.open();
		
		new RequestTask(database, this).execute("http://67.194.54.19:8888/website_wip/getcsv.php");
		
	
		    
		
		
		try {
			
			Vector<Button> buttons = new Vector<Button>();
			buttons.add((Button) findViewById(R.id.upButton));
			buttons.add((Button) findViewById(R.id.rightButton));
			buttons.add((Button) findViewById(R.id.downButton));
			buttons.add((Button) findViewById(R.id.leftButton));

			//Initialize left button on click listener
			buttons.elementAt(DataStructures.Directions.WEST.ordinal()).setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Log.v("MazeActivity", "West Button View");
					
					if(player.tryMove(mazeObject, DataStructures.Directions.WEST)) {
						player.movePlayer(mazeObject, v, MazeGUI.this, DataStructures.Directions.WEST);
						
						//Update Player location
						updatePlayerGraphic(player.getLastLoc(), player.getCurLoc());
					}
					else {
						System.out.println("Invalid move");
					}
					
				}
			});
			
			// Initialize right button on click listener
			buttons.elementAt(DataStructures.Directions.EAST.ordinal()).setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Log.v("MazeActivity", "East Button View");
					
					if(player.tryMove(mazeObject, DataStructures.Directions.EAST)) {
						player.movePlayer(mazeObject, v, MazeGUI.this, DataStructures.Directions.EAST);
						
						//Update Player location
						updatePlayerGraphic(player.getLastLoc(), player.getCurLoc());
					}
					else {
						System.out.println("Invalid move");
					}
								
				}
			});
			
			// Initialize up button on click listener
			buttons.elementAt(DataStructures.Directions.NORTH.ordinal()).setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Log.v("MazeActivity", "North Button View");
					
					if(player.tryMove(mazeObject, DataStructures.Directions.NORTH)) {
						player.movePlayer(mazeObject, v, MazeGUI.this, DataStructures.Directions.NORTH);
						
						//Update Player location
						updatePlayerGraphic(player.getLastLoc(), player.getCurLoc());
					}
					else {
						System.out.println("Invalid move");
					}
									
				}
			});
			
			// Initialize down button on click listener
			buttons.elementAt(DataStructures.Directions.SOUTH.ordinal()).setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
					Log.v("MazeActivity", "South Button View");
					
					if(player.tryMove(mazeObject, DataStructures.Directions.SOUTH)) {
						player.movePlayer(mazeObject, v, MazeGUI.this, DataStructures.Directions.SOUTH);
						
						//Update player location
						updatePlayerGraphic(player.getLastLoc(), player.getCurLoc());
					}
					else {
						System.out.println("Invalid move");
					}
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
	
	//I don't think it makes sense to have an up button in our app
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
		startActivity(new Intent(MazeGUI.this, MainActivity.class)); 
        return true;
	}
	
	private void updatePlayerGraphic(Pair<Integer, Integer> currentLoc, Pair<Integer, Integer> nextLoc)
	{
		Log.v("MazeGUI", "Redraw Player moved from: " + currentLoc.first + " " + currentLoc.second
				+ " to " + nextLoc.first + " " + nextLoc.second);
		
		ImageView lastCell = (ImageView) findViewById(idArray[currentLoc.first][currentLoc.second]);
		lastCell.setImageResource(0);
		
		ImageView currentCell = (ImageView) findViewById(idArray[nextLoc.first][nextLoc.second]);
		currentCell.setImageResource(R.drawable.player_graphic);
	}
	
	public void graphicsMapping(char[][] textArray)
	{	
		int idCount = 0;
		
		for (int i = 0; i < x; i++)
		{
			for(int j = 0; j < y; j++)
			{
				idArray[j][i] = idCount;
				
				if(textArray[j][i] == 'X')
				{
					ImageView WallGraphic = new ImageView(this);
					WallGraphic.setImageResource(R.drawable.wall_graphic);
					WallGraphic.setId(idCount);
					WallGraphic.setScaleType(ScaleType.FIT_XY);

					mazeImage.addView(WallGraphic);
				}
				else if(textArray[j][i] == '.')
				{
					ImageView PathGraphic = new ImageView(this);
					PathGraphic.setBackgroundResource(R.drawable.path_graphic);
					PathGraphic.setId(idCount);
					PathGraphic.setScaleType(ScaleType.FIT_XY);
					
					mazeImage.addView(PathGraphic);
				}
				else if(textArray[j][i] == 'R')
				{
					ImageView RoadblockGraphic = new ImageView(this);
					RoadblockGraphic.setBackgroundResource(R.drawable.roadblock_graphic);
					RoadblockGraphic.setId(idCount);
					RoadblockGraphic.setScaleType(ScaleType.FIT_XY);
					
					mazeImage.addView(RoadblockGraphic);
				}
				else if(textArray[j][i] == 'S')
				{
					ImageView StartGraphic = new ImageView(this);
					StartGraphic.setBackgroundResource(R.drawable.start_graphic);
					StartGraphic.setImageResource(R.drawable.player_graphic);
					StartGraphic.setId(idCount);
					StartGraphic.setScaleType(ScaleType.FIT_XY);
					
					mazeImage.addView(StartGraphic);
				}
				else if(textArray[j][i] == 'G')
				{
					ImageView GoalGraphic = new ImageView(this);
					GoalGraphic.setBackgroundResource(R.drawable.goal_graphic);
					GoalGraphic.setId(idCount);
					GoalGraphic.setScaleType(ScaleType.FIT_XY);
					
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
    		//System.out.println(deco + title + deco);
    		
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
    		//System.out.println(ex);
    		
    		title = "End Assets Method";
    		//System.out.println(deco + title + deco);    		
    		inputStream.close();
    		
    	} catch(Exception e) {
    		System.out.println("ERMEGERD");
    	}
    	
    	    	    	
    	//Method if we use the resource raw method
    	try {
    		String title = "Start Resource Method";
    		String deco = "--------------------";
    		String decoLine = deco + title + deco;
    		//System.out.println(deco + title + deco);    		
    		
    		//opens the file into an input stream using the resource id
    		InputStream inputStream = null;
        	inputStream = getResources().openRawResource(R.raw.sample2);
        	
        	//variable to store the contents of the file
            byte[] reader = new byte[inputStream.available()];
            
            //reads the whole file
            while (inputStream.read(reader) != -1) {}
            
            //stores the contents of the file into q
    		String ex = new String(reader);
    		//System.out.println(ex);
    		
    		title = "End Resource Method";
    		//System.out.println(deco + title + deco);
    		inputStream.close();
    		
    	} catch(Exception e) {
    		Log.e("Eerrrrrr.....", e.getMessage());
    	}    	
    	
    	// AVI TEST TO ACCESS DATABASE //
    	try {
    		List<dbEntry> questions = new ArrayList<dbEntry>();
    		
    		System.out.println("About to access Database");
    		
    		//database.open();
    		
    		System.out.println("Database Open");
    		
    		questions = database.getAllEntries();
    		
    		System.out.println("post Query: " + questions.size());
    		
    		if (questions.isEmpty()) {
    			System.out.println("EMPTY DB");
    			throw new Exception();
    		}
    		else {
    			intent.putExtra("question", questions.get(0).qestion);
    			intent.putExtra("cAns", questions.get(0).ansCorrect);
    			intent.putExtra("wAns1", questions.get(0).ans2);
    			intent.putExtra("wAns2", questions.get(0).ans3);
    			intent.putExtra("wAns3", questions.get(0).ans4);
    			intent.putExtra("qId", questions.get(0).id);
    			
    			System.out.println("Accesing DataBase");
    		}
    		
    		//database.close();
    	}
    	catch (Exception e){
    		Log.e("YOUR_APP_LOG_TAG", "I got an error", e);
    		System.out.println("TROUBLE ACCESING DATABASE");
    	}
    	// END AVI TEST TO ACCESS DATABASE //
    	
    	/*
    	//Default
    	q = "What is the Capital of the USA?";
    	String cAns = "Washington DC";
    	String wAns1 = "Alabama";
    	String wAns2 = "California";
    	String wAns3 = "Michigan";
    	String qId = "11111";
    	
    	//Adding q to the intent
    	intent.putExtra("question", q);
    	intent.putExtra("cAns", cAns);
    	intent.putExtra("wAns1", wAns1);
    	intent.putExtra("wAns2", wAns2);
    	intent.putExtra("wAns3", wAns3);
    	intent.putExtra("qId", qId);
    	*/
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
			//Question Id and Ans will be processed regardless
			String qId = data.getStringExtra("qId");
			String ans = data.getStringExtra("answer");
			System.out.println(ans);
			System.out.println(qId);
			
			//if correct ans = "correct"
			if (ans.equals("correct")){
						
				//Replace space on board with P
				mazeObject.maze[player.getCurLoc().first][player.getCurLoc().second] = DataStructures.MazeSpaces.PASSED.SpaceChar();
				
				Pair<Integer, Integer> curLoc = player.getCurLoc();
				ImageView lastCell = (ImageView) findViewById(idArray[curLoc.first][curLoc.second]);
				lastCell.setBackgroundResource(R.drawable.cleared_roadblock_graphic);
				
				Log.v("MazeGUI", "Player passed the Roadblock!");
				
			} else {
				//Player was incorrect
				
				//update player loc graphic
				updatePlayerGraphic(player.getCurLoc(), player.getLastLoc());
				Log.v("MazeGUI", "Player moved back from: " + player.getCurLoc().first + " " + player.getCurLoc().second
						+ " to " + player.getLastLoc().first + " " + player.getLastLoc().second);
				
				//move player
				Pair<Integer, Integer> prevLoc = player.getCurLoc();
				player.setCurLoc(player.getLastLoc());
				
			}
		}
	}

	public void finishedMaze() {
		Log.v("MazeGui", "Game is finished - prompt user to continue");
		
		AlertDialog.Builder alert = new AlertDialog.Builder(MazeGUI.this);
		alert.setTitle("Game Won!");
		alert.setMessage("Would you like to start a new game?");
		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Log.v("MazeComplete", "Start new game");
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Ok is clicked", Toast.LENGTH_LONG)
						.show();
				mazeObject = new MazeGeneration(x, y);
				
				//TODO: need to delete the old maze object
				
				MazeGUI.this.graphicsMapping(mazeObject.maze);
				player.setCurLoc(new Pair<Integer, Integer>(0, 0));
			}
		});
		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Log.v("MazeComplete", "Do not start new game");
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "Cancel is clicked",
								Toast.LENGTH_LONG).show();
					}
				});

		alert.show();
	}
}
