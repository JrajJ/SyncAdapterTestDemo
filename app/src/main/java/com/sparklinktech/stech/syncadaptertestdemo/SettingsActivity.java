package com.sparklinktech.stech.syncadaptertestdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class SettingsActivity extends AppCompatActivity
{
    ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        lv =(ListView)findViewById(R.id.setting_listview);

    }
}
