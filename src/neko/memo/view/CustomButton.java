package neko.memo.view;

import neko.memo.R;
import android.content.Context;
import android.view.Gravity;
import android.widget.Button;

public class CustomButton extends Button {

	public CustomButton(Context context, int id, String title, int width, OnClickListener listner) {
		super(context);
		
		setBackgroundResource(R.drawable.memo_screen_button);
		setGravity(Gravity.CENTER);
		setId(id);
		setText(title);
		setWidth(width);
		setOnClickListener(listner);
		
		setTextSize(18);
	}
	
//	public void setTextSize(int size) {
//		setTextSize(18);
//	}
}
