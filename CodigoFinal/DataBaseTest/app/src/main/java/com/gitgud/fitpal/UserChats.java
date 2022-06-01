package com.gitgud.fitpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.google.android.material.navigation.NavigationView;
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

public class UserChats extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    assert user != null;
                    String userid = user.getUid();

                    ImageView foto =new ImageView(UserChats.this);
                    foto.setImageResource(R.drawable.viga);
                    foto.setMinimumHeight(100);
                    foto.setMaxHeight(100);
                    TextView nombreTextView = new TextView(UserChats.this);
                    nombreTextView.setText(nombre);
                    nombreTextView.setTextSize(20);
                    Button button = new Button(UserChats.this);
                    button.setTag(usuario);
                    button.setText("Chat");
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getBaseContext(), MessageActivity.class);
                            intent.putExtra("userid",usuario.getUserId());
                            intent.putExtra("name", usuario.getNombre());
                            startActivity(intent);

                            //Bundle bundle = new Bundle();
                            //bundle.putDouble("latitud", usuario.getLatitude());
                            //bundle.putDouble("longitud", usuario.getLongitude());
                            //intent.putExtra("pUsuario", bundle);
                        }
                    });
                    listaUsuarios.addView(foto);
                    listaUsuarios.addView(nombreTextView);
                    listaUsuarios.addView(button);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent;
        switch (menuItem.getItemId()) {
            case R.id.establecerDisponible:
                intent = new Intent(UserChats.this, MiPerfil.class);
                startActivity(intent);
                break;
            case R.id.mapaPrincipal:
                intent = new Intent(UserChats.this, Mapa.class);
                startActivity(intent);
                break;
            case R.id.salir:
                mAuth.signOut();
                intent = new Intent(UserChats.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        myRef.removeEventListener(listener);
    }



}
