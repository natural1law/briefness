package com.androidx.view.dialog.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.androidx.view.R;
import com.androidx.view.dialog.DialogServlet;
import com.androidx.view.dialog.listener.OnClickCameraListener;

public final class CameraAdapter extends RecyclerView.Adapter<CameraAdapter.HolderView> {

    private String[] data;
    private int textColor;
    private int textSize;
    private DialogServlet dt;
    private HolderView holder;
    private Context context;
    private OnClickCameraListener listener;

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(context = parent.getContext()).inflate(R.layout.item_camera, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        this.holder = holder;
        holder.textView.setText(data[position]);
        holder.textView.setTextColor(context.getResources().getColor(textColor, context.getTheme()));
        holder.textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        holder.layoutView.setOnClickListener(v -> listener.ok(position, dt));
        if (holder.getAdapterPosition() + 1 == data.length) holder.v.setVisibility(View.GONE);
        else holder.v.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.length;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(String... data) {
        this.data = data;
    }

    public HolderView getHolder() {
        return holder;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setDt(DialogServlet dt) {
        this.dt = dt;
    }

    public void setListener(OnClickCameraListener listener) {
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

}
