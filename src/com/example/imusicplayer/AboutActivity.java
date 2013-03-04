package com.example.imusicplayer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

public class AboutActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.about);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		new Handler(){
			public void handleMessage(android.os.Message msg) {
				animationStart(views);
			};
		}.sendEmptyMessageDelayed(0, 500);
	}
	View[] views;
	private void animationStart(View[] views) {
		View target1 = findViewById(R.id.tvAppInfo);
		View target2 = findViewById(R.id.tvAppInfo1);
		View target3 = findViewById(R.id.tvAppInfo2);
		View target4 = findViewById(R.id.check);
		View target5 = findViewById(R.id.weibo);
		View target6 = findViewById(R.id.twitter);
		View target7 = findViewById(R.id.qq);
		View target8 = findViewById(R.id.email);
		View target9 = findViewById(R.id.copyright1);
		View target10 = findViewById(R.id.copyright2);
		views= new View[] { target1, target2, target3, target4,
				target5, target6, target7, target8, target9 ,target10};
		for (int i = 0; i < views.length; i++) {
			View view = views[i];
			View targetParent = findViewById(R.id.aboutlayout);
			view.setVisibility(View.VISIBLE);
			Animation a = new TranslateAnimation(0.0f, targetParent.getWidth()-view.getWidth(), 0.0f, 0.0f);
			a.setStartOffset(300*i);
			a.setDuration(900);
			a.setRepeatCount(1);
			a.setRepeatMode(Animation.REVERSE);
			a.setInterpolator(AnimationUtils.loadInterpolator(
					AboutActivity.this, android.R.anim.bounce_interpolator));
//			view.setAnimation(a);
//			a.start();
			 view.startAnimation(a);
		}
	}
}
