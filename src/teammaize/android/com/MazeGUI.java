package teammaize.android.com;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.os.Bundle;

import java.util.Vector;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.*;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import ask.scanninglibrary.ASKActivity;
import ask.scanninglibrary.views.ASKAlertDialog;


public class MazeGUI extends ASKActivity {
	
	//These are only sample values.
	int x = 10, y = 10;
	
	private MazeGeneration mazeObject;
	private int[][] idArray;
	private GridLayout mazeImage;
	private UserMovement player;

	private QuestionsDataSource database;
	private ConnectionDetector connection;
	private List<dbEntry> dataList;
	private int index;
	private Random r;
	private String subject;

	public void setDataList(List<dbEntry> list) {
		dataList = list;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_maze_gui);

			mazeObject = new MazeGeneration(x, y);
			idArray = new int[x][y];
		
			mazeImage = (GridLayout)findViewById(R.id.mazeImage);
			mazeImage.setColumnCount(x);
			mazeImage.setRowCount(y);
			mazeImage.setColumnOrderPreserved(true);
			mazeImage.setRowOrderPreserved(true);
			 
			this.graphicsMapping(mazeObject.maze);
			this.player = new UserMovement(new Pair<Integer, Integer>(0, 0));
			
			//get the passed in intent
			Intent intent = getIntent();
			subject = intent.getStringExtra("subject");
		}
		catch(Exception e) {
			Log.v("MazeGUI", "Exception thown in onCreate: " + e.toString());
		}
		
		// This is where I request the Connection and parse data		
		//database = new QuestionsDataSource(this);
		//database.open();
		
		dataList = new ArrayList<dbEntry>();
		r = new Random();	
		
		try {
			database = new QuestionsDataSource(this);
			database.open();
		}
		catch (Exception e) {
			Log.v("MazeGUI", "Trouble Opening Database " + e.toString());
		}
		
		try {
			connection = new ConnectionDetector(this);
			if (connection.isConnectingToInternet()) {
			new RequestTask(database, this, dataList, this).execute("http://aura.feralhosting.com/s1syphus1/website_wip/getCSV.php");		
			}
			else {
				dataList = database.getAllEntries();
				
				if (dataList.isEmpty()) {
					System.out.println("THERE ARE NO QUESTIONS PLEASE CONNECT TO ONLINE DATABASE");
				}
			}
		}
		catch (Exception e) {
			Log.v("MazeGUI", "Exception thrown in RequestTask " + e.toString());
		}
		
		try {
			
			Vector<Button> buttons = new Vector<Button>();
			buttons.add((Button) findViewById(R.id.upButton));
			buttons.add((Button) findViewById(R.id.rightButton));
			buttons.add((Button) findViewById(R.id.downButton));
			buttons.add((Button) findViewById(R.id.leftButton));
			buttons.add((Button) findViewById(R.id.closeButton));

			//Initialize left button on click listener
			buttons.elementAt(DataStructures.Directions.WEST.ordinal()).setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Log.v("MazeActivity", "West Button View");
					
					if(player.tryMove(mazeObject, DataStructures.Directions.WEST)) {
						player.movePlayer(v, MazeGUI.this, DataStructures.Directions.WEST);
						
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
						player.movePlayer(v, MazeGUI.this, DataStructures.Directions.EAST);
						
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
						player.movePlayer(v, MazeGUI.this, DataStructures.Directions.NORTH);
						
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
						player.movePlayer(v, MazeGUI.this, DataStructures.Directions.SOUTH);
						
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

	// Update the location of the player graphic from current location to the next location
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
		int finalDimen = 0;
		Display d = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		@SuppressWarnings("deprecation")
		int width = d.getWidth();
		@SuppressWarnings("deprecation")
		int height = d.getHeight();
		
		//Set percentage of screen taken up by maze
		if(width < height)
		{
			finalDimen = (int) (width/this.x * 0.80);
		}
		else
		{
			finalDimen = (int) (height/this.x * 0.60);
		}
		
		LayoutParams dimensions = new LayoutParams(finalDimen, finalDimen);
		
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
					WallGraphic.setAdjustViewBounds(true);
					WallGraphic.setLayoutParams(dimensions);
					
					mazeImage.addView(WallGraphic);
				}
				else if(textArray[j][i] == '.')
				{
					ImageView PathGraphic = new ImageView(this);
					PathGraphic.setBackgroundResource(R.drawable.path_graphic);
					PathGraphic.setId(idCount);
					PathGraphic.setScaleType(ScaleType.FIT_XY);
					PathGraphic.setAdjustViewBounds(true);
					PathGraphic.setLayoutParams(dimensions);
					
					mazeImage.addView(PathGraphic);
				}
				else if(textArray[j][i] == 'R')
				{
					ImageView RoadblockGraphic = new ImageView(this);
					RoadblockGraphic.setBackgroundResource(R.drawable.roadblock_graphic);
					RoadblockGraphic.setId(idCount);
					RoadblockGraphic.setScaleType(ScaleType.FIT_XY);
					RoadblockGraphic.setAdjustViewBounds(true);
					RoadblockGraphic.setLayoutParams(dimensions);
					
					mazeImage.addView(RoadblockGraphic);
				}
				else if(textArray[j][i] == 'S')
				{
					ImageView StartGraphic = new ImageView(this);
					StartGraphic.setBackgroundResource(R.drawable.start_graphic);
					StartGraphic.setImageResource(R.drawable.player_graphic);
					StartGraphic.setId(idCount);
					StartGraphic.setScaleType(ScaleType.FIT_XY);
					StartGraphic.setAdjustViewBounds(true);
					StartGraphic.setLayoutParams(dimensions);
					
					mazeImage.addView(StartGraphic);
				}
				else if(textArray[j][i] == 'G')
				{
					ImageView GoalGraphic = new ImageView(this);
					GoalGraphic.setBackgroundResource(R.drawable.goal_graphic);
					GoalGraphic.setId(idCount);
					GoalGraphic.setScaleType(ScaleType.FIT_XY);
					GoalGraphic.setAdjustViewBounds(true);
					GoalGraphic.setLayoutParams(dimensions);
					
					mazeImage.addView(GoalGraphic);
				}
				
				idCount++;
			}
		}
	}
	
	public void roadBlockEnc (View view) {
    	//Initiating the roadblock intent
    	Intent intent = new Intent(MazeGUI.this, RoadBlock.class);

    	try {
    		List<dbEntry> questions = new ArrayList<dbEntry>();
    		
    		if(subject.equals("All")) {
    	    	questions = database.getAllEntries();
    	    }
    		else {
    			questions = database.getSubLevEntries(subject, 0);
    		}
    		
    		Log.v("MazeGUI: roadBlockEnc", "questions size: " + questions.size());
    		
    		if (questions.isEmpty()) {
    			System.out.println("EMPTY DB");
    			
    			//TODO: should show a nice error rather than using this question but this is to ensure
    			//demoability
    			String quest = "Which is NOT one of the original 13 colonies?";
    			String a1 = "Kentucky";
    			String a2 = "New York";
    			String a3 = "Maryland";
    			String a4 = "Connecticut";
    			String subject = "Social Studies";
    			String level = "6";
    			dbEntry entry = new dbEntry(0, quest, a1, a2, a3, a4, subject, level);
    			questions.add(entry);
    			
    			//throw new Exception();
    		}    			
    		index = (r.nextInt(questions.size()));
    		
   			intent.putExtra("question", questions.get(index).qestion);
   			intent.putExtra("cAns", questions.get(index).ansCorrect);
   			intent.putExtra("wAns1", questions.get(index).ans2);
   			intent.putExtra("wAns2", questions.get(index).ans3);
   			intent.putExtra("wAns3", questions.get(index).ans4);
   			intent.putExtra("qId", questions.get(index).id);
    			
   			System.out.println("Accesing DataBase: " + questions.size());
    		
    		//database.close();
    	}
    	catch (Exception e){
    		Log.v("MazeGUI", e.toString());
    		System.out.println("TROUBLE ACCESING DATABASE");
    	}
 
    	//switch to the roadblock activity
    	startActivityForResult(intent, 10); //10 is arbitrary, can be anything
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
				//Player was incorrect, update player loc graphic
				updatePlayerGraphic(player.getCurLoc(), player.getLastLoc());
				Log.v("MazeGUI", "Player moved back from: " + player.getCurLoc().first + " " + player.getCurLoc().second
						+ " to " + player.getLastLoc().first + " " + player.getLastLoc().second);
				
				//move player
				player.setCurLoc(player.getLastLoc());
			}
		}
	}

	public void finishedMaze() {
		Log.v("MazeGui", "Game is finished - prompt user to continue");
		
		ASKAlertDialog alert = new ASKAlertDialog(MazeGUI.this);
		alert.setTitle("Game Won!");
		alert.setMessage("Would you like to start a new game?");
		alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				Log.v("MazeComplete", "Start new game");
				
				//generate a new maze
				mazeObject = new MazeGeneration(x, y);
				
				//delete the old maze image
				mazeImage.removeAllViews();
				
				//make the new maze image
				MazeGUI.this.graphicsMapping(mazeObject.maze);
				player.setCurLoc(new Pair<Integer, Integer>(0, 0));
			}
		});
		alert.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",	new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Log.v("MazeComplete", "Do not start new game");
					}
				});

		alert.show();
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
	
	//Closes activity
	public void closeActivity(View view) {
		//return to the previous activity with no results intent
		finish();
	}
	
	public void setDatabase(List<dbEntry> list) {
		
		dbEntry temp;
		
		System.out.println("The Database is called: " + database.Name() + " list size = " + list.size());
		System.out.println("DATABASE PATH: " + database.Path());
		
		for (int i=0; i<list.size(); i++) {
			temp = database.createEntry(list.get(i));
		}
	}
	
	public void clearDatabase() {
		database.forceClear();
	}
}

