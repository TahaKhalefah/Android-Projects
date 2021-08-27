package com.tahadroid.quraanapp.ui.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tahadroid.quraanapp.R;
import com.tahadroid.quraanapp.adapter.RadioAdapter;
import com.tahadroid.quraanapp.data.remote.ApiManager;
import com.tahadroid.quraanapp.pojo.RadioItem;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Estema3Fragment extends BaseFragment {
    private MediaPlayer mediaPlayer;
    private RadioAdapter radioAdapter;
    private RecyclerView radioRV;

    public Estema3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_estema3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        radioRV = view.findViewById(R.id.estema3_rv);
        radioRV.setLayoutManager(new LinearLayoutManager(getContext()));
        radioAdapter = new RadioAdapter(, new RadioAdapter.onClickRadioListner() {
            @Override
            public void onClickRadio(View v, int position, RadioItem radioItem) {
                if (v.getId() == R.id.play_ptn) {
                    mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(radioItem.getRadio_url());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mediaPlayer.start();
                        }
                    });
                } else if (v.getId() == R.id.pause_ptn) {
                    stopPlayer();
                }
            }
        });
        radioRV.setAdapter(RadioAdapter);
        getRadioChannels();
    }

    private void getRadioChannels() {
        ApiManager.apiS
    }

    private void stopPlayer() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

}
