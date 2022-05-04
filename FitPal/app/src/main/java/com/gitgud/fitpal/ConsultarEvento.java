package com.gitgud.fitpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gitgud.fitpal.entidades.Deporte;
import com.gitgud.fitpal.entidades.Evento;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.StringValue;

public class ConsultarEvento extends AppCompatActivity {

    private FirebaseFirestore db;
    private TextView tvDeporte,tvOrganizador, tvDescripcion,tvFecha, tvDuracion;
    private ListView listAsistentes;
    private String idEvento;
    private Evento evento;
    private String deporte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_evento);
        db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        idEvento = intent.getStringExtra("idEvento");
        cargarEvento();
        cargarDeporte();

        inflarObjetos();

    }

    private void inflarObjetos(){
        tvDeporte.setText(deporte);
        tvOrganizador.setText(evento.getOrganizador());
        tvDescripcion.setText(evento.getDescrpicion());
        tvFecha.setText(evento.getFechahora());
        int minutos = (int)(evento.getDuracion()*60);
        String duracion = (String.valueOf(minutos)+" minutos");
        tvDuracion.setText(duracion);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, evento.getAsistentes());
        listAsistentes.setAdapter(adapter);
    }

    private void cargarDeporte(){
        final String[] name = new String[1];
        DocumentReference docRef = db.collection("deportes").document(evento.getDeporte());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Deporte ddeporte = documentSnapshot.toObject(Deporte.class);
                name[0] = ddeporte.getNombre();
            }
        });

        deporte = name.toString();

    }

    private void cargarEvento(){
        DocumentReference docRef = db.collection("evento").document(idEvento);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                evento = documentSnapshot.toObject(Evento.class);
            }
        });
    }
}