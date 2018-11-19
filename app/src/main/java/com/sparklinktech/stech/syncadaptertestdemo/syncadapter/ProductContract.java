package com.sparklinktech.stech.syncadaptertestdemo.syncadapter;

import android.net.Uri;

public class ProductContract
{// ContentProvider information
    public static final String CONTENT_AUTHORITY = "com.sparklinktech.stech.syncadaptertestdemo.sync";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    static final String PATH_ARTICLES = "product";

    // Database information
    static final String DB_NAME = "productdb";
    static final int DB_VERSION = 1;


    /**
     * This represents our SQLite table for our articles.
     */
    public static abstract class Products
    {
        public static final String NAME   = "product";
        public static final String ID     = "id";
        public static final String PNAME   = "pname";
        public static final String PID     = "pid";
        public static final String SID     = "sid";
        public static final String CAT     = "cat";
        public static final String DSCR    = "dscr";
        public static final String MRP     = "mrp";
        public static final String OFFERPR = "offerpr";
        public static final String VIEWS   = "views";
        public static final String IMG     = "img";



        // ContentProvider information for articles
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ARTICLES).build();
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI + "/" + PATH_ARTICLES;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_ARTICLES;
    }
}