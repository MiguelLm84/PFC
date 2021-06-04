
package com.miguel_lm.pfc.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.singletons.ColorConfigurator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class NotificacionTopicActivity extends AppCompatActivity {

    TextView titulo_notificaciones_topic,titulo_notificaciones,tv_titulo_topic;
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
    String topic = "afiliados";
    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorConfigurator.getInstance().readTheme(this, getSupportActionBar());
        setContentView(R.layout.activity_notificacion);
        getSupportActionBar().hide();
        init();
        visibilidadElementosVista();
        bundle = getIntent().getExtras();

        bt_volver_notificaciones.setOnClickListener(v -> {
            onBackPressedFrag();
            cardViewLogo.setVisibility(View.GONE);
            cardViewLogo2.setVisibility(View.GONE);
            btn_enviar.setVisibility(View.GONE);
            ed_titulo_notificacion.setVisibility(View.GONE);
            ed_detalle_notificacion.setVisibility(View.GONE);
        });

        btn_enviar.setOnClickListener(v -> {
            System.out.println("He detectado el click deenviar la notificaicon");
            enviarNotificacionPorTopic();
        });

        if(bundle != null){
            obtenerDatosBundle();

        } else {
            tv_titulo_topic.setText(topic);
            ed_titulo_notificacion.setText("");
            ed_detalle_notificacion.setText("");
        }
    }

    @Override
    public void onResume() {

        super.onResume();
        ColorConfigurator.getInstance().readTheme(this, getSupportActionBar());
    }

    public void init() {

        titulo_notificaciones = findViewById(R.id.titulo_notificaciones);
        titulo_notificaciones_topic = findViewById(R.id.titulo_notificaciones_topic);
        tv_titulo_topic = findViewById(R.id.tv_titulo_topic);

        cardViewLogo = findViewById(R.id.cardViewLogo);
        cardViewLogo2 = findViewById(R.id.cardView_logo);
        bt_volver_notificaciones = findViewById(R.id.bt_volver_notificaciones);
        ed_titulo_notificacion = findViewById(R.id.ed_titulo_notificacion);
        ed_detalle_notificacion = findViewById(R.id.ed_detalle_notificacion);
        btn_enviar = findViewById(R.id.btn_enviar);
        spinner_users = findViewById(R.id.spinner_users);

        tv_titulo_topic.setText(topic);
        ed_titulo_notificacion.setText("");
        ed_detalle_notificacion.setText("");
    }

    public void visibilidadElementosVista(){

        titulo_notificaciones.setVisibility(View.INVISIBLE);
        titulo_notificaciones_topic.setVisibility(View.VISIBLE);
        spinner_users.setVisibility(View.INVISIBLE);
        tv_titulo_topic.setVisibility(View.VISIBLE);
    }

    public void obtenerDatosBundle() {

        ed_titulo_notificacion.setText("");
        ed_detalle_notificacion.setText("");

        if (bundle != null) {

            for(String key : bundle.keySet()){

                emisorNotificacion = bundle.getString(key);
                tituloNotificacion = bundle.getString("titulo");
                detalleNotificacion = bundle.getString("detalle");

                ed_titulo_notificacion.setText(tituloNotificacion);
                ed_detalle_notificacion.setText(detalleNotificacion);
                //ed_detalle_notificacion.append(emisorNotificacion + "\n" + tituloNotificacion + "\n" + detalleNotificacion);
            }
        }
    }

    public void enviarNotificacionPorTopic() {

        String tituloNotificacion = ed_titulo_notificacion.getText().toString();
        String detalleNotificacion = ed_detalle_notificacion.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject jsonBody = new JSONObject();

        try {
            jsonBody.put("to", "/topics/" + "afiliados");
            JSONObject notificacion = new JSONObject();
            notificacion.put("titulo", tituloNotificacion);
            notificacion.put("detalle", detalleNotificacion);
            jsonBody.put("data", notificacion);
        } catch (JSONException e) {
            Log.e("TAG JSONException", "Error al enviar notificacion push por suscripción");
        }

        String URL = "https://fcm.googleapis.com/fcm/send";

        final String requestBody = jsonBody.toString();
        System.out.println("El tipic: " + requestBody.toString());
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