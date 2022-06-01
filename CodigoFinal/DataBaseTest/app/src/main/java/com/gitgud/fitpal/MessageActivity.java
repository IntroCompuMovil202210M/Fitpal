package com.gitgud.fitpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gitgud.fitpal.entidades.Chat;
import com.gitgud.fitpal.entidades.Usuario;
import com.gitgud.fitpal.entidades.messageAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username;
    ImageButton btn_send;
    EditText text_send;
    DatabaseReference reference;
    private FirebaseDatabase database;
    public static final String PATH_USERS = "usuarios/";


    FirebaseUser fuser;
    Intent intent;
    messageAdapter messageAdapter;
    List<Chat> mChat;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        database = FirebaseDatabase.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        profile_image= findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        btn_send= findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);
        intent = getIntent();
        String userid = intent.getStringExtra("userid");
        String name = intent.getStringExtra("name");
        fuser= FirebaseAuth.getInstance().getCurrentUser();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = text_send.getText().toString();
                if (!msg.equals ("")){
                    sendMessage(fuser.getUid(), userid, msg);
                }else{
                    Toast.makeText(MessageActivity.this, "No puedes enviar mensajes vacios", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });

        reference = database.getReference(PATH_USERS+fuser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue(Usuario.class);
                username.setText(name);
                profile_image.setImageResource(R.drawable.viga);
                readMessages(fuser.getUid(), userid, "viga.jpg");


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void sendMessage(String sender, String receiver, String message){

        DatabaseReference reference = database.getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        reference.child("Chats").push().setValue(hashMap);



    }
    private void readMessages(String myid, String userid, String image){
        mChat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);

                    if(chat.getReceiver().equals(myid) && chat.getSender().equals(userid) || chat.getReceiver().equals(userid) && chat.getSender().equals(myid)){
                        mChat.add(chat);
                    }
                    messageAdapter = new messageAdapter(MessageActivity.this, mChat, image);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}