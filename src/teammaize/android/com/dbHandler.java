package teammaize.android.com;

import java.util.ArrayList;
import java.util.List;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHandler extends SQLiteOpenHelper {

	// All Static variables
    // Database Version
    private static final int DB_VERSION = 1;
 
    // Database Name
    private static final String DB_NAME = "questionsManager";
 
    // Contacts table name
    private static final String TABLE_QUESTIONS = "questions";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_ANS1 = "ans1";
    private static final String KEY_ANS2 = "ans2";
    private static final String KEY_ANS3 = "ans3";
    private static final String KEY_ANS4 = "ans4";
 
    public dbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_QUESTIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_QUESTION + " TEXT,"
                + KEY_ANS1 + " TEXT," + KEY_ANS2 + " TEXT," + KEY_ANS3 + 
                " TEXT," + KEY_ANS4 + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
 
        // Create tables again
        onCreate(db);
    }
    
    public void addQuestion(dbEntry input) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put(KEY_ID, input.getID()); //ID
    	values.put(KEY_QUESTION, input.getQ()); //Question
    	values.put(KEY_ANS1, input.getA1()); //Answer 1
    	values.put(KEY_ANS2, input.getA2()); //Answer 2
    	values.put(KEY_ANS3, input.getA3()); //Answer 3
    	values.put(KEY_ANS4, input.getA4()); //Answer 4
    	
    	db.insert(TABLE_QUESTIONS, null, values);
    	db.close();
    }
	
 
}
