package neko.memo.activity;

import java.util.ArrayList;
import java.util.List;

import neko.memo.R;
import neko.memo.constant.Constant;
import neko.memo.db.MemoDao;
import neko.memo.db.MemoDbHelper;
import neko.memo.global.Global;
import neko.memo.manager.TextureManager;
import neko.memo.view.ButtonContena;
import neko.memo.view.CustomButton;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

	private EditText inputForm;

	TableLayout underButtonlayout;
	
	ButtonContena speakButton;
	ButtonContena otherAppliButton;
	ButtonContena clearButton;
	ButtonContena saveButton;
	
	private long selectedId = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		currentScreen = ScreenName.MEMO_INPUT;

		RelativeLayout baseChildLayout = new RelativeLayout(this);
		RelativeLayout.LayoutParams baseChildLayoutParams = new RelativeLayout.LayoutParams(WC, WC);
		baseChildLayoutParams.addRule(RelativeLayout.BELOW, 1);
		baseLayout.addView(baseChildLayout, baseChildLayoutParams);
		
		inputForm = new EditText(this);
		inputForm.setId(Constant.ID_INPUT_FORM);
		inputForm.setHeight((int) (screenHeight / 5));
		inputForm.setPadding(5, 5, 5, 5);
		RelativeLayout.LayoutParams inputFormParams = new RelativeLayout.LayoutParams(FP, WC);
		inputFormParams.setMargins(5, 0, 5, 0);
		baseChildLayout.addView(inputForm, inputFormParams);
		
		int upperButtonWidth = screenWidth / 4;
		int lowerButtonWidth = screenWidth / 7;

		Button newLineButton = 
				new CustomButton(
						this, 
						Constant.ID_NEW_LINE_BUTTON, 
						"改行",
						upperButtonWidth,
						new InputCharaClickListener("\n"));
		RelativeLayout.LayoutParams testButtonLayoutParam = 
				new RelativeLayout.LayoutParams(WC, WC);
		testButtonLayoutParam.addRule(RelativeLayout.BELOW, Constant.ID_INPUT_FORM);
		baseChildLayout.addView(newLineButton, testButtonLayoutParam);
		
		Button toLeftButton = 
				new CustomButton(
						this, 
						Constant.ID_TO_LEFT_BUTTON, 
						Constant.TO_LEFT,
						upperButtonWidth,
						new ToTransClickListener(-1));
		toLeftButton.setWidth(screenWidth / 4);
		RelativeLayout.LayoutParams toLeftButtonLayoutParam = 
				new RelativeLayout.LayoutParams(WC, WC);
		toLeftButtonLayoutParam.addRule(RelativeLayout.BELOW, Constant.ID_INPUT_FORM);
		toLeftButtonLayoutParam.addRule(RelativeLayout.RIGHT_OF, Constant.ID_NEW_LINE_BUTTON);
		baseChildLayout.addView(toLeftButton, toLeftButtonLayoutParam);
		
		Button torightButton = 
				new CustomButton(
						this, 
						Constant.ID_TO_RIGHT_BUTTON, 
						Constant.TO_RIGHT,
						upperButtonWidth,
						new ToTransClickListener(1));
		RelativeLayout.LayoutParams toRightButtonLayoutParam = 
				new RelativeLayout.LayoutParams(WC, WC);
		toRightButtonLayoutParam.addRule(RelativeLayout.BELOW, Constant.ID_INPUT_FORM);
		toRightButtonLayoutParam.addRule(RelativeLayout.RIGHT_OF, Constant.ID_TO_LEFT_BUTTON);
		baseChildLayout.addView(torightButton, toRightButtonLayoutParam);
		
		Button delButton = 
				new CustomButton(
						this,
						Constant.ID_DEL_BUTTON,
						"DEL",
						screenWidth - (upperButtonWidth * 3),
						new DelClickListener());
		RelativeLayout.LayoutParams test8ButtonLayoutParam = 
				new RelativeLayout.LayoutParams(WC, WC);
		test8ButtonLayoutParam.addRule(RelativeLayout.BELOW, Constant.ID_INPUT_FORM);
		test8ButtonLayoutParam.addRule(RelativeLayout.RIGHT_OF, Constant.ID_TO_RIGHT_BUTTON);
		baseChildLayout.addView(delButton, test8ButtonLayoutParam);
		
		Button inputCommaButton = 
				new CustomButton(
						this,
						Constant.ID_INPUT_COMMA_BUTTON,
						Constant.COMMA,
						lowerButtonWidth,
						new InputCharaClickListener(Constant.COMMA));
		RelativeLayout.LayoutParams test2ButtonLayoutParam = 
				new RelativeLayout.LayoutParams(WC, WC);
		test2ButtonLayoutParam.addRule(RelativeLayout.BELOW, Constant.ID_NEW_LINE_BUTTON);
		baseChildLayout.addView(inputCommaButton, test2ButtonLayoutParam);
		
		Button inputPeriodButton = 
				new CustomButton(
						this,
						Constant.ID_INPUT_PERIOD_BUTTON,
						Constant.PERIOD,
						lowerButtonWidth,
						new InputCharaClickListener(Constant.PERIOD));
		RelativeLayout.LayoutParams test3ButtonLayoutParam = 
				new RelativeLayout.LayoutParams(WC, WC);
		test3ButtonLayoutParam.addRule(RelativeLayout.BELOW, Constant.ID_NEW_LINE_BUTTON);
		test3ButtonLayoutParam.addRule(RelativeLayout.RIGHT_OF, Constant.ID_INPUT_COMMA_BUTTON);
		baseChildLayout.addView(inputPeriodButton, test3ButtonLayoutParam);
		
		Button inputQuestionButton = 
				new CustomButton(
						this,
						Constant.ID_INPUT_QUESTION_BUTTON,
						Constant.QUESTION,
						lowerButtonWidth,
						new InputCharaClickListener(Constant.QUESTION));
		RelativeLayout.LayoutParams test4ButtonLayoutParam = 
				new RelativeLayout.LayoutParams(WC, WC);
		test4ButtonLayoutParam.addRule(RelativeLayout.BELOW, Constant.ID_NEW_LINE_BUTTON);
		test4ButtonLayoutParam.addRule(RelativeLayout.RIGHT_OF, Constant.ID_INPUT_PERIOD_BUTTON);
		baseChildLayout.addView(inputQuestionButton, test4ButtonLayoutParam);
		
		Button inputTildeButton = 
				new CustomButton(
						this,
						Constant.ID_INPUT_TILDE_BUTTON,
						Constant.TILDE,
						lowerButtonWidth,
						new InputCharaClickListener(Constant.TILDE));
		RelativeLayout.LayoutParams test5ButtonLayoutParam = 
				new RelativeLayout.LayoutParams(WC, WC);
		test5ButtonLayoutParam.addRule(RelativeLayout.BELOW, Constant.ID_NEW_LINE_BUTTON);
		test5ButtonLayoutParam.addRule(RelativeLayout.RIGHT_OF, Constant.ID_INPUT_QUESTION_BUTTON);
		baseChildLayout.addView(inputTildeButton, test5ButtonLayoutParam);
		
		Button inputExclamationButton = 
				new CustomButton(
						this,
						Constant.ID_INPUT_EXCLAMATION_BUTTON,
						Constant.EXCLAMATION,
						lowerButtonWidth,
						new InputCharaClickListener(Constant.EXCLAMATION));
		RelativeLayout.LayoutParams test6ButtonLayoutParam = 
				new RelativeLayout.LayoutParams(WC, WC);
		test6ButtonLayoutParam.addRule(RelativeLayout.BELOW, Constant.ID_NEW_LINE_BUTTON);
		test6ButtonLayoutParam.addRule(RelativeLayout.RIGHT_OF, Constant.ID_INPUT_TILDE_BUTTON);
		baseChildLayout.addView(inputExclamationButton, test6ButtonLayoutParam);
		
		Button inputPointButton = 
				new CustomButton(
						this,
						Constant.ID_INPUT_POINT_BUTTON,
						Constant.POINT,
						lowerButtonWidth,
						new InputCharaClickListener(Constant.POINT));
		RelativeLayout.LayoutParams pointButtonLayoutParam = 
				new RelativeLayout.LayoutParams(WC, WC);
		pointButtonLayoutParam.addRule(RelativeLayout.BELOW, Constant.ID_NEW_LINE_BUTTON);
		pointButtonLayoutParam.addRule(RelativeLayout.RIGHT_OF, Constant.ID_INPUT_EXCLAMATION_BUTTON);
		baseChildLayout.addView(inputPointButton, pointButtonLayoutParam);
		
		Button inputSpaceButton = 
				new CustomButton(
						this,
						Constant.ID_INPUT_SPACE_BUTTON,
						Constant.LABEL_SPACE,
						screenWidth - lowerButtonWidth * 6,
						new InputCharaClickListener(Constant.SPACE));
		inputSpaceButton.setTextSize(12);
		RelativeLayout.LayoutParams spaceButtonLayoutParam = 
				new RelativeLayout.LayoutParams(WC, WC);
		spaceButtonLayoutParam.addRule(RelativeLayout.BELOW, Constant.ID_NEW_LINE_BUTTON);
		spaceButtonLayoutParam.addRule(RelativeLayout.RIGHT_OF, Constant.ID_INPUT_POINT_BUTTON);
		baseChildLayout.addView(inputSpaceButton, spaceButtonLayoutParam);
		
		underButtonlayout = new TableLayout(this);
		underButtonlayout.setOrientation(TableLayout.HORIZONTAL);
		underButtonlayout.setGravity(Gravity.CENTER);//中央寄せ
		TableRow tableRow = new TableRow(this);
		tableRow.setGravity(Gravity.CENTER);
		tableRow.setLayoutParams(new TableLayout.LayoutParams(FP, WC));
		underButtonlayout.addView(tableRow);

		RelativeLayout.LayoutParams underButtonLayoutParam = 
				new RelativeLayout.LayoutParams(FP, WC);
		underButtonLayoutParam.addRule(RelativeLayout.BELOW, Constant.ID_INPUT_COMMA_BUTTON);
		underButtonLayoutParam.setMargins(0, 10, 0, 0);
		baseChildLayout.addView(underButtonlayout, underButtonLayoutParam);
		
		speakButton = 
				new ButtonContena(this,
								R.drawable.speak_button, 
								"音声認識");
		speakButton.setPadding(10, 0, 10, 0);
		// Check to see if a recognition activity is present
		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() != 0) {
			speakButton.setOnClickListener(new SpeakButtonClickListener());

		} else {
			speakButton.setEnabled(false);
		}
		tableRow.addView(speakButton);
	
		clearButton = new ButtonContena(this, R.drawable.clear_button, "クリア");
		clearButton.setPadding(10, 0, 10, 0);
		clearButton.setOnClickListener(new ClearClickListener());
		tableRow.addView(clearButton);
		
		saveButton = new ButtonContena(this, R.drawable.save_button, "保存");
		saveButton.setPadding(10, 0, 10, 0);
		saveButton.setOnClickListener(new MemoInsertClickListener());
		tableRow.addView(saveButton);
		
		otherAppliButton = new ButtonContena(this, R.drawable.other_appli_button, "他アプリ\n起動");
		otherAppliButton.setPadding(10, 0, 10, 0);
		otherAppliButton.setOnClickListener(new OtherAppliButtonClickListener());
		tableRow.addView(otherAppliButton);
				
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String selectedMemo = extras.getString("selectedMemo");
			selectedId = extras.getLong("selectedId");
			inputForm.setText(selectedMemo);
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
		currentScreen = ScreenName.MEMO_INPUT;
		glSurfaceView.onResume();
	}

	/**
	 * Fire an intent to start the speech recognition activity.
	 */
	private void startVoiceRecognitionActivity() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
				"はっきりとお話下さい。");
		startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
	}

	/**
	 * Handle the results from the recognition activity.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE
				&& resultCode == RESULT_OK) {
			// Fill the list view with the strings the recognizer thought it
			// could have heard
			ArrayList<String> matches = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

			final CharSequence[] chars = new String[matches.size()];

			for (int i = 0; i < matches.size(); i++) {
				chars[i] = matches.get(i);
			}

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					MainActivity.this);
			// タイトルを設定
			alertDialogBuilder.setTitle("候補一覧");
			// 表示項目とリスナの設定
			alertDialogBuilder.setItems(chars,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							inputForm.append(chars[which]);
						}
					});

			// ダイアログを表示
			alertDialogBuilder.create().show();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	class InputCharaClickListener implements OnClickListener {
		private String inputChara;
		InputCharaClickListener(String inputChara) {
			this.inputChara = inputChara;
		}
		@Override
		public void onClick(View v) {

			int focusIndex = inputForm.getSelectionStart();
			
			Editable editableText = inputForm.getText();
			int length = editableText.length();
			
			CharSequence fitstPart = editableText.subSequence(0, focusIndex);
			CharSequence lastPart = "";
			if (length > focusIndex) {
				lastPart = editableText.subSequence(focusIndex, length);
			}
			inputForm.setText(fitstPart + inputChara + lastPart);
			inputForm.setSelection(focusIndex + 1);
		}
	}
	
	class ToTransClickListener implements OnClickListener {
		private int trans;
		ToTransClickListener(int trans) {
			this.trans = trans;
		}
		@Override
		public void onClick(View v) {

			int focusIndex = inputForm.getSelectionStart();
			if (trans == -1) {
				if (focusIndex > 0) {
					inputForm.setSelection(focusIndex - 1);
				}
			}
			else if (trans == 1) {
				if (focusIndex < inputForm.length()) {
					inputForm.setSelection(focusIndex + 1);
				}
			}
		}
	}
	
	class DelClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {			
			int focusIndex = inputForm.getSelectionStart();
			
			Editable editableText = inputForm.getText();
			int length = editableText.length();
			
			String retText;
			if (focusIndex > 0) {
				CharSequence fitstPart = editableText.subSequence(0, focusIndex - 1);
				CharSequence lastPart = editableText.subSequence(focusIndex, length);
				
				retText = fitstPart.toString() + lastPart.toString();
			}
			else {
				retText = editableText.toString();
			}
			inputForm.setText(retText);
			if (focusIndex > 0) {
				inputForm.setSelection(focusIndex - 1);
			}
		}
	}
	
	class MemoInsertClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Editable editText = inputForm.getText();
			String memo = editText.toString().trim();
			
			if (memo.length() > 0) {
				MemoDbHelper dbHelper = new MemoDbHelper(MainActivity.this);
				SQLiteDatabase db = dbHelper.getWritableDatabase();

				if (selectedId > 0) {
					MemoDao.update(db, memo, selectedId);
				}
				else {
					MemoDao.insert(db, memo);
				}
				dbHelper.close();
				// クリップボードにコピー
				cm.setText(memo);
				// 入力フォームをクリア
				inputForm.setText("");
				
				Toast.makeText(MainActivity.this, memo + "\nをクリップボードにコピーしました。", Toast.LENGTH_SHORT).show();
				// finish();
				Intent intent = new Intent(MainActivity.this,
						MemoListActivity.class);
				startActivity(intent);
			}
			else {
				Toast.makeText(MainActivity.this, "入力して下さい。", Toast.LENGTH_SHORT).show();
			}
		}
	}

	class ClearClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// 入力フォームをクリア
			inputForm.setText("");
		}
	}
	
	class SpeakButtonClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			startVoiceRecognitionActivity();
		}
	}
	
	class OtherAppliButtonClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			String inputText = inputForm.getText().toString().trim();
			if (inputText.length() > 0) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_SEND);
				intent.setType("text/plain");
	            intent.putExtra(Intent.EXTRA_TEXT, inputText);
				startActivity(intent);
			}
			else {
				Toast.makeText(MainActivity.this, "入力して下さい。", Toast.LENGTH_SHORT).show();
			}
		}
	}
}