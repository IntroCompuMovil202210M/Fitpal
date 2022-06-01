package com.gitgud.fitpal;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MiPerfil extends AppCompatActivity {
    ImageView imagen;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    ImageButton img;
    BottomNavigationView menuInferior;
    TextView desc;
    TextView userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        img=findViewById(R.id.confi);
        img.setImageResource(R.drawable.ic_android_black_24dp);
        img.setOnClickListener(openActivity);
        userName = findViewById(R.id.user);
        desc = findViewById(R.id.descrip);
        db.collection("Usuario")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String idDco = document.getId().toString();
                                if (user.getEmail().equals(idDco)){
                                    userName.setText(document.getData().get("username").toString());
                                    //desc.setText(document.getData().get("bio").toString());
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
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
                    startActivity(new Intent(MiPerfil.this, UserChats.class));
                    finish();
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