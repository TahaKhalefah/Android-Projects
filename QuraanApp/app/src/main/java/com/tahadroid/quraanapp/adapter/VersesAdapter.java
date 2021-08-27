package com.tahadroid.quraanapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tahadroid.quraanapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VersesAdapter extends RecyclerView.Adapter<VersesAdapter.VersesViewHolder> {
    List<String> verses;
    Context context;
    public VersesAdapter(Context context,List<String> verses) {
        this.verses = verses;
        this.context = context;
    }

    @NonNull
    @Override
    public VersesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VersesViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_verses_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VersesViewHolder holder, int position) {
     bindData(holder,verses.get(position));
    }

    private void bindData(VersesViewHolder holder, String title) {
        holder.nameTv.setText(title);
    }

    @Override
    public int getItemCount() {
        return verses.size();
    }

    class VersesViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv;

        public VersesViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.versesTextView);
        }
    }
}
