package priv.zxy.moonstep.login.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.Utils.UserNameCheckUtil;
import priv.zxy.moonstep.login.presenter.UserRegisterPresenter;
import priv.zxy.moonstep.login.presenter.UserVerifyPhoneNumberPresenter;
import priv.zxy.moonstep.main_page.MainActivity;

/**
 *  Created by Zxy on 2018/9/23
 */

public class UserRegisterActivity extends AppCompatActivity implements IUserRegisterView {

    private Button checkUsername;
    private EditText accountName;
    private RadioGroup radioGroup;
    private RadioButton man;
    private RadioButton woman;
    private EditText password;
    private EditText passwordCheck;
    private Button clickRegister;
    private Button returnLoginPage;
    private ImageView backButton;
    private View deepBackground;
    private View plainBackground;
    private ContentLoadingProgressBar progressBar;
    private String phoneNumber;
    private Context mContext;
    private Activity mActivity;
    private String userName;
    private String userPassword = "";
    private String confirmPassword = "";
    private String userGender = "";

    //创建一个桥接对象，通过Presenter完成和Module层的交互
    private UserRegisterPresenter userRegisterPresenter;
    //加载动画资源
    private Animation shake;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            hideLoading();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        checkUsername = (Button) findViewById(R.id.check_username);
        accountName = (EditText) findViewById(R.id.accountName);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        man = (RadioButton) findViewById(R.id.man);
        woman = (RadioButton) findViewById(R.id.woman);
        password = (EditText) findViewById(R.id.password);
        passwordCheck = (EditText) findViewById(R.id.password_check);
        clickRegister = (Button) findViewById(R.id.click_register);
        returnLoginPage = (Button) findViewById(R.id.return_login_page);
        backButton = (ImageView) findViewById(R.id.back_button);
        deepBackground = (View) findViewById(R.id.deepBackground);
        plainBackground = (View) findViewById(R.id.plainBackground);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
        mContext = this.getApplicationContext();
        mActivity = this;

        phoneNumber = getPhoneNumber();

        userRegisterPresenter = new UserRegisterPresenter(this, mActivity, mContext);
        hideLoading();

        checkUsername.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        checkUsername.setAnimation(shake);
                        showLoading();
                        break;
                    case MotionEvent.ACTION_UP:
                        //检测用户名是否已经存在于数据库中，并跳出弹窗对用户进行提示
                        userName = accountName.getText().toString();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Looper.prepare();
                                try {
                                    userRegisterPresenter.UserNameCheck(userName);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                mHandler.sendEmptyMessage(0x01);
                                Looper.loop();
                            }
                        }).start();
                        break;
                }
                return true;
            }
        });

        backButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        backButton.setAnimation(shake);
                        break;
                    case MotionEvent.ACTION_UP:
                        finishActivitySelf();
                        break;
                }
                return true;
            }
        });

        clickRegister.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        clickRegister.setAnimation(shake);
                        showLoading();
                        break;
                    case MotionEvent.ACTION_UP:
                        getData();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Looper.prepare();
                                try {
                                    userRegisterPresenter.doRegister(userName, userPassword, confirmPassword, userGender);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                mHandler.sendEmptyMessage(0x01);
                                Looper.loop();
                            }
                        }).start();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getUserPassWord() {
        return null;
    }

    @Override
    public void clearUserName() {
        accountName.clearComposingText();
    }

    @Override
    public void clearUserPassword() {
        password.clearComposingText();
    }

    @Override
    public void clearUserConfirmPassword() {
        passwordCheck.clearComposingText();
    }

    @Override
    public void showLoading() {
        progressBar.show();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        deepBackground.setVisibility(View.VISIBLE);
        plainBackground.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        deepBackground.setVisibility(View.GONE);
        plainBackground.setVisibility(View.GONE);
        progressBar.hide();
    }

    @Override
    public void toLoginActivity() {
        Intent intent = new Intent(this, UserLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void toMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void showFailedError(int code) {

    }

    @Override
    public String getPhoneNumber() {
        Intent intent = getIntent();
        return intent.getStringExtra("phoneNumber");
    }

    @Override
    public void getData() {
        userName = accountName.getText().toString();
        userPassword = password.getText().toString();
        confirmPassword = passwordCheck.getText().toString();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.man:
                        userGender = "男";
                        break;
                    case R.id.woman:
                        userGender = "女";
                        break;
                }
            }
        });
    }

    @Override
    public void finishActivitySelf() {
        this.finish();
    }
}