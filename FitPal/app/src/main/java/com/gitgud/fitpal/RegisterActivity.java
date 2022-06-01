package com.gitgud.fitpal;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gitgud.fitpal.entidades.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    Button boton;
    Button register;
    FirebaseAuth mAuth;
    TextView email;
    TextView password;
    TextView password_verify;
    TextView login;
    DatabaseReference myRef;
    FirebaseDatabase database;
    FirebaseStorage storage;
    StorageReference mStorageRef;
    private FirebaseFirestore db;
    public static final String PATH_USERS = "usuarios/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        mStorageRef = storage.getReference();

        email = (TextView) findViewById(R.id.et_correo);
        password = (TextView) findViewById(R.id.et_pass);
        password_verify = (TextView) findViewById(R.id.et_vpass);
        login = (TextView) findViewById(R.id.textView);
        login.setOnClickListener(loginActivity_bt);
        register = findViewById(R.id.bt_register);
        register.setOnClickListener(registrar);
        TextView terminos = findViewById(R.id.textTermsTV);
        terminos.setOnClickListener((View.OnClickListener) terminos_bt);
    }

    private View.OnClickListener loginActivity_bt = new android.view.View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    };
    private View.OnClickListener terminos_bt= new android.view.View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(RegisterActivity.this, termsActivity.class);
            startActivity(intent);
        }
    };
    private View.OnClickListener registrar = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            boolean mail = true,pass=true,vpass=true;
            if(!password.getText().toString().equals(password_verify.getText().toString())){
                password.setError("Contraseñas diferentes.");
                password_verify.setError("Contraseñas diferentes.");
                vpass=false;
                pass=false;
            }
            if(password.getTextSize()<8){
                    if(password.getText().length()==0){
                        password.setError("Ingrese una contraseña");
                        pass=false;
                    }
                    else{
                        password.setError("La contraseña debe tener mas de 8 caracteres.");
                        pass=false;
                    }
                }
            if(password_verify.getText().length()==0){
                password_verify.setError("Repita su contraseña.");
                vpass=false;
            }
            if(!validarEmail(email.getText().toString())){
                email.setError("Correo invalido.");
                mail = false;
            }
            if(email.getText().length()==0){
                email.setError("Ingrese un correo electronico.");
                mail=false;
            }
            if(mail&&pass&&vpass){
                String correo = email.getText().toString();
                String contraseña = password.getText().toString();
                mAuth.createUserWithEmailAndPassword(correo,contraseña)
                        .addOnCompleteListener(
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (task.isSuccessful() && user!=null) {
                                            Usuario nuevoUsuario = new Usuario();
                                            nuevoUsuario.setEmail(correo);
                                            nuevoUsuario.setPassword(contraseña);
                                            myRef= database.getReference(PATH_USERS+user.getUid());
                                            myRef.setValue(nuevoUsuario);
                                            db.collection("Usuario").document(user.getUid()).set(nuevoUsuario);
                                            Toast.makeText(RegisterActivity.this, "¡Registro Exitoso!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RegisterActivity.this, CompleteRegister.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else {
                                            Toast.makeText(
                                                    getApplicationContext(),
                                                    "Error al registrar!",
                                                    Toast.LENGTH_LONG)
                                                    .show();
                                        }
                                    }
                                }
                        );
            }
        }
    };
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

}
