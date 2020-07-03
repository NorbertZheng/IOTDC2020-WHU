package com.example.qingnang;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MediAdapter extends RecyclerView.Adapter<MediAdapter.ViewHolder>{
    private List<Medi> mediList;
    public MediAdapter(List<Medi> mediList){
        this.mediList = mediList;
    }

    public interface OnItemOnClickListener{
        void onItemOnClick(View view, int pos);
        void onItemLongOnClick(View view, int pos);
    }
    private OnItemOnClickListener mOnItemOnClickListener;
    public void setOnItemClickListener(OnItemOnClickListener listener){
        this.mOnItemOnClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView medi_name,dosage,date,time;
        public ViewHolder(View itemView) {
            super(itemView);
            medi_name = itemView.findViewById(R.id.medi_name);
            dosage = itemView.findViewById(R.id.dosage);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
        }
    }

    @NonNull
    @Override
    public MediAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,final int position) {
        Medi medi = mediList.get(position);
        TextView medi_name = holder.medi_name, dosage = holder.dosage, time = holder.time,date = holder.date;
        medi_name.setText(medi.getName());
        dosage.setText(String.valueOf(medi.getDosage()));
        time.setText(String.valueOf(medi.gettime()));
        String t = medi.getYear() + "/" + medi.getMonth() + "/" + medi.getDay();
        date.setText(t);
        if (mOnItemOnClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemOnClickListener.onItemOnClick(holder.itemView,holder.getLayoutPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemOnClickListener.onItemLongOnClick(holder.itemView,holder.getLayoutPosition());
                    return true;
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return mediList.size();
    }
    public void removeItem(int pos){
        mediList.remove(pos);
        notifyItemRemoved(pos);
    }
}
