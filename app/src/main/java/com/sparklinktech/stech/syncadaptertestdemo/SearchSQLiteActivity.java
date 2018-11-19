package com.sparklinktech.stech.syncadaptertestdemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import android.widget.ListView;
import android.widget.Toast;

import com.sparklinktech.stech.syncadaptertestdemo.syncadapter.DatabaseClient;
import com.sparklinktech.stech.syncadaptertestdemo.syncadapter.ProductContract;

import java.util.ArrayList;
import java.util.Objects;

public class SearchSQLiteActivity extends AppCompatActivity
{
    String key="no";
    ListView listView;
    ArrayList<NewProduct> StudentList = new ArrayList<NewProduct>();
    DatabaseClient sqLiteHelper;
    ListAdapter listAdapter;
    EditText editText,editText2,editText3;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_sqlite);

        listView = (ListView) findViewById(R.id.listView1);

        editText = (EditText) findViewById(R.id.edittext1);
        editText2 = (EditText) findViewById(R.id.edittext2);

        editText3 = (EditText) findViewById(R.id.edittext3);


        listView.setTextFilterEnabled(true);

        sqLiteHelper = new DatabaseClient(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Getting Search ListView clicked item.
                NewProduct ListViewClickData = (NewProduct) parent.getItemAtPosition(position);

                // printing clicked item on screen using Toast message.
                Toast.makeText(SearchSQLiteActivity.this, ListViewClickData.getPname(), Toast.LENGTH_SHORT).show();
            }
        });




        editText.addTextChangedListener(new TextWatcher()
        {

            public void afterTextChanged(Editable s)
            {

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            public void onTextChanged(CharSequence stringVar, int start, int before, int count)
            {

                listAdapter.getFilter().filter(stringVar.toString());
                Log.e("Filtered TEXT       >>>","          "+stringVar.toString());


            }
        });

        editText2.addTextChangedListener(new TextWatcher()
        {


            public void afterTextChanged(Editable s)
            {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            public void onTextChanged(CharSequence stringVar, int start, int before, int count)
            {

                listAdapter.getFilter().filter(stringVar.toString());
                Log.e("Filtered TEXT       >>>","          "+stringVar.toString());


            }
        });

        editText3.addTextChangedListener(new TextWatcher()
        {

            public void afterTextChanged(Editable s)
            {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            public void onTextChanged(CharSequence stringVar, int start, int before, int count)
            {

                listAdapter.getFilter().filter(stringVar.toString());
                Log.e("Filtered TEXT       >>>","          "+stringVar.toString());


            }
        });

    }

    public void DisplayDataInToListView()
    {

        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        key = Objects.requireNonNull(getIntent().getExtras()).getString("key");

        assert key != null;
        if(key.equals("search"))
        {
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + ProductContract.Products.NAME+"", null);

        }
        else if(key.equals("Alphabetical List")||key.equals("वर्णक्रमानुसार यादी"))
            {
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+ ProductContract.Products.NAME+" ORDER BY 3 ASC", null);

            }
            else
        {
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + ProductContract.Products.NAME+"", null);

        }

        NewProduct student;
        StudentList = new ArrayList<>();

        if (cursor.moveToFirst())
        {
            do {

                String tempPname = cursor.getString(cursor.getColumnIndex(ProductContract.Products.PNAME));
                Log.e("PNAME    >>>","     "+tempPname);

                String tempPid= cursor.getString(cursor.getColumnIndex(ProductContract.Products.PID));
                Log.e("PID      >>>","     "+tempPid);
                String tempSid = cursor.getString(cursor.getColumnIndex(ProductContract.Products.SID));
                Log.e("SID      >>>","     "+tempSid);
                String tempCat= cursor.getString(cursor.getColumnIndex(ProductContract.Products.CAT));
                Log.e("CAT      >>>","     "+tempCat);
                String tempDscr = cursor.getString(cursor.getColumnIndex(ProductContract.Products.DSCR));
                Log.e("DSCR     >>>","     "+tempDscr);
                String tempMrp= cursor.getString(cursor.getColumnIndex(ProductContract.Products.MRP));
                Log.e("MRP      >>>","     "+tempMrp);
                String tempOfferpr = cursor.getString(cursor.getColumnIndex(ProductContract.Products.OFFERPR));
                Log.e("OFFER    >>>","     "+tempOfferpr);
                String tempViews= cursor.getString(cursor.getColumnIndex(ProductContract.Products.VIEWS));
                Log.e("VIEWS    >>>","     "+tempViews);
                String tempImg= cursor.getString(cursor.getColumnIndex(ProductContract.Products.IMG));
                Log.e("IMG      >>>","     "+tempImg);


                student = new NewProduct(tempPid,tempPname, tempSid,tempCat,tempDscr,tempMrp,tempOfferpr,tempViews,tempImg);

                /*student.setPid(tempPid);
                student.setPname(tempPname);

                student.setSid(tempSid);
                student.setCat(tempCat);

                student.setDscr(tempDscr);
                student.setMrp(tempMrp);

                student.setOfferpr(tempOfferpr);
                student.setViews(tempViews);
                student.setImg(tempImg);*/


                StudentList.add(student);



            } while (cursor.moveToNext());
        }

        listAdapter = new ListAdapter(SearchSQLiteActivity.this, R.layout.custom_layout, StudentList);
        Log.e("STUDENT LIST  >>>","          "+StudentList.toString().toLowerCase());

        listView.setAdapter(listAdapter);

        cursor.close();
    }

    @Override
    protected void onResume() {

        DisplayDataInToListView() ;

        super.onResume();
    }

}