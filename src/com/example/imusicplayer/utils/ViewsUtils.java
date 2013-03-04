package com.example.imusicplayer.utils;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.example.imusicplayer.IApplication;
import com.example.imusicplayer.R;
import com.example.imusicplayer.entity.MusicDataBean;

public class ViewsUtils {
	public static ArrayList<View> getPagerViews(Context context) {
		ArrayList<View> list = new ArrayList<View>();
		ImageView iv;
		MusicDataBean bean = IApplication.listLocalMusics.get(IApplication
				.getCurrentIndex());
		if (bean.musicAlbumPicPath != null || "".equals(bean.musicAlbumPicPath)
				|| "null".equals(bean.musicAlbumPicPath)) {
			Bitmap bm = BitmapFactory.decodeFile(bean.musicAlbumPicPath);
			if (bm != null) {
				iv = createReflectedImages(bm);
				iv.setTag("ALBUM");
				iv.setScaleType(ScaleType.FIT_CENTER);
				list.add(iv);
				bm = null;
				System.gc();
			} else {
				iv = new ImageView(context);
				iv.setImageResource(R.drawable.album);
				iv.setTag("SILLY");
				iv.setScaleType(ScaleType.FIT_CENTER);
				list.add(iv);
			}
		}
		return list;
	}

	/**
	 * 创建倒影效果
	 * 
	 * @return
	 */
	public static ImageView createReflectedImages(Bitmap originalImage) {
		final int reflectionGap = 4;
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
				height / 2, width, height / 2, matrix, false);
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 2), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(originalImage, 0, 0, null);
		Paint deafaultPaint = new Paint();
		deafaultPaint.setAntiAlias(false);
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
		Paint paint = new Paint();
		paint.setAntiAlias(false);
		LinearGradient shader = new LinearGradient(0,
				originalImage.getHeight(), 0, bitmapWithReflection.getHeight()
						+ reflectionGap, 0x70ffffff, 0x00ffffff,
				TileMode.MIRROR);
		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(
				android.graphics.PorterDuff.Mode.DST_IN));
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		ImageView imageView = new ImageView(IApplication.GLOBAL_CONTEXT);
		imageView.setImageBitmap(bitmapWithReflection);
		return imageView;
	}
}
