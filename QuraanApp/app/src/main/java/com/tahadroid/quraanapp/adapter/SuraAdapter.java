package com.tahadroid.quraanapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tahadroid.quraanapp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SuraAdapter extends RecyclerView.Adapter<SuraAdapter.SuraViewHolder> {

    public static String[] suraList;

    private Context mContext;

    public SuraAdapter(Context context, String[] suraList) {
        this.suraList = suraList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public SuraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SuraViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.layout_suras_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull SuraViewHolder holder, int position) {
        holder.setData(suraList[position]);
    }

    @Override
    public int getItemCount() {
        return suraList.length;
    }

    class SuraViewHolder extends RecyclerView.ViewHolder {

        private TextView suraTv;

        public SuraViewHolder(@NonNull View itemView) {
            super(itemView);
            suraTv = itemView.findViewById(R.id.sura_name_tv);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(mContext, VersesActivity.class);
//                    intent.putExtra("position", getAdapterPosition() + 1);
//                    intent.putExtra("title", suraList[getAdapterPosition()]);
//                    mContext.startActivity(intent);
                    listenerxxxx.xxxx(getAdapterPosition() + 1, suraList[getAdapterPosition()]);
                }
            });
        }

        public void setData(String s) {
            suraTv.setText(s);
        }
    }

    public OnClickListenerxxxx listenerxxxx;

  public   interface OnClickListenerxxxx {
        void xxxx(int p, String s);
    }
}
