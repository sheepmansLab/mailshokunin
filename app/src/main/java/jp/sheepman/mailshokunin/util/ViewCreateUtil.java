package jp.sheepman.mailshokunin.util;

import android.content.Context;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ViewCreateUtil {
    public static View createViewObject(Context context, String viewName){
        View v = null;
        try {
            Class<?> cls = Class.forName(viewName);
            Constructor con = cls.getConstructor(Context.class);
            v = (View)con.newInstance(context);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return v;
    }
}
