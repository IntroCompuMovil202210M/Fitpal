package com.gitgud.fitpal;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.gitgud.fitpal.entidades.Deporte;
import com.gitgud.fitpal.entidades.Evento;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class EventosAdapter extends ArrayAdapter<Evento> {

    private List<Evento> mList;
    private Context mContext;
    private int resourceLayout;

    public EventosAdapter(@NonNull Context context, int resource, List<Evento> mList) {
        super(context, resource, mList);
        this.mList = mList;
        this.mContext = context;
        this.resourceLayout = resource;
    }


    public View getView(int posicion, View convertView, ViewGroup viewGroup){
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_eventos, viewGroup, false);
        }

        Evento evento = mList.get(posicion);
        TextView tvIdUsuario = (TextView) view.findViewById(R.id.idUsuario);
        TextView tvDeporte = (TextView) view.findViewById(R.id.Deporte);

        tvIdUsuario.setText(evento.getOrganizador());
        tvDeporte.setText(evento.getDeporte());

        return view;
    }

}
