package neko.memo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoDbHelper extends SQLiteOpenHelper {

	static final String DB_NAME = "curry.db";
	static final int DB_VERSION = 1;
	
	public MemoDbHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// メモテーブル作成
		db.execSQL("CREATE TABLE MEMO (_id INTEGER PRIMARY KEY AUTOINCREMENT, MEMO TEXT, AT TEXT DEFAULT CURRENT_TIMESTAMP)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
