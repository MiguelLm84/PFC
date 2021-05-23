package com.miguel_lm.pfc.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.messaging.FirebaseMessaging;
import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.modelo.Usuario;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AuthActivity extends AppCompatActivity {

    private long tiempoParaSalir = 0;
    EditText ed_email,ed_password;
    Button bt_login;
    TextView bt_registrar, btn_restablecer_password;
    String email = "";
    String password = "";
    boolean emailVerified;
    FirebaseAuth  mAuth = FirebaseAuth.getInstance();
    FirebaseUser  user = mAuth.getCurrentUser();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    String passwordEncriptado = "";

    private final static String AES ="AES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        init();
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void init(){

        ed_email = findViewById(R.id.ed_email);
        ed_password = findViewById(R.id.ed_password);
        bt_registrar = findViewById(R.id.btn_registro);
        btn_restablecer_password= findViewById(R.id.btn_restablecer_password);
        bt_login = findViewById(R.id.btn_login);
    }

    private void updateUI(FirebaseUser user) {

        if(user != null){
            Intent intent = new Intent(AuthActivity.this, ActivityNavigationDrawer.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    public void obtenerToken(){

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {

            if (!task.isSuccessful()) {
                Log.w("ERROR_TOKEN", "Error al obtener el token de registro de FCM", task.getException());

            } else {
                //Obtener un nuevo token de registro de FCM
                String token = task.getResult();
                Log.d("TOKEN_ID", token);

                mDatabase.child("Users");
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()) {

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                                String id = dataSnapshot.getKey();
                                if(id != null){
                                    Usuario usuario = dataSnapshot.getValue(Usuario.class);

                                    String email = usuario.getEmail();
                                    String emailUser = user.getEmail();

                                    if(user != null && email != null){
                                        DatabaseReference database = mDatabase.child(id);
                                        database.addListenerForSingleValueEvent(new ValueEventListener() {

                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                if (email.equals(emailUser)) {
                                                    database.child("Token").child(Objects.requireNonNull(mAuth.getUid())).setValue(token);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private String encriptar(String password) throws Exception {

        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] bytesSecretKey = secretKey.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(bytesSecretKey,AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
        byte[] passwordEncript = cipher.doFinal(password.getBytes());
        String pswd = new String(passwordEncript);
        Log.d("PASSWORD_ENCRIPTADO", pswd);

        ed_password.setText(pswd);
        passwordEncriptado = ed_password.getText().toString();

        return passwordEncriptado;
    }

    public String desemcriptarPassword(String password) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

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
    }

    public void OnClickRegistrar(View view){

        Intent intent = new Intent(AuthActivity.this, RegistroActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void OnClickRestablecerPassword(View view){

        Intent intent = new Intent(AuthActivity.this, RestablecerPasswordActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void OnClickAcceder(View view){

        login();
    }

    public void login(){

        email = ed_email.getText().toString();
        password = ed_password.getText().toString();

        if(email.isEmpty() && password.isEmpty()){
            Toast.makeText(AuthActivity.this, "Error, los campos no pueden estar vacios", Toast.LENGTH_SHORT).show();

        } else if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(AuthActivity.this, "Error, el campo del email o de la contraseña está vacio.", Toast.LENGTH_SHORT).show();

        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {

                    user = mAuth.getCurrentUser();

                    if (user != null) {
                        String emailUser = user.getEmail();
                        String passwordUser = user.getDisplayName();

                        emailVerified = user.isEmailVerified();

                        if(!email.isEmpty() && !password.isEmpty() || email.equals(emailUser) && password.equals(passwordUser)){

                            if(password.length() >= 6){

                                comprobarIsAdmin(email);
                                obtenerToken();

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
            });
        }
    }

    public void comprobarIsAdmin(String email){

        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        Usuario user = dataSnapshot.getValue(Usuario.class);

                        if (user != null && email.equals(user.getEmail())) {
                            String rol = user.getRol();
                            boolean admin = user.isAdmin();

                            Intent intent = new Intent(AuthActivity.this, ActivityNavigationDrawer.class);
                            intent.putExtra("rol", rol);
                            intent.putExtra("isAdmin", admin);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    }

                } else {
                    Log.e("TAG ERROR COMPROBACIÓN ROL", "ERROR, los datos no se han podido recuperar.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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