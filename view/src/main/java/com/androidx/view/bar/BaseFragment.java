package com.androidx.view.bar;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author 李玄道
 * @date 2020/06/29
 */
@SuppressWarnings({"RedundantSuppression", "unused"})
public abstract class BaseFragment extends Fragment {

    private volatile View rootView;
    private LayoutInflater inflater;
    private ViewGroup container;
    protected Context context;

    protected View setRootView(@LayoutRes int layoutId) {
        if (rootView == null) rootView = inflater.inflate(layoutId, container, false);
        else ((ViewGroup) rootView.getParent()).removeView(rootView);
        return rootView;
    }

    protected abstract View onCreateView();

    protected abstract void onViewCreated(View view);

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        return this.onCreateView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            this.onViewCreated(view);
        }catch (Exception e){
            Log.e(getClass().getName(), Log.getStackTraceString(e));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
