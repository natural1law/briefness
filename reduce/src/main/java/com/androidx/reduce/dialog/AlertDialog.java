package com.androidx.reduce.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidx.reduce.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class AlertDialog {

    private final Context context;
    private Dialog dialog;
    private TextView txt_title;
    private TextView txt_msg;
    private Button btn_neg, btn_pos;
    private View img_line;
    private DisplayMetrics dm;
    private boolean showTitle = false;
    private boolean showMsg = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;
    private Type mType = Type.NORMAL;
    private TextView mProMsgText;
    private List<ItemBean> mAlertViewItems;
    private AlertAdapter mAlertViewAdapter;
    private View mDialogLayout;

    public enum Type {
        NORMAL, PROGRESS, BOTTOM, CUSTOM_DIALOG
    }

    public static AlertDialog build(Context context) {
        synchronized (AlertDialog.class) {
            return new AlertDialog(context);
        }
    }

    private AlertDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        if (windowManager != null) {
            dm = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(dm);
        }
//        Display display = windowManager != null ? windowManager.getDefaultDisplay() : null;
    }

    /**
     * 默认NORMAL类型
     */
    public AlertDialog builder() {
        initNormalType();
        return this;
    }

    public AlertDialog builder(Type type) {
        this.mType = type;
        switch (type) {
            case NORMAL:
                initNormalType();
                break;
            case PROGRESS:
                initProgressType();
                break;
            case BOTTOM:
                initBottomType();
                break;
        }
        return this;
    }

    public AlertDialog builder(Type type, int resource) {
        this.mType = type;
        if (type == Type.CUSTOM_DIALOG) {
            initCustomDialog(resource);
        }
        return this;
    }


    private void initNormalType() {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.view_alert, null);
        LinearLayout lLayout_bg = view.findViewById(R.id.lLayout_bg);
        txt_title = view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        txt_msg = view.findViewById(R.id.txt_msg);
        txt_msg.setVisibility(View.GONE);
        btn_neg = view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        btn_pos = view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        img_line = view.findViewById(R.id.v_line);
        img_line.setVisibility(View.GONE);

        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (dm.widthPixels * 0.85), LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    private void initProgressType() {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.view_alert_loading, null);
        mProMsgText = view.findViewById(R.id.tipTextView);
        LinearLayout dialog_loading_bg = view.findViewById(R.id.dialog_loading_bg);
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        //背景改透明
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            //去除半透明阴影
            window.setDimAmount(0.0f);
        }
        dialog_loading_bg.setLayoutParams(new FrameLayout.LayoutParams(
                (int) (dm.widthPixels * 0.4), LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    @SuppressLint("RtlHardcoded")
    private void initBottomType() {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.view_alert_bottom, null, false);

        view.setMinimumWidth(dm.widthPixels);
        RecyclerView bottom_rv_content = view.findViewById(R.id.rv_content);
        TextView txt_cancel = view.findViewById(R.id.txt_cancel);
        txt_cancel.setOnClickListener(v -> dialog.dismiss());

        mAlertViewItems = new ArrayList<>();
        mAlertViewAdapter = new AlertAdapter(context, mAlertViewItems);
        bottom_rv_content.setAdapter(mAlertViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dec = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.alert_line);
        if (drawable != null) {
            dec.setDrawable(drawable);
        }
        bottom_rv_content.addItemDecoration(dec);
        bottom_rv_content.setLayoutManager(layoutManager);
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        if (dialogWindow != null) {
            dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.x = 0;
            lp.y = 0;
            dialogWindow.setAttributes(lp);
        }
    }

    private void initCustomDialog(int resource) {
        mDialogLayout = LayoutInflater.from(context).inflate(resource, null);
        dialog = new Dialog(context, R.style.CustomDialogStyle);
        dialog.setContentView(mDialogLayout);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setDimAmount(0.0f);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
            window.setAttributes(lp);
        }
    }

    public interface OnInitDialogLayoutListener {
        void initView(View view, Dialog dialog);
    }

    public AlertDialog initDialogLayout(OnInitDialogLayoutListener onInitDialogLayoutListener) {
        onInitDialogLayoutListener.initView(mDialogLayout, dialog);
        return this;
    }

    /**
     * 设置标题
     */
    public AlertDialog setProgressText(String msg) {
        if (mProMsgText != null) {
            mProMsgText.setVisibility(View.VISIBLE);
            mProMsgText.setText(msg == null ? "" : msg);
        }
        return this;
    }

    public AlertDialog setProgressTextGone() {
        if (mProMsgText != null) mProMsgText.setVisibility(View.GONE);
        return this;
    }


    /**
     * 设置标题
     */
    public AlertDialog setTitle(String title) {
        showTitle = true;
        if (txt_title != null) txt_title.setText(title == null ? "" : title);
        return this;
    }

    /**
     * 设置内容
     */
    public AlertDialog setMsg(String msg) {
        showMsg = true;
        if (txt_msg != null) txt_msg.setText(msg == null ? "" : msg);
        return this;
    }

    /**
     * 设置false点击屏幕或物理返回键，dialog不消失
     */
    public AlertDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    /**
     * 设置false点击屏幕：dialog不消失；点击物理返回键：dialog消失
     */
    public AlertDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     * 设置确定键及监听事件
     */
    public AlertDialog setPositiveButton(String text,
                                         final View.OnClickListener listener) {
        showPosBtn = true;
        btn_pos.setText(TextUtils.isEmpty(text) ? "确定" : text);
        btn_pos.setOnClickListener(v -> {
            listener.onClick(v);
            dialog.dismiss();
        });
        return this;
    }

    /**
     * 设置确定监听事件
     */
    public AlertDialog setPositiveButton(final View.OnClickListener listener) {
        showPosBtn = true;
        btn_pos.setText("确定");
        btn_pos.setOnClickListener(v -> {
            listener.onClick(v);
            dialog.dismiss();
        });
        return this;
    }

    /**
     * 设置取消键及监听事件
     */
    public AlertDialog setNegativeButton(String text, final View.OnClickListener listener) {
        showNegBtn = true;
        if (btn_neg != null) {
            btn_neg.setText(TextUtils.isEmpty(text) ? "取消" : text);
            btn_neg.setOnClickListener(v -> {
                listener.onClick(v);
                dialog.dismiss();
            });
        }
        return this;
    }

    /**
     * 设置取消监听事件
     */
    public AlertDialog setNegativeButton(final View.OnClickListener listener) {
        showNegBtn = true;
        if (btn_neg != null) {
            btn_neg.setText("取消");
            btn_neg.setOnClickListener(v -> {
                listener.onClick(v);
                dialog.dismiss();
            });
        }
        return this;
    }

    /**
     * 设置布局
     */
    private void setLayout() {
        if (txt_title != null && showTitle) {
            txt_title.setVisibility(View.VISIBLE);
        }

        if (txt_msg != null && showMsg) {
            txt_msg.setVisibility(View.VISIBLE);
        }

        if (btn_pos != null && !showPosBtn && !showNegBtn) {
            btn_pos.setText("确定");
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alert_btn_bottom_selector);
            btn_pos.setOnClickListener(v -> dialog.dismiss());
        }

        if (btn_pos != null && btn_neg != null && img_line != null && showPosBtn && showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alert_btn_right_selector);
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alert_btn_left_selector);
            img_line.setVisibility(View.VISIBLE);
        }

        if (btn_pos != null && showPosBtn && !showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alert_btn_bottom_selector);
        }

        if (btn_neg != null && !showPosBtn && showNegBtn) {
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alert_btn_bottom_selector);
        }
    }

    public interface OnAlertItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * 条目点击事件
     */
    public AlertDialog setOnItemClickListener(final OnAlertItemClickListener onAlertItemClickListener) {
        if (mAlertViewAdapter != null) {
            mAlertViewAdapter.setOnItemClickLitener((view, position) -> {
                onAlertItemClickListener.onItemClick(view, position);
                dialog.dismiss();
            });
            dialog.dismiss();
        }
        return this;
    }

    /**
     * @param strItem 条目名称
     * @param color   条目字体颜色，设置null则默认蓝色
     */
    public AlertDialog addItem(String strItem, int color) {
        if (mAlertViewItems != null)
            mAlertViewItems.add(new ItemBean(strItem, color));
        return this;
    }

    public AlertDialog addItem(String strItem) {
        if (mAlertViewItems != null)
            mAlertViewItems.add(new ItemBean(strItem, 0));
        return this;
    }

    public AlertDialog addItem(String[] strItem) {
        if (mAlertViewItems != null) {
            for (String s : strItem)
                mAlertViewItems.add(new ItemBean(s, 0));
        }
        return this;
    }

    public AlertDialog addItem(List<ItemBean> strItem) {
        if (mAlertViewItems != null && strItem != null) {
            mAlertViewItems.addAll(strItem);
        }

        return this;
    }

    private void notifyData() {
        if (mAlertViewItems == null || mAlertViewItems.size() <= 0)
            return;
        mAlertViewItems.size();
        mAlertViewAdapter.notifyDataSetChanged();
    }

    public void show() {
        switch (mType) {
            case NORMAL:
                setLayout();
                break;
            case PROGRESS:
                break;
            case BOTTOM:
                notifyData();
                break;
        }
        dialog.show();
    }

    private static final class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.ViewHolder> {

        private List<ItemBean> mAlertViewItems;
        private Context mContext;
        private OnItemClickListener mOnItemClickListener;

        public AlertAdapter(Context context, List<ItemBean> alertViewItems) {
            this.mContext = context;
            this.mAlertViewItems = alertViewItems;
        }

        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }

        public void setOnItemClickLitener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        @NotNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_alert_view_bottom, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(v -> {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, pos);
                });
            }
            ItemBean bean = mAlertViewItems.get(position);
            if (mAlertViewItems.size() == 1) {
                holder.tv_text.setBackground(ContextCompat.getDrawable(mContext, R.drawable.alert_bottom_single2_selector));
            } else {
                if (position == 0) {
                    holder.tv_text.setBackground(ContextCompat.getDrawable(mContext, R.drawable.alert_bottom_top2_selector));
                } else if (position == mAlertViewItems.size() - 1) {
                    holder.tv_text.setBackground(ContextCompat.getDrawable(mContext, R.drawable.alert_bottom_bottom2_selector));
                } else {
                    holder.tv_text.setBackground(ContextCompat.getDrawable(mContext, R.drawable.alert_bottom_middle2_selector));
                }
            }
            holder.tv_text.setText(bean.name);
        }

        @Override
        public int getItemCount() {
            return mAlertViewItems.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView tv_text;

            ViewHolder(View itemView) {
                super(itemView);
                tv_text = itemView.findViewById(R.id.tv_text);
            }
        }
    }

    private static final class ItemBean {
        String name;
        int color;

        public ItemBean(String name, int color) {
            this.name = name;
            this.color = color;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }

}
