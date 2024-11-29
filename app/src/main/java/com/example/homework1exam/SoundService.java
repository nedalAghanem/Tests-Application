package com.example.homework1exam;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class SoundService extends Service {
    private MediaPlayer mediaPlayer ;

    public SoundService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.sound_click);
        mediaPlayer.setLooping(false); // إذا أردت تكرار الصوت استخدم true
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopSelf();
                Log.d("MyService","StopSelf");
            }
        });
        Log.d("MyService","onCreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
        Log.d("MyService","onStartCommand");
        return START_STICKY ;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            Log.d("MyMediaService", "Audio Stopped and MediaPlayer Released");
        }
    }
}