package com.miguel_lm.pfc.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.miguel_lm.pfc.R;

public class AuthActivity extends AppCompatActivity {

    private long tiempoParaSalir = 0;
    EditText ed_email,ed_password;
    Button bt_login;
    TextView bt_registrar;
    String email = "";
    String password = "";
    boolean emailVerified;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        mAuth = FirebaseAuth.getInstance();

        ed_email = findViewById(R.id.ed_email);
        ed_password = findViewById(R.id.ed_password);
        bt_registrar = findViewById(R.id.btn_registro);
        bt_login = findViewById(R.id.btn_login);
    }

    public void OnClickRegistrar(View view){

        Intent intent = new Intent(AuthActivity.this, RegistroActivity.class);
        startActivity(intent);
        finish();
    }

    public void OnClickAcceder(View view){

        login();

        /*email = ed_email.getText().toString();
        password = ed_password.getText().toString();

        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String emailUser = user.getEmail();
            String passwordUser = user.getDisplayName();

            emailVerified = user.isEmailVerified();

            if(!email.isEmpty() && !password.isEmpty() || email.equals(emailUser) && password.equals(passwordUser)){

                if(password.length() >= 6){

                    Intent intent = new Intent(AuthActivity.this, ActivityNavigationDrawer.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                } else {
                    Toast.makeText(this, "La contarseña debe tener al menos 6 caracteres.", Toast.LENGTH_SHORT).show();
                }

            } else {
                if(password.length() < 6){
                    Toast.makeText(this, "La contarseña debe tener al menos 6 caracteres.", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "El email o la contraseña no es correcta", Toast.LENGTH_SHORT).show();
                }
            }

        } else {
            Toast.makeText(this, "El email o la contraseña no es correcta", Toast.LENGTH_SHORT).show();
        }*/
    }

    public void login(){

        email = ed_email.getText().toString();
        password = ed_password.getText().toString();

        if(email.isEmpty() && password.isEmpty()){
            Toast.makeText(AuthActivity.this, "Error, los campos no pueden estar vacios", Toast.LENGTH_SHORT).show();

        } else if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(AuthActivity.this, "Error, el campo del email o de la contraseña está vacio.", Toast.LENGTH_SHORT).show();

        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        user = mAuth.getCurrentUser();

                        if (user != null) {
                            String emailUser = user.getEmail();
                            String passwordUser = user.getDisplayName();

                            emailVerified = user.isEmailVerified();

                            if(!email.isEmpty() && !password.isEmpty() || email.equals(emailUser) && password.equals(passwordUser)){

                                if(password.length() >= 6){

                                    Intent intent = new Intent(AuthActivity.this, ActivityNavigationDrawer.class);
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                                } else {
                                    Toast.makeText(AuthActivity.this, "La contarseña debe tener al menos 6 caracteres.", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                if(password.length() < 6){
                                    Toast.makeText(AuthActivity.this, "La contarseña debe tener al menos 6 caracteres.", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(AuthActivity.this, "El email o la contraseña no es correcta", Toast.LENGTH_SHORT).show();
                                }
                            }

                        } else {
                            Toast.makeText(AuthActivity.this, "El email o la contraseña no es correcta", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(AuthActivity.this, "Error, la autenticación ha fallado.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed(){

        long tiempo = System.currentTimeMillis();
        if (tiempo - tiempoParaSalir > 3000) {
            tiempoParaSalir = tiempo;
            Toast.makeText(this, "Presione de nuevo 'Atrás' si desea salir",
                    Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }
}