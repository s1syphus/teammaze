package teammaize.android.com;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class QuestionsDataSource {

  // Database fields
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
      MySQLiteHelper.COLUMN_QUESTION, MySQLiteHelper.COLUMN_ANSCORRECT, 
      MySQLiteHelper.COLUMN_ANS2, MySQLiteHelper.COLUMN_ANS3, MySQLiteHelper.COLUMN_ANS4,
      MySQLiteHelper.COLUMN_SUBJECT, MySQLiteHelper.COLUMN_LEVEL};

  public QuestionsDataSource(Context context) {
    dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public dbEntry createEntry(dbEntry entry) {
    ContentValues values = new ContentValues();
    values.put(MySQLiteHelper.COLUMN_QUESTION, entry.getQ());
    values.put(MySQLiteHelper.COLUMN_ANSCORRECT, entry.getACorrect());
    values.put(MySQLiteHelper.COLUMN_ANS2, entry.getA2());
    values.put(MySQLiteHelper.COLUMN_ANS3, entry.getA3());
    values.put(MySQLiteHelper.COLUMN_ANS4, entry.getA4());
    values.put(MySQLiteHelper.COLUMN_SUBJECT, entry.getSub());
    values.put(MySQLiteHelper.COLUMN_LEVEL, entry.getLevel());
    values.put(MySQLiteHelper.COLUMN_CORATTEMPTS, entry.getCorAttempts());
    values.put(MySQLiteHelper.COLUMN_LEVEL, entry.getAttempts());
    
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

  public List<dbEntry> getAllComments() {
    List<dbEntry> comments = new ArrayList<dbEntry>();

    Cursor cursor = database.query(MySQLiteHelper.TABLE_DATA,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      dbEntry entry = cursorToEntry(cursor);
      comments.add(entry);
      cursor.moveToNext();
    }
    // make sure to close the cursor
    cursor.close();
    return comments;
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
