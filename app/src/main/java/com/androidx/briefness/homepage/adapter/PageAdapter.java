package com.androidx.briefness.homepage.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.androidx.briefness.R;
import com.androidx.view.page.HolderView;
import com.androidx.view.page.PaginationRecycleView;
import com.google.gson.JsonObject;

public final class PageAdapter extends PaginationRecycleView.Adapter<JsonObject, HolderView> {

    @Override
    protected HolderView createHolderView(@NonNull ViewGroup parent) {
        return HolderView.createHolderView(parent, R.layout.adapter_page);
    }

    @Override
    protected void bindHolderView(HolderView holder, JsonObject json) {
        holder.setText(R.id.info, json.get("hds_name").getAsString());
        holder.setOnClickListener(R.id.info, view -> holder.setFlexible(R.id.info1));
    }

}
