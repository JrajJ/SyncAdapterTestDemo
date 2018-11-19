package com.sparklinktech.stech.syncadaptertestdemo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.sparklinktech.stech.syncadaptertestdemo.sharedpref.SessionManager;
import com.sparklinktech.stech.syncadaptertestdemo.syncadapter.ProductContract;
import com.sparklinktech.stech.syncadaptertestdemo.syncadapter.ProductGeneral;
import com.sparklinktech.stech.syncadaptertestdemo.syncadapter.SyncAdapter;

import java.util.ArrayList;

/**
 * Your SyncAdapter is good to go!
 *
 * Your SyncAdapter will run all on its own by Android if you specified it to sync
 * automatically and periodically. If not, you can force a sync using our performSync()
 * method we made.
 *
 * Use {@link android.database.ContentObserver} to get callbacks for data changes when
 * Android runs your SyncAdapter or when you manually run it.
 */
@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity /*implements LoaderManager.LoaderCallbacks<Cursor>*/
{
    Button search,reports,survey,graphs,settings,registration,changeLang,about_us,mainb;
    SessionManager manager;
     ArrayList<Product> mArrayList=new ArrayList<>();

     ArrayList<Product> mFilteredList;

    /**
     * This is our example content observer.
     */
    private ArticleObserver articleObserver;

    public static final int PRODUCT_LOADER = 0;
    static Boolean flag =false;

//==================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search      = (Button)findViewById(R.id.search);
        reports     = (Button)findViewById(R.id.reports);
        survey      = (Button)findViewById(R.id.survey);
        graphs      = (Button)findViewById(R.id.graphs);
        settings    = (Button)findViewById(R.id.settings);
        registration = (Button)findViewById(R.id.registration);
        changeLang  = (Button)findViewById(R.id.marathi);
        about_us    = (Button)findViewById(R.id.about_us);
        mainb    = (Button)findViewById(R.id.main_b);

        manager = new SessionManager();
        manager.setPreferences(MainActivity.this, "lang", "en");

        // Create your sync account
        ProductGeneral.createSyncAccount(this);



        // Setup example content observer
        articleObserver = new ArticleObserver();

       // Perform a manual sync by calling this:
        SyncAdapter.performSync();


    }
    //==================================================================================



    //==================================================================================

    @Override
    protected void onStart()
    {
        super.onStart();

        // Register the observer at the start of our activity
        getContentResolver().registerContentObserver(
                ProductContract.Products.CONTENT_URI, // Uri to observe (our articles)
                true, // Observe its descendants
                articleObserver); // The observer
    }
    //==================================================================================
    @Override
    protected void onStop()
    {
        super.onStop();

        if (articleObserver != null)
        {
            // Unregister the observer at the stop of our activity
            getContentResolver().unregisterContentObserver(articleObserver);
        }
    }
    //==================================================================================
    private void refreshArticles()
    {
        Log.i(getClass().getName(), "Articles data has changed!");
    }


    //==================================================================================
    // Perform a manual sync by calling this:
    public void syncManual(View view)
    {
        // Perform a manual sync by calling this:
        SyncAdapter.performSync();
    }


    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }


    //==================================================================================
    public void mainB(View view)
    {
    }
    //==================================================================================
    public void changeLangB(View view)
    {
        String lang_check = manager.getPreferences(getApplicationContext(),"lang");

        if(lang_check.equals("en"))
        {
            Context context = LocaleHelper.setLocale(this, "mr");
            Resources resources = context.getResources();

            changeLang.setText(resources.getString(R.string.marathi));
            reports.setText(resources.getString(R.string.reports));
            search.setText(resources.getString(R.string.search));
            about_us.setText(resources.getString(R.string.about_us));
            registration.setText(resources.getString(R.string.registration));
            settings.setText(resources.getString(R.string.settings));
            graphs.setText(resources.getString(R.string.graphs));
            survey.setText(resources.getString(R.string.survey));
            mainb.setText(resources.getString(R.string.main));


            manager.setPreferences(MainActivity.this, "lang", "mr");
        }
        else if(lang_check.equals("mr"))
        {
            Context context = LocaleHelper.setLocale(this, "en");
            Resources resources = context.getResources();

            changeLang.setText(resources.getString(R.string.marathi));
            reports.setText(resources.getString(R.string.reports));
            search.setText(resources.getString(R.string.search));
            about_us.setText(resources.getString(R.string.about_us));
            registration.setText(resources.getString(R.string.registration));
            settings.setText(resources.getString(R.string.settings));
            graphs.setText(resources.getString(R.string.graphs));
            survey.setText(resources.getString(R.string.survey));
            mainb.setText(resources.getString(R.string.main));

            manager.setPreferences(MainActivity.this, "lang", "en");

        }
    }
    //==================================================================================
    public void reportsB(View view)
    {
        Intent i =new Intent(MainActivity.this,ReportsActivity.class);
        startActivity(i);
    }
    //==================================================================================
    public void surveyB(View view)
    {
    }
    //==================================================================================
    public void graphsB(View view)
    {
    }
    //==================================================================================
    public void settingsB(View view)
    {
        Intent i =new Intent(MainActivity.this,SettingsActivity.class);
        i.putExtra("key","search");
        startActivity(i);
    }
    //==================================================================================
    public void registrationB(View view)
    {
    }
    //==================================================================================
    public void searchB(View view)
    {
        Intent i =new Intent(MainActivity.this,SearchSQLiteActivity.class);
        i.putExtra("key","search");
        startActivity(i);
    }
    //==================================================================================
    public void aboutusB(View view)
    {

    }

    //==================================================================================



//==================================================================================
    /**
     * Example content observer for observing article data changes.
     */
    private final class ArticleObserver extends ContentObserver
    {
        private ArticleObserver()
        {
            // Ensure callbacks happen on the UI thread
            super(new Handler(Looper.getMainLooper()));
        }

        @Override
        public void onChange(boolean selfChange, Uri uri)
        {
            // Handle your data changes here!!!
            refreshArticles();
        }
    }



    //=========================================================================================================

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);

        //searchView.setQueryHint("Search Here.....");
        search(searchView);
        return true;
    }*/
//=========================================================================================================



    private void search(SearchView searchView)
    {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                //MainActivity.flag=true;

                getProducts(newText);
                //productAdapter.getFilter().filter(newText);
                //recyclerView.setAdapter(productAdapter);
                return true;
            }
        });
    }
    private void getProducts(String searchTerm)
    {
        mArrayList.clear();

        DBAdapter db=new DBAdapter(this);
        db.openDB();
        Product p=null;
        Cursor c=db.retrieve(searchTerm);

        while (c.moveToNext())
        {
            String pid    = c.getString(1);
            String pname    = c.getString(2);
            String sid    = c.getString(3);
            String cat    = c.getString(4);
            String dscr     = c.getString(5);
            String mrp      = c.getString(6);
            String offerpr  = c.getString(7);
            String views    = c.getString(8);
            String img      = c.getString(9);

            p=new Product();
            p.setPid(pid);
            p.setPname(pname);
            p.setSid(sid);
            p.setCat(cat);
            p.setDscr(dscr);
            p.setMrp(mrp);
            p.setOfferpr(offerpr);
            p.setViews(views);
            p.setImg(img);



            mArrayList.add(p);
        }
        db.closeDB();



    }

    //=========================================================================================================

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        *//*if (id == R.id.action_settings) {
            return true;
        }*//*

        return super.onOptionsItemSelected(item);
    }*/
}