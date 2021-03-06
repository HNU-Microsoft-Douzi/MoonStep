package priv.zxy.moonstep.library.cache;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 创建人: Administrator
 * 创建时间: 2018/11/21
 * 描述: ConcreteSharedPreference类的子类，用来在原有ConcreteSharedPreference的基础上添加条件判断功能
 *      既给定一个element，判断是否在所在库中，如果在的话，就返回为该element对应的值，如果不在，就返回false
 *      简单来说，是用来处理缓存中存入的boolean类型的值
 **/

public class JudgeConcreteSharedPreference extends ConcreteSharedPreference {

    private String element = null;
    private Context context;
    private boolean b;
    private static int mode = 0;

    public JudgeConcreteSharedPreference(Context context) {
        super(context);
        this.context = context;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getElement() {
        return element;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    public boolean getB(){
        return b;
    }

    /**
     * 如果element在数据中的话，就返回它本身的结果，如果不存在的话，就返回false
     * @return
     */
    public boolean check(){
        SharedPreferences sp = context.getSharedPreferences(super.getLibrary(), mode);
        return sp.getBoolean(element, false);
    }

    public void save(){
        SharedPreferences sp = context.getSharedPreferences(super.getLibrary(), mode);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(element, b);
        editor.apply();
    }

}
