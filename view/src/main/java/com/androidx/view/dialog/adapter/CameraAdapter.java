package com.androidx.view.dialog.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.androidx.view.R;
import com.androidx.view.dialog.DialogTools;

public final class CameraAdapter extends RecyclerView.Adapter<CameraAdapter.HolderView> {

    private String[] data;
    private int textColor;
    private int textSize;
    private DialogTools dt;
    private OnClickCameraAdapterListener listener;

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog6, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.textView.setText(data[position]);
        holder.textView.setTextColor(textColor);
        holder.textView.setTextSize(textSize);
        holder.layoutView.setOnClickListener(v -> listener.onClick(position, dt));
        if (holder.getAdapterPosition() + 1 == data.length) holder.v.setVisibility(View.GONE);
        else holder.v.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.length;
    }

    public void setData(String[] data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setDt(DialogTools dt) {
        this.dt = dt;
    }

    public void setListener(OnClickCameraAdapterListener listener) {
        this.listener = listener;
    }

    public static class HolderView extends RecyclerView.ViewHolder {

        private final AppCompatTextView textView;
        private final LinearLayoutCompat layoutView;
        private final View v;

        public HolderView(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.dialog_item);
            layoutView = itemView.findViewById(R.id.item_dialog_camera_layout);
            v = itemView.findViewById(R.id.dialog_v1);
        }

    }

    public interface OnClickCameraAdapterListener {
        void onClick(int position, DialogTools dialog);

        default void cancel(DialogTools dialog) {
            dialog.cancel();
        }
    }
}
