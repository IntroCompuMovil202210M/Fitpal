package com.gitgud.fitpal;

import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.gitgud.fitpal.entidades.Evento;
import com.gitgud.fitpal.entidades.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.List;

public class listaEventos extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    EventosAdapter mEventosAdapter;
    List<Evento> mEventos = new ArrayList<>();
    ListView listaEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos);

        user = mAuth.getCurrentUser();

        db = FirebaseFirestore.getInstance();
        inflarObjetos();

    }

    private void inflarObjetos(){

        listaEventos = (ListView) findViewById(R.id.listaEventos);
        obtenerEventosBase();
        mEventosAdapter = new EventosAdapter(this, R.layout.item_eventos, mEventos);
        listaEventos.setAdapter(mEventosAdapter);
    }

    private void obtenerEventosBase(){
        String correo = user.getEmail();
        ArrayList<String> eventosStrings = new ArrayList<>();
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
        eventosStrings = usuarios.get(0).getEventos();
        ArrayList<Evento> eventos;
        for(String eventoString: eventosStrings){
            DocumentReference docRef = db.collection("eventos").document(eventoString);

            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Evento evento = documentSnapshot.toObject(Evento.class);
                    mEventos.add(evento);
                }
            });
        }



    }
}