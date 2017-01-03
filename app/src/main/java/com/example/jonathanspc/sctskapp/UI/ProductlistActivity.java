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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jonathanspc.sctskapp.BE.BECategory;
import com.example.jonathanspc.sctskapp.BE.BEProduct;
import com.example.jonathanspc.sctskapp.BE.BEUser;
import com.example.jonathanspc.sctskapp.BE.Cart;
import com.example.jonathanspc.sctskapp.DAL.DALC.Abstraction.ICrud;
import com.example.jonathanspc.sctskapp.DAL.DALC.Abstraction.IDALC;
import com.example.jonathanspc.sctskapp.DAL.DALC.Implementation.DALCCart;
import com.example.jonathanspc.sctskapp.DAL.DALC.Implementation.DALCCategory;
import com.example.jonathanspc.sctskapp.DAL.DALC.Implementation.DALCProduct;
import com.example.jonathanspc.sctskapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductlistActivity extends AppCompatActivity {

    private BEUser m_user;
    private BEUser m_user2;
    public static final String TAG = "ProductlistActivity";
    private RadioGroup radioGroup;

    RadioButton radioButton;
    RadioButton radioButtonView;


    private ProductAdapter productAdapter;
    private ListView lstProducts;
    private ArrayList<BEProduct> productListArray;
    private ArrayList<BEProduct> brødArray = new ArrayList<>();
    private ArrayList<BEProduct> drikkevarerArray = new ArrayList<>();

    private IDALC<BECategory> dc;
    private IDALC<BEProduct> dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlist);
        m_user = new BEUser();
        dc = DALCCategory.getInstance();
        dp = DALCProduct.getInstance();

        getFromIntent();
        setTitle("Produkter");
        getWidgets();
        setAdapter();

        fillArrays();
        Log.d("Produktlist bruger:", m_user.getFirstName() + " "+ m_user.getId());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

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

        if(id == R.id.action_cart){
            Intent intent = new Intent();
            intent.setClass(this, CartActivity.class);
            intent.putExtra(TAG, m_user);
            intent.putExtra("activity", "products");
            startActivity(intent);
        }

        if(id == R.id.viewOrders){
            Intent intent = new Intent();
            intent.setClass(this, ViewOrderActivity.class);
            intent.putExtra(TAG, m_user);
            intent.putExtra("activity", "products");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void getFromIntent(){
        Intent getIntent = getIntent();
        String activity = getIntent.getStringExtra("activity");
        Log.d("produktlist ", activity);
        if(activity.equals("login")){
            Log.d("produktlist ", "true");
            m_user = (BEUser) getIntent().getSerializableExtra(LoginActivity.TAG);
        } else if(activity.equals("cart")){
            m_user = (BEUser) getIntent().getSerializableExtra(CartActivity.TAG);
        }else if(activity.equals("vieworder")){
            m_user = (BEUser) getIntent().getSerializableExtra(ViewOrderActivity.USERTAG);
        }else if(activity.equals("checkout")){
            m_user = (BEUser) getIntent().getSerializableExtra(CheckoutActivity.TAG);
        }
        /*
        m_user = (BEUser) getIntent().getSerializableExtra(LoginActivity.TAG);
        if(m_user == null){
            Log.d("productlister test ", "cart is called");
            m_user = (BEUser) getIntent().getSerializableExtra(CartActivity.TAG);
            m_user2 = m_user;
        }//else if(m_user2 == null) {
            //m_user = (BEUser) getIntent().getSerializableExtra(ViewOrderActivity.TAG);
        //}*/

    }

    public void getWidgets(){
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        lstProducts = (ListView)findViewById(R.id.lstViewProducts);
    }

    public void setUpRadioButtons(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()){
            case R.id.rdbAll:
                if(checked){
                    setAdapter();
                    fillArrays();
                }
                break;
            case R.id.rdbBrød:
                if(checked){
                    setBrødAdapter();
                    fillArrays();
                }
                break;
            case R.id.rdbDrikkevarer:
                if(checked){
                    setDrikkevarerAdapter();
                    fillArrays();
                }
                break;
        }
    }

    /**
     * This method should be called when the array adapter and listview needs to be updated
     */
    public void setAdapter(){
        productListArray = (ArrayList<BEProduct>) dp.getAll();

        productAdapter = new ProductAdapter(this, R.layout.product_cell, productListArray);
        lstProducts.setAdapter(productAdapter);
    }

    /**
     * This method will clear the array, and populate the listview with the array of completed
     */
    public void setBrødAdapter(){
        productAdapter = new ProductAdapter(this, R.layout.product_cell, brødArray);
        lstProducts.setAdapter(productAdapter);
    }

    /**
     * This method will clear the array, and populate the listview with the array of watching
     */
    public void setDrikkevarerAdapter(){
        productAdapter = new ProductAdapter(this, R.layout.product_cell, drikkevarerArray);
        lstProducts.setAdapter(productAdapter);
    }

    /**
     * This method should fill the different arrays with right objects from status
     */
    public void fillArrays(){
        brødArray.clear();
        drikkevarerArray.clear();
        //Fill array with the animelist objects that contains a the specified status
        for(BEProduct product : dp.getAll()){
            if(product.getCategory().getName().equals("Brød")){
                brødArray.add(product);
            } else if (product.getCategory().getName().equals("Drikkevarer")) {
                drikkevarerArray.add(product);
            }
        }
    }



    /**
     * This class extends arrayAdapter which is going to be used for displaying data in listview
     */
    class ProductAdapter extends ArrayAdapter<BEProduct>
    {
        private int layout;
        Context context;

        private ICrud<Cart> dcart;
        public ProductAdapter(Context context, int resource, ArrayList<BEProduct> productlistsArray) {
            super(context, R.layout.product_cell, productlistsArray);
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
                v = li.inflate(R.layout.product_cell, null);
                Log.d("LIST", "Position: " + position + " View created");

                holder = new ViewHolder();
                holder.title = (TextView) v.findViewById(R.id.txtProductTitle);
                holder.price = (TextView)v.findViewById(R.id.txtCartProductPrice);
                holder.description = (TextView) v.findViewById(R.id.txtDescription);
                holder.imgProduct = (ImageView)v.findViewById(R.id.imgProduct);
                holder.btnAddToCart = (Button)v.findViewById(R.id.btnAddToCart);
                final BEProduct product = getItem(position);
                holder.btnAddToCart.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        try{
                            String productTitle = product.getTitle();
                            int productPrice = product.getPrice();
                            String productImage = product.getImage();
                            int quantity = 1;
                            Cart newCart = new Cart(0, product.getId(), productTitle, productPrice, productImage, quantity);
                            dcart.add(newCart);
                            Toast.makeText(getBaseContext(), newCart.getProductTitle() + " er tilføjet til kurv", Toast.LENGTH_SHORT).show();
                        }catch(Exception e){
                            Toast.makeText(getBaseContext(), "Der skete en fejl", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                v.setTag(holder);
            }
            else
                Log.d("LIST", "Position: " + position + " View Reused");
            holder = (ViewHolder) v.getTag();

            final BEProduct product = getItem(position);
            holder.btnAddToCart.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    try{
                        if(!checkProduct(product)){
                            String productTitle = product.getTitle();
                            int productPrice = product.getPrice();
                            String productImage = product.getImage();
                            int quantity = 1;
                            Cart newCart = new Cart(0, product.getId(), productTitle, productPrice, productImage, quantity);
                            dcart.add(newCart);
                            Toast.makeText(getBaseContext(), newCart.getProductTitle() + " er tilføjet til kurv", Toast.LENGTH_SHORT).show();
                        }

                    }catch(Exception e){
                        Toast.makeText(getBaseContext(), "Der skete en fejl", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            holder.title.setText(" " + product.getTitle());
            holder.price.setText("" + " " + product.getPrice());
            holder.description.setText("" + " " + product.getDescription());
            Picasso.with(context).load(product.getImage()).into(holder.imgProduct);
            return v;
        }


        /**
         * This is used to get all the widgets the arrayadapter and listview needs
         * So that they only get called one time
         */
        public class ViewHolder {
            public TextView title;
            public TextView price;
            public TextView description;
            public ImageView imgProduct;
            public Button btnAddToCart;
        }

        private boolean checkProduct(BEProduct product){
            for(Cart cart : dcart.readAll()){
                if(cart.getProductTitle().equals(product.getTitle())){
                    Toast.makeText(context, cart.getProductTitle()+ " er allerede tilføjet",
                            Toast.LENGTH_LONG).show();
                    return true;
                }
            }
            return false;
        }


    }

}
