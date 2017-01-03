package com.example.jonathanspc.sctskapp.DAL.DALC.Implementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.jonathanspc.sctskapp.BE.Cart;
import com.example.jonathanspc.sctskapp.DAL.DALC.Abstraction.ICrud;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by JonathansPC on 30-12-2016.
 */
public class DALCCart implements ICrud<Cart> {
    private Context context;
    private SQLiteDatabase db;
    private SQLiteStatement insertStmt;
    private static final String INSERT = "insert into " + MySQLHelper.TABLE_Cart
            + "(productId, productTitle, productPrice, productImage, quantity) values (?,?,?,?,?)";

    public DALCCart(Context context) {
        this.context = context;
        MySQLHelper mySQLHelper = new MySQLHelper(this.context);
        this.db = mySQLHelper.getWritableDatabase();
        this.insertStmt = db.compileStatement(INSERT);
    }

    /**
     * This method will add a new anielsit objet to the SQLlite database using the INSERT string which is the same as sql statement
     * @param item The animelist object to be added
     * @return The animelist that were added
     */
    @Override
    public Cart add(Cart item) {
        Log.d("cart", "adding" + item.getProductTitle());
        insertStmt.bindDouble(1, item.getProductId());
        insertStmt.bindString(2, item.getProductTitle());
        insertStmt.bindDouble(3, item.getProductPrice());
        insertStmt.bindString(4, item.getProductImage());

        insertStmt.bindDouble(5, item.getQuantity());

        item.setId((int) insertStmt.executeInsert());
        return item;
    }

    /**
     * This method will get a specific animelist object from the database
     * @param id the object containing this id
     * @return the animelist object
     */
    @Override
    public Cart read(int id) {
        List<Cart> list = new ArrayList<Cart>();
        Cursor cursor = db.query(MySQLHelper.TABLE_Cart, new String[] { "Id", "productId", "productTitle", "productPrice", "productImage", "quantity"},
                "id = " + id, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Cart(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getInt(5)));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return list.get(0);
    }

    /**
     * This method gets all animelist objects from the SQLlite database
     * @return all animelist objects
     */
    @Override
    public Collection<Cart> readAll() {
        List<Cart> list = new ArrayList<Cart>();
        Cursor cursor = db.query(MySQLHelper.TABLE_Cart, new String[] { "Id","productId", "productTitle", "productPrice", "productImage", "quantity"},
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Cart(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getInt(5)));
            } while (cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }

    @Override
    public void deleteAll() {
        db.delete(MySQLHelper.TABLE_Cart, null, null);
    }

    @Override
    public void delete(int id) {
        db.delete(MySQLHelper.TABLE_Cart, "id=" + id, null);
    }

    @Override
    public Cart update(Cart item) {
        ContentValues cv = new ContentValues();
        cv.put("productId", item.getProductId());
        cv.put("productTitle", item.getProductTitle());
        cv.put("productPrice", item.getProductPrice());
        cv.put("productImage", item.getProductImage());
        cv.put("quantity", item.getQuantity());
        db.update(MySQLHelper.TABLE_Cart, cv, "id=" + item.getId(), null);
        return item;
    }
}
