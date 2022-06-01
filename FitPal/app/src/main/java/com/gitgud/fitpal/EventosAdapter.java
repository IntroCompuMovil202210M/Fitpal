package com.gitgud.fitpal;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gitgud.fitpal.entidades.Deporte;
import com.gitgud.fitpal.entidades.Evento;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class EventosAdapter extends RecyclerView.Adapter<EventosAdapter.ViewHolder> {

    private List<Evento> mData;
    private LayoutInflater mInflater;
    private Context context;
    private ItemClickListener mitemClickListener;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public EventosAdapter(List<Evento> items, Context context, ItemClickListener itemClickListener){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = items;
        this.mitemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {return mData.size();}

    public interface ItemClickListener{
        void onItemClick(Evento evento);
    }

    @Override
    public EventosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.item_eventos, null);
        return new EventosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EventosAdapter.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));

        holder.itemView.setOnClickListener(view -> {
            mitemClickListener.onItemClick(mData.get(position));
        });
    }

    public void setItems(List<Evento> items){mData=items;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView icono;
        TextView deporteTextView, organizadorTextView;

        ViewHolder(View itemView){
            super(itemView);
            icono = itemView.findViewById(R.id.itemimage);
            deporteTextView = itemView.findViewById(R.id.itemTVDeporte);
            organizadorTextView = itemView.findViewById(R.id.itemTVOrganizador);
        }

        void bindData(final Evento item){

            deporteTextView.setText(item.getDeporte());
            organizadorTextView.setText(item.getOrganizador());
        }
    }

}
