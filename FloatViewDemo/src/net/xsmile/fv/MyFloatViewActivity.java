package net.xsmile.fv;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class MyFloatViewActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */

	private WindowManager wm = null;
	private WindowManager.LayoutParams wmParams = null;

	private MyFloatView myFV = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 创建悬浮窗口
		createView();
	}

	private void createView() {
		myFV = new MyFloatView(getApplicationContext());
		myFV.setOnClickListener(this);
		myFV.setImageResource(R.drawable.assistivetouch);
		// 获取WindowManager
		wm = (WindowManager) getApplicationContext().getSystemService("window");
		// 设置LayoutParams(全局变量）相关参数
		wmParams = ((MyApplication) getApplication()).getMywmParams();

		/**
		 * 以下都是WindowManager.LayoutParams的相关属性 具体用途可参考SDK文档
		 */
		wmParams.type = LayoutParams.TYPE_PHONE; // 设置window type
		wmParams.format = PixelFormat.RGBA_8888; // 设置图片格式，效果为背景透明

		// 设置Window flag
		wmParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
				| LayoutParams.FLAG_NOT_FOCUSABLE;
		/*
		 * 下面的flags属性的效果形同“锁定”。 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应。
		 * wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL |
		 * LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCHABLE;
		 */

		wmParams.gravity = Gravity.LEFT | Gravity.TOP; // 调整悬浮窗口至左上角
		// 以屏幕左上角为原点，设置x、y初始值
		wmParams.x = 0;
		wmParams.y = 0;

		// 设置悬浮窗口长宽数据
		wmParams.width = 40;
		wmParams.height = 40;

		// 显示myFloatView图像
		wm.addView(myFV, wmParams);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 在程序退出(Activity销毁）时销毁悬浮窗口
		wm.removeView(myFV);
	}

	public void onClick(View v) {
		
		List<String> names = getAllTheLauncher();
		
		Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);
        
		// Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();

//		 Intent it = new Intent("android.provider.Settings.ACTION_SETTINGS");
//		 it.setComponent(new ComponentName(context.getPackageName(),
//		 MyMainActivity.class.getName()));
//		 it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		 startActivity(it);

//		 Intent intent=new
//		 Intent("android.provider.Settings.ACTION_SETTINGS");
//		 ComponentName cName = new
//		 ComponentName("com.android.phone","com.android.phone.Settings");
//		 intent.setComponent(cName);
//		 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		 startActivity(intent);

//		 Intent intent = new Intent(
//		 android.provider.Settings.ACTION_SETTINGS);
//		 startActivityForResult( intent , 0);
		 Log.i("0.0", "onClick");
	}

	private List<String> getAllTheLauncher() {

		List<String> names = null;
		PackageManager pkgMgt = this.getPackageManager();
		Intent it = new Intent(Intent.ACTION_MAIN);
		it.addCategory(Intent.CATEGORY_HOME);
		List<ResolveInfo> ra = pkgMgt.queryIntentActivities(it, 0);
		if (ra.size() != 0) {
			names = new ArrayList<String>();
		}
		for (int i = 0; i < ra.size(); i++) {
			String packageName = ra.get(i).activityInfo.packageName;
			names.add(packageName);
			Log.i("0.0", "packageName="+packageName);
		}
		return names;
	}

}