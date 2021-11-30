package com.exoplayersampleapptransitions;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ClippingMediaSource;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String userAgent;
    DataSource.Factory dataSourceFactory;
    ExtractorsFactory extractorsFactory;

    public SimpleExoPlayer exoPlayer;
    PlayerView exoplayerView;
//    We are using this EPlayerView instead of regular one, since we have implemented filters using this library for videos.
//    EPlayerView exoplayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exoplayerView = findViewById(R.id.exoplayerView);
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this,
                new DefaultRenderersFactory(this), new DefaultTrackSelector(), new DefaultLoadControl());
        prepareSources();

        exoplayerView.setPlayer(exoPlayer);
        exoplayerView.setUseController(true);

        //        exoplayerView.setSimpleExoPlayer(exoPlayer);
//        exoplayerView.onResume();
    }

    private void prepareSources() {
        userAgent = Util.getUserAgent(this, getString(R.string.app_name));
        dataSourceFactory = new DefaultDataSourceFactory(this, userAgent);
        extractorsFactory = new DefaultExtractorsFactory();

        List<MediaSource> mediaSourceList = new ArrayList<>();

        mediaSourceList.add(buildMediaSource("asset:///videotwo.mp4"));
        mediaSourceList.add(buildMediaSource("asset:///videofour.mp4"));
        mediaSourceList.add(buildMediaSource("asset:///videothree.mp4"));
        mediaSourceList.add(buildMediaSource("asset:///videoone.mp4"));

        ConcatenatingMediaSource concatenatingMediaSource = new ConcatenatingMediaSource();
        concatenatingMediaSource.addMediaSources(mediaSourceList);

        exoPlayer.prepare(concatenatingMediaSource);
        exoPlayer.setPlayWhenReady(true);

    }

    private MediaSource buildMediaSource(String path1) {
        return new ClippingMediaSource(new ExtractorMediaSource.Factory(dataSourceFactory)
                .setExtractorsFactory(extractorsFactory)
                .createMediaSource(Uri.parse(path1)), 0, 5000 * 1000);
    }

    @Override
    protected void onDestroy() {
        exoPlayer.release();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        exoPlayer.release();
        super.onPause();
    }

}