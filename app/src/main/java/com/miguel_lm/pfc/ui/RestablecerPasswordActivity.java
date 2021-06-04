package com.miguel_lm.pfc.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.singletons.ColorConfigurator;

public class RestablecerPasswordActivity extends AppCompatActivity {

    EditText ed_email;
    Button btn_restablecer_password;
    ImageView btn_regresar;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String email = "";
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorConfigurator.getInstance().readThemeNoBackgroundDrawable(this, getSupportActionBar());
        setContentView(R.layout.activity_restablecer_password);

        mDialog = new ProgressDialog(this);

        init();

        btn_regresar.setOnClickListener(v -> onBackPressed());
        btn_restablecer_password.setOnClickListener(v -> restablecerPassword());
    }

    @Override
    public void onResume() {

        super.onResume();
        ColorConfigurator.getInstance().readThemeNoBackgroundDrawable(this, getSupportActionBar());
    }

    public void init(){

        ed_email = findViewById(R.id.ed_email);
        btn_restablecer_password = findViewById(R.id.btn_login);
        btn_regresar = findViewById(R.id.btn_Regresar);
    }

    public void restablecerPassword(){

        email = ed_email.getText().toString();

        if(!email.isEmpty()){

            mDialog.setMessage("Espere un momento...");
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.show();
            resetPassword();
            startActivity(new Intent(RestablecerPasswordActivity.this, AuthActivity.class));
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        } else {
            Toast.makeText(RestablecerPasswordActivity.this,"Debe ingresar el email con el que se registró",Toast.LENGTH_SHORT).show();
        }
    }

    public void resetPassword(){

        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    Toast.makeText(RestablecerPasswordActivity.this,"Correo de restablecimiento de contraseña enviado correctamente.",Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(RestablecerPasswordActivity.this,"No se ha podido enviar el correo de restablecimiento de contraseña.",Toast.LENGTH_SHORT).show();
                }

                mDialog.dismiss();
            }
        });
    }

    public void onBackPressed() {

        Intent intent = new Intent(this, AuthActivity.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}