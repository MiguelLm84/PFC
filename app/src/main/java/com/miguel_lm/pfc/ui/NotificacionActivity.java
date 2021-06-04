package com.miguel_lm.pfc.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.modelo.Usuario;
import com.miguel_lm.pfc.singletons.ColorConfigurator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NotificacionActivity extends AppCompatActivity {

    ImageButton bt_volver_notificaciones;
    EditText ed_titulo_notificacion, ed_detalle_notificacion;
    Button btn_enviar;
    CardView cardViewLogo, cardViewLogo2;
    Spinner spinner_users;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    Bundle bundle;
    String emisorNotificacion;
    String tituloNotificacion;
    String detalleNotificacion;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    List<Usuario> listaUsuarios = new ArrayList<>();
    static String TOKEN_ID = "";

    Usuario userToSendNotification = new Usuario();

    ArrayAdapter<Usuario> arrayAdapterUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorConfigurator.getInstance().readTheme(this, getSupportActionBar());
        setContentView(R.layout.activity_notificacion);

        getSupportActionBar().hide();
        init();
        suscribirAtopics();
        bundle = getIntent().getExtras();

        bt_volver_notificaciones.setOnClickListener(v -> {
            onBackPressedFrag();
            cardViewLogo.setVisibility(View.GONE);
            cardViewLogo2.setVisibility(View.GONE);
            btn_enviar.setVisibility(View.GONE);
            ed_titulo_notificacion.setVisibility(View.GONE);
            ed_detalle_notificacion.setVisibility(View.GONE);
        });

        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("He detectado el click deenviar la notificaicon");
                enviarNotificacionConToken();
            }
        });

        if(bundle != null){
            obtenerDatosBundle();

        } else {
            cargarDatosSpinner();
        }
    }

    @Override
    public void onResume() {

        super.onResume();
        ColorConfigurator.getInstance().readTheme(this, getSupportActionBar());
    }

    public void init() {

        cardViewLogo = findViewById(R.id.cardViewLogo);
        cardViewLogo2 = findViewById(R.id.cardView_logo);
        bt_volver_notificaciones = findViewById(R.id.bt_volver_notificaciones);
        ed_titulo_notificacion = findViewById(R.id.ed_titulo_notificacion);
        ed_detalle_notificacion = findViewById(R.id.ed_detalle_notificacion);
        btn_enviar = findViewById(R.id.btn_enviar);
        spinner_users = findViewById(R.id.spinner_users);

        ed_titulo_notificacion.setText("");
        ed_detalle_notificacion.setText("");

        arrayAdapterUsuarios = new ArrayAdapter<>(NotificacionActivity.this, R.layout.spinner_item_text, listaUsuarios);
        spinner_users.setAdapter(arrayAdapterUsuarios);

        spinner_users.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userToSendNotification =  listaUsuarios.get(position);
                // @Todo HACER ALGO CON EL TOKEN DEL USUARIO QUE HE SELECCCIONADO
                Log.d("NotifiticationActivity", "He seleccionado el usuario " + position + " que tiene" + userToSendNotification.toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void obtenerDatosBundle() {

        ed_titulo_notificacion.setText("");
        ed_detalle_notificacion.setText("");

        if (bundle != null) {

            for(String key : bundle.keySet()){

                emisorNotificacion = bundle.getString(key);
                tituloNotificacion = bundle.getString("titulo");
                detalleNotificacion = bundle.getString("detalle");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    spinner_users.setTooltipText(emisorNotificacion);
                }
                ed_titulo_notificacion.setText(tituloNotificacion);
                ed_detalle_notificacion.setText(detalleNotificacion);
                //ed_detalle_notificacion.append(emisorNotificacion + "\n" + tituloNotificacion + "\n" + detalleNotificacion);
            }
        }
    }

    public void cargarDatosSpinner() {

        listaUsuarios.clear();
        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    for (DataSnapshot ds : snapshot.getChildren()) {
                        String id = ds.getKey();
                        final String[] token = new String[1];
                        ds.child("apellido2").getValue();
                        if(ds.hasChild("Token")){
                            Iterator<DataSnapshot> items = ds.child("Token").getChildren().iterator();
                            while(items.hasNext()){
                                DataSnapshot item = items.next();
                                token[0] = item.getValue().toString();
                            }
                        }

                        String nombre = ds.child("nombre").getValue().toString();
                        String ap1 = ds.child("apellido1").getValue().toString();
                        String ap2 = ds.child("apellido2").getValue().toString();
                        Usuario u = new Usuario(token[0], id, nombre, ap1, ap2);
                        listaUsuarios.add(u);
                        System.out.println("El token del usuario " + u.getNombre() + " es: " + u.getToken());
                    }
                    arrayAdapterUsuarios.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void suscribirAtopics(){

        FirebaseMessaging.getInstance().subscribeToTopic("afiliados")
                .addOnCompleteListener(task -> Log.i("TAG SUSCRIPCIÓN TOPIC","Usuario suscrito al topic 'afiliados'."));

    }



    public void enviarNotificacionConToken(){
        System.out.println("[enviarNotificacionConToken]");
        String tituloNotifi = ed_titulo_notificacion.getText().toString();
        String detalleNotifi = ed_detalle_notificacion.getText().toString();

        if(userToSendNotification.getToken()!=null){
            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("to", userToSendNotification.getToken());

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("titulo",tituloNotifi);
                jsonObject.put("detalle",detalleNotifi);

                jsonBody.putOpt("data", jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            final String requestBody = jsonBody.toString();

            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest sr = new StringRequest(Request.Method.POST,"https://fcm.googleapis.com/fcm/send", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("TAG_RESPONSE", "Response: " + response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("TAG_ERROR_RESPONSE", "That didn't work..." + error);
                }
            }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();

                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("Authorization","key=AAAA3prh0ZI:APA91bGcczq5rUbg4qSV2XTdBqcCpt7e5-8SgNtZJiDtixwG2BpzF1Tz42jJccBVG1Pfckfw4lCu7-ZRXUE8cAa64e7OzyEWPQSEktaocvXAY8feuTMoxK-d7rWCgb_bXutlNfVYLK9B");

                    return params;
                }

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        System.out.println("Envio: ");
                        System.out.println(requestBody.toString());
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Codificación no admitida al intentar obtener los bytes de %s usando %s", requestBody, "utf-8");
                        return null;
                    }
                }
            };

            sr.setRetryPolicy(new DefaultRetryPolicy(500000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            queue.add(sr);
            Toast.makeText(this, "Notificación enviada correctamente.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Usuario no alcanzable, pídale que vuelva a hacer login", Toast.LENGTH_SHORT).show();
        }

    }
    public void onBackPressedFrag(){

        Fragment fragGestion = new FragmentGestion();
        getSupportFragmentManager().beginTransaction().add(R.id.FrameLayoutNotificacion,fragGestion).setCustomAnimations(R.anim.fade_in, R.anim.fade_out).commit();
        getSupportActionBar().show();
        getSupportActionBar().setTitle("Administración Usuarios");
        cardViewLogo.setVisibility(View.GONE);
        cardViewLogo2.setVisibility(View.GONE);
        btn_enviar.setVisibility(View.GONE);
        ed_titulo_notificacion.setVisibility(View.GONE);
        ed_detalle_notificacion.setVisibility(View.GONE);
    }

    public void onBackPressed(){

        Intent intent = new Intent(this, AdminActivity.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}