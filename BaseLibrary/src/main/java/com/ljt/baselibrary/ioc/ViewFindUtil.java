package com.ljt.baselibrary.ioc;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by ljt on 2018/6/12.
 */

public class ViewFindUtil {

    public static void inject(Activity activity){
        inject(new ViewFinder(activity), activity);
    }

    public static void inject(View view){
        inject(new ViewFinder(view), view);
    }

    public static void inject(View view, Object obj){
        inject(new ViewFinder(view), obj);
    }

    /**
     * 注入
     * @param finder 注入工具类
     * @param obj 反射执行的类
     */
    private static void inject(ViewFinder finder, Object obj){
        injectField(finder, obj);
        injectEvent(finder, obj);
    }

    /**
     * 注入事件
     * @param finder
     * @param obj
     */
    private static void injectEvent(ViewFinder finder, Object obj) {
        Class<?> clazz = obj.getClass();

        // 获取所有方法
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {

            OnClick onClick = method.getAnnotation(OnClick.class);

            // 获取标记了 onClick 的方法
            if (onClick != null){
                // viewId 数组
                int[] viewIds = onClick.value();

                for (int viewId : viewIds) {
                    View viewById = finder.findViewById(viewId);

                    boolean isCheckNet = method.getAnnotation(CheckNet.class) != null;

                    // 执行反射方法
                    if (viewById != null){
                        viewById.setOnClickListener(new DeclaredOnClickListener(method, obj, isCheckNet));
                    }
                }

            }

        }

    }

    /**
     * 注入属性
     * @param finder
     * @param obj
     */
    private static void injectField(ViewFinder finder, Object obj) {
        Class<?> clazz = obj.getClass();

        // 获取到所有属性
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {

            // 获取标记了「FindView」注解的属性
            FindView findView = field.getAnnotation(FindView.class);
            if (findView != null){
                // 获取注解的viewId，并且获取对应的View
                int viewId = findView.value();
                View viewById = finder.findViewById(viewId);

                if (viewById == null){
                    throw new Resources.NotFoundException("No such resource ID in this xml: " + viewId);
                }

                // 可以操作所有权限修饰的属性，包括 private
                field.setAccessible(true);

                try {
                    // 对属性进行注入
                    field.set(obj, viewById);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class DeclaredOnClickListener implements View.OnClickListener{

        private Method method;
        private Object obj;
        private boolean isCheckNet;

        public DeclaredOnClickListener(Method method, Object obj, boolean isCheckNet) {
            this.method = method;
            this.obj = obj;
            this.isCheckNet = isCheckNet;
        }

        @Override
        public void onClick(View v) {

            if (isCheckNet && !isNetworkAvailable(v.getContext())){
                Toast.makeText(v.getContext(), "请检查网络后重试", Toast.LENGTH_SHORT).show();
                return;
            }

            method.setAccessible(true);
            try {
                method.invoke(obj, v);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    method.invoke(obj, null);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 监测网络是否可用
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = mgr.getAllNetworkInfo();
        if (info != null) {
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

}
