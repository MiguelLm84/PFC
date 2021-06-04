package com.miguel_lm.pfc.modelo;

import android.annotation.SuppressLint;
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

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.miguel_lm.pfc.R;
import com.miguel_lm.pfc.ui.NotificacionActivity;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    /*@Override
    public void onNewToken(@NotNull String s) {
        super.onNewToken(s);



        Log.e("NEW_TOKEN","EL NUEVO TOKEN ES: " + s);
    }*/

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        System.out.println("NOTIFICACION RECEIVEEEED");

        String from = remoteMessage.getFrom();
        String titulo = remoteMessage.getData().get("titulo");
        String detalle = remoteMessage.getData().get("detalle");

        Log.e("TAG_FROM", "Mensaje recibido de: "+from);
        Log.e("TAG_TITULO", "Titulo: "+titulo);
        Log.e("TAG_DETALLE", "Detalle: "+detalle);

        Bundle bundle = new Bundle();
        bundle.putString("titulo",titulo);
        bundle.putString("detalle",detalle);

        if(remoteMessage.getData() != null){


            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                versionMayorQueOreo(from,titulo,detalle, bundle);
            }
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
                versionMenorQueOreo(from,titulo,detalle, bundle);
            }
        }

        if(remoteMessage.getData().size() > 0){

            Log.d("TAG_DATA", "DATA: " + remoteMessage.getData());
        }
        Log.i("TAG_MENSAJE", String.valueOf(remoteMessage.getData()));

        //versionMayorQueOreo(from,titulo,detalle);

        /*Intent nf = new Intent(getApplicationContext(),NotificacionActivity.class);
        nf.putExtras(bundle);
        nf.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(nf);*/
    }

    public void versionMayorQueOreo(String from,String titulo, String detalle, Bundle bundle){

        String id = "mensaje";

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
                .setContentIntent(clickNotify(from,titulo,detalle, bundle))

                .setContentInfo("nuevo");

        assert nm != null;
        nm.notify(0,builder.build());
    }

    private void versionMenorQueOreo(String from,String titulo, String detalle, Bundle bundle) {

        String id = "mensaje";

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id);

        builder.setAutoCancel(true)
                .setSound(soundUri)
                .setContentTitle(titulo)
                .setSmallIcon(R.mipmap.ic_asociation_manager_notificaciones)
                .setTicker("Association Manager")

                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setWhen(System.currentTimeMillis())
                .setContentText(detalle)
                .setContentIntent(clickNotify(from,titulo,detalle, bundle))
                .setContentInfo("nuevo");

        assert nm != null;
        nm.notify(0,builder.build());
    }

    public PendingIntent clickNotify(String from,String titulo, String detalle, Bundle bundle){

        Intent nf = new Intent(getApplicationContext(), NotificacionActivity.class);
        /*nf.putExtra("from", from);
        nf.putExtra("titulo", titulo);
        nf.putExtra("detalle", detalle);*/
        nf.putExtras(bundle);
       // nf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        nf.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(MyFirebaseMessagingService.this, 0, nf, PendingIntent.FLAG_UPDATE_CURRENT,bundle);
        //return PendingIntent.getActivity(this,0,nf,0);
    }
}
