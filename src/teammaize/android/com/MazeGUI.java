package teammaize.android.com;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.content.Intent;
import android.view.*;


public class MazeGUI extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maze_gui);
		// Show the Up button in the action bar.
		setupActionBar();
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
