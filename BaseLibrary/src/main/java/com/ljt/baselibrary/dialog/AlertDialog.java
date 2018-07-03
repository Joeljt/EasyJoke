package com.ljt.baselibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ljt.baselibrary.R;

/**
 * Created by lijiateng on 2018/7/2.
 * 自定义万能 Dialog
 */

public class AlertDialog extends Dialog {

    public AlertController mAlert;

    public AlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);

        mAlert = new AlertController(this, getWindow());

    }

    public void setText(int viewId, CharSequence text) {
        mAlert.setText(viewId, text);
    }

    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        mAlert.setOnClickListener(viewId, listener);
    }

    public <V extends View> V getView(int viewId) {
        return mAlert.getView(viewId);
    }

    public static class Builder{

        public final AlertController.AlertParams P;

        public Builder(Context context){
            this(context, R.style.dialog);
        }

        public Builder(Context context, int themeResId) {
            P = new AlertController.AlertParams(context, themeResId);
        }

        public Builder setContentView(int layoutResId) {
            P.mView = null;
            P.mViewLayoutResId = layoutResId;
            return this;
        }

        public Builder setContentView(View view) {
            P.mView = view;
            P.mViewLayoutResId = 0;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }

        /**
         * 因为 AlertController.AlertParams 只是暂时存放各种数据，最后的拼装在 apply() 方法中进行
         * 同时又会有多个按钮等待赋值，因此使用 map
         * @param viewId
         * @param text
         * @return
         */
        public Builder setText(int viewId, CharSequence text) {
            P.mTextArray.put(viewId, text);
            return this;
        }

        public Builder setOnClickListener(int view, View.OnClickListener listener){
            P.mClickArray.put(view, listener);
            return this;
        }

        /**
         * Sets the callback that will be called when the dialog is dismissed for any reason.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        /**
         * Sets the callback that will be called if a key is dispatched to the dialog.
         *
         * @return This Builder object to allow for chaining of calls to set methods
         */
        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }

        /**
         * 是否横向充满屏幕
         * @return
         */
        public Builder fullWidth() {
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        /**
         * 从底部弹出
         * @param isAnimation 是否有动画
         * @return
         */
        public Builder fromBottom(boolean isAnimation){
            if(isAnimation){
                P.mAnimation = R.style.dialog_from_bottom_anim;
            }
            P.mGravity = Gravity.BOTTOM;
            return this;
        }

        public Builder setWidthAndHeight(int width, int height) {
            P.mWidth = width;
            P.mHeight = height;
            return this;
        }

        /**
         * 对 AlertController.AlertParams 中设置的各种属性进行最后的拼装
         * 初始化一个 AlertDialog 的实例并进行返回
         * @return
         */
        public AlertDialog create() {
            // We can't use Dialog's 3-arg constructor with the createThemeContextWrapper param,
            // so we always have to re-set the theme
            final AlertDialog dialog = new AlertDialog(P.mContext, P.mThemeResId);
            P.apply(dialog.mAlert);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }

        public AlertDialog show() {
            final AlertDialog dialog = create();
            dialog.show();
            return dialog;
        }



    }



}
