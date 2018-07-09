package com.ljt.baselibrary.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Author: ljt@yonyou.com
 * Date&Time: 2018/07/08, 22:43
 * For：
 */

public abstract class AbsNavigationBar<P extends AbsNavigationBar.Builder.AbsNavigationParams> implements INavigationBar {

//    private Builder.AbsNavigationParams mParams;
    private P mParams;

    private View mNavigationView;

    public AbsNavigationBar(P params) {
        mParams = params;
        createAndBindView();
    }

    public P getParams() {
        return mParams;
    }

    /**
     * 给控件设置文本信息
     * @param viewId
     * @param text
     */
    protected void setText(int viewId, String text) {
        TextView tv = findViewById(viewId);
        if (tv != null && !TextUtils.isEmpty(text)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
    }

    protected void setOnClickListener(int viewId, View.OnClickListener listener) {
        View v = findViewById(viewId);
        if(v != null && listener != null) {
            v.setOnClickListener(listener);
        }
    }

    private <T extends View> T findViewById(int viewId) {
        return (T) mNavigationView.findViewById(viewId);
    }

    /**
     * 绑定和创建View
     */
    private void createAndBindView() {

        // 对 parent 判空，如果为空，说明未指明id
        if (mParams.mParent == null) {
            // 获取 contentView 的根布局
            ViewGroup rootView = (ViewGroup) ((Activity) (mParams.mContext)).findViewById(android.R.id.content);
            mParams.mParent = (ViewGroup) rootView.getChildAt(0);
        }

        if (mParams.mParent == null) {
            return;
        }

        // 1.创建 View
        mNavigationView = LayoutInflater.from(mParams.mContext).inflate(bindLayoutId(), mParams.mParent, false);

        // 2.添加
        mParams.mParent.addView(mNavigationView, 0);

        applyView();

    }

    public abstract static class Builder{

//        public final AbsNavigationParams P;

        public Builder(Context context, ViewGroup parent) {
//            P = new AbsNavigationParams(context, parent);
        }

        public abstract AbsNavigationBar build();

        public static class AbsNavigationParams {

            public Context mContext;
            public ViewGroup mParent;

            public AbsNavigationParams(Context context, ViewGroup parent) {
                this.mContext = context;
                this.mParent = parent;
            }
        }

    }

}
