package com.miguel_lm.pfc.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.miguel_lm.pfc.R;

public class LogoutActivity extends AppCompatActivity {

    private long tiempoParaSalir = 0;
    TextView tv_email, tv_password;
    Button btn_logout;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    FirebaseUser user;
    String emailUser;
    String passwordUser;
    ImageView btn_regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        tv_email = findViewById(R.id.tv_email);
        tv_password = findViewById(R.id.tv_password);
        btn_logout = findViewById(R.id.btn_logout);
        btn_regresar = findViewById(R.id.btn_regresar);

        btn_regresar.setOnClickListener(v -> onBackPressed2());

        user = mAuth.getCurrentUser();

        if (user != null) {
            emailUser = user.getEmail();
            passwordUser = user.getDisplayName();    //.getProviderId();
            tv_email.setText(emailUser);
            tv_password.setText(passwordUser);

        } else {
            Toast.makeText(this, "Error, no se han podido recuperar los datos.",Toast.LENGTH_SHORT).show();
        }
    }

    public void cerrarSesion(View view){

        mAuth.signOut();
        startActivity(new Intent(LogoutActivity.this, AuthActivity.class));
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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

    public void onBackPressed2() {

        Intent intent = new Intent(this, ActivityNavigationDrawer.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}