package com.gitgud.fitpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MiPerfil extends AppCompatActivity {
    ImageView imagen;
    ImageButton img;
    BottomNavigationView menuInferior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mi_perfil);
        img=findViewById(R.id.confi);
        img.setImageResource(R.drawable.ic_android_black_24dp);
        img.setOnClickListener(openActivity);
        menuInferior = (BottomNavigationView) findViewById(R.id.menuInferior_map);
        menuInferior.setSelectedItemId(R.id.perfil_navegacion);
        menuInferior.setOnItemSelectedListener(item->{
            switch (item.getItemId()){
                case R.id.inicio_navegacion:
                    startActivity(new Intent(MiPerfil.this, Mapa.class));
                    finish();
                    break;
                case R.id.amigos_navegacion:
                    break;
                case R.id.eventos_deportivos_navegacion:
                    startActivity(new Intent(MiPerfil.this, listaEventos.class));
                    finish();
                    break;
                case R.id.chat_navegacion:
                    break;
                case R.id.perfil_navegacion:
                    break;
            }
            return true;
        });
    }
    private View.OnClickListener openActivity = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MiPerfil.this, ModificarPerfil.class);
            startActivity(intent);
        }
    };


}