package com.sparklinktech.stech.syncadaptertestdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ReportsActivity extends AppCompatActivity
{
    ListView lv;
    String[] list = {"Alphabetical List","Agewise List","Villagewise List","Partwise List",
            "Addresswise List","Surnamewise List","NativePlacewise List","New Addresswise List"
            ,"Birthdatewise List","Anniversarywise List","Occupationwise List","Dead Voters"
            ,"Poling Stationwise List","Castwise List","Voters Having Phone Numbers","Voted Voters List"
            ,"Non Voted Voter List","New Voter List","Print Report"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_activity);

        //ArrayAdapter aa = new ArrayAdapter(getApplicationContext(),R.layout.list_view,list);

        lv =(ListView)findViewById(R.id.report_listview);
        //lv.setAdapter(aa);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Log.e("1  ","<<<< "+adapterView.getItemAtPosition(position).toString());
                Toast.makeText(ReportsActivity.this,
                        ""+adapterView.getItemAtPosition(position).toString().trim(),
                        Toast.LENGTH_SHORT).show();

                Intent i =new Intent(ReportsActivity.this,SearchSQLiteActivity.class);
                i.putExtra("key",""+adapterView.getItemAtPosition(position).toString().trim());
                startActivity(i);
            }
        });


    }


}
