package com.gitgud.fitpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CompleteRegister extends AppCompatActivity {
    Bundle infoA = new Bundle();
    Button compRegA;
    String username;
    String nombre;
    String apellido;
    String bio;
    TextView user;
    TextView name;
    TextView lname;
    TextView biog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_register);
        compRegA = findViewById(R.id.bt_register_a);
        user = (TextView) findViewById(R.id.et_username);
        name = (TextView) findViewById(R.id.et_nombre);
        lname = (TextView) findViewById(R.id.et_apellido);
        biog = (TextView) findViewById(R.id.et_biografia);
        compRegA.setOnClickListener(registrarDatosA);
    }
    private View.OnClickListener registrarDatosA = new android.view.View.OnClickListener() {
        @Override
        public void onClick(View view) {
            username = user.getText().toString();
            nombre  = name.getText().toString();
            bio  = biog.getText().toString();
            apellido  = lname.getText().toString();
            infoA.putString("username",username);
            infoA.putString("nombre",nombre);
            infoA.putString("apellido",apellido);
            infoA.putString("bio",bio);
            Intent intent = new Intent(CompleteRegister.this, CompleteRegister2.class);
            intent.putExtras(infoA);
            startActivity(intent);
            finish();
        }
    };
}