package neko.memo.activity;

import neko.memo.R;
import neko.memo.constant.Constant;
import neko.memo.renderer.MyRenderer;
import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class BaseActivity extends Activity {
	protected ClipboardManager cm;
	protected GLSurfaceView glSurfaceView;
	
	protected final int FP = ViewGroup.LayoutParams.FILL_PARENT;
	protected final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
	
	protected RelativeLayout baseLayout;
	
	protected int screenWidth;
	protected int screenHeight;
	
	enum ScreenName {
		MEMO_INPUT,
		MEMO_LIST
	}
	
	protected static ScreenName currentScreen;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		Display display = getWindowManager().getDefaultDisplay(); 
		screenWidth = display.getWidth();
		screenHeight = display.getHeight();
		this.cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		
        // フルスクリーン表示
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        MyRenderer renderer = new MyRenderer(getApplicationContext());
        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setRenderer(renderer);
        
        baseLayout = new RelativeLayout(this);
        baseLayout.addView(glSurfaceView, new LinearLayout.LayoutParams(FP, FP));

		Button memoInputButton = new Button(this);
		memoInputButton.setId(Constant.ID_MEMO_INPUT_BUTTON);
		memoInputButton.setBackgroundResource(R.drawable.memo_screen_button);
		memoInputButton.setText("めも入力");
		memoInputButton.setWidth(screenWidth / 2);
		memoInputButton.setOnClickListener(new MemoInputClickListener());
		RelativeLayout.LayoutParams memoInputParam = 
				new RelativeLayout.LayoutParams(WC, WC);
		memoInputParam.setMargins(0, 0, 0, 0);
		baseLayout.addView(memoInputButton, memoInputParam);
        
		Button memoListButton = new Button(this);
		memoListButton.setId(Constant.ID_MEMO_LIST_BUTTON);
		memoListButton.setBackgroundResource(R.drawable.memo_screen_button);
		memoListButton.setText("めも一覧");
		memoListButton.setWidth(screenWidth / 2);
		RelativeLayout.LayoutParams memoListParam = 
				new RelativeLayout.LayoutParams(WC, WC);
		memoListParam.addRule(RelativeLayout.RIGHT_OF, Constant.ID_MEMO_INPUT_BUTTON);
		memoListParam.setMargins(0, 0, 0, 0);
		memoListButton.setOnClickListener(new MemoListClickListener());
		baseLayout.addView(memoListButton, memoListParam);
		
        setContentView(baseLayout, new RelativeLayout.LayoutParams(WC, WC));
      }  
      

	class MemoInputClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (currentScreen == ScreenName.MEMO_LIST) {
				finish();
			}
		}
	}
	class MemoListClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (currentScreen == ScreenName.MEMO_INPUT) {
				Intent intent = new Intent(BaseActivity.this,
						MemoListActivity.class);
				startActivity(intent);
			}
		}
	}
}
