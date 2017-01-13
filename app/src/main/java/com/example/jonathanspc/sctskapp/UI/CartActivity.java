package com.example.jonathanspc.sctskapp.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonathanspc.sctskapp.BE.BEUser;
import com.example.jonathanspc.sctskapp.BE.Cart;
import com.example.jonathanspc.sctskapp.DAL.DALC.Abstraction.ICrud;
import com.example.jonathanspc.sctskapp.DAL.DALC.Implementation.DALCCart;
import com.example.jonathanspc.sctskapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private BEUser m_user;
    public static final String TAG = "CartActivity";

    private TextView txtTotal;

    private Button btnClear;
    private Button btnCheckout;
    private ListView lstCart;

    private CartAdapter cartAdapter;
    private ICrud<Cart> dcart;
    private ArrayList<Cart> cartListArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Indkøbskurv");
        dcart = new DALCCart(this);

        getWidget();
        setInfo();
        setAdapter();
        setUpButtons();
        getFromIntent();
        Log.d("Inkøbskruv bruger: ", m_user.getFirstName());
    }
    public void getFromIntent(){

        Intent getIntent = getIntent();
        String activity = getIntent.getStringExtra("activity");
        Log.d("cart ", activity);
        if(activity.equals("products")){
            m_user = (BEUser) getIntent().getSerializableExtra(ProductlistActivity.TAG);
        } else if(activity.equals("checkout")){
            m_user = (BEUser) getIntent().getSerializableExtra(CheckoutActivity.TAG);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cart_menu, menu);

        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(this, ProductlistActivity.class);
        myIntent.putExtra(TAG, m_user);
        myIntent.putExtra("activity", "cart");
        startActivity(myIntent);
        finish();

        int id = item.getItemId();

        if(id == R.id.cartaction_orders){
            Intent intent = new Intent();
            intent.setClass(this, ViewOrderActivity.class);
            intent.putExtra(TAG, m_user);
            intent.putExtra("activity", "cart");
            startActivity(intent);
        }



        return super.onOptionsItemSelected(item);

    }



    public void getWidget(){
        txtTotal = (TextView)findViewById(R.id.txtCheckTotal);

        btnClear = (Button)findViewById(R.id.btnClear);
        btnCheckout = (Button)findViewById(R.id.btnCheckout);
        lstCart = (ListView)findViewById(R.id.lstCart);
    }

    /**
     * This method should be called when the array adapter and listview needs to be updated
     */
    public void setAdapter(){
        cartListArray = (ArrayList<Cart>) dcart.readAll();
        Log.d("cart ", " "+ cartListArray.size());

        cartAdapter = new CartAdapter(this, R.layout.cart_item_cell, cartListArray);
        lstCart.setAdapter(cartAdapter);
    }

    public void setInfo(){
        int total = 0;
        for(Cart c : dcart.readAll()){
            int Subtotal = c.getProductPrice() * c.getQuantity();
            total = total + Subtotal;
        }
        txtTotal.setText("" + total);
    }

    public void setUpButtons(){
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCart();
                setInfo();
            }
        });
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!dcart.readAll().isEmpty()){
                    GoToCheckout();
                }else {
                    Toast.makeText(getBaseContext(), "Indkøbsvognen er tom", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void GoToCheckout(){
        Intent intent = new Intent();
        intent.setClass(this, CheckoutActivity.class);
        intent.putExtra(TAG, m_user);
        startActivity(intent);
    }

    public void clearCart(){
        dcart.deleteAll();
        Toast.makeText(getBaseContext(), "Indkøbsvognen tømt", Toast.LENGTH_SHORT).show();
        setAdapter();
    }

    /**
     * This is used to get all the widgets the arrayadapter and listview needs
     * So that they only get called one time
     */
    static class ViewHolder {
        public TextView txtItemTotal;
        public TextView cartProdTitle;
        public TextView cartProdPrice;
        public ImageView imgCartProduct;
        public NumberPicker nrpQuantity;
        public Button btnCartDelete;
    }

    /**
     * This class extends arrayAdapter which is going to be used for displaying data in listview
     */
    class CartAdapter extends ArrayAdapter<Cart>
    {
        private int layout;
        Context context;
        private ICrud<Cart> dcart;
        public CartAdapter(Context context, int resource, ArrayList<Cart> cartlistArray) {
            super(context, R.layout.cart_item_cell, cartlistArray);
            this.context = context;
            this.layout = resource;
            dcart = new DALCCart(context);
        }

        @SuppressLint({"SetTextI18n", "InflateParams"})
        @Override
        public View getView(final int position, View v, ViewGroup parent) {
            ViewHolder holder;
            if (v == null) {
                LayoutInflater li = (LayoutInflater) getContext().getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                v = li.inflate(R.layout.cart_item_cell, null);
                Log.d("LIST", "Position: " + position + " View created");

                holder = new ViewHolder();
                holder.nrpQuantity = (NumberPicker) v.findViewById(R.id.nrpQuantity);
                holder.txtItemTotal = (TextView)v.findViewById(R.id.txtItemTotal);
                holder.cartProdTitle = (TextView)v.findViewById(R.id.txtCartProductTitle);
                holder.cartProdPrice = (TextView)v.findViewById(R.id.txtCartProductPrice);
                holder.imgCartProduct = (ImageView)v.findViewById(R.id.imgCartProduct);
                holder.btnCartDelete = (Button)v.findViewById(R.id.btnCartDelete);
                final Cart cart = getItem(position);
                holder.btnCartDelete.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        dcart.delete(cart.getId());
                        if(context instanceof CartActivity){
                            ((CartActivity)context).setAdapter();
                            ((CartActivity)context).setInfo();
                        }
                    }
                });
                //-------------------------------------------------------------------
                holder.nrpQuantity.setMinValue(1);
                holder.nrpQuantity.setMaxValue(10);
                holder.nrpQuantity.setWrapSelectorWheel(true);
                holder.nrpQuantity.setValue(cart.getQuantity());

                holder.nrpQuantity.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        int cartId = cart.getId();
                        int productId = cart.getProductId();
                        String productTitle = cart.getProductTitle();
                        int productPrice = cart.getProductPrice();
                        String productImage = cart.getProductImage();
                        int quantity = newVal;
                        Cart updatedCart = new Cart(cartId, productId, productTitle, productPrice, productImage, quantity);
                        dcart.update(updatedCart);
                        if(context instanceof CartActivity){
                            ((CartActivity)context).setAdapter();
                            ((CartActivity)context).setInfo();
                        }
                    }
                });

                //-------------------------------------------------------------------


                v.setTag(holder);
            }
            else
                Log.d("LIST", "Position: " + position + " View Reused");
            holder = (ViewHolder) v.getTag();

            final Cart cart = getItem(position);
            holder.btnCartDelete.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    dcart.delete(cart.getId());
                    if(context instanceof CartActivity){
                        ((CartActivity)context).setAdapter();
                        ((CartActivity)context).setInfo();
                    }
                }
            });
            int itemTotal = cart.getQuantity() * cart.getProductPrice();
            holder.txtItemTotal.setText("" + "" +itemTotal + " Kr.");

            holder.cartProdTitle.setText(" " + cart.getProductTitle());
            holder.cartProdPrice.setText("" + " " + cart.getProductPrice());
            //holder.txtCartQunatity.setText("" + " " + cart.getQuantity());
            Picasso.with(context).load(cart.getProductImage()).into(holder.imgCartProduct);
            //-------------------------------------------------------------------
            holder.nrpQuantity.setMinValue(1);
            holder.nrpQuantity.setMaxValue(10);
            holder.nrpQuantity.setWrapSelectorWheel(true);

            holder.nrpQuantity.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    int cartId = cart.getId();
                    int productId = cart.getProductId();
                    String productTitle = cart.getProductTitle();
                    int productPrice = cart.getProductPrice();
                    String productImage = cart.getProductImage();
                    int quantity = newVal;
                    Cart updatedCart = new Cart(cartId, productId, productTitle, productPrice, productImage, quantity);
                    dcart.update(updatedCart);
                    if(context instanceof CartActivity){
                        ((CartActivity)context).setAdapter();
                        ((CartActivity)context).setInfo();
                    }
                }
            });
            //-------------------------------------------------------------------



            return v;
        }




    }




}
