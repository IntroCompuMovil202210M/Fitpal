package com.gitgud.fitpal;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.gitgud.fitpal.entidades.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;

public class Splash extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(user==null){
                    startActivity(new Intent(Splash.this, LoginActivity.class));
                    finish();
                }
                else{
                    db.collection("Usuario")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if(user.getEmail().equals(document.getData().get("correo"))){
                                                if((boolean) document.getData().get("registroCompleto")){
                                                    startActivity(new Intent(Splash.this, MainActivity.class));
                                                    finish();
                                                }
                                                else{
                                                    startActivity(new Intent(Splash.this, CompleteRegister.class));
                                                    finish();
                                                }
                                            }
                                        }
                                    } else {
                                        Log.w(TAG, "Error getting documents.", task.getException());
                                    }
                                }
                            });

                    //borrar esto despues

                    String correo = user.getEmail();
                    ArrayList<String> eventos = new ArrayList<>();
                    ArrayList<Usuario> usuarios = null;
                    db.collection("usuarios")
                            .whereEqualTo("correo", correo)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            usuarios.add(document.toObject(Usuario.class));
                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                    eventos = usuarios.get(0).getEventos();

                    for(String evento: eventos){
                        DocumentReference docRef = db.collection("eventos").document(evento);

                        Source source = Source.CACHE;

                    // Get the document, forcing the SDK to use the offline cache
                        docRef.get(source).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    // Document found in the offline cache
                                    DocumentSnapshot document = task.getResult();
                                    Log.d(TAG, "Cached document data: " + document.getData());
                                } else {
                                    Log.d(TAG, "Cached get failed: ", task.getException());
                                }
                            }
                        });
                    }
                    //borrar esto despues
                }

            }
        },3000);
    }
}