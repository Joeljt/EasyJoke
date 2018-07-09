package com.ljt.baselibrary.ioc;

import android.app.Activity;
import android.view.View;

/**
 * Created by ljt on 2018/6/12.
 * 执行 findViewById
 */

public class ViewFinder {

    private Activity mActivity;
    private View mView;

    public ViewFinder(Activity activity) {
        this.mActivity = activity;
    }

    public ViewFinder(View view) {
        this.mView = view;
    }

    public View findViewById(int viewId){
        return mActivity != null ? mActivity.findViewById(viewId) : mView.findViewById(viewId);
    }

}
