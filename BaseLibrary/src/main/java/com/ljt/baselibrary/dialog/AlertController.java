package com.ljt.baselibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;

/**
 * Created by lijiateng on 2018/7/2.
 */
class AlertController {

    private AlertDialog mDialog;
    private Window mWindow;

    public AlertController(AlertDialog dialog, Window window) {
        this.mDialog = dialog;
        this.mWindow = window;
    }

    public AlertDialog getDialog() {
        return mDialog;
    }

    public Window getWindow() {
        return mWindow;
    }

    public static class AlertParams{

        public Context mContext;
        public int mThemeResId;

        /**
         * 是否可以通过外部点击取消弹窗
         */
        public boolean mCancelable = true;

        /**
         * 取消，关闭，按键监听
         */
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnKeyListener mOnKeyListener;

        /**
         * ContentView
         */
        public View mView;

        /**
         * Content View resource id
         */
        public int mViewLayoutResId;

        /**
         * 存放按钮的文本信息
         */
        public SparseArray<CharSequence> mTextArray = new SparseArray<>();

        /**
         * 存放点击事件
         */
        public SparseArray<View.OnClickListener> mClickArray = new SparseArray<>();

        public AlertParams(Context context, int themeResId){
            this.mContext = context;
            this.mThemeResId = themeResId;
        }

        /**
         * 设置和绑定参数
         * @param mAlert 持有AlertDialog的引用
         *               因为最开始在 AlertDialog 中初始化 AlertController 的时候，传入了 this
         */
        public void apply(AlertController mAlert) {

            // 1.设置布局
            DialogViewHelper mViewHelper = null;
            if (mViewLayoutResId != 0){
                mViewHelper = new DialogViewHelper(mContext, mViewLayoutResId);
            }

            if (mView != null) {
                mViewHelper = new DialogViewHelper();
                mViewHelper.setContentView(mView);
            }

            if (mViewHelper == null) {
                throw new IllegalArgumentException("method setContentView() must be called! ");
            }

            // 设置布局
            mAlert.getDialog().setContentView(mViewHelper.getContentView());

            // 2.设置文本展示
            for (int i = 0; i < mTextArray.size(); i++) {
                mViewHelper.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
            }

            // 3.设置点击事件
            for (int i = 0; i < mClickArray.size(); i++) {
                mViewHelper.setOnClickListener(mClickArray.keyAt(i), mClickArray.valueAt(i));
            }

            // 4.配置自定义效果

        }

    }

}
