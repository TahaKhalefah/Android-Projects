package com.tahadroid.quraanapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tahadroid.quraanapp.R;
import com.tahadroid.quraanapp.adapter.DividerItemDecorationn;
import com.tahadroid.quraanapp.adapter.VersesAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AhadeethFragment extends BaseFragment {
    RecyclerView ahadeethRecyclerView;
    VersesAdapter versesAdapter;

    public AhadeethFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        versesAdapter = new VersesAdapter(getContext(),getVersesList("ahadeth" + ".txt"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ahadeeth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ahadeethRecyclerView = view.findViewById(R.id.ahadeeth_rv);
        ahadeethRecyclerView.addItemDecoration(new DividerItemDecorationn(getResources().getDrawable(R.drawable.divider)));

        ahadeethRecyclerView.setAdapter(versesAdapter);
    }

    private List<String> getVersesList(String fileName) {
        List<String> verses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(

                new InputStreamReader(getContext().getAssets().open(fileName),
                        StandardCharsets.UTF_8))) {
            // do reading, usually loop until end of file reading
            String query = "";
            String komut;
            while ((komut = reader.readLine()) != null) {
                if (komut.length() != 0) {
                    if (komut.charAt(komut.length() - 1) == '#') {
                        verses.add(query + "\n"+ komut);
                        query = "";
                    } else {
                        query += komut;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return verses;
    }
}
