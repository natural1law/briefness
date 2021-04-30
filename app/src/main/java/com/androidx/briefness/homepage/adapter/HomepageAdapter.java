package com.androidx.briefness.homepage.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidx.briefness.R;
import com.androidx.briefness.homepage.listener.OnClickHomepageAdapterListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @date 2021/04/30
 */
public final class HomepageAdapter extends RecyclerView.Adapter<HomepageAdapter.HolderView> {

    private OnClickHomepageAdapterListener listener;
    private List<String> list = new ArrayList<>();

    public void setListener(OnClickHomepageAdapterListener listener) {
        this.listener = listener;
    }

    public void addData(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homepage, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.nameView.setText(list.get(position));
        holder.nameView.setOnClickListener(v -> listener.onClick(position));
    }

    @Override
    public int getItemCount() {
        return list.isEmpty() ? 0 : list.size();
    }

    @SuppressLint("NonConstantResourceId")
    protected static final class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.homepage_name)
        public AppCompatTextView nameView;

        protected HolderView(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
