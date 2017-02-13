package com.susion.boring.music.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.susion.boring.music.model.Song;
import com.susion.boring.music.presenter.IMediaPlayPresenter;
import com.susion.boring.music.presenter.MediaPlayPresenter;
import com.susion.boring.music.view.IMediaPlayView;
import com.susion.boring.utils.BroadcastUtils;

/**
 * Created by susion on 17/2/13.
 */
public class MusicPlayerService extends Service implements IMediaPlayView{

    private ServiceMusicReceiver mReceiver;
    private IMediaPlayPresenter mPresenter;


    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initMusicInfo(intent);
        return super.onStartCommand(intent, flags, startId);
    }


    private void init() {
        mPresenter = new MediaPlayPresenter(this, this);

        mReceiver = new ServiceMusicReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(MusicInstruction.SERVICE_RECEIVER_PLAY_MUSIC);
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,filter);
    }

    private void initMusicInfo(Intent intent) {
        Song song = (Song) intent.getSerializableExtra(MusicInstruction.CLIENT_ACTION_MUSIC_INFO);

        try {
            mPresenter.initMediaPlayer(song.audio, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void updateBufferedProgress(int percent) {

    }

    @Override
    public void updatePlayProgress(int curPos, int duration) {

    }

    @Override
    public void preparedPlay(MediaPlayer mPlayer) {
        BroadcastUtils.sendIntentAction(this,MusicInstruction.CLIENT_RECEIVER_PLAYER_PREPARED);
    }

    @Override
    public void completionPlay() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.releaseResource();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }

    class ServiceMusicReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case MusicInstruction.SERVICE_RECEIVER_PLAY_MUSIC:
                    mPresenter.startPlay();
                    break;
            }
        }
    }

}