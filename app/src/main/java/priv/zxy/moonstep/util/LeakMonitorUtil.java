package priv.zxy.moonstep.util;

import com.squareup.leakcanary.RefWatcher;

import priv.zxy.moonstep.data.application.Application;

/**
 * 创建人: Administrator
 * 创建时间: 2018/12/22
 * 描述: 把LeakCanary的RefWatcher封装一下，方便使用。
 **/

public class LeakMonitorUtil {

    /**
     * 不加volatile会有DCL失效问题
     */
    private static volatile LeakMonitorUtil instance = null;
    private static RefWatcher refWatcher = null;

    public static LeakMonitorUtil getInstance() {
        if (instance == null){
            synchronized (LeakMonitorUtil.class){
                if (instance == null){
                    instance = new LeakMonitorUtil();
                    refWatcher = Application.mRefWatcher;
                }
            }
        }
        return instance;
    }

    /**
     * 对某个对象设置内存泄漏的监听
     * @param obj
     */
    public void setMonitor(Object obj){
        refWatcher.watch(obj);
    }
}
