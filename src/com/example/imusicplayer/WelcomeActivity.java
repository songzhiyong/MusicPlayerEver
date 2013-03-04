package com.example.imusicplayer;

import java.io.File;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import com.example.imusicplayer.entity.GridItemInfo;
import com.example.imusicplayer.utils.LocalMusicUtils;
import com.example.imusicplayer.utils.XMLUtils;

public class WelcomeActivity extends Activity {

	private Animation part1Animation;
	private Animation part2Animation;
	private Animation part3Animation;
	private Animation part4Animation;

	private Handler endAnimationHandler;
	private Runnable endAnimationRunnable;
//	 private DrawingThread mDrawingThread;
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		mDrawingThread = new DrawingThread();
//		mDrawingThread.start();
		setContentView(R.layout.welcome);
		part1Animation = AnimationUtils.loadAnimation(this, R.anim.part4_out);
		part2Animation = AnimationUtils.loadAnimation(this, R.anim.part3_out);
		part3Animation = AnimationUtils.loadAnimation(this, R.anim.part2_out);
		part4Animation = AnimationUtils.loadAnimation(this, R.anim.part1_out);
		part1Animation.setFillAfter(true);
		part2Animation.setFillAfter(true);
		part3Animation.setFillAfter(true);
		part4Animation.setFillAfter(true);
//		   mDrawingThread = new DrawingThread();
		endAnimationHandler = new Handler();
		
		endAnimationRunnable = new Runnable() {
			@Override
			public void run() {
				findViewById(R.id.ivPart1).startAnimation(part1Animation);
				findViewById(R.id.ivPart2).startAnimation(part2Animation);
				findViewById(R.id.ivPart3).startAnimation(part3Animation);
				findViewById(R.id.ivPart4).startAnimation(part4Animation);
			}
		};

		part1Animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
//				Log.i("info", "surfaceView^^^");
//				WelcomeActivity.this.getWindow().takeSurface(WelcomeActivity.this);
//				 mDrawingThread.start();
				Log.i("info", "--0--:" + System.currentTimeMillis());
				MainActivity.launch(WelcomeActivity.this);
				Log.i("info", "--00--:" + System.currentTimeMillis());
				WelcomeActivity.this.finish();

			}
		});
		Thread initDataThread = new Thread() {
			public void run() {
				initData();
				IApplication.gridItems = loadItemInfos();
			};
		};
		endAnimationHandler.removeCallbacks(endAnimationRunnable);
		endAnimationHandler.postDelayed(endAnimationRunnable, 5000);
		initDataThread.start();
		initDataThread = null;
		System.gc();
	}

	private void initData() {
		try {
			if (new File("/data/data/com.example.imusicplayer/files/localMusicInfos.xml").exists()) {
				IApplication.listLocalMusics = XMLUtils.parserLocalList();
			}else {
				IApplication.listLocalMusics = LocalMusicUtils.getMusics();
				XMLUtils.saveToXML(IApplication.listLocalMusics);
			}
			if (new File(
					"/data/data/com.example.imusicplayer/files/onlineAlbumsInfo.xml")
					.exists()) {
				Log.i("info", "存在专辑缓存");
				IApplication.albumCacheBeans = XMLUtils
						.parserOnlineAlbumCache();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		  synchronized (mDrawingThread) {
//	            mDrawingThread.mQuit = true;
//	            mDrawingThread.notify();
//	        }
	}
	public ArrayList<GridItemInfo> loadItemInfos() {
		ArrayList<GridItemInfo> infos = new ArrayList<GridItemInfo>();
		infos.add(new GridItemInfo("全部歌曲", IApplication.listLocalMusics.size()
				+ "首歌曲", R.drawable.list_icon_media));
		infos.add(new GridItemInfo("我的最爱", "0首歌曲",
				R.drawable.list_icon_favourite));
		infos.add(new GridItemInfo("最近播放", "0首歌曲", R.drawable.list_icon_recent));
		infos.add(new GridItemInfo("歌手", "0位歌手", R.drawable.list_icon_artist));
		infos.add(new GridItemInfo("专辑", "0张专辑", R.drawable.list_icon_album));
		infos.add(new GridItemInfo("文件夹", "0个文件夹", R.drawable.list_icon_folder));
		infos.add(new GridItemInfo("新建列表", "", R.drawable.list_icon_new_list));
		return infos;
	}

//	@Override
//	public void surfaceChanged(SurfaceHolder holder, int format, int width,
//			int height) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void surfaceCreated(SurfaceHolder holder) {
//		synchronized (mDrawingThread) {
//            mDrawingThread.mSurface = holder;
//            mDrawingThread.notify();
//        }
//		
//	}
//
//	@Override
//	public void surfaceDestroyed(SurfaceHolder holder) {
//		 synchronized (mDrawingThread) {
//	            mDrawingThread.mSurface = holder;
//	            mDrawingThread.notify();
//	            while (mDrawingThread.mActive) {
//	                try {
//	                    mDrawingThread.wait();
//	                } catch (InterruptedException e) {
//	                    e.printStackTrace();
//	                }
//	            }
//	        }
//	}
//
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		 synchronized (mDrawingThread) {
//	            mDrawingThread.mRunning = true;
//	            mDrawingThread.notify();
//	        }
//	}
//	@Override
//	public void surfaceRedrawNeeded(SurfaceHolder holder) {
//		// TODO Auto-generated method stub
//		
//	}
//	class DrawingThread extends Thread {
//        // These are protected by the Thread's lock.
//        SurfaceHolder mSurface;
//        boolean mRunning;
//        boolean mActive;
//        boolean mQuit;
//        
//        // Internal state.
//        int mLineWidth;
//        float mMinStep;
//        float mMaxStep;
//        
//        boolean mInitialized;
//        final MovingPoint mPoint1 = new MovingPoint();
//        final MovingPoint mPoint2 = new MovingPoint();
//        
//        static final int NUM_OLD = 100;
//        int mNumOld = 0;
//        final float[] mOld = new float[NUM_OLD*4];
//        final int[] mOldColor = new int[NUM_OLD];
//        int mBrightLine = 0;
//        
//        // X is red, Y is blue.
//        final MovingPoint mColor = new MovingPoint();
//        
//        final Paint mBackground = new Paint();
//        final Paint mForeground = new Paint();
//        
//        int makeGreen(int index) {
//            int dist = Math.abs(mBrightLine-index);
//            if (dist > 10) return 0;
//            return (255-(dist*(255/10))) << 8;
//        }
//        
//        @Override
//        public void run() {
//            mLineWidth = (int)(getResources().getDisplayMetrics().density * 1.5);
//            if (mLineWidth < 1) mLineWidth = 1;
//            mMinStep = mLineWidth * 2;
//            mMaxStep = mMinStep * 3;
//            
//            mBackground.setColor(0xff000000);
//            mForeground.setColor(0xff00ffff);
//            mForeground.setAntiAlias(false);
//            mForeground.setStrokeWidth(mLineWidth);
//            
//            while (true) {
//                // Synchronize with activity: block until the activity is ready
//                // and we have a surface; report whether we are active or inactive
//                // at this point; exit thread when asked to quit.
//                synchronized (this) {
//                    while (mSurface == null || !mRunning) {
//                        if (mActive) {
//                            mActive = false;
//                            notify();
//                        }
//                        if (mQuit) {
//                            return;
//                        }
//                        try {
//                            wait();
//                        } catch (InterruptedException e) {
//                        }
//                    }
//                    
//                    if (!mActive) {
//                        mActive = true;
//                        notify();
//                    }
//                    
//                    // Lock the canvas for drawing.
//                    Canvas canvas = mSurface.lockCanvas();
//                    if (canvas == null) {
//                        Log.i("WindowSurface", "Failure locking canvas");
//                        continue;
//                    }
//                    
//                    // Update graphics.
//                    if (!mInitialized) {
//                        mInitialized = true;
//                        mPoint1.init(canvas.getWidth(), canvas.getHeight(), mMinStep);
//                        mPoint2.init(canvas.getWidth(), canvas.getHeight(), mMinStep);
//                        mColor.init(127, 127, 1);
//                    } else {
//                        mPoint1.step(canvas.getWidth(), canvas.getHeight(),
//                                mMinStep, mMaxStep);
//                        mPoint2.step(canvas.getWidth(), canvas.getHeight(),
//                                mMinStep, mMaxStep);
//                        mColor.step(127, 127, 1, 3);
//                    }
//                    mBrightLine+=2;
//                    if (mBrightLine > (NUM_OLD*2)) {
//                        mBrightLine = -2;
//                    }
//                    
//                    // Clear background.
//                    canvas.drawColor(mBackground.getColor());
//                    
//                    // Draw old lines.
//                    for (int i=mNumOld-1; i>=0; i--) {
//                        mForeground.setColor(mOldColor[i] | makeGreen(i));
//                        mForeground.setAlpha(((NUM_OLD-i) * 255) / NUM_OLD);
//                        int p = i*4;
//                        canvas.drawLine(mOld[p], mOld[p+1], mOld[p+2], mOld[p+3], mForeground);
//                    }
//                    
//                    // Draw new line.
//                    int red = (int)mColor.x + 128;
//                    if (red > 255) red = 255;
//                    int blue = (int)mColor.y + 128;
//                    if (blue > 255) blue = 255;
//                    int color = 0xff000000 | (red<<16) | blue;
//                    mForeground.setColor(color | makeGreen(-2));
//                    canvas.drawLine(mPoint1.x, mPoint1.y, mPoint2.x, mPoint2.y, mForeground);
//                    
//                    // Add in the new line.
//                    if (mNumOld > 1) {
//                        System.arraycopy(mOld, 0, mOld, 4, (mNumOld-1)*4);
//                        System.arraycopy(mOldColor, 0, mOldColor, 1, mNumOld-1);
//                    }
//                    if (mNumOld < NUM_OLD) mNumOld++;
//                    mOld[0] = mPoint1.x;
//                    mOld[1] = mPoint1.y;
//                    mOld[2] = mPoint2.x;
//                    mOld[3] = mPoint2.y;
//                    mOldColor[0] = color;
//                    
//                    // All done!
//                    mSurface.unlockCanvasAndPost(canvas);
//                }
//            }
//        }
//    }
//	static final class MovingPoint {
//        float x, y, dx, dy;
//        
//        void init(int width, int height, float minStep) {
//            x = (float)((width-1)*Math.random());
//            y = (float)((height-1)*Math.random());
//            dx = (float)(Math.random()*minStep*2) + 1;
//            dy = (float)(Math.random()*minStep*2) + 1;
//        }
//        
//        float adjDelta(float cur, float minStep, float maxStep) {
//            cur += (Math.random()*minStep) - (minStep/2);
//            if (cur < 0 && cur > -minStep) cur = -minStep;
//            if (cur >= 0 && cur < minStep) cur = minStep;
//            if (cur > maxStep) cur = maxStep;
//            if (cur < -maxStep) cur = -maxStep;
//            return cur;
//        }
//        
//        void step(int width, int height, float minStep, float maxStep) {
//            x += dx;
//            if (x <= 0 || x >= (width-1)) {
//                if (x <= 0) x = 0;
//                else if (x >= (width-1)) x = width-1;
//                dx = adjDelta(-dx, minStep, maxStep);
//            }
//            y += dy;
//            if (y <= 0 || y >= (height-1)) {
//                if (y <= 0) y = 0;
//                else if (y >= (height-1)) y = height-1;
//                dy = adjDelta(-dy, minStep, maxStep);
//            }
//        }
//    }

}
