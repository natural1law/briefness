package com.androidx.view.page;

public interface OnChangedListener {
    /**
     * 选中页改变时回调
     *
     * @param c 当前选中的页码
     */
    void onPageSelectedChanged(int c);
}
