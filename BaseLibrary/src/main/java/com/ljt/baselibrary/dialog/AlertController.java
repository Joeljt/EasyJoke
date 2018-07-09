package com.ljt.baselibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

/**
 * Created by lijiateng on 2018/7/2.
 */
class AlertController {

    private AlertDialog mDialog;
    private Window mWindow;

    private DialogViewHelper mViewHelper;

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

    public void setViewHelper(DialogViewHelper mViewHelper) {
        this.mViewHelper = mViewHelper;
    }

    public void setText(int viewId, CharSequence text) {
        mViewHelper.setText(viewId, text);
    }

    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        mViewHelper.setOnClickListener(viewId, listener);
    }

    public <V extends View> V getView(int viewId) {
        return mViewHelper.getView(viewId);
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

        /**
         * 默认宽度
         */
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

        public int mGravity = Gravity.CENTER;

        /**
         * 默认的
         */
        public int mAnimation = 0;

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

            // 设置 ViewHelper
            mAlert.setViewHelper(mViewHelper);

            // 2.设置文本展示
            for (int i = 0; i < mTextArray.size(); i++) {
                mAlert.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
            }

            // 3.设置点击事件
            for (int i = 0; i < mClickArray.size(); i++) {
                mAlert.setOnClickListener(mClickArray.keyAt(i), mClickArray.valueAt(i));
            }

            // 4.配置自定义效果
            Window window = mAlert.getWindow();
            window.setGravity(mGravity);
            if (mAnimation != 0) {
                window.setWindowAnimations(mAnimation);
            }
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.width = mWidth;
            layoutParams.height = mHeight;
            window.setAttributes(layoutParams);

        }

    }

}
