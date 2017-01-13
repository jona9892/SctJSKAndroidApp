package com.example.jonathanspc.sctskapp.DAL.DALC.Implementation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jona9892 on 01-04-2016.
 */
public class MySQLHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "sctjskapp.db";
    private final static int DATABASE_VERSION = 4;
    public static final String TABLE_Cart = "Cart";


    MySQLHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_Cart
                + "(id INTEGER PRIMARY KEY, productId INTEGER, " +
                "productTitle TEXT, productPrice INTEGER, productImage TEXT, quantity INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
