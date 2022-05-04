package com.gitgud.fitpal;

import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.gitgud.fitpal.entidades.Evento;
import com.gitgud.fitpal.entidades.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

public class listaEventos extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    EventosAdapter mEventosAdapter;
    ArrayList<Evento> mEventos = new ArrayList<>();
    ListView listaEventos;
    BottomNavigationView menuInferior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_eventos);
        db = FirebaseFirestore.getInstance();
        menuInferior = (BottomNavigationView) findViewById(R.id.menuInferior_map);
        menuInferior.setSelectedItemId(R.id.eventos_deportivos_navegacion);
        menuInferior.setOnItemSelectedListener(item->{
            switch (item.getItemId()){
                case R.id.inicio_navegacion:
                    startActivity(new Intent(listaEventos.this, Mapa.class));
                    finish();
                    break;
                case R.id.amigos_navegacion:
                    break;
                case R.id.eventos_deportivos_navegacion:

                    break;
                case R.id.chat_navegacion:
                    break;
                case R.id.perfil_navegacion:
                    startActivity(new Intent(listaEventos.this, MiPerfil.class));
                    finish();
                    break;
            }
            return true;
        });
        user = mAuth.getCurrentUser();
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
        DocumentReference docRef = db.collection("Usuario").document(correo);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Usuario usuario = documentSnapshot.toObject(Usuario.class);
                ArrayList<String> idEventos =usuario.getEventos();
                for(String idEvento: idEventos){
                    DocumentReference RefEvento = db.collection("evento").document(idEvento);
                    RefEvento.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Evento evento = documentSnapshot.toObject(Evento.class);
                            evento.setId(idEvento);
                            mEventos.add(evento);
                        }
                    });
                }

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this,"Evento clicado",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ConsultarEvento.class);
        intent.putExtra("idEvento", mEventosAdapter.getItem(i).getId());
        startActivity(intent);
    }
}