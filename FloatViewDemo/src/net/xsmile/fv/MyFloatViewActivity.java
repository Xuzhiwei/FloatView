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
		// ������������
		createView();
	}

	private void createView() {
		myFV = new MyFloatView(getApplicationContext());
		myFV.setOnClickListener(this);
		myFV.setImageResource(R.drawable.assistivetouch);
		// ��ȡWindowManager
		wm = (WindowManager) getApplicationContext().getSystemService("window");
		// ����LayoutParams(ȫ�ֱ�������ز���
		wmParams = ((MyApplication) getApplication()).getMywmParams();

		/**
		 * ���¶���WindowManager.LayoutParams��������� ������;�ɲο�SDK�ĵ�
		 */
		wmParams.type = LayoutParams.TYPE_PHONE; // ����window type
		wmParams.format = PixelFormat.RGBA_8888; // ����ͼƬ��ʽ��Ч��Ϊ����͸��

		// ����Window flag
		wmParams.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
				| LayoutParams.FLAG_NOT_FOCUSABLE;
		/*
		 * �����flags���Ե�Ч����ͬ���������� ���������ɴ������������κ��¼�,ͬʱ��Ӱ�������¼���Ӧ��
		 * wmParams.flags=LayoutParams.FLAG_NOT_TOUCH_MODAL |
		 * LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCHABLE;
		 */

		wmParams.gravity = Gravity.LEFT | Gravity.TOP; // �����������������Ͻ�
		// ����Ļ���Ͻ�Ϊԭ�㣬����x��y��ʼֵ
		wmParams.x = 0;
		wmParams.y = 0;

		// �����������ڳ�������
		wmParams.width = 40;
		wmParams.height = 40;

		// ��ʾmyFloatViewͼ��
		wm.addView(myFV, wmParams);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// �ڳ����˳�(Activity���٣�ʱ������������
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