package com.androidx.view.bar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author 李玄道
 * @date 2020/06/29
 */
@SuppressWarnings({"RedundantSuppression", "unused"})
public abstract class BaseFragment extends Fragment {

    private volatile View rootView;
    public BytesDataListener bytesDataListener;
    public StringDataListener stringDataListener;
    public ObjectDataListener objectDataListener;
    public JsonDataListener jsonDataListener;
    public ListDataListener listDataListener;
    public MapDataListener mapDataListener;

    protected View setRootView(LayoutInflater inflater, ViewGroup container, @LayoutRes int layoutId) {
        if (rootView == null) {
            rootView = inflater.inflate(layoutId, container, false);
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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

    protected void setData(byte[] var) {
        bytesDataListener.data(var);
    }

    protected void setData(String var) {
        stringDataListener.data(var);
    }

    protected void setData(Object var) {
        objectDataListener.data(var);
    }

    protected void setData(JSONObject var) {
        jsonDataListener.data(var);
    }

    protected <T> void setData(List<T> var) {
        listDataListener.data(var);
    }

    protected void setData(Map<String, Object> var) {
        mapDataListener.data(var);
    }

    public interface BytesDataListener {
        void data(byte[] bytes);
    }

    public interface JsonDataListener {
        void data(JSONObject json);
    }

    public interface ListDataListener {
        <T> void data(List<T> list);
    }

    public interface MapDataListener {
        void data(Map<String, Object> map);
    }

    public interface ObjectDataListener {
        void data(Object obj);
    }

    public interface StringDataListener {
        void data(String var);
    }
}
