package com.sparklinktech.stech.syncadaptertestdemo;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.sparklinktech.stech.syncadaptertestdemo.syncadapter.DatabaseClient;
import com.sparklinktech.stech.syncadaptertestdemo.syncadapter.ProductContract;

public class DBAdapter
{
    Context c;
    SQLiteDatabase db;
    DatabaseClient helper;

    public DBAdapter(Context c) {
        this.c = c;
        helper=new DatabaseClient(c);
    }

    //OPEN DB
    public void openDB()
    {
        try
        {
            db=helper.getWritableDatabase();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //CLOSE
    public void closeDB()
    {
        try
        {
            helper.close();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }



    //RETRIEVE OR FILTERING
    public Cursor retrieve(String searchTerm)
    {
        String[] columns={ProductContract.Products.PID,ProductContract.Products.PNAME,
                ProductContract.Products.SID,ProductContract.Products.CAT,
                ProductContract.Products.DSCR,ProductContract.Products.MRP,
                ProductContract.Products.OFFERPR,ProductContract.Products.VIEWS,
                ProductContract.Products.IMG};
        Cursor c=null;

        if(searchTerm != null && searchTerm.length()>0)
        {
            String sql="SELECT * FROM "+ProductContract.Products.NAME+" WHERE "+ProductContract.Products.PNAME+" LIKE '%"+searchTerm+"%'";
            c=db.rawQuery(sql,null);
            return c;
        }

        c=db.query(ProductContract.Products.NAME,columns,null,null,null,null,null);
        return c;

    }

}
