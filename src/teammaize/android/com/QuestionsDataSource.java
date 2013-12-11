package teammaize.android.com;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class QuestionsDataSource {

  // Database fields
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private Boolean createNew;
  private String toParseData;
  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
      MySQLiteHelper.COLUMN_QUESTION, MySQLiteHelper.COLUMN_ANSCORRECT, 
      MySQLiteHelper.COLUMN_ANS2, MySQLiteHelper.COLUMN_ANS3, MySQLiteHelper.COLUMN_ANS4,
      MySQLiteHelper.COLUMN_SUBJECT, MySQLiteHelper.COLUMN_LEVEL, 
      MySQLiteHelper.COLUMN_CORATTEMPTS, MySQLiteHelper.COLUMN_ATTEMPTS};
  private String subjectWhere;
  private String levelWhere;

  public QuestionsDataSource(Context context) {
      
	  dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
    Log.v("QuestionsDataSource","DB OPEN: " + database.getPath());
  }

  public void close() {
    dbHelper.close();
  }

  public void addData(String parseData) {

	  System.out.println("PARSING DATA");
	  System.out.println(parseData);
	  
	try {  
	 StringTokenizer strtok = new StringTokenizer(parseData, ",\n\t\r");
	 dbEntry insertEntry;
	 String garb;
	 

	
	 garb = new String("");
	 
	 while(!garb.equals("<body>")) {
		 System.out.println(garb);
		 garb = strtok.nextToken();
	 }
	 
	 
	 long count = 0;
	 
	 while (strtok.hasMoreTokens()) {
		 insertEntry = new dbEntry();
		 
		 try {
			 String tempStr = strtok.nextToken(",");
			 if (tempStr.equals("</body>")) {
				 break;
			 }
			 if (tempStr.equals("")) {
				 break;
			 }
         }
         catch (NumberFormatException nef) {
        	 System.out.println("ID isn't a Number");
         }
		 
		   insertEntry.qestion = strtok.nextToken(",");
		   insertEntry.ansCorrect = strtok.nextToken(",");
		   insertEntry.ans2 = strtok.nextToken(",");
		   insertEntry.ans3 = strtok.nextToken(",");
		   insertEntry.ans4 = strtok.nextToken(",");
		   insertEntry.subject = strtok.nextToken(",");
		   insertEntry.level = strtok.nextToken(",");
		   insertEntry.corAttempts = strtok.nextToken(",");
		   insertEntry.attempts = strtok.nextToken(",");
		   
		   System.out.println("//////////////");
		   insertEntry.printEntry();
		   System.out.println("//////////////");
		   
		   insertEntry.id = count;
		   
		   dbEntry temp = createEntry(insertEntry);
		   count++;
	 } 
	 
	}
	catch (NoSuchElementException e) {
		System.out.println("NOSUCHELEMENTS MEANS THAT NUMBERING IS OFF");
	}
	  
  }
  
  public dbEntry createEntry(dbEntry entry) {
    ContentValues values = new ContentValues();
    values.put(MySQLiteHelper.COLUMN_ID, entry.getID());
    values.put(MySQLiteHelper.COLUMN_QUESTION, entry.getQ());
    values.put(MySQLiteHelper.COLUMN_ANSCORRECT, entry.getACorrect());
    values.put(MySQLiteHelper.COLUMN_ANS2, entry.getA2());
    values.put(MySQLiteHelper.COLUMN_ANS3, entry.getA3());
    values.put(MySQLiteHelper.COLUMN_ANS4, entry.getA4());
    values.put(MySQLiteHelper.COLUMN_SUBJECT, entry.getSub());
    values.put(MySQLiteHelper.COLUMN_LEVEL, entry.getLevel());
    values.put(MySQLiteHelper.COLUMN_CORATTEMPTS, entry.getCorAttempts());
    values.put(MySQLiteHelper.COLUMN_ATTEMPTS, entry.getAttempts());
    
    
    
    long insertId = database.insert(MySQLiteHelper.TABLE_DATA, null,
        values);
    
    dbEntry newEntry = new dbEntry();
    
    return newEntry;
  }

  public void deleteComment(dbEntry entry) {
    long id = entry.getID();
    System.out.println("Comment deleted with id: " + id);
    database.delete(MySQLiteHelper.TABLE_DATA, MySQLiteHelper.COLUMN_ID
        + " = " + id, null);
  }

  public List<dbEntry> getAllEntries() {
	  
	  System.out.println("In get Entries");
	  
    List<dbEntry> entries = new ArrayList<dbEntry>();

    Cursor cursor = null;
    
    try {
    cursor = database.query(MySQLiteHelper.TABLE_DATA,
        allColumns, null, null, null, null, null);
    }
    catch (Exception e) {
    	Log.v("QuestionsDataSource", e.toString());
    }

    
    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      dbEntry entry = cursorToEntry(cursor);
      entries.add(entry);
      cursor.moveToNext();
    }
    // make sure to close the cursor
    cursor.close();
    
    System.out.println("Entries: " + entries.size());
    
    return entries;
  }
  
  public List<dbEntry> getSubLevEntries(String subject, int level) {
	  System.out.println("In get Sub Lev Entries");
	  
	    List<dbEntry> entries = new ArrayList<dbEntry>();
	    String whereClause;
	    Cursor cursor = null;
	    
	    whereClause = "subject='" + subject + "'";
	    
	    try {
	    cursor = database.query(MySQLiteHelper.TABLE_DATA,
	        allColumns, whereClause, null, null, null, null);
	    }
	    catch (Exception e) {
	    	Log.v("QuestionsDataSource", e.toString());
	    	return null;
	    }
	    
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      dbEntry entry = cursorToEntry(cursor);
	      entries.add(entry);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    
	    return entries;
  }

  private dbEntry cursorToEntry(Cursor cursor) {
    dbEntry entry = new dbEntry();
    entry.id = cursor.getLong(0);
    entry.qestion = cursor.getString(1);
    entry.ansCorrect = cursor.getString(2);
    entry.ans2 = cursor.getString(3);
    entry.ans3 = cursor.getString(4);
    entry.ans4 = cursor.getString(5);
    entry.subject = cursor.getString(6);
    entry.level = cursor.getString(7);
    entry.corAttempts = cursor.getString(8);
    entry.attempts = cursor.getString(9);
    return entry;
  }
  
  public String Name() {
	 return dbHelper.getDatabaseName();
  }
  
  public String Path() {
	  return database.getPath();
  }
  
  public void forceClear() {
	  dbHelper.onUpgrade(database, 1, 1);
  }
} 
