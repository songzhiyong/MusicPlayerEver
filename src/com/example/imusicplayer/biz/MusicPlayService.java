package com.example.imusicplayer.biz;

import java.io.IOException;
import java.util.Random;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.example.imusicplayer.IApplication;
import com.example.imusicplayer.MainActivity;
import com.example.imusicplayer.R;
import com.example.imusicplayer.entity.MusicDataBean;
import com.example.imusicplayer.local.LocalMusicActivity;
import com.example.imusicplayer.utils.GlobalUtils;
import com.example.imusicplayer.utils.LocalMusicUtils;

public class MusicPlayService extends Service {
	private MediaPlayer mPlayer;
	private boolean isPause;
	private int playMode;
	private Random random;
	private ServiceReceiver receiver;
	private boolean needUpdate;
	private Thread workThread;
	private boolean isLoop;
	private TelephonyManager telMgr;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	mPhoneCallListener listener;

	@Override
	public void onCreate() {
		Notification notification = new Notification(R.drawable.icon,
				"Foreground Service Started.", System.currentTimeMillis());
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, MainActivity.class), 0);
		notification.setLatestEventInfo(this, "Foreground Service",
				"Foreground Service Started.", contentIntent);
		// 注意使用 startForeground ，id 为 0 将不会显示 notification
		startForeground(1, notification);
		super.onCreate();
		random = new Random();
		mPlayer = new MediaPlayer();
		telMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		listener = new mPhoneCallListener();
		telMgr.listen(listener, mPhoneCallListener.LISTEN_CALL_STATE);
		mPlayer.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				next();
			}
		});
		playMode = GlobalUtils.PLAY_MODE_LOOP;
		receiver = new ServiceReceiver();
		needUpdate = true;
		isLoop = true;
		workThread = new Thread() {
			public void run() {
				while (isLoop) {
					while (needUpdate && mPlayer.isPlaying()) {
						Intent intent = new Intent();
						intent.setAction(GlobalUtils.ACTION_UPDATE_PROGRESS);
						intent.putExtra(GlobalUtils.EXTRA_CUR_PROGRESS,
								mPlayer.getCurrentPosition());
						intent.putExtra(GlobalUtils.EXTRA_DURATION,
								mPlayer.getDuration());
						sendBroadcast(intent);
						try {
							sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					synchronized (this) {
						try {
							wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}
			};
		};
		workThread.start();
		IntentFilter filter = new IntentFilter();
		filter.addAction(GlobalUtils.ACTION_PLAY);
		filter.addAction(GlobalUtils.ACTION_PAUSE);
		filter.addAction(GlobalUtils.ACTION_PREVIOUS);
		filter.addAction(GlobalUtils.ACTION_NEXT);
		filter.addAction(GlobalUtils.ACTION_STOP);
		filter.addAction(GlobalUtils.ACTION_PLAY_MODE_CHANGED);
		filter.addAction(GlobalUtils.ACTION_SEEK_TO);
		filter.addAction(GlobalUtils.ACTION_UPDATE_STATE_CHANGED);
		filter.addAction(GlobalUtils.ACTION_REQUEST_PLAY_STATE);
		filter.addAction(GlobalUtils.ACTION_DELETE);
		filter.addAction(GlobalUtils.ACTION_SERVICE_EXIT);
		registerReceiver(receiver, filter);
	}

	private static int curIndex;

	public void play() {
		int recentIndex = IApplication.getCurrentIndex();
		if (isPause && recentIndex == curIndex) {
			mPlayer.start();
		} else {
			try {
				curIndex = IApplication.getCurrentIndex();
				if (curIndex >= 0
						&& curIndex < IApplication.listLocalMusics.size()) {
					mPlayer.reset();
					MusicDataBean music = IApplication.listLocalMusics
							.get(curIndex);
					mPlayer.setDataSource(music.musicPath);
					mPlayer.prepare();
					mPlayer.start();
					IApplication.setCurrentIndex(curIndex);
					Intent intent = new Intent();
					intent.setAction(GlobalUtils.ACTION_CUR_MUSIC_CHANGED);
					intent.putExtra(GlobalUtils.EXTRA_MUSIC, music);
					intent.putExtra(GlobalUtils.EXTRA_DURATION,
							mPlayer.getDuration());
					sendStickyBroadcast(intent);
					IApplication.listRecentPlayMusics.add(music);
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		isPause = false;
		synchronized (workThread) {
			workThread.notify();
		}
		Intent intent1 = new Intent(GlobalUtils.ACTION_PLAY_STATE_CHANGED);
		intent1.putExtra(GlobalUtils.EXTRA_PLAY_STATE, mPlayer.isPlaying());
		sendBroadcast(intent1);
	}

	public void pause() {
		int recentIndex = IApplication.getCurrentIndex();
		if (mPlayer.isPlaying() && recentIndex == curIndex) {
			mPlayer.pause();
			isPause = true;
		} else {
			play();
		}
		Intent intent1 = new Intent(GlobalUtils.ACTION_PLAY_STATE_CHANGED);
		intent1.putExtra(GlobalUtils.EXTRA_PLAY_STATE, mPlayer.isPlaying());
		sendBroadcast(intent1);
	}

	public void stop() {
		mPlayer.stop();
	}

	public void previous() {

		int curIndex = IApplication.getCurrentIndex();
		switch (playMode) {
		case GlobalUtils.PLAY_MODE_LOOP:
			if (--curIndex < 0) {
				curIndex = IApplication.listLocalMusics.size() - 1;
			}
			break;
		case GlobalUtils.PLAY_MODE_ORDER:
			if (--curIndex < 0) {
				stop();
			}
			break;
		case GlobalUtils.PLAY_MODE_RANDOM:
			curIndex = random.nextInt(IApplication.listLocalMusics.size());
			break;
		case GlobalUtils.PLAY_MODE_SINGLE_LOOP:
			break;
		}
		IApplication.setCurrentIndex(curIndex);
		isPause = false;
		play();
	}

	public void next() {
		int curIndex = IApplication.getCurrentIndex();
		switch (playMode) {
		case GlobalUtils.PLAY_MODE_LOOP:
			if (++curIndex == IApplication.listLocalMusics.size()) {
				curIndex = 0;
			}
			break;
		case GlobalUtils.PLAY_MODE_ORDER:
			if (++curIndex == IApplication.listLocalMusics.size()) {
				stop();
			}
			break;
		case GlobalUtils.PLAY_MODE_RANDOM:
			curIndex = random.nextInt(IApplication.listLocalMusics.size());
			break;
		case GlobalUtils.PLAY_MODE_SINGLE_LOOP:
			break;
		}
		IApplication.setCurrentIndex(curIndex);
		isPause = false;
		play();
	}

	public void seekTo(int position) {
		mPlayer.seekTo(position);
		mPlayer.start();
		isPause = false;
		synchronized (workThread) {
			workThread.notify();
		}
		Intent intent = new Intent(GlobalUtils.ACTION_PLAY_STATE_CHANGED);
		intent.putExtra(GlobalUtils.EXTRA_PLAY_STATE, mPlayer.isPlaying());
		sendBroadcast(intent);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mPlayer.release();
		unregisterReceiver(receiver);
		isLoop = false;
		synchronized (workThread) {
			workThread.notify();
		}
	}

	private class ServiceReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (GlobalUtils.ACTION_PLAY.equals(action)) {
				play();
			} else if (GlobalUtils.ACTION_PAUSE.equals(action)) {
				pause();
			} else if (GlobalUtils.ACTION_PREVIOUS.equals(action)) {
				previous();
			} else if (GlobalUtils.ACTION_NEXT.equals(action)) {
				next();
			} else if (GlobalUtils.ACTION_PLAY_MODE_CHANGED.equals(action)) {
				playMode = intent.getIntExtra(GlobalUtils.EXTRA_PLAY_MODE,
						GlobalUtils.PLAY_MODE_LOOP);
			} else if (GlobalUtils.ACTION_STOP.equals(action)) {
				stop();
			} else if (GlobalUtils.ACTION_SEEK_TO.equals(action)) {
				int position = intent.getIntExtra(
						GlobalUtils.EXTRA_CUR_PROGRESS, -1);
				if (position != -1) {
					seekTo(position * mPlayer.getDuration() / 100);
				}
			} else if (GlobalUtils.ACTION_UPDATE_STATE_CHANGED.equals(action)) {
				needUpdate = intent.getBooleanExtra(
						GlobalUtils.EXTRA_NEED_UPDATE, true);
				synchronized (workThread) {
					workThread.notify();
				}
			} else if (GlobalUtils.ACTION_SERVICE_EXIT.equals(action)) {
				stopSelf();
			} else if (GlobalUtils.ACTION_REQUEST_PLAY_STATE.equals(action)) {
				Intent intent2 = new Intent();
				intent2.setAction(GlobalUtils.ACTION_PLAY_STATE_CHANGED);
				intent2.putExtra(GlobalUtils.EXTRA_PLAY_STATE,
						mPlayer.isPlaying());
				sendBroadcast(intent2);
			} else if (GlobalUtils.ACTION_DELETE.equals(action)) {
				stop();
				mPlayer.reset();
				LocalMusicUtils.delete();
				Intent intent3 = new Intent();
				intent3.setAction(GlobalUtils.ACTION_UPDATE_LIST);
				sendBroadcast(intent3);
			}
		}
	}

	/* 判断PhoneStateListener现在的状态 */
	public class mPhoneCallListener extends PhoneStateListener {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			switch (state) {
			/* 判断手机目前是待机状态 */
			case TelephonyManager.CALL_STATE_IDLE:
				break;
			/* 判断状态为通话中 */
			case TelephonyManager.CALL_STATE_OFFHOOK:
				break;
			/* 判断状态为来电 */
			case TelephonyManager.CALL_STATE_RINGING:
				pause();
				break;
			}
			super.onCallStateChanged(state, incomingNumber);
		}
	}

}
