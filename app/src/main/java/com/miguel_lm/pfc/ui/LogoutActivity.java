package com.miguel_lm.pfc.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.modelo.Usuario;
import com.miguel_lm.pfc.singletons.ColorConfigurator;
import com.miguel_lm.pfc.singletons.FotoPerfilProvider;

public class LogoutActivity extends AppCompatActivity {

    private long tiempoParaSalir = 0;
    TextView tv_email, tv_password;
    Button btn_logout;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    FirebaseUser user;
    ImageView btn_regresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorConfigurator.getInstance().readThemeNoBackgroundDrawable(this, getSupportActionBar());
        setContentView(R.layout.activity_logout);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        tv_email = findViewById(R.id.tv_email);
        tv_password = findViewById(R.id.tv_password);
        btn_logout = findViewById(R.id.btn_logout);
        btn_regresar = findViewById(R.id.btn_regresar);

        btn_regresar.setOnClickListener(v -> onBackPressed2());

        mostrarDatosUser();
    }

    @Override
    public void onResume() {

        super.onResume();
        ColorConfigurator.getInstance().readThemeNoBackgroundDrawable(this, getSupportActionBar());
    }

    public void cerrarSesion(View view){
        FotoPerfilProvider.fotoPerfil=null;
        mAuth.signOut();
        startActivity(new Intent(LogoutActivity.this, AuthActivity.class));
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void mostrarDatosUser(){

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = mAuth.getCurrentUser();

        if (user != null) {

            String emailUser = user.getEmail();
            assert emailUser != null;

            mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.exists()){

                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                            Usuario user = dataSnapshot.getValue(Usuario.class);
                            assert user != null;

                            if(emailUser.equals(user.getEmail())){
                                String email = user.getEmail();
                                String password = user.getPassword();

                                tv_email.setText(email);
                                tv_password.setText(password);
                            }
                        }

                    } else {
                        Toast.makeText(LogoutActivity.this,"ERROR, los datos no se han podido recuperar.",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else {
            Toast.makeText(this, "Error, no se han podido recuperar los datos.",Toast.LENGTH_SHORT).show();
        }
    }

    /*public String desemcriptarPassword(String password) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] bytesSecretKey = secretKey.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(bytesSecretKey,AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
        byte[] passwordDesencript = cipher.doFinal(password.getBytes());
        String pswd = new String(passwordDesencript);
        Log.d("PASSWORD_DESENCRIPTADO", pswd);

        return pswd;
    }*/

    @Override
    public void onBackPressed(){

        long tiempo = System.currentTimeMillis();
        if (tiempo - tiempoParaSalir > 3000) {
            tiempoParaSalir = tiempo;
            Toast.makeText(this, "Presione de nuevo 'Atr??s' si desea salir",
                    Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    public void onBackPressed2() {

        Intent intent = new Intent(this, ActivityNavigationDrawer.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}