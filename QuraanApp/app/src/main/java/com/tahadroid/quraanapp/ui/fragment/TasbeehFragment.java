package com.tahadroid.quraanapp.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tahadroid.quraanapp.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TasbeehFragment extends BaseFragment {
    private static final String TAG = "TasbeehFragment";
    private ImageView imageView;
    private Button button;
    private TextView textView;
    int count = 0;

    public TasbeehFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tasbeeh, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.sebha_iv);
        button = view.findViewById(R.id.reset_btn);
        textView = view.findViewById(R.id.count_tv);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                textView.setText("" + count);
                Log.d(TAG, "onClick: " + count);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = 0;
                textView.setText("" + count);
            }
        });

    }
}
