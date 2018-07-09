package com.ljt.easyjoke;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.ljt.baselibrary.navigationbar.AbsNavigationBar;

/**
 * Author: ljt@yonyou.com
 * Date&Time: 2018/07/08, 22:57
 * For：
 */

public class DefaultNavigationBar extends AbsNavigationBar<DefaultNavigationBar.Builder.DefaultNavigationParams> {

    public DefaultNavigationBar(DefaultNavigationBar.Builder.DefaultNavigationParams params) {
        super(params);
    }

    @Override
    public int bindLayoutId() {
        return R.layout.title_bar;
    }

    @Override
    public void applyView() {
        setText(R.id.tv_test, getParams().mTitle);


    }

    public static class Builder extends AbsNavigationBar.Builder {

        public DefaultNavigationParams P;

        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            P = new DefaultNavigationParams(context, parent);
        }

        public Builder(Context context) {
            super(context, null);
            P = new DefaultNavigationParams(context, null);
        }

        // 1.设置所有的效果

        public DefaultNavigationBar.Builder setTitle(String title) {
            P.mTitle = title;
            return this;
        }

        public DefaultNavigationBar.Builder setRightText(String rightText) {
            P.mRightText = rightText;
            return this;
        }

        public DefaultNavigationBar.Builder setRightIcon(int rightResId) {
            P.mRightResId = rightResId;
            return this;
        }

        public DefaultNavigationBar.Builder setRightClickListener(View.OnClickListener listener) {
            P.mRightClickListener = listener;
            return this;
        }

        @Override
        public DefaultNavigationBar build() {
            return new DefaultNavigationBar(P);
        }

        public static class DefaultNavigationParams extends AbsNavigationBar.Builder.AbsNavigationParams {

            public String mTitle;
            public String mRightText;
            public int mRightResId;
            public View.OnClickListener mRightClickListener;

            public DefaultNavigationParams(Context context, ViewGroup parent) {
                super(context, parent);
            }

            // 2.放置所有的效果

        }

    }

}
