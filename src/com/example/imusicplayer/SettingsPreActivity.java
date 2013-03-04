package com.example.imusicplayer;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingsPreActivity extends PreferenceActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings_pre);
	}
}
