package com.gitgud.fitpal;

import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.gitgud.fitpal.data.Evento;
import com.gitgud.fitpal.data.Usuario;
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

public class listaEventos extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    EventosAdapter mEventosAdapter;
    ListView listaEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos);

        user = mAuth.getCurrentUser();

        db = FirebaseFirestore.getInstance();

    }

    private void inflarObjetos(){

        listaEventos = (ListView) findViewById(R.id.listaEventos);
        //mCursor = getContentResolver()
        //mEventosAdapter = new EventosAdapter(this, null, 0);
        listaEventos.setAdapter(mEventosAdapter);
    }

    private void obtenerEventosBase(){
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



    }
}