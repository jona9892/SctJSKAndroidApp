package com.example.jonathanspc.sctskapp.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jonathanspc.sctskapp.BE.BEUser;
import com.example.jonathanspc.sctskapp.DAL.ServiceGateway.Abstraction.IGatewayUser;
import com.example.jonathanspc.sctskapp.DAL.ServiceGateway.Implementation.GatewayUser;
import com.example.jonathanspc.sctskapp.R;

public class LoginActivity extends AppCompatActivity {


    public static final String TAG = "LoginActivity";

    private EditText usernameText;
    private EditText _passwordText;
    private Button _loginButton;
    //-------------------------------------------------
    private IGatewayUser da;


    //private UserGateway ug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        da = GatewayUser.getInstance();

        getWidgets();
        setupButtons();
    }

    public void getWidgets(){
        usernameText = (EditText)findViewById(R.id.input_email);
        _passwordText = (EditText)findViewById(R.id.input_password);

    }
    public void setupButtons(){
        _loginButton = (Button) findViewById(R.id.btn_login);
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();

            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        String username = usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        BEUser user = da.Login(username, password);
        Log.d("Login user: ", user.getFirstName() + " " + user.getId());
        if(user != null){
            onLoginSuccess(user);
        }else {
            onLoginFailed();
        }

        /*
        Credentials c = new Credentials();
        c.setUsername(username);
        c.setPassword(password);

        new LoadDataTask(this).execute(c);*/
        //BEUser user = ug.Login(username, password);

    }

    public void onLoginSuccess(BEUser user) {
        Toast.makeText(getBaseContext(), "Login succes", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, ProductlistActivity.class);
        intent.putExtra("activity", "login");
        intent.putExtra(TAG, user);

        startActivity(intent);


    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_SHORT).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty()) {
            usernameText.setError("Skriv et gyldigt brugernavn");
            valid = false;
        } else {
            usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Mellem 4 og 10 tegn");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    /*
    class LoadDataTask extends AsyncTask<Credentials, Void, BEUser> {

        LoginActivity m_context;

        public LoadDataTask(LoginActivity context)
        {
            m_context = context;
        }

        @Override
        protected void onPostExecute(BEUser user) {

        }

        @Override
        protected BEUser doInBackground(Credentials... params) {
            return ug.Login(params[0].getUsername(), params[0].getPassword());
        }
    }*/

}
