package com.gitgud.fitpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;

import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class UploadImageActivity extends AppCompatActivity {

    Button seleccionar, camara, camara1;
    ImageView imagen;
    Uri uricamara;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        seleccionar = findViewById(R.id.seleccionar);
        camara = findViewById(R.id.camara);
        camara1 = findViewById(R.id.camara1);
        imagen = findViewById(R.id.imagen);
        imagen.setImageResource(R.drawable.viga);

        File archivo = new File(getFilesDir(),"fotodesdeCamara");
        uricamara = FileProvider.getUriForFile(this,getApplicationContext().getPackageName()+".fileprovider",archivo);

        ActivityResultLauncher<String> obtenerImagen = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        //Carga una imagen en la vista...
                        imagen.setImageURI(result);

                    }
                }
        );

        ActivityResultLauncher<Uri> tomarFoto = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        imagen.setImageURI(uricamara);
                    }
                }
        );

        seleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerImagen.launch("image/*");
            }
        });

        camara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarFoto.launch(uricamara);
            }
        });

        camara1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StorageReference storageRef = storage.getReference();
                Bitmap bitmap = ((BitmapDrawable) imagen.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();
                StorageReference mountainsRef = storageRef.child(currentUser.getEmail());

                UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                    }
                });
                finish();
            }
        });


    }

}