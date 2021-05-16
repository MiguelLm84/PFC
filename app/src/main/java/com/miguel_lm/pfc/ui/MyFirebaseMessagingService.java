package com.miguel_lm.pfc.ui;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.miguel_lm.pfc.R;
import java.util.Random;

@SuppressLint("MissingFirebaseInstanceTokenRefresh")
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String from = remoteMessage.getFrom();
        String titulo = remoteMessage.getData().get("titulo");
        String detalle = remoteMessage.getData().get("detalle");

        Log.e("TAG_FROM", "Mensaje recibido de: "+from);
        Log.e("TAG_TITULO", "Titulo: "+titulo);
        Log.e("TAG_DETALLE", "Detalle: "+detalle);

        if(remoteMessage.getData() != null){

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                versionMayorQueOreo(titulo,detalle);
            }
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
                versionMenorQueOreo(titulo,detalle);
            }
        }

        if(remoteMessage.getData().size() > 0){

            Log.d("TAG_DATA", "DATA: " + remoteMessage.getData());
        }
    }

    public void versionMayorQueOreo(String titulo, String detalle){

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
                .setContentIntent(clickNotify(titulo,detalle))
                .setContentInfo("nuevo");
        Random random = new Random();
        int idNotify = random.nextInt(8000);

        assert nm != null;
        nm.notify(idNotify,builder.build());
    }

    private void versionMenorQueOreo(String titulo, String detalle) {

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
                .setContentIntent(clickNotify(titulo,detalle))
                .setContentInfo("nuevo");
        Random random = new Random();
        int idNotify = random.nextInt(8000);

        assert nm != null;
        nm.notify(idNotify,builder.build());
    }

    public PendingIntent clickNotify(String titulo, String detalle){

        Intent nf = new Intent(getApplicationContext(),NotificacionActivity.class);
        nf.putExtra("titulo", titulo);
        nf.putExtra("detalle", detalle);
        nf.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        return PendingIntent.getActivity(this,0,nf,0);
    }
}
