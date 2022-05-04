package com.gitgud.fitpal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.FileProvider;

import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class UploadImageActivity extends AppCompatActivity {

    Button seleccionar, camara, camara1;
    ImageView imagen;
    Uri uricamara;

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
            }
        });


    }

}