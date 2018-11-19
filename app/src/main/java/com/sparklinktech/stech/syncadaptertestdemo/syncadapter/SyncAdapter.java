package com.sparklinktech.stech.syncadaptertestdemo.syncadapter;


import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.sparklinktech.stech.syncadaptertestdemo.ConnectToServer.ConnectToServer;
import com.sparklinktech.stech.syncadaptertestdemo.Product;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
     * This is used by the Android framework to perform synchronization. IMPORTANT: do NOT create
     * new Threads to perform logic, Android will do this for you; hence, the name.
     *
     * The goal here to perform synchronization, is to do it efficiently as possible. We use some
     * ContentProvider features to batch our writes to the local data source. Be sure to handle all
     * possible exceptions accordingly; random crashes is not a good user-experience.
     */
    public class SyncAdapter extends AbstractThreadedSyncAdapter
{
    static Map<String, Product> networkEntries;
    int i;

        private static final String TAG = "SYNC_ADAPTER";

        /**
         * This gives us access to our local data source.
         */
        private final ContentResolver resolver;


        public SyncAdapter(Context c, boolean autoInit)
        {
            this(c, autoInit, false);
        }

        public SyncAdapter(Context c, boolean autoInit, boolean parallelSync)
        {
            super(c, autoInit, parallelSync);
            this.resolver = c.getContentResolver();
        }

        /**
         * This method is run by the Android framework, on a new Thread, to perform a sync.
         * @param account Current account
         * @param extras Bundle extras
         * @param authority Content authority
         * @param provider {@link ContentProviderClient}
         * @param syncResult Object to write stats to
         */
        @Override
        public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
            Log.w(TAG, "Starting synchronization...");

            try {
                // Synchronize our news feed
                syncNewsFeed(syncResult);

                // Add any other things you may want to sync

            } catch (IOException ex) {
                Log.e(TAG, "Error synchronizing!! 1 ", ex);
                syncResult.stats.numIoExceptions++;
            } catch (JSONException ex) {
                Log.e(TAG, "Error synchronizing!  2 ", ex);
                syncResult.stats.numParseExceptions++;
            } catch (RemoteException |OperationApplicationException ex)
            {
                Log.e(TAG, "Error synchronizing!  3 ", ex);
                syncResult.stats.numAuthExceptions++;
            }

            Log.w(TAG, "Finished synchronization! 4 ");
        }

        /**
         * Performs synchronization of our pretend news feed source.
         * @param syncResult Write our stats to this
         */
        private void syncNewsFeed(SyncResult syncResult) throws IOException, JSONException, RemoteException, OperationApplicationException
        {
            final String rssFeedEndpoint = ConnectToServer.URL_GETTRENDING;

            // We need to collect all the network items in a hash table
            Log.i(TAG, "Fetching server entries...");
             networkEntries = new HashMap<>();

            // Parse the pretend json news feed
            String jsonFeed = download(rssFeedEndpoint);
            Log.e("jsonFEED 1111   >>>    ",""+jsonFeed);

            //Log.e("jsonFEED    >>>    ",""+jsonFeed);

           // JSONArray jsonArr = jsonFeed.getJSONArray("tahsil");
            /*JSONObject jo = */
            JSONArray jsonArticles = new JSONArray(jsonFeed);
            ArrayList a=new ArrayList();
            Log.e("jsonARTICLE    >>>    ",""+jsonArticles);



            /*JSONResponse jsonResponse = jsonArticles.opt(i);
            ProductAdapter.mArrayList = new ArrayList<>(Arrays.asList(jsonResponse.getProduct()));*/



            for ( i = 0; i < jsonArticles.length(); i++)
            {
               // Log.e("LOOOOOOOOOOOOOP >>> ","        <<<        "+i);

                Product particle = ProductParser.parse(jsonArticles.optJSONObject(i));
                //Log.e("jsonArticles <"+i+">  >>> ","    "+jsonArticles.getJSONObject(i));
               // ProductAdapter.mArrayList.add(jsonArticles.getJSONObject(i));
               // ProductAdapter.mFilteredList.add(jsonArticles.getJSONObject(i));
               // ProductAdapter.mArrayList.add(Product.class.cast(i));
                //Log.e("ARRY LI <<  ","    "+particle.toString());
                a.add(particle.toString());


                /*ProductAdapter.mArrayList.add();*/

                /*ProductAdapter.mArrayList.add(particle);
                ProductAdapter.mFilteredList.add(particle);*/



                networkEntries.put(particle.getPid(), particle);
            }

            Log.e("ARRY LI ALL <<  ","    "+a);
           /* ProductAdapter.mArrayList = new ArrayList<>(a);
            ProductAdapter.mFilteredList = new ArrayList<>(a);*/



            //Log.e("ARRY LI ALL <<  ","    "+particle.toString());

            // Create list for batching ContentProvider transactions
            ArrayList<ContentProviderOperation> batch = new ArrayList<>();

            // Compare the hash table of network entries to all the local entries
            Log.i(TAG, "Fetching local entries...");
            Cursor c = resolver.query(ProductContract.Products.CONTENT_URI, null, null, null, null, null);
            assert c != null;
            c.moveToFirst();

            String pid;
            String pname;
            String sid;
            String cat;
            String dscr;
            String mrp;
            String offerpr;
            String views;

            Product found;
            for (int i = 0; i < c.getCount(); i++)
            {
                syncResult.stats.numEntries++;

                // Create local article entry
                pid          = c.getString(c.getColumnIndex(ProductContract.Products.PID));
                pname        = c.getString(c.getColumnIndex(ProductContract.Products.PNAME));
                /*sid          = c.getString(c.getColumnIndex(ProductContract.Products.SID));
                cat          = c.getString(c.getColumnIndex(ProductContract.Products.CAT));
                dscr         = c.getString(c.getColumnIndex(ProductContract.Products.DSCR));
                mrp          = c.getString(c.getColumnIndex(ProductContract.Products.MRP));
                offerpr      = c.getString(c.getColumnIndex(ProductContract.Products.OFFERPR));
                views        = c.getString(c.getColumnIndex(ProductContract.Products.VIEWS));
                cat          = c.getString(c.getColumnIndex(ProductContract.Products.CAT));*/



                // Try to retrieve the local entry from network entries
                found = networkEntries.get(pid);
                if (found != null) {
                    // The entry exists, remove from hash table to prevent re-inserting it
                    networkEntries.remove(pid);

                    // Check to see if it needs to be updated
                    if (!pname.equals(found.getPname())
                            /*|| !content.equals(found.getContent())
                            || !link.equals(found.getLink())*/)
                    {
                        // Batch an update for the existing record
                        Log.i(TAG, "Scheduling update: " + pname);
                        batch.add(ContentProviderOperation.newUpdate(ProductContract.Products.CONTENT_URI)
                                .withSelection(ProductContract.Products.PID + "='" + pid + "'", null)
                                .withValue(ProductContract.Products.PNAME,      found.getPname())
                                .withValue(ProductContract.Products.SID,        found.getSid())
                                .withValue(ProductContract.Products.CAT,        found.getCat())
                                .withValue(ProductContract.Products.DSCR,       found.getDscr())
                                .withValue(ProductContract.Products.MRP,        found.getMrp())
                                .withValue(ProductContract.Products.OFFERPR,    found.getOfferpr())
                                .withValue(ProductContract.Products.VIEWS,      found.getViews())
                                .withValue(ProductContract.Products.IMG,        found.getImg())


                                .build());
                        syncResult.stats.numUpdates++;
                    }
                } else
                    {
                    // Entry doesn't exist, remove it from the local database
                    Log.i(TAG, "Scheduling delete: " + pname);
                    batch.add(ContentProviderOperation.newDelete(ProductContract.Products.CONTENT_URI)
                            .withSelection(ProductContract.Products.PID + "='" + pid + "'", null)
                            .build());
                    syncResult.stats.numDeletes++;
                }
                c.moveToNext();
            }
            c.close();

            // Add all the new entries
            for (Product particle : networkEntries.values())
            {
                Log.i(TAG, "Scheduling insert: " + particle.getPname());
                batch.add(ContentProviderOperation.newInsert(ProductContract.Products.CONTENT_URI)
                        .withValue(ProductContract.Products.PID,        particle.getPid())
                        .withValue(ProductContract.Products.PNAME,      particle.getPname())
                        .withValue(ProductContract.Products.SID,        particle.getSid())
                        .withValue(ProductContract.Products.CAT,        particle.getCat())
                        .withValue(ProductContract.Products.DSCR,       particle.getDscr())
                        .withValue(ProductContract.Products.MRP,        particle.getMrp())
                        .withValue(ProductContract.Products.OFFERPR,    particle.getOfferpr())
                        .withValue(ProductContract.Products.VIEWS,      particle.getViews())
                        .withValue(ProductContract.Products.IMG,        particle.getImg())
                        .build());
                syncResult.stats.numInserts++;
            }

/*
            Log.e("HASH  STRING   >>>>","      "+networkEntries.toString());
*/





            // Synchronize by performing batch update
            Log.i(TAG, "Merge solution ready, applying batch update...");
            resolver.applyBatch(ProductContract.CONTENT_AUTHORITY, batch);
            resolver.notifyChange(ProductContract.Products.CONTENT_URI, // URI where data was modified
                    null, // No local observer
                    false);

            // IMPORTANT: Do not sync to network
        }

        /**
         * A blocking method to stream the server's content and build it into a string.
         * @param url API call
         * @return String response
         */
        private String download(String url) throws IOException
        {
            // Ensure we ALWAYS close these!
            HttpURLConnection client = null;
            InputStream is = null;

            try {
                // Connect to the server using GET protocol
                URL server = new URL(url);
                client = (HttpURLConnection)server.openConnection();
                client.connect();

                // Check for valid response code from the server
                int status = client.getResponseCode();
                is = (status == HttpURLConnection.HTTP_OK)
                        ? client.getInputStream() : client.getErrorStream();

                // Build the response or error as a string
                Log.e("INPUT STREAM    >>>>   ","    "+is.toString());
                //ProductAdapter.mFilteredList.add();
                //ProductAdapter.mArrayList.add();

                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb = new StringBuilder();
                for (String temp; ((temp = br.readLine()) != null);)
                {

                    sb.append(temp);
                }
                //JSONResponse jsonResponse = is.body();


                return sb.toString();
            } finally {
                if (is != null) { is.close(); }
                if (client != null) { client.disconnect(); }
            }
        }

        /**
         * Manual force Android to perform a sync with our SyncAdapter.
         */
        public static void performSync()
        {
/*
            Log.d("FLOW       >>>>        ","2");
*/
            Bundle b = new Bundle();
            b.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
            b.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
            ContentResolver.requestSync(ProductGeneral.getAccount(),
                    ProductContract.CONTENT_AUTHORITY, b);
        }
    }

