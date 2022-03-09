package com.GitGudDevs.fitpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PantallaRegistro3 extends AppCompatActivity {
    Button siguiente;
    Button anterior;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registro3);

        siguiente=findViewById(R.id.siguiente3);
        anterior=findViewById(R.id.anterior3);

        anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(PantallaRegistro3.this, PantallaRegistro2.class);
                startActivity(intent);

            }
        });

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(PantallaRegistro3.this, PantallaRegistro4.class);
                startActivity(intent);

            }
        });


    }
}