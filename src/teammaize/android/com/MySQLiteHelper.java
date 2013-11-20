package teammaize.android.com;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	  public static final String TABLE_DATA = "data";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_QUESTION = "question";
	  public static final String COLUMN_ANSCORRECT = "anscorrect";
	  public static final String COLUMN_ANS2 = "ans2";
	  public static final String COLUMN_ANS3 = "ans3";
	  public static final String COLUMN_ANS4 = "ans4";
	  public static final String COLUMN_SUBJECT = "subject";
	  public static final String COLUMN_LEVEL = "level";
	  public static final String COLUMN_CORATTEMPTS = "corattempts";
	  public static final String COLUMN_ATTEMPTS = "attempts";

	  private static final String DATABASE_NAME = "questions.db";
	  private static final int DATABASE_VERSION = 1;

	  // Database creation sql statement
	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_DATA + "(" + COLUMN_ID
	      + " integer primary key autoincrement, " + COLUMN_QUESTION
	      + " text not null, " + COLUMN_ANSCORRECT + " text not null, "
	      + COLUMN_ANS2 + " text not null, " + COLUMN_ANS3 + " text not null, "
	      + COLUMN_ANS4 + " text not null, " + COLUMN_SUBJECT + " text not null, "
	      + COLUMN_LEVEL + " text not null, " +COLUMN_CORATTEMPTS + " text not null, "
	      + COLUMN_ATTEMPTS + "text not null);";

	  public MySQLiteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(MySQLiteHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
	    onCreate(db);
	  }
	
}
