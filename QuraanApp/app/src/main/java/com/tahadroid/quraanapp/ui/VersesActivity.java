package com.tahadroid.quraanapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.tahadroid.quraanapp.R;
import com.tahadroid.quraanapp.adapter.VersesAdapter;
import com.tahadroid.quraanapp.ui.fragment.VersesFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class VersesActivity extends AppCompatActivity {
    private static final String TAG = "VersesActivity";

    int fileIndex = 0;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verses);
        fileIndex = getIntent().getIntExtra("position", 0);
        title = getIntent().getStringExtra("title");
        VersesFragment versesFragment =VersesFragment.newInstance(title,fileIndex);
        FragmentManager fm =getSupportFragmentManager();
        FragmentTransaction ft =fm.beginTransaction();
        ft.replace(R.id.fragment_verses_container,versesFragment)
                .commit();

    }
}
