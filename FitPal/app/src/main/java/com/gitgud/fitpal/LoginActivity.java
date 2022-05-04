package com.gitgud.fitpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    Button login;
    TextView email;
    TextView password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView registerBT = findViewById(R.id.textView_login);
        registerBT.setOnClickListener(register);
        mAuth = FirebaseAuth.getInstance();
        email = (TextView) findViewById(R.id.et_correo_login);
        password = (TextView) findViewById(R.id.et_pass_login);
        login = findViewById(R.id.bt_register_a);
        login.setOnClickListener(loginFun);
    }
    private View.OnClickListener register = new android.view.View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }
    };
    private View.OnClickListener loginFun  = new android.view.View.OnClickListener() {
        @Override
        public void onClick(View view) {boolean mail = true,pass=true;
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
            if(!validarEmail(email.getText().toString())){
                email.setError("Correo invalido.");
                mail = false;
            }
            if(email.getText().length()==0){
                email.setError("Ingrese un correo electronico.");
                mail=false;
            }
            if(mail&&pass){
                String correo = email.getText().toString();
                String contraseña = password.getText().toString();
                mAuth.signInWithEmailAndPassword(correo, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "¡Iniciando sesion!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, Mapa.class);
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
                });
            }
        }
    };
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}