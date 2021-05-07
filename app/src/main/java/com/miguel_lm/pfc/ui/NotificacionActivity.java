package com.miguel_lm.pfc.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.modelo.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificacionActivity extends AppCompatActivity {

    ImageButton bt_volver_notificaciones;
    EditText ed_titulo_notificacion, ed_detalle_notificacion;
    Button btn_enviar;
    CardView cardViewLogo,cardViewLogo2;
    Spinner spinner_users;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion);

        cardViewLogo = findViewById(R.id.cardViewLogo);
        cardViewLogo2 = findViewById(R.id.cardViewLogo2);
        bt_volver_notificaciones = findViewById(R.id.bt_volver_notificaciones);
        ed_titulo_notificacion = findViewById(R.id.ed_titulo_notificacion);
        ed_detalle_notificacion = findViewById(R.id.ed_detalle_notificacion);
        btn_enviar = findViewById(R.id.btn_enviar);
        spinner_users = findViewById(R.id.spinner_users);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        bt_volver_notificaciones.setOnClickListener(v -> {
            onBackPressed();
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

                    /*ArrayAdapter arrayAdapterUsuarios = new ArrayAdapter(NotificacionActivity.this, R.layout.spinner_item_text, listaUsuarios);
                    spinner_users.setAdapter(arrayAdapterUsuarios);*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }

    public void enviarNotificacionIndividual(View view){

        String tituloNotificacion = ed_titulo_notificacion.getText().toString();
        String detalleNotificacion = ed_detalle_notificacion.getText().toString();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject json = new JSONObject();

        try{
            String token = "cJP26R6UQI2gl-dRcLhNRK:APA91bGaE-ZwmZwPFudtXYtcocUs57PsUepx3BpT_cQqJx7PbGM-PAYidUV7M5EP04U9DUhwXqOtAVHgbeR4DNW1ZUpoiNRTKDmraj6jWAN9KWeUdbDUDqK-0wwV8tI9bQvk6csXs1qI";

            json.put("to",token);
            JSONObject notificacion = new JSONObject();
            notificacion.put("titulo",tituloNotificacion);
            notificacion.put("detalle",detalleNotificacion);

            json.put("data", notificacion);

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
            requestQueue.add(request);
            Toast.makeText(this, "La notificación ha sido enviada correctamente.", Toast.LENGTH_SHORT).show();

        } catch(JSONException e){
            Log.e("TAG JSONException","Error al enviar notificacion push individual");
        }
    }

    public void enviarNotificacionPorTopic(){


    }
    public void onBackPressed(){

        Fragment fragGestion = new FragmentGestion();
        getSupportFragmentManager().beginTransaction().add(R.id.FrameLayoutNotificacion,fragGestion).setCustomAnimations(R.anim.fade_in, R.anim.fade_out).commit();
        cardViewLogo.setVisibility(View.GONE);
        cardViewLogo2.setVisibility(View.GONE);
        btn_enviar.setVisibility(View.GONE);
        ed_titulo_notificacion.setVisibility(View.GONE);
        ed_detalle_notificacion.setVisibility(View.GONE);
    }
}