package com.gitgud.fitpal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    //Declaracion de componentes.
    BottomNavigationView menuInferior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuInferior = findViewById(R.id.menuInferior);
        getSupportActionBar().hide();
    }
}