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

	private WindowManager wm = (WindowManager) getContext()
			.getApplicationContext().getSystemService("window");

	// ��wmParamsΪ��ȡ��ȫ�ֱ��������Ա����������ڵ�����
	private WindowManager.LayoutParams wmParams = ((MyApplication) getContext()
			.getApplicationContext()).getMywmParams();

	public MyFloatView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		boolean b_click = false;
		// ��ȡ�����Ļ�����꣬������Ļ���Ͻ�Ϊԭ��
		x = event.getRawX();
		y = event.getRawY() - 25; // 25��ϵͳ״̬���ĸ߶�
		Log.i("currP", "currX" + x + "====currY" + y);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// ��ȡ���View�����꣬���Դ�View���Ͻ�Ϊԭ��
			mTouchStartX = event.getX();
			mTouchStartY = event.getY();
//			Log.i("0.0", "111startX" + mTouchStartX + "====startY"
//					+ mTouchStartY);

			break;
		case MotionEvent.ACTION_MOVE:
//			Log.i("0.0", "222startX" + mTouchStartX + "====startY"
//					+ mTouchStartY);
			updateViewPosition();
			break;

		case MotionEvent.ACTION_UP:
			mTouchEndX = event.getX();
			mTouchEndY = event.getY();
			if ((mTouchEndX == mTouchStartX) && (mTouchEndY == mTouchStartY)){
				Log.i("0.0", "click");
				b_click = true;
			}else{
				Log.i("0.0", "touch");
				b_click = false;
			}
//			Log.i("0.0", "333startX" + mTouchStartX + "====startY"
//					+ mTouchStartY);
			updateViewPosition();
			mTouchStartX = mTouchStartY = 0;
			break;

		}
		if (b_click == false)
			return true;
		else
			return false;
	}

	
	private void updateViewPosition() {
		// ���¸�������λ�ò���
		wmParams.x = (int) (x - mTouchStartX);
		wmParams.y = (int) (y - mTouchStartY);
		wm.updateViewLayout(this, wmParams);

	}

}
