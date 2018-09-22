package priv.zxy.moonstep.login.module.biz;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import priv.zxy.moonstep.Utils.LoginUtil;
import priv.zxy.moonstep.Utils.PhoneCheckUtil;
import priv.zxy.moonstep.Utils.ToastUtil;
import priv.zxy.moonstep.login.module.bean.ErrorCode;

/**
 * Created by Zxy on 2018/9/20
 */
public class UserBiz implements IUser {

    /**
     * 在做doLogin()之前，你必须先开启刷新页面，否则将会影响用户的体验
     * 当doLogin()方法的调用结束以后，你必须关闭刷新页面，否则将会无法使界面对用户进行响应
     * @param mActivity 调用者当前Activity
     * @param mContext 调用者当前的上下文
     * @param userName 用户名
     * @param userPassword 用户密码
     * @param loginListener 登陆监听
     */
    @Override
    public void doLogin(Activity mActivity, Context mContext, String userName, String userPassword, OnLoginListener loginListener) throws InterruptedException {
        if (userName != null && userPassword != null) {
            LoginUtil loginUtil = new LoginUtil(mContext, mActivity);
            loginUtil.LoginRequest(userName, userPassword);
            boolean isSuccess = false;
            //这里开启一个延迟，用来获得正确的反馈结果,注意这个Thread的执行实际上是异步的
            Thread.currentThread().sleep(1300);
            isSuccess = loginUtil.isSuccess;//获得反馈的结果
            if(isSuccess){
                loginListener.loginSuccess();
            }else{
                loginListener.loginFail();
            }
        } else {
            ToastUtil toastUtil = new ToastUtil(mContext, mActivity);
            toastUtil.showToast("您的账户/密码不能为空哦！");
        }
    }

    /**
     * 在做doRegister之前，你必须先开启页面的刷新，否则将会影响到用户体验
     * 在doRegister调用完成后，你必须关闭页面的刷新机制，否则程序将会无法对用户进行响应
     * @param mActivity 得到调用的当前Activity
     * @param mContext 得到当前的上下文环境
     * @param userName 用户名
     * @param userPassword 用户密码
     * @param gender 性别
     * @param registerListener 注册监听
     */
    @Override
    public void doRegister(Activity mActivity, Context mContext, String userName, String userPassword, String gender, OnRegisterListener registerListener) {

    }

    /**
     * doVerfiyPhoneNumber是在手机注册的第一个页面中来检验手机账号的合法性
     * 具体是进行一个网络操作，并在数据库中进行查询是否有当前手机的一个数据记录
     * 如果数据记录已经存在，那么当前手机的验证失败
     * 如果数据记录不存在的话，手机验证成功，并且可以进入下一页面
     * 因为要对数据库进行查询的请求操作
     * 所以这里要设置一个刷新页面的开启和关闭，但是需要注意的是：modle层本身用来处理逻辑的操作，而对页面的刷新和关闭则是View层的内容，因此我们不在Module层设置页面的刷新机制
     * 但是因为页面刷新机制本身中并不带有线程休眠时间的控制，所以在doVerifyPhoneNumber中应该加入线程休眠的时间
     * @param phoneNumber 用户输入的手机号码
     * @param mContext 上下文环境
     * @param mActivity 持掌上下文的Activity本身
     * @param verifyPhoneNumber 对于phoneNumber的事件监听，这里应该根据实际的状况向verifyPhoneNumber的两个函数传递具体的事件监听的状态码，并根据实际的状态码来处理实际的事件，状态吗可以由bean层中的枚举类来承接
     */
    @Override
    public void doVerifyPhoneNumber(String phoneNumber, Context mContext, Activity mActivity, OnVerifyPhoneNumber verifyPhoneNumber) {
        if (phoneNumber.equals("")) {
            // 这里应该设置一个具体的状态码：手机号为空
            verifyPhoneNumber.verifyFail(ErrorCode.PhoneNumberISEmpty);
        } else {
            PhoneCheckUtil peUtil = new PhoneCheckUtil(mContext, mActivity);
            peUtil.phoneCheck(phoneNumber);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            boolean isSuccess = false;
            isSuccess = peUtil.isSuccess;
            if(isSuccess){
                verifyPhoneNumber.verifySuccess();
            }
        }
    }
}
