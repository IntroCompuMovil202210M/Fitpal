package com.gitgud.fitpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MiPerfil extends AppCompatActivity {
    ImageView imagen;
    ImageButton img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mi_perfil);
        img=findViewById(R.id.confi);
        imagen.setImageResource(R.drawable.viga);
        img.setOnClickListener(openActivity);
    }
    private View.OnClickListener openActivity = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MiPerfil.this, ModificarPerfil.class);
            startActivity(intent);
        }
    };


}