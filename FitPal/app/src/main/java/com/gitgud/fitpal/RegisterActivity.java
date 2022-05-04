package com.gitgud.fitpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    //Button botonMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //botonMapa = findViewById(R.id.button);
        //botonMapa.setOnClickListener(abrirMapa);
    }

    /*private View.OnClickListener abrirMapa = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), Mapa.class);
            startActivity(intent);
        }
    };*/
}