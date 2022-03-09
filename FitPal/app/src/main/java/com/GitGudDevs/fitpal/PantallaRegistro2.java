package com.GitGudDevs.fitpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PantallaRegistro2 extends AppCompatActivity {
    Button siguiente;
    Button anterior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registro2);

        siguiente=findViewById(R.id.siguiente2);
        anterior=findViewById(R.id.anterior2);

        anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(PantallaRegistro2.this, PantallaRegistro1.class);
                startActivity(intent);

            }
        });

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(PantallaRegistro2.this, PantallaRegistro3.class);
                startActivity(intent);

            }
        });

    }
}