package neko.memo.view;

import neko.memo.R;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ButtonContena extends LinearLayout {
	private Button button;
	private TextView buttonText;
	
	private Context mContext;
	private int resourceId;
	private String underText;
	private int textColor = Color.BLACK;
	private OnClickListener listener;
	
	
	public ButtonContena(Context context, int resId, String buttonTitle) {
		super(context);
		
		this.mContext = context;
		this.resourceId = resId;
		this.underText = buttonTitle;

		button = new Button(context);
		buttonText = new TextView(mContext);
		
		setOrientation(LinearLayout.VERTICAL);
		
		button.setGravity(Gravity.CENTER_HORIZONTAL);
		button.setBackgroundResource(resourceId);
		button.setOnClickListener(listener);
		addView(button);
		
		buttonText.setText(underText);
		buttonText.setTextColor(textColor);
		buttonText.setGravity(Gravity.CENTER_HORIZONTAL);
		buttonText.setHeight(100);
		addView(buttonText);
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}
	
	public void setEnabled(boolean enabled) {
		this.button.setEnabled(enabled);
	}
	
	public void setOnClickListener(OnClickListener listener) {
		button.setOnClickListener(listener);
	}
}
