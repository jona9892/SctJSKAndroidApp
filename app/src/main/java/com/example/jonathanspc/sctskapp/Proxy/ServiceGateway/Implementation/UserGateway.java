package com.example.jonathanspc.sctskapp.Proxy.ServiceGateway.Implementation;

import android.util.Log;

import com.example.jonathanspc.sctskapp.BE.BEUser;
import com.example.jonathanspc.sctskapp.Proxy.ServiceGateway.Abstraction.IUserGateway;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by JonathansPC on 28-12-2016.
 */
public class UserGateway implements IUserGateway<BEUser> {

    private final String URL = "http://127.0.0.1:55391/api/user";
    private final String TAG = "User gateway";

    BEUser _LoggedUser;

    @Override
    public ArrayList<BEUser> getAll() {
        return null;
}

    @Override
    public BEUser Login(String username, String password) {
        try {
            String url = URL + "?username=" + username + "&password=" + password;
            Log.d("asd", url);
            String result = getContent(url);
            Log.d("asd", result);
            if (result == null) {
                Log.d(TAG, "Nothing returned...");
                return null;
            }

            JSONObject object = new JSONObject(result);
            String FirstName = object.getString("FirstName");
            String LastName = object.getString("LastName");
            String Email = object.getString("Email");
            String Username = object.getString("Username");
            String Password = object.getString("Password");
            BEUser user = new BEUser(1,FirstName, LastName, Email, Username, Password);
            return user;
        }catch (JSONException e) {
            Log.e(TAG,
                    "There was an error parsing the JSON", e);
        } catch (Exception e) {
            Log.d(TAG, "General exception in loadAll " + e);
        }

        return null;
    }

    /**
     * Get the content of the url as a string. Based on using a scanner.
     * @param urlString - the url must return data typical in either json, xml, csv etc..
     * @return the content as a string. Null is something goes wrong.
     */
    private String getContent(String urlString)
    {
        StringBuilder sb = new StringBuilder();
        try {


            java.net.URL url = new URL(urlString);
            Scanner s = new Scanner(url.openStream());

            while (s.hasNextLine()) {
                String line = s.nextLine();
                sb.append(line);
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }
}
