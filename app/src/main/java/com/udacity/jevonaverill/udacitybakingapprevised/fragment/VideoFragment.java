package com.udacity.jevonaverill.udacitybakingapprevised.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.udacity.jevonaverill.udacitybakingapprevised.R;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jevonaverill on 9/6/17.
 */

public class VideoFragment extends Fragment {

    @BindView(R.id.exoplayer_view)
    SimpleExoPlayerView mPlayerView;

    @BindView(R.id.step_image)
    ImageView stepImage;

    @BindString(R.string.key_step_url)
    String STEP_URL;
    @BindString(R.string.key_step_image_url)
    String STEP_IMAGE_URL;

    SimpleExoPlayer mPlayer;
    Bundle args;
    private Unbinder mUnbinder;
    private String videoUrl;
    private String imageUrl;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = false;
    private static long position;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments();
        if (savedInstanceState != null) {
            position = savedInstanceState.getLong("playbackPosition", C.TIME_UNSET);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        videoUrl = args.getString(STEP_URL);
        imageUrl = args.getString(STEP_IMAGE_URL);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("playbackPosition", playbackPosition);
        position = playbackPosition;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) initializePlayer();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if (Util.SDK_INT <= 23 || mPlayer == null) initializePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23 || mPlayer == null) releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23 || mPlayer == null) releasePlayer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            playbackPosition = mPlayer.getCurrentPosition();
            currentWindow = mPlayer.getCurrentWindowIndex();
            playWhenReady = mPlayer.getPlayWhenReady();
            mPlayer.release();
            mPlayer = null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initializePlayer() {
        mPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(),
                new DefaultLoadControl()
        );
        if (!TextUtils.isEmpty(videoUrl)) {
            stepImage.setVisibility(View.GONE);
            mPlayerView.setPlayer(mPlayer);
            mPlayerView.setVisibility(View.VISIBLE);
            mPlayer.setPlayWhenReady(playWhenReady);
            if (position != 0L && position != C.TIME_UNSET) {
                mPlayer.seekTo(currentWindow, position);
            } else {
                mPlayer.seekTo(currentWindow, playbackPosition);
            }
            Uri uri = Uri.parse(videoUrl);
            MediaSource mediaSource = buildMediaSource(uri);
            mPlayer.prepare(mediaSource, true, false);
        } else if (!TextUtils.isEmpty(imageUrl)) {
            mPlayerView.setVisibility(View.GONE);
            Picasso.with(getContext()).load(imageUrl).into(stepImage);
            stepImage.setVisibility(View.VISIBLE);
        } else {
            mPlayerView.setVisibility(View.GONE);
            stepImage.setVisibility(View.GONE);
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        mPlayerView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(
                uri,
                new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(),
                null,
                null
        );
    }

}
