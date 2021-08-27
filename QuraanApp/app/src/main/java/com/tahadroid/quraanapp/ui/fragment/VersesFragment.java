package com.tahadroid.quraanapp.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tahadroid.quraanapp.adapter.DividerItemDecorationn;
import com.tahadroid.quraanapp.R;
import com.tahadroid.quraanapp.adapter.VersesAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class VersesFragment extends Fragment {

    private static final String ARG_SURA = "title";
    private static final String ARG_FILE_INDEX = "fileIndex";

    private String mTitle;
    private int fileIndex ;
    TextView titleTextView;
    RecyclerView versesRecyclerView;
    VersesAdapter versesAdapter;

    public VersesFragment() {
        // Required empty public constructor
    }


    public static VersesFragment newInstance(String mTitle,int fileIndex) {
        VersesFragment fragment = new VersesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SURA, mTitle);
        args.putInt(ARG_FILE_INDEX, fileIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_SURA);
            fileIndex = getArguments().getInt(ARG_FILE_INDEX);
        }
        versesAdapter = new VersesAdapter(getContext(),getVersesList(fileIndex + ".txt"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verses, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleTextView = view.findViewById(R.id.name_tv);
        versesRecyclerView = view.findViewById(R.id.verses_rv);
        versesRecyclerView.addItemDecoration(new DividerItemDecorationn(getResources().getDrawable(R.drawable.divider)));
        titleTextView.setText(mTitle);
        versesRecyclerView.setAdapter(versesAdapter);
    }

    private List<String> getVersesList(String fileName) {
        List<String> verses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(

                new InputStreamReader(getContext().getAssets().open(fileName),
                        StandardCharsets.UTF_8))) {
            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                verses.add(mLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return verses;
    }

}
