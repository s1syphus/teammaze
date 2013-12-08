package teammaize.android.com;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import ask.scanninglibrary.ASKActivity;

public class RoadBlock extends ASKActivity {

	String[] randomKeyArray;
	String[] ansArray;
	boolean ansResult;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_road_block);
		
		//get the passed in intent
		Intent intent = getIntent();
		
		//get the question 
		String q = intent.getStringExtra("question");
		String cAns = intent.getStringExtra("cAns");
		String wAns1 = intent.getStringExtra("wAns1");		
		String wAns2 = intent.getStringExtra("wAns2");
		String wAns3 = intent.getStringExtra("wAns3");
		
		//randomize
		String[] temp = {"cAns", "wAns1", "wAns2", "wAns3"};
		randomKeyArray = temp; 
		List<String> randomList = Arrays.asList(randomKeyArray);
		Collections.shuffle(randomList);
		
		//ans array instantiation
		ansArray = new String[4];
		
		//should add for true/false questions later
		for(int x = 0; x < 4; x++){
			if(randomKeyArray[x] == "cAns")
				ansArray[x] = cAns;
			else if (randomKeyArray[x] == "wAns1")
				ansArray[x] = wAns1;
			else if (randomKeyArray[x] == "wAns2")
				ansArray[x] = wAns2;
			else if (randomKeyArray[x] == "wAns3")
				ansArray[x] = wAns3;			
		}						
		
		//instantiate textVIew variable to point to layout Question Text View
		TextView textView = (TextView) findViewById(R.id.question);
		textView.setText(q);
		Button button1 = (Button) findViewById(R.id.ans1);
		button1.setText(ansArray[0]);
		
		Button button2 = (Button) findViewById(R.id.ans2);
		button2.setText(ansArray[1]);
		
		Button button3 = (Button) findViewById(R.id.ans3);
		button3.setText(ansArray[2]);
		
		Button button4 = (Button) findViewById(R.id.ans4);
		button4.setText(ansArray[3]);
		
		ansResult = false;
		
		//android:id="@+id/ans1"
		
		/*example code
		View.findViewById() or Activity.findViewById(). [reference]
		*/		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.road_block, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// This ID represents the Home or Up button. In the case of this
		// activity, the Up button is shown. Use NavUtils to allow users
		// to navigate up one level in the application structure. For
		// more details, see the Navigation pattern on Android Design:
		//
		// http://developer.android.com/design/patterns/navigation.html#up-vs-back
		
		startActivity(new Intent(RoadBlock.this, MazeGUI.class)); 
        return true;
	}
	
	//manages the closing of the activity
	public void closeActivity(View view) {
		//show toast
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		
		CharSequence text;
		
		if(ansResult){
			text = "Correct Answer!";
		} else {
			text = "Incorrect Answer.";
		}
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		
		//return to the previous activity with no results intent
		Intent results = new Intent();
		if(ansResult) //if correct answer
			results.putExtra("answer", "correct");
		else
			results.putExtra("answer", "incorrect");
		String qId = "11111";
		results.putExtra("qId", qId);
		setResult(Activity.RESULT_OK, results);		
		finish();
	}
	
	//checks ans
	public void checkAnsSelection(int ansSel){
		if(randomKeyArray[ansSel] == "cAns")
			ansResult = true;	
	}
	
	//checks which button was clicked
	public void checkButton(View view){
		if(view.getId() == R.id.ans1)
			checkAnsSelection(0);
		else if(view.getId() == R.id.ans2)
			checkAnsSelection(1);
		else if(view.getId() == R.id.ans3)
			checkAnsSelection(2);
		else if(view.getId() == R.id.ans4)
			checkAnsSelection(3);
		
		closeActivity(view);
	
	}
	
}
