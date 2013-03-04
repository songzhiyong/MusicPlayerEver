package com.example.imusicplayer.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class PlayViewAdapter extends PagerAdapter {
	private ArrayList<View> views;

	public void setViews(ArrayList<View> views) {
		this.views = views;
	}

	public PlayViewAdapter(ArrayList<View> views) {
		if (views != null)
			this.views = views;
		else
			this.views = new ArrayList<View>();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return views.size();
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		View view = views.get(position);
		((ViewPager) container).removeView(view);
	}

	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		View view = views.get(position);
		((ViewPager) container).addView(view);
		return view;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}
}
