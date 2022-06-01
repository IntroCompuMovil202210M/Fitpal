package com.gitgud.fitpal;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.transcode.BitmapBytesTranscoder;
import com.gitgud.fitpal.databinding.ActivityCompleteRegister2Binding;
import com.gitgud.fitpal.entidades.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CompleteRegister2 extends AppCompatActivity {
    private FirebaseFirestore db;
    HashMap<String, String> deportes = new HashMap<String, String>();
    ArrayList<String> listaDeportes = new ArrayList<>();
    String username;
    String nombre;
    String apellido;
    String bio;
    String userid;
    Bundle parametros;
    ImageButton imagen;
    DatabaseReference myRef;
    FirebaseDatabase database;
    FirebaseStorage storage;
    StorageReference mStorageRef;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    Button subirDatos;
    public static final String PATH_USERS = "usuarios/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        mStorageRef = storage.getReference();
        database = FirebaseDatabase.getInstance();
        mStorageRef = storage.getReference();
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
                                deportes.put(document.getData().get("nombre").toString(), document.getId());
                                chips.addView(chip);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
        chips.setOnCheckedChangeListener((group, checkedId) -> {
            Chip chip = (Chip) group.findViewById(checkedId);
            if(listaDeportes.contains(chip.getText().toString())){
                listaDeportes.remove(chip.getText().toString());
                Log.i("Chip eliminado: ",""+chip.getText().toString());
            }
            else{
                listaDeportes.add(
                        deportes.get(chip.getText().toString())
                );
                Log.i("Chip agregado: ",""+chip.getText().toString());
            }
        });
        subirDatos = findViewById(R.id.bt_register_a);
        subirDatos.setOnClickListener(cargarDatos);

    }
    private View.OnClickListener cargarImagen = new android.view.View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(view.getContext(), UploadImageActivity.class));
            onResume();
        }
    };
    private View.OnClickListener cargarDatos = new android.view.View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Usuario nuevoUsuario = new Usuario();
            assert currentUser != null;
            userid = currentUser.getUid();

            nuevoUsuario.setUserId(userid);
            nuevoUsuario.setApellido(apellido);
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setBio(bio);
            nuevoUsuario.setUsername(username);
            myRef= database.getReference(PATH_USERS+currentUser.getUid());
            myRef.setValue(nuevoUsuario);
            //Firestore Database
            db.collection("Usuario").document(currentUser.getUid()).update("apellido",nuevoUsuario.getApellido());
            db.collection("Usuario").document(currentUser.getUid()).update("username",nuevoUsuario.getUsername());
            db.collection("Usuario").document(currentUser.getUid()).update("nombre",nuevoUsuario.getNombre());
            db.collection("Usuario").document(currentUser.getUid()).update("bio",nuevoUsuario.getBio());
            db.collection("Usuario").document(currentUser.getUid()).update("perfilCompleto",true);
            db.collection("Usuario").document(currentUser.getUid()).update("deportes",listaDeportes);
            startActivity(new Intent(view.getContext(), Mapa.class));
            finish();
        }
    };
    @Override
    protected void onResume() {
        super.onResume();
        StorageReference storageReference = storage.getReference().child("usuarios/"+currentUser.getUid()+"/fotoperfil.jpg");
        if(storageReference!=null){
            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String imageURL = uri.toString();
                    Log.i("URL IMAGEN:",""+imageURL);
                    Glide.with(imagen.getContext()).load(imageURL).into(imagen);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });

        }
    }
}