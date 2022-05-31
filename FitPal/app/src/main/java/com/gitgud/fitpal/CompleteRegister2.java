package com.gitgud.fitpal;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.gitgud.fitpal.entidades.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class CompleteRegister2 extends AppCompatActivity {
    private FirebaseFirestore db;
    ArrayList<String> deportes = new ArrayList<>();
    String username;
    String nombre;
    String apellido;
    String bio;
    Bundle parametros;
    ImageButton imagen;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    Button subirDatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_complete_register2);
        parametros = this.getIntent().getExtras();
        username = parametros.getString("username");
        nombre = parametros.getString("nombre");
        apellido = parametros.getString("apellido");
        bio = parametros.getString("bio");
        ChipGroup chips = (ChipGroup) findViewById(R.id.chip_group_cr2);
        imagen = (ImageButton) findViewById(R.id.profile_pic);
        imagen.setOnClickListener(cargarImagen);
        db.collection("deportes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Chip chip = new Chip(chips.getContext());
                                chip.setCheckable(true);
                                chip.setClickable(true);
                                chip.setText((String) document.getData().get("nombre"));
                                chips.addView(chip);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        subirDatos = findViewById(R.id.bt_register_a);
        subirDatos.setOnClickListener(cargarDatos);

    }
    private View.OnClickListener cargarImagen = new android.view.View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(view.getContext(), UploadImageActivity.class));

        }
    };
    private View.OnClickListener cargarDatos = new android.view.View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setApellido(apellido);
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setBio(bio);
            nuevoUsuario.setUsername(username);
            db.collection("Usuario").document(currentUser.getEmail()).update("apellido",nuevoUsuario.getApellido());
            db.collection("Usuario").document(currentUser.getEmail()).update("username",nuevoUsuario.getUsername());
            db.collection("Usuario").document(currentUser.getEmail()).update("nombre",nuevoUsuario.getNombre());
            db.collection("Usuario").document(currentUser.getEmail()).update("bio",nuevoUsuario.getBio());
            db.collection("Usuario").document(currentUser.getEmail()).update("perfilCompleto",true);
            startActivity(new Intent(view.getContext(), Mapa.class));
            finish();
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        StorageReference storageReference = storage.getReference(currentUser.getEmail());
        if(storageReference!=null){
            Glide.with(this)
                    .load(storageReference)
                    .into(imagen);
        }
    }
}