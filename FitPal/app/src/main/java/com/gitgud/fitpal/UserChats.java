package com.gitgud.fitpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gitgud.fitpal.entidades.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class UserChats extends AppCompatActivity {

    ImageView imagen;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    ImageButton img;
    BottomNavigationView menuInferior;
    TextView desc;
    TextView userName;
    private DrawerLayout drawerLayout;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ValueEventListener listener;
    private LinearLayout listaUsuarios;
    private FirebaseStorage storage;
    private StorageReference mStorageRef;
    public static final String PATH_USERS = "usuarios/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chats);
        listaUsuarios = findViewById(R.id.listaUsuarios);
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        mStorageRef = storage.getReference();
        database = FirebaseDatabase.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        drawerLayout = findViewById(R.id.drawer_layout);
        androidx.appcompat.app.ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mAuth = FirebaseAuth.getInstance();
        loadUsers();

        /*menuInferior = (BottomNavigationView) findViewById(R.id.menuInferior_map);
        menuInferior.setSelectedItemId(R.id.chat_navegacion);
        menuInferior.setOnItemSelectedListener(item->{
            switch (item.getItemId()){
                case R.id.inicio_navegacion:
                    startActivity(new Intent(UserChats.this, Mapa.class));
                    finish();
                    break;
                case R.id.amigos_navegacion:
                    break;
                case R.id.eventos_deportivos_navegacion:
                    startActivity(new Intent(UserChats.this, listaEventos.class));
                    finish();
                    break;
                case R.id.chat_navegacion:
                    startActivity(new Intent(UserChats.this, UserChats.class));
                    finish();
                case R.id.perfil_navegacion:
                    break;
            }
            return true;
        });*/
    }

    private void loadUsers() {

        myRef = database.getReference(PATH_USERS);

        myRef.addValueEventListener(listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaUsuarios.removeAllViews();

                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){

                    Usuario usuario = singleSnapshot.getValue(Usuario.class);
                    String nombre = usuario.getNombre() + " " + usuario.getApellido();
                    try{

                        File file = File.createTempFile("usuarios","jpg");
                        StorageReference img =mStorageRef.child("usuarios/" + singleSnapshot.getKey() + "/viga.jpg");
                        img.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Uri imagen =Uri.fromFile(file);
                                ImageView foto =new ImageView(UserChats.this);
                                foto.setImageURI(imagen);
                                foto.setMinimumHeight(450);
                                foto.setMaxHeight(450);
                                TextView nombreTextView = new TextView(UserChats.this);
                                nombreTextView.setText(nombre);
                                nombreTextView.setTextSize(20);
                                Button button = new Button(UserChats.this);
                                button.setTag(usuario);
                                button.setText("Ubicacion");
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(getBaseContext(), MiPerfil.class);
                                        startActivity(intent);
                                        //Bundle bundle = new Bundle();
                                        //bundle.putDouble("latitud", usuario.getLatitude());
                                        //bundle.putDouble("longitud", usuario.getLongitude());
                                        //intent.putExtra("pUsuario", bundle);
                                    }
                                });

                                nombreTextView.setText(nombre);
                                nombreTextView.setTextSize(20);
                                listaUsuarios.addView(foto);
                                listaUsuarios.addView(nombreTextView);
                                listaUsuarios.addView(button);

                            }
                        })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        myRef.removeEventListener(listener);
    }

}
