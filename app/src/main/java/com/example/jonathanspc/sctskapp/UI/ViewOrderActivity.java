package com.example.jonathanspc.sctskapp.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jonathanspc.sctskapp.BE.BEOrder;
import com.example.jonathanspc.sctskapp.BE.BEUser;
import com.example.jonathanspc.sctskapp.DAL.DALC.Abstraction.IDALCOrder;
import com.example.jonathanspc.sctskapp.DAL.DALC.Implementation.DALCOrder;
import com.example.jonathanspc.sctskapp.R;

import java.util.ArrayList;

public class ViewOrderActivity extends AppCompatActivity {

    private BEUser m_user;
    ListView lstOrders;
    OrderAdapter orderAdapter;
    IDALCOrder<BEOrder> dalcorder;
    private ArrayList<BEOrder> ordersArray;
    public static final String USERTAG = "ViewOrderUserActivity";
    public static final String ORDERTAG = "ViewOrderOrderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        setTitle("Mine ordrer");

        dalcorder = DALCOrder.getInstance();
        getWidgets();
        setAdapter();
        getFromIntent();
        setUpListView();
        Log.d("order bruger: ", m_user.getFirstName());
        Log.d("order bruger: ", "" + dalcorder.getAll().size());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_order_menu, menu);

        return true;
    }

    /**
     * If an item in the menu is clicked this method will handle it
     * @param item The selected menuitem
     * @return the selected menuitem
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_products){
            Intent intent = new Intent();
            intent.setClass(this, ProductlistActivity.class);
            intent.putExtra(USERTAG, m_user);
            intent.putExtra("activity", "vieworder");
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void getFromIntent(){

        Intent getIntent = getIntent();
        String activity = getIntent.getStringExtra("activity");
        if(activity.equals("checkout")){
            m_user = (BEUser) getIntent().getSerializableExtra(CheckoutActivity.TAG);
        } else if(activity.equals("cart")){
            m_user = (BEUser) getIntent().getSerializableExtra(CartActivity.TAG);
        } else if(activity.equals("products")){
            m_user = (BEUser) getIntent().getSerializableExtra(ProductlistActivity.TAG);
        }
    }

    public void getWidgets(){
        lstOrders = (ListView)findViewById(R.id.lstOrders);
    }

    public void setUpListView(){
        lstOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onClick((ListView)parent, view, position, id);
            }
        });
    }

    public void onClick(ListView parent,
                        View v, int position, long id) {
        ArrayList<BEOrder> col = dalcorder.getAll();
        Log.d("asd", "" + col.get(position).getId());

        Intent intent = new Intent();
        intent.setClass(this, OrderDetailActivity.class);
        intent.putExtra(ORDERTAG, col.get(position));
        intent.putExtra(USERTAG, m_user);
        startActivity(intent);
    }

    public void setAdapter(){
        ordersArray = (ArrayList<BEOrder>) dalcorder.getAll();

        orderAdapter = new OrderAdapter(this, R.layout.vieworders_cell, ordersArray);
        lstOrders.setAdapter(orderAdapter);
    }

    class OrderAdapter extends ArrayAdapter<BEOrder>
    {
        private int layout;
        Context context;

        public OrderAdapter(Context context, int resource, ArrayList<BEOrder> ordersArray) {
            super(context, R.layout.vieworders_cell, ordersArray);
            this.context = context;
            this.layout = resource;
        }

        @SuppressLint({"SetTextI18n", "InflateParams"})
        @Override
        public View getView(final int position, View v, ViewGroup parent) {
            ViewHolder holder;
            if (v == null) {
                LayoutInflater li = (LayoutInflater) getContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                v = li.inflate(R.layout.vieworders_cell, null);
                Log.d("LIST", "Position: " + position + " View created");

                holder = new ViewHolder();
                holder.orderNr = (TextView) v.findViewById(R.id.txtOrderNr);
                holder.ordercreated = (TextView)v.findViewById(R.id.txtOrderCreated);
                holder.orderdate = (TextView)v.findViewById(R.id.txtOrderDate);
                holder.txtprice = (TextView)v.findViewById(R.id.txtOrderPrice);
                holder.txtTimeofday = (TextView)v.findViewById(R.id.txtTimeofDay);

                v.setTag(holder);
            }
            else
                Log.d("LIST", "Position: " + position + " View Reused");
            holder = (ViewHolder) v.getTag();

            BEOrder order = getItem(position);

            holder.orderNr.setText(" " + "" + order.getId());
            holder.ordercreated.setText("" + " " + order.getOrderCreated());
            holder.orderdate.setText(" " + order.getOrderDate());
            holder.txtprice.setText(" " + "" + order.getTotalPrice());
            holder.txtTimeofday.setText(" " + "" + order.getTimeOfDay());

            return v;
        }


        /**
         * This is used to get all the widgets the arrayadapter and listview needs
         * So that they only get called one time
         */
        public class ViewHolder {
            public TextView orderNr;
            public TextView ordercreated;
            public TextView orderdate;
            public TextView txtprice;
            public TextView txtTimeofday;
        }

    }


}
