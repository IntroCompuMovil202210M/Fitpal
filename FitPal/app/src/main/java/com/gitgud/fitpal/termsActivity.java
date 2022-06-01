package com.gitgud.fitpal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class termsActivity extends AppCompatActivity {

    TextView terminosTV;
    Button regresarBT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        regresarBT = (Button) findViewById(R.id.regresarBT);
        terminosTV = (TextView) findViewById(R.id.terminos);
        regresarBT.setOnClickListener(regresar);

        URL myURL = null;
        /*try {
            myURL = new URL("https://taller3-b0411-default-rtdb.firebaseio.com/terminos");
            HttpURLConnection conn = (HttpURLConnection) myURL.openConnection();
            conn.setRequestMethod("GET");
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            String response = sb.toString();
            //se parsea la respues en un JSONObject
            JsonObject jsonResult = JsonParser.parseReader(in).getAsJsonObject();
            System.out.println(jsonResult.get("terminos"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
    private View.OnClickListener regresar = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            finish();
        }
    };
}