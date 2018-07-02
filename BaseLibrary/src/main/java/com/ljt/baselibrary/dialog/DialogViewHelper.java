package com.ljt.baselibrary.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/**
 * Created by lijiateng on 2018/7/2.
 *
 * Dialog 的辅助工具类
 *
 */

public class DialogViewHelper {

    private View mContentView = null;

    /**
     * 用于存储 Dialog 中涉及到的 View，减少 findViewById 的次数
     */
    private SparseArray<WeakReference<View>> mViews;


    public DialogViewHelper(Context context, int layoutResId) {
        this();
        mContentView = LayoutInflater.from(context).inflate(layoutResId, null);
    }

    public DialogViewHelper() {
        mViews = new SparseArray<>();
    }

    public void setContentView(View view) {
        mContentView = view;
    }

    public void setText(int viewId, CharSequence text) {
        TextView view = getView(viewId);
        if (view != null){
            view.setText(text);
        }
    }

    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        if (view != null) {
            view.setOnClickListener(listener);
        }
    }

    public View getContentView() {
        return mContentView;
    }

    /**
     * 获取已经加载过的 View 并存入 SparseArray
     * @param viewId
     * @param <V>
     * @return
     */
    public <V extends View> V getView(int viewId) {
        View view = null;
        WeakReference<View> viewWeakReference = mViews.get(viewId);

        if (viewWeakReference != null) {
            view = viewWeakReference.get();
        }

        if (view == null){
            view = mContentView.findViewById(viewId);
            mViews.put(viewId, new WeakReference<>(view));
        }

        return (V) view;

    }

}
