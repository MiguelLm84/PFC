package com.miguel_lm.pfc.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.modelo.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class NotificacionActivity extends AppCompatActivity {

    ImageButton bt_volver_notificaciones;
    EditText ed_titulo_notificacion, ed_detalle_notificacion;
    Button btn_enviar;
    CardView cardViewLogo,cardViewLogo2;
    Spinner spinner_users;
    DatabaseReference mDatabase;
    Bundle bundle;
    String tituloNotificacion;
    String detalleNotificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion);

        getSupportActionBar().hide();

        cardViewLogo = findViewById(R.id.cardViewLogo);
        cardViewLogo2 = findViewById(R.id.cardView_logo);
        bt_volver_notificaciones = findViewById(R.id.bt_volver_notificaciones);
        ed_titulo_notificacion = findViewById(R.id.ed_titulo_notificacion);
        ed_detalle_notificacion = findViewById(R.id.ed_detalle_notificacion);
        btn_enviar = findViewById(R.id.btn_enviar);
        spinner_users = findViewById(R.id.spinner_users);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        bundle = getIntent().getExtras();

        if(bundle != null){

            tituloNotificacion = bundle.getString("titulo");
            detalleNotificacion = bundle.getString("detalle");

            ed_titulo_notificacion.setText(tituloNotificacion);
            ed_detalle_notificacion.setText(detalleNotificacion);
        }

        bt_volver_notificaciones.setOnClickListener(v -> {
            onBackPressedFrag();
            cardViewLogo.setVisibility(View.GONE);
            cardViewLogo2.setVisibility(View.GONE);
            btn_enviar.setVisibility(View.GONE);
            ed_titulo_notificacion.setVisibility(View.GONE);
            ed_detalle_notificacion.setVisibility(View.GONE);
        });

        cargarDatosSpinner();
    }

    public void cargarDatosSpinner(){

        List<Usuario> listaUsuarios = new ArrayList<>();
        mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    for(DataSnapshot ds : snapshot.getChildren()){
                        //String id = ds.getKey();
                        //String token = ds.child("token").getValue().toString();
                        String nombre = ds.child("nombre").getValue().toString();
                        String ap1 = ds.child("apellido1").getValue().toString();
                        String ap2 = ds.child("apellido2").getValue().toString();

                        listaUsuarios.add(new Usuario(/*token,id,*/nombre,ap1,ap2));
                    }

                    ArrayAdapter<Usuario> arrayAdapterUsuarios = new ArrayAdapter<>(NotificacionActivity.this, R.layout.spinner_item_text, listaUsuarios);
                    spinner_users.setAdapter(arrayAdapterUsuarios);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }

    public void enviarNotificaciones(View view){

        String titulo = ed_titulo_notificacion.getText().toString();
        String detalle = ed_detalle_notificacion.getText().toString();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            enviarNotificacionesOreoOsuperior(titulo,detalle);
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            enviarNotificacionesOreoOmenor(titulo,detalle);
        }
    }

    public void enviarNotificacionesOreoOsuperior(String titulo, String detalle){

        String id = "Association Manager";

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel nc = new NotificationChannel(id,"nuevo",NotificationManager.IMPORTANCE_HIGH);
            nc.setShowBadge(true);
            assert nm != null;
            nm.createNotificationChannel(nc);
        }
        builder.setAutoCancel(true)
                .setSound(soundUri)
                .setContentTitle(titulo)
                .setSmallIcon(R.mipmap.ic_asociation_manager_notificaciones)
                .setTicker("Association Manager")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setWhen(System.currentTimeMillis())
                .setContentText(detalle)
                .setContentIntent(clickNotify())
                .setContentInfo("nuevo");
        Random random = new Random();
        int idNotify = random.nextInt(8000);

        assert nm != null;
        nm.notify(idNotify,builder.build());
    }

    private void enviarNotificacionesOreoOmenor(String titulo,String detalle){

        String id = "Association Manager";

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);

        builder.setAutoCancel(true)
                .setSound(soundUri)
                .setContentTitle(titulo)
                .setSmallIcon(R.mipmap.ic_asociation_manager)
                .setTicker("Association Manager")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setWhen(System.currentTimeMillis())
                .setContentText(detalle)
                .setContentIntent(clickNotify())
                .setContentInfo("nuevo");
        Random random = new Random();
        int idNotify = random.nextInt(8000);

        assert nm != null;
        nm.notify(idNotify,builder.build());
    }

    public PendingIntent clickNotify(){

        String titulo = ed_titulo_notificacion.getText().toString();
        String detalle = ed_detalle_notificacion.getText().toString();

        Intent nf = new Intent(getApplicationContext(),NotificacionActivity.class);
        nf.putExtra("titulo", titulo);
        nf.putExtra("detalle", detalle);
        nf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        return PendingIntent.getActivity(this,0,nf,0);
    }

    public void enviarNotificacionPorTopic(View view){

        String tituloNotificacion = ed_titulo_notificacion.getText().toString();
        String detalleNotificacion = ed_detalle_notificacion.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext()); //getApplicationContext()
        JSONObject json = new JSONObject();

        try{
            //String token = "cJP26R6UQI2gl-dRcLhNRK:APA91bGaE-ZwmZwPFudtXYtcocUs57PsUepx3BpT_cQqJx7PbGM-PAYidUV7M5EP04U9DUhwXqOtAVHgbeR4DNW1ZUpoiNRTKDmraj6jWAN9KWeUdbDUDqK-0wwV8tI9bQvk6csXs1qI";
            //String token = "fX_gdvc5SxWalUlxTA1CoI:APA91bGVmCKuHo_A1A3Pf6hRo68r6WYQcfBrlirDKW9nJ6zcmpFnW_NmAipHcuyl5-vNJo2_13ZHk6IFSZs6Cnwfk5wX9zrxFm_hhwEFx9InLADlihruJm_XMkGflLxy_zVARTPv8Jh9";
            String token = FirebaseInstanceId.getInstance().getToken();

            json.put("to",token);
            JSONObject notificacion = new JSONObject();
            notificacion.put("titulo",tituloNotificacion);
            notificacion.put("detalle",detalleNotificacion);

            json.put("data", notificacion);


        } catch(JSONException e){
            Log.e("TAG JSONException","Error al enviar notificacion push individual");
        }

        String URL = "https://fcm.googleapis.com/fcm/send";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,URL,json,null,null){
            @Override
            public Map<String, String> getHeaders(){
                Map<String,String> header = new HashMap<>();

                header.put("content-type", "aplication/json");
                header.put("authorization", "key=AAAA3prh0ZI:APA91bGcczq5rUbg4qSV2XTdBqcCpt7e5-8SgNtZJiDtixwG2BpzF1Tz42jJccBVG1Pfckfw4lCu7-ZRXUE8cAa64e7OzyEWPQSEktaocvXAY8feuTMoxK-d7rWCgb_bXutlNfVYLK9B");

                return header;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(500000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(request);
        Toast.makeText(this, "La notificación ha sido enviada correctamente.", Toast.LENGTH_SHORT).show();
    }

    public void enviarNotificacionIndividual(View view){

        String tituloNotifi = ed_titulo_notificacion.getText().toString();
        String detalleNotifi = ed_detalle_notificacion.getText().toString();

        //String token = "cJP26R6UQI2gl-dRcLhNRK:APA91bGaE-ZwmZwPFudtXYtcocUs57PsUepx3BpT_cQqJx7PbGM-PAYidUV7M5EP04U9DUhwXqOtAVHgbeR4DNW1ZUpoiNRTKDmraj6jWAN9KWeUdbDUDqK-0wwV8tI9bQvk6csXs1qI";
        String token = "fX_gdvc5SxWalUlxTA1CoI:APA91bGVmCKuHo_A1A3Pf6hRo68r6WYQcfBrlirDKW9nJ6zcmpFnW_NmAipHcuyl5-vNJo2_13ZHk6IFSZs6Cnwfk5wX9zrxFm_hhwEFx9InLADlihruJm_XMkGflLxy_zVARTPv8Jh9";
        //String token = "dpJGJO28S4qXihAAFqaXXh:APA91bE9dmqUPkHl2_a0q9dGZwU_Go1PaW8N1bBtMYh6mbxJNa6Y-v_aRBkbWqhCUJ3-AnZbU9yDYx4tqhlUbrIyh6_Jl7euoslc3yh2hRrpwTrXpkHpYGFQx-VxJSrUC4if48x-2Iw8";
        //String token = FirebaseInstanceId.getInstance().getToken();

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("to", token);

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
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Codificación no admitida al intentar obtener los bytes de %s usando %s", requestBody, "utf-8");
                    return null;
                }
            }
        };

        sr.setRetryPolicy(new DefaultRetryPolicy(500000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(sr);
        Toast.makeText(this, "La notificación ha sido enviada correctamente.", Toast.LENGTH_SHORT).show();
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
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}