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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jonathanspc.sctskapp.BE.BEOrder;
import com.example.jonathanspc.sctskapp.BE.BEOrderDetail;
import com.example.jonathanspc.sctskapp.BE.BEUser;
import com.example.jonathanspc.sctskapp.BE.Cart;
import com.example.jonathanspc.sctskapp.DAL.DALC.Abstraction.ICrud;
import com.example.jonathanspc.sctskapp.DAL.ServiceGateway.Abstraction.IGatewayOrder;
import com.example.jonathanspc.sctskapp.DAL.ServiceGateway.Abstraction.IGatewayOrderDetail;
import com.example.jonathanspc.sctskapp.DAL.DALC.Implementation.DALCCart;
import com.example.jonathanspc.sctskapp.DAL.ServiceGateway.Implementation.GatewayOrder;
import com.example.jonathanspc.sctskapp.DAL.ServiceGateway.Implementation.GatewayOrderDetail;
import com.example.jonathanspc.sctskapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CheckoutActivity extends AppCompatActivity {

    private BEUser m_user;
    public static final String TAG = "CheckoutActivity";

    private Spinner spnTimeOfDay;
    private ListView lstCheckout;
    private TextView txtCheckoutTotal;
    private Button btnOrder;
    CalendarView cvOrderdate;
    String orderdate = "";
    String timeofday;

    private IGatewayOrder<BEOrder> dOrder;
    private IGatewayOrderDetail<BEOrderDetail> dOrderdetail;
    private ICrud<Cart> dcart;
    private ArrayList<Cart> cartListArray;
    private CheckoutAdapter checkoutAdapter;

    int total = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Kassen");
        dcart = new DALCCart(this);
        dOrder = GatewayOrder.getInstance();
        dOrderdetail = GatewayOrderDetail.getInstance();
        getWidget();
        setAdapter();
        setUpSpinner();
        setUpButtons();
        setInfo();
        getFromIntent();
        setUpDatePicker();

        Log.d("checkout bruger: ", m_user.getFirstName());
    }
    /*
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), CartActivity.class);
        myIntent.putExtra(TAG, m_user);
        startActivityForResult(myIntent, 0);
        finish();
        return true;
    }*/

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.checkout_menu, menu);

        return true;
    }

    /**
     * If an item in the menu is clicked this method will handle it
     * @param item The selected menuitem
     * @return the selected menuitem
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent myIntent = new Intent(this, CartActivity.class);
        myIntent.putExtra(TAG, m_user);
        myIntent.putExtra("activity", "checkout");
        startActivity(myIntent);
        finish();
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.checkoutaction_products){
            Intent intent = new Intent();
            intent.setClass(this, ProductlistActivity.class);
            intent.putExtra(TAG, m_user);
            intent.putExtra("activity", "checkout");
            startActivity(intent);
            finish();
        }
        if(id == R.id.checkoutaction_orders){
            Intent intent = new Intent();
            intent.setClass(this, ViewOrderActivity.class);
            intent.putExtra(TAG, m_user);
            intent.putExtra("activity", "checkout");
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void getFromIntent(){
        m_user = (BEUser) getIntent().getSerializableExtra(CartActivity.TAG);
    }

    public void getWidget(){
        spnTimeOfDay = (Spinner)findViewById(R.id.spnTimeOfDay);
        Log.d("spinner", spnTimeOfDay.toString());
        lstCheckout = (ListView)findViewById(R.id.lstCheckout);
        txtCheckoutTotal = (TextView)findViewById(R.id.txtCheckTotal);
        btnOrder = (Button)findViewById(R.id.btnOrder);
        cvOrderdate = (CalendarView) findViewById(R.id.cvOrderdate);

    }

    public void setUpButtons(){
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrder();
            }
        });
    }

    public void setUpDatePicker(){
        cvOrderdate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = ""+dayOfMonth + "-" + month + "-" + year;
                orderdate = date;
                Log.d("Date ", date);
            }
        });
    }

    public void setUpSpinner(){
        //String compareStatus = m_animelist.getStatus();
        String[] breaks = {"09:50", "11:30"};
        ArrayAdapter<String> adapterBreak = new ArrayAdapter<String>(this, R.layout.spinner_layout, breaks);
        Log.d("Adapter", ""+adapterBreak.getCount());
        spnTimeOfDay.setAdapter(adapterBreak);

    }

    public void addOrder(){
        String ordercreated = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, +1);
        String orderdate2 = sdf.format(cal.getTime());
        timeofday = spnTimeOfDay.getSelectedItem().toString();
        //Log.d("timeofday:", orderdate);
        if(orderdate == null || orderdate.equals("")){
            orderdate =orderdate2;
        }

        BEOrder order = new BEOrder(0, m_user.getId(), ordercreated, orderdate, total, timeofday);

        dOrder.add(order);
        for(Cart cart : dcart.readAll()){
            BEOrderDetail orderdetail = new BEOrderDetail();
            orderdetail.setOrderId(order.getId());
            orderdetail.setProductId(cart.getProductId());
            orderdetail.setQuantity(cart.getQuantity());
            int price = cart.getQuantity() * cart.getProductPrice();
            orderdetail.setPrice(price);
            dOrderdetail.add(orderdetail);
        }
        dcart.deleteAll();

        Intent intent = new Intent();
        intent.setClass(this, ViewOrderActivity.class);
        intent.putExtra(TAG, m_user);
        intent.putExtra("activity", "checkout");
        startActivity(intent);
        finish();
    }

    public void setAdapter(){
        cartListArray = (ArrayList<Cart>) dcart.readAll();
        Log.d("cart ", " "+ cartListArray.size());

        checkoutAdapter = new CheckoutAdapter(this, R.layout.checkout_cell, cartListArray);
        lstCheckout.setAdapter(checkoutAdapter);
    }
    public void setInfo(){

        for(Cart c : dcart.readAll()){
            int Subtotal = c.getProductPrice() * c.getQuantity();
            total = total + Subtotal;
        }
        txtCheckoutTotal.setText("" + total);
    }

    /**
     * This is used to get all the widgets the arrayadapter and listview needs
     * So that they only get called one time
     */
    static class ViewHolder {
        public TextView txtCheckoutQty;
        public TextView txtCheckoutProduct;
        public TextView txtCheckoutPrice;
    }

    class CheckoutAdapter extends ArrayAdapter<Cart>
    {
        private int layout;
        Context context;
        public CheckoutAdapter(Context context, int resource, ArrayList<Cart> cartlistArray) {
            super(context, R.layout.checkout_cell, cartlistArray);
            this.context = context;
            this.layout = resource;
        }

        @SuppressLint({"SetTextI18n", "InflateParams"})
        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            ViewHolder holder;
            if (view == null) {
                LayoutInflater li = (LayoutInflater) getContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                view = li.inflate(R.layout.checkout_cell, null);
                Log.d("LIST", "Position: " + position + " View created");

                holder = new ViewHolder();
                holder.txtCheckoutQty = (TextView)view.findViewById(R.id.txtCheckoutQty);
                holder.txtCheckoutProduct = (TextView)view.findViewById(R.id.txtCheckoutproduct);
                holder.txtCheckoutPrice = (TextView)view.findViewById(R.id.txtCheckoutPrice);
                view.setTag(holder);
            }
            else
                Log.d("LIST", "Position: " + position + " View Reused");
            holder = (ViewHolder) view.getTag();

            Cart cart = getItem(position);


            holder.txtCheckoutProduct.setText(" " + cart.getProductTitle());
            holder.txtCheckoutPrice.setText("" + " " + cart.getProductPrice());
            holder.txtCheckoutQty.setText("" + " " + cart.getQuantity());
            //Picasso.with(context).load(cart.getProductImage()).into(holder.);
            return view;
        }




    }
}
