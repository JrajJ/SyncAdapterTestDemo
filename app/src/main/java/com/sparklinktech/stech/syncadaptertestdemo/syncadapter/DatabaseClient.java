package com.sparklinktech.stech.syncadaptertestdemo.syncadapter;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.sparklinktech.stech.syncadaptertestdemo.syncadapter.ProductContract.DB_NAME;
import static com.sparklinktech.stech.syncadaptertestdemo.syncadapter.ProductContract.DB_VERSION;

public final class DatabaseClient extends SQLiteOpenHelper
{
    private static volatile DatabaseClient instance;
     final SQLiteDatabase db;


    public DatabaseClient(Context c)
    {
        super(c, DB_NAME, null, DB_VERSION);
        this.db = getWritableDatabase();
    }

    /**
     * We use a Singleton to prevent leaking the SQLiteDatabase or Context.
     * @return {@link DatabaseClient}
     */
    public static DatabaseClient getInstance(Context c)
    {
        if (instance == null)
        {
            synchronized (DatabaseClient.class)
            {
                if (instance == null) {
                    instance = new DatabaseClient(c);
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // Create any SQLite tables here
        createArticlesTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Update any SQLite tables here
        db.execSQL("DROP TABLE IF EXISTS [" + ProductContract.Products.NAME + "];");
        onCreate(db);
    }

    /**
     * Provide access to our database.
     */
    public SQLiteDatabase getDb()
    {
        return db;
    }

    /**
     * Creates our 'products' SQLite database table.
     * @param db {@link SQLiteDatabase}
     */
    private void createArticlesTable(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE [" + ProductContract.Products.NAME + "] ([" +
                ProductContract.Products.ID + "] INTEGER  PRIMARY KEY,[" +
                ProductContract.Products.PID + "]  VARCHAR ,[" +
                ProductContract.Products.PNAME + "] VARCHAR ,[" +
                ProductContract.Products.SID + "] VARCHAR,[" +
                ProductContract.Products.CAT + "] VARCHAR,[" +
                ProductContract.Products.DSCR + "] VARCHAR,[" +
                ProductContract.Products.MRP + "] VARCHAR,[" +
                ProductContract.Products.OFFERPR + "] VARCHAR,[" +
                ProductContract.Products.VIEWS + "] VARCHAR,[" +
                ProductContract.Products.IMG + "] VARCHAR);");
    }
}
