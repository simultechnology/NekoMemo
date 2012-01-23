package neko.memo.db;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MemoDao {
  public static final String TABLE_NAME = "MEMO";
  public static final String COL_MEMO = "MEMO";
  public static final String COL_AT = "AT";

  public static Cursor getAll(SQLiteDatabase db) {
//	    return db.query(TABLE_NAME, new String[] { "_id", COL_MEMO, COL_AT },
//	    					null, null, null, null, COL_AT + " DESC");
	    return db.rawQuery("select _id, MEMO, strftime('%Y/%m/%d %H:%M', AT, 'localtime') as LOCALAT from MEMO order by AT desc", null);
	  }
  
  public static void insert(SQLiteDatabase db, String memo) {
    ContentValues values = new ContentValues();
    values.put(COL_MEMO, memo);
    db.insert(TABLE_NAME, null, values);
  }
  
  public static void update(SQLiteDatabase db, String memo, long id) {
	  db.execSQL("UPDATE " +
			  		TABLE_NAME +
			  		" SET MEMO = '" +
			  		memo +
			  		"', AT = datetime() WHERE _id = '" + 
			  		id +
			  		"'");
	  }

  public static void delete(SQLiteDatabase db, long _id) {
    db.delete(TABLE_NAME, "_id = ?", new String[] { String.valueOf(_id) });
  }
  
  // 画面表示には利用しないがtestで便利なので用意
  static int count(SQLiteDatabase db) {
    Cursor c = db.rawQuery("SELECT count(*) as cnt FROM " + TABLE_NAME, null);
    c.moveToFirst();
    int count = c.getInt(c.getColumnIndex("cnt"));
    c.close();
    return count;
  }
}

