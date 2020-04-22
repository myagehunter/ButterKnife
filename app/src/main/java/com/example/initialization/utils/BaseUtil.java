package com.example.initialization.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import com.example.initialization.BaseActivity;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * author : jianghuizhong
 * date   : 2020/4/21
 * desc   : 基础工具
 */

public class BaseUtil {


    /**
     * 使用java反射机制
     * 设置Activity不用findViewbyid
     *
     */
    public static void smartInject(Activity activity) {
        try {
            /** 获取类的字节码通过getclase()方法**/
            Class<? extends Activity> clz = activity.getClass();
            while (clz != BaseActivity.class) {
                /** 获取公共属性对象集合**/
                Field[] fs = clz.getDeclaredFields();
                Resources res = activity.getResources();
                /** 获取私有化对象**/
                String packageName = activity.getPackageName();
                for (Field field : fs) {
                    if (!View.class.isAssignableFrom(field.getType())) {
                        continue;
                    }
                    int viewId = res.getIdentifier(field.getName(), "id", packageName);
                    if (viewId == 0)
                        continue;
                    /**允许反射时访问私有变量**/
                    field.setAccessible(true);
                    try {
                        /**获取控件id,动态注册view**/
                        View v = activity.findViewById(viewId);
                        field.set(activity, v);
                        /**发射类型实现点击事件绑定**/
                        Class<?> c = field.getType();
                        Method m = c.getMethod("setOnClickListener", View.OnClickListener.class);
                        m.invoke(v, activity);
                    } catch (Throwable e) {
                    }
                    field.setAccessible(false);

                }
                clz = (Class<? extends Activity>) clz.getSuperclass();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
