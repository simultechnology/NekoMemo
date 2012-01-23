package neko.memo.activity;

import neko.memo.R;
import neko.memo.db.MemoDao;
import neko.memo.db.MemoDbHelper;
import neko.memo.global.Global;
import neko.memo.manager.TextureManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * メモ一覧画面のActivity
 * 
 */
public class MemoListActivity extends BaseActivity {
	private final int FP = ViewGroup.LayoutParams.FILL_PARENT;
	final Context self = this;
	MemoDbHelper mDbHelper;

	private ListView listView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		currentScreen = ScreenName.MEMO_LIST;
		
		mDbHelper = new MemoDbHelper(this);

		TextView header = new TextView(this);
		header.setId(3);
		header.setText("めも一覧");
		header.setTextColor(Color.WHITE);
		header.setGravity(Gravity.CENTER_HORIZONTAL);
		header.setBackgroundColor(Color.DKGRAY);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(FP,
				WC);
		params.addRule(RelativeLayout.BELOW, 1);
		baseLayout.addView(header, params);
        
		listView = new ListView(self);
		listView.setBackgroundColor(Color.TRANSPARENT);
		listView.setCacheColorHint(Color.TRANSPARENT);
		// メモ一覧の表示
		ensureMemoList();
		
		RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(FP, (int) (screenHeight * 0.55));
        layoutParams2.addRule(RelativeLayout.BELOW, 3);
        baseLayout.addView(listView, layoutParams2);
		
		// イベントリスナの設定
		listView.setOnItemClickListener(new MemoDialogClickListener());
	}

	static String getCursorString(Cursor c, String colName) {
		return c.getString(c.getColumnIndex(colName));
	}

	void setTextViewToText(int id, String text) {
		TextView textView = (TextView) findViewById(id);
		textView.setText(text);
	}

	void ensureMemoList() {
		SQLiteDatabase db = mDbHelper.getReadableDatabase();
		Cursor c = MemoDao.getAll(db);
		startManagingCursor(c);
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.memo_list_row, c, new String[] { MemoDao.COL_MEMO,
				"LOCALAT" }, new int[] { R.id.memo, R.id.at });
		listView.setAdapter(adapter);
	}

	// ボタン押した際のイベントリスナー
	class MemoEditClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// メモ入力画面を呼び出す
			Intent intent = new Intent(self, MainActivity.class);
			startActivity(intent);
		}
	}

	
	class MemoItemOnClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int arg2,
				long arg3) {
			RelativeLayout relativeLayout = (RelativeLayout)view;
			TextView textView = (TextView) relativeLayout.getChildAt(0);
			String selecetedMemo = textView.getText().toString();
			cm.setText(selecetedMemo);
		
			Toast.makeText(MemoListActivity.this, selecetedMemo + "\nをクリップボードにコピーしました。", Toast.LENGTH_SHORT).show();
		
		}		
	}
	
	// メモ一覧のアイテムタップのイベントリスナー
	class MemoDialogClickListener implements OnItemClickListener {
	    String resultItem;				
	    @Override
		public void onItemClick(AdapterView<?> paramAdapterView,
				final View paramView, int paramInt, final long paramLong) {
			
		    final String[] menuItems ={ "編集する",
		    							"クリップボードにコピーする",
		    							"削除する",
		    							"戻る"};
		    
			RelativeLayout relativeLayout = (RelativeLayout)paramView;
			TextView textView = (TextView) relativeLayout.getChildAt(0);
			final String selecetedMemo = textView.getText().toString();
		    
			Builder builder = new AlertDialog.Builder(self);
			builder.setTitle(selecetedMemo);
//			TextView title = new TextView(self);
//			title.setText(selecetedMemo);
//			title.setWidth(16);
//			builder.setIcon(R.drawable.ic_dialog_menu_generic);
//			builder.setCustomTitle(title);

			builder.setItems(menuItems,
					new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					resultItem = menuItems[which];
					
	        		if (resultItem != null && resultItem.contains("削除")) {
	        			SQLiteDatabase db = mDbHelper.getWritableDatabase();
						MemoDao.delete(db, paramLong);
						ensureMemoList();
	        		}
	        		else if (resultItem != null && resultItem.contains("クリップボード")) {
	        			cm.setText(selecetedMemo);
	        			Toast.makeText(MemoListActivity.this, selecetedMemo + "\nをクリップボードにコピーしました。", Toast.LENGTH_SHORT).show();
	        		}
	        		else if (resultItem != null && resultItem.contains("戻る")) {
	        			// 何もしない
	        		}
	        		else {
						RelativeLayout relativeLayout = (RelativeLayout)paramView;
						TextView textView = (TextView) relativeLayout.getChildAt(0);
						String selecetedMemo = textView.getText().toString();
						//Toast.makeText(self, selecetedMemo, Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(MemoListActivity.this,MainActivity.class);
						intent.putExtra("selectedMemo", selecetedMemo);
						intent.putExtra("selectedId", paramLong);
						startActivity(intent);
	        		}
				}
			}).show();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		glSurfaceView.onPause();

		TextureManager.deleteAll(Global.gl);
	}

	@Override
	protected void onResume() {
		super.onResume();
		currentScreen = ScreenName.MEMO_LIST;
		glSurfaceView.onResume();
	}
	
	@Override
	protected void onDestroy() {
		mDbHelper.close();
		super.onDestroy();
	}
}