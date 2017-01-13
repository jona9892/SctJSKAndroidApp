package com.example.jonathanspc.sctskapp.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jonathanspc.sctskapp.BE.BEOrder;
import com.example.jonathanspc.sctskapp.BE.BEOrderDetail;
import com.example.jonathanspc.sctskapp.BE.BEProduct;
import com.example.jonathanspc.sctskapp.BE.BEUser;
import com.example.jonathanspc.sctskapp.DAL.ServiceGateway.Abstraction.IGateway;
import com.example.jonathanspc.sctskapp.DAL.ServiceGateway.Abstraction.IGatewayOrderDetail;
import com.example.jonathanspc.sctskapp.DAL.ServiceGateway.Implementation.GatewayOrderDetail;
import com.example.jonathanspc.sctskapp.DAL.ServiceGateway.Implementation.GatewayProduct;
import com.example.jonathanspc.sctskapp.R;

import java.util.ArrayList;

public class OrderDetailActivity extends AppCompatActivity {
    BEOrder m_order;
    BEUser m_user;
    public static final String USERTAG = "OrderDetailActivity";
    private TextView txtODOrderNr;
    private TextView txtODOrderCreated;
    private TextView txtODOrderDate;
    private ListView lstOrderDetails;
    private TextView txtODTotalPrice;
    private TextView txtPause;

    IGatewayOrderDetail<BEOrderDetail> dOD;
    private OrderDetailAdapter orderdetailAdapter;
    private ArrayList<BEOrderDetail> orderdetailListArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dOD = GatewayOrderDetail.getInstance();
        getFromIntent();
        setTitle("Ordre: " + m_order.getId());

        getWidgets();
        setInfo();
        setAdapter();
        Log.d("Order ", "" + m_order.getId());
    }

    public boolean onOptionsItemSelected(MenuItem item){

        finish();
        return true;
    }

    public void getFromIntent(){
        m_user = (BEUser) getIntent().getSerializableExtra(ViewOrderActivity.USERTAG);
        m_order = (BEOrder) getIntent().getSerializableExtra(ViewOrderActivity.ORDERTAG);
    }

    public void getWidgets(){
        txtODOrderNr = (TextView)findViewById(R.id.txtODorderNr);
        txtODOrderCreated = (TextView)findViewById(R.id.txtODOrderCreated);
        txtODOrderDate = (TextView)findViewById(R.id.txtODOrderDate);
        txtODTotalPrice = (TextView)findViewById(R.id.txtODTotal);
        lstOrderDetails = (ListView)findViewById(R.id.lstOrderDetails);
        txtPause = (TextView)findViewById(R.id.txtPause);
    }

    public void setInfo(){
        txtODOrderNr.setText(""+ m_order.getId());
        txtODOrderCreated.setText(m_order.getOrderCreated());
        txtODOrderDate.setText(m_order.getOrderDate());
        txtODTotalPrice.setText("" + m_order.getTotalPrice());
        txtPause.setText(m_order.getTimeOfDay());
    }

    public void setAdapter(){
        orderdetailListArray = (ArrayList<BEOrderDetail>) dOD.getAll(m_order.getId());
        Log.d("count od ", ""+orderdetailListArray.size());
        orderdetailAdapter = new OrderDetailAdapter(this, R.layout.orderdetail_cell, orderdetailListArray);
        lstOrderDetails.setAdapter(orderdetailAdapter);
    }

    class OrderDetailAdapter extends ArrayAdapter<BEOrderDetail>
    {
        private int layout;
        Context context;
        IGateway<BEProduct> dp;
        public OrderDetailAdapter(Context context, int resource, ArrayList<BEOrderDetail> orderdetailsArray) {
            super(context, R.layout.orderdetail_cell, orderdetailsArray);
            this.context = context;
            this.layout = resource;
            dp = GatewayProduct.getInstance();
        }

        @SuppressLint({"SetTextI18n", "InflateParams"})
        @Override
        public View getView(final int position, View v, ViewGroup parent) {
            ViewHolder holder;
            if (v == null) {
                LayoutInflater li = (LayoutInflater) getContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                v = li.inflate(R.layout.orderdetail_cell, null);
                Log.d("LIST", "Position: " + position + " View created");

                holder = new ViewHolder();
                holder.txtODQuantity = (TextView) v.findViewById(R.id.txtODQuantity);
                holder.txtODProduct = (TextView)v.findViewById(R.id.txtODProductt);
                holder.txtODPrice = (TextView)v.findViewById(R.id.txtODPrice);

                v.setTag(holder);
            }
            else
                Log.d("LIST", "Position: " + position + " View Reused");
            holder = (ViewHolder) v.getTag();

            BEOrderDetail orderdetail = getItem(position);
            BEProduct product = dp.getById(orderdetail.getProductId());
            holder.txtODQuantity.setText(" " + "" + orderdetail.getQuantity());

            holder.txtODProduct.setText("" + " " + product.getTitle());
            holder.txtODPrice.setText(" " + orderdetail.getPrice());

            return v;
        }


        /**
         * This is used to get all the widgets the arrayadapter and listview needs
         * So that they only get called one time
         */
        public class ViewHolder {
            public TextView txtODQuantity;
            public TextView txtODProduct;
            public TextView txtODPrice;

        }

    }


}
