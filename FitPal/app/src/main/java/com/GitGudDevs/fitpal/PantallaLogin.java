package com.GitGudDevs.fitpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PantallaLogin extends AppCompatActivity {
    Button login;
    Button registro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_login1);
        login=findViewById(R.id.ingreso);
        registro=findViewById(R.id.registro);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(PantallaLogin.this, PantallaRegistro1.class);
                startActivity(intent);

            }
        });
    }
}