package com.tahadroid.quraanapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.tahadroid.quraanapp.R;
import com.tahadroid.quraanapp.pojo.RadioItem;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RadioAdapter extends RecyclerView.Adapter<RadioAdapter.RadioViewHolder> {
    private List<RadioItem> radioItemList = new ArrayList<>();
    private onClickRadioListner listner;

    public RadioAdapter(List<RadioItem> radioItemList,onClickRadioListner listner) {
        this.radioItemList = radioItemList;
        this.listner=listner;
    }

    @NonNull
    @Override
    public RadioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RadioViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_radoi_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RadioViewHolder holder, int position) {
        RadioItem radioItem = radioItemList.get(position);
        bindData(holder, radioItem);
    }

    public void swapData(List<RadioItem> data) {
        this.radioItemList = data;
        notifyDataSetChanged();
    }

    private void bindData(RadioViewHolder holder, RadioItem radioItem) {
        holder.name.setText(radioItem.getName());
    }

    @Override
    public int getItemCount() {
        return radioItemList.size();
    }

    class RadioViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        Button play, pause;

        public RadioViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_tv);
            play = itemView.findViewById(R.id.play_ptn);
            pause = itemView.findViewById(R.id.pause_ptn);
            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onClickRadio(view, getAdapterPosition(),radioItemList.get(getAdapterPosition()));
                }
            });
            pause.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onClickRadio(view, getAdapterPosition(),radioItemList.get(getAdapterPosition()));
                }
            });

        }
    }


    public interface onClickRadioListner {
        void onClickRadio(View v, int position ,RadioItem radioItem);
    }
}
