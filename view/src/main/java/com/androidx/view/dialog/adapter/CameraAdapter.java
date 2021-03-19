package com.androidx.view.dialog.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.androidx.view.R;

public final class CameraAdapter extends RecyclerView.Adapter<CameraAdapter.HolderView> {

    private String[] data;
    private int textColor;
    private int textSize;
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
        holder.layoutView.setOnClickListener(v -> listener.onClick(position));
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

    public void setListener(OnClickCameraAdapterListener listener) {
        this.listener = listener;
    }

    public static class HolderView extends RecyclerView.ViewHolder {

        private final AppCompatTextView textView;
        private final LinearLayoutCompat layoutView;

        public HolderView(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.dialog_item);
            layoutView = itemView.findViewById(R.id.item_dialog_camera_layout);
        }

    }

    public interface OnClickCameraAdapterListener {
        void onClick(int position);
    }
}
