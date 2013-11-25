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

  public QuestionsDataSource(Context context) {
      
	  dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
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
	 
	 System.out.println("parseData has: " + strtok.countTokens() + " tokens");
	
	 garb = new String("");
	 
	 while(!garb.equals("<body>")) {
		 System.out.println(garb);
		 garb = strtok.nextToken();
	 }
	 
	 System.out.println("AHHHHHH");
	 
	 long count = 0;
	 
	 while (strtok.hasMoreTokens()) {
		// StringTokenizer entry = new StringTokenizer(strtok.nextToken(), ",");
		 //System.out.println("THERE ARE THIS MANY TOKENS IN THIS LINE: " + entry.countTokens());
		 insertEntry = new dbEntry();
		 
		/*
		 while(strtok.hasMoreTokens()) {
			 System.out.println(strtok.nextToken(","));
		 }
		 
		 
		 for (int i=0;i<6;i++) {
			 String garbage = strtok.nextToken(",");
		 }
		 */
		 try {
			 String tempStr = strtok.nextToken(",");
			 if (tempStr.equals("</body>")) {
				 break;
			 }
			 if (tempStr.equals("")) {
				 break;
			 }/*
			 System.out.println("first Token: " + tempStr);
			 Long tempLong = Long.valueOf(tempStr);
			 System.out.println("Long Val: " + tempLong);
       	     insertEntry.id = tempLong.longValue();
       	     */
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
	
	
	
      
	  
	  /* parseData is in the form of XML as follows:
	  
	  <ENTRY>
	  		<id> ## 				</id>
	  		<question> wjefjewi 	</question>
	  		<ansCorrect> alkdjflkaj </ansCorrect>
	  		<ans2>  alkdjflka		</ans2>
	  		<ans3>  alkdjflka		</ans3>
	  		<ans4>  alkdjflka		</ans4>
	  		<subjet> alksdjf		</subject>
	  		<level> ## 				</level>
	  		<corAttempts> ##		</corAttempts>
	  		<attempts>  ##			</attempts>
	  
	  
	  </ENTRY>
	  
	 
	 */
	  /*List<String> singleEntry = new ArrayList<String>();
	  
	  try {
	        //For String source
		  
		  
	        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	        factory.setNamespaceAware(true);
	        XmlPullParser xpp = factory.newPullParser();
	        xpp.setInput(new StringReader(parseData)); 

	        xpp.next();
	        int eventType = xpp.getEventType();                        
	        
	        
	          while (xpp.getEventType()!=XmlPullParser.END_DOCUMENT) {
	              if (xpp.getEventType()==XmlPullParser.START_TAG) {
	                  if (xpp.getName().equals("ENTRY")) {
	                     for (int i=0;i<10;i++) {
	                    	 while (xpp.getEventType()!=XmlPullParser.START_TAG) {
	                    	 }
	                    	 singleEntry.add(xpp.getText());
	                     }
	                  }
	                  dbEntry insertEntry = new dbEntry();
	                  try {
	                	  insertEntry.id = Long.parseLong(singleEntry.get(0));
	                  }
	                  catch (NumberFormatException nef) {
	                  }
	                  
	                  insertEntry.qestion = singleEntry.get(1);
	                  insertEntry.ansCorrect = singleEntry.get(2);
	                  insertEntry.ans2 = singleEntry.get(3);
	                  insertEntry.ans3 = singleEntry.get(4);
	                  insertEntry.ans4 = singleEntry.get(5);
	                  insertEntry.subject = singleEntry.get(6);
	                  insertEntry.level = singleEntry.get(7);
	                  insertEntry.corAttempts = singleEntry.get(8);
	                  insertEntry.attempts = singleEntry.get(9);
	                  
	                  dbEntry temp = createEntry(insertEntry);
	              }
	              xpp.next();
	          }

	    }
	   
	    catch (XmlPullParserException e) {
	          e.printStackTrace();
	    } catch (IOException e) {
	          e.printStackTrace();
	    }*/
	  
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
    Cursor cursor = database.query(MySQLiteHelper.TABLE_DATA,
        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
        null, null, null);
    cursor.moveToFirst();
    dbEntry newEntry = cursorToEntry(cursor);
    cursor.close();
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

    Cursor cursor = database.query(MySQLiteHelper.TABLE_DATA,
        allColumns, null, null, null, null, null);

    System.out.println("After Query");
    
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
} 
