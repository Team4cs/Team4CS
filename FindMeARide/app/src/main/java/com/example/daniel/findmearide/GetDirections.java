package com.example.daniel.findmearide;

import android.os.AsyncTask;

import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by danie on 03/02/2016.
 */
public class GetDirections extends AsyncTask {

    @Override
    protected Object doInBackground(Object[] directionsStr) {
        String jsonString = null;
        HttpURLConnection connection = null;
        URL url = null;
        JsonParser parser;

        try {
            url = new URL(directionsStr[0].toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            int responseCode = connection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = null;

                InputStream inputStream = connection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return false;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null)  {
                    buffer.append(line + "/n");
                }

                jsonString = buffer.toString();
                
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
