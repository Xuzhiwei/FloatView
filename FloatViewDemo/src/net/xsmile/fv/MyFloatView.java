package net.xsmile.fv;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;

public class MyFloatView extends ImageView {
	private float mTouchStartX, mTouchEndX;
	private float mTouchStartY, mTouchEndY;
	private float x;
	private float y;
	private OnClickListener mClickListener;	
	
	private WindowManager wm = (WindowManager) getContext()
			.getApplicationContext().getSystemService("window");

	// 此wmParams为获取的全局变量，用以保存悬浮窗口的属性
	private WindowManager.LayoutParams wmParams = ((MyApplication) getContext()
			.getApplicationContext()).getMywmParams();

	public MyFloatView(Context context) {		
		super(context);		
		// TODO Auto-generated constructor stub
	}
	
	public void setOnClickListener(OnClickListener  l) {
		Log.i("0.0", "setOnClickListener");
		this.mClickListener = l;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 获取相对屏幕的坐标，即以屏幕左上角为原点
		x = event.getRawX();
		y = event.getRawY() - 25; // 25是系统状态栏的高度
		Log.i("currP", "currX" + x + "====currY" + y);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 获取相对View的坐标，即以此View左上角为原点
			mTouchStartX = event.getX();
			mTouchStartY = event.getY();
			// Log.i("0.0", "111startX" + mTouchStartX + "====startY"
			// + mTouchStartY);

			break;
		case MotionEvent.ACTION_MOVE:
			// Log.i("0.0", "222startX" + mTouchStartX + "====startY"
			// + mTouchStartY);
			updateViewPosition();
			break;

		case MotionEvent.ACTION_UP:
			mTouchEndX = event.getX();
			mTouchEndY = event.getY();
			if ((mTouchEndX == mTouchStartX) && (mTouchEndY == mTouchStartY)) {
				Log.i("0.0", "myclick");							
				if(mClickListener!=null) {
					mClickListener.onClick(this);
				}
			} else {
				Log.i("0.0", "touch");				
			}
			// Log.i("0.0", "333startX" + mTouchStartX + "====startY"
			// + mTouchStartY);
			updateViewPosition();
			mTouchStartX = mTouchStartY = 0;
			break;

		}
		return true;
	}

	private void updateViewPosition() {
		// 更新浮动窗口位置参数
		wmParams.x = (int) (x - mTouchStartX);
		wmParams.y = (int) (y - mTouchStartY);
		wm.updateViewLayout(this, wmParams);

	}
}
