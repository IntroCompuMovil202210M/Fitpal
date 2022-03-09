package com.GitGudDevs.fitpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PantallaRegistro4 extends AppCompatActivity {

    Button siguiente;
    Button anterior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registro4);

        siguiente=findViewById(R.id.siguiente4);
        anterior=findViewById(R.id.anterior4);

        anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(PantallaRegistro4.this, PantallaRegistro3.class);
                startActivity(intent);

            }
        });

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(PantallaRegistro4.this, PantallaLogin.class);
                startActivity(intent);

            }
        });


    }
}