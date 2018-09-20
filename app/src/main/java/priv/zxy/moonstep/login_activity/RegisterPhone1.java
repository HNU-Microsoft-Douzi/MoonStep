package priv.zxy.moonstep.login_activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.Utils.PhoneCheckUtil;
import priv.zxy.moonstep.Utils.ToastUtil;

public class RegisterPhone1 extends AppCompatActivity {


    private String phoneNumberText;
    private TextView inputText;
    private LinearLayout content1;
    private TextView countryChoice;
    private View countryLine;
    private LinearLayout content2;
    private TextView phoneHeader;
    private EditText phoneNumber;
    private View phoneLine;
    private Button submit;
    private ImageView backButton;
    private View deepBackground;
    private View plainBackground;
    private ContentLoadingProgressBar progressBar;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 0x01:
                    progressBar.hide();
                    deepBackground.setVisibility(View.GONE);
                    plainBackground.setVisibility(View.GONE);
                    break;
            }
        }
    };

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.obtainMessage(0x01).sendToTarget();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_phone1);
        initView();
        initData();
    }

    private void initData() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
                try {
                    checkAndOperatePhoneNumber();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        countryChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * 这里应该跳转到一个选择国家并改变手机国家标号的页面，可以用fragment来做
                 */
                jump_to_country_choice_page();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishThis();
            }
        });
    }

    private void getData() {
        phoneNumberText = phoneNumber.getText().toString();
    }

    private void checkAndOperatePhoneNumber() throws InterruptedException {
        if (phoneNumberText.equals("")) {
            ToastUtil toastUtil = new ToastUtil(this.getApplicationContext(), this);
            toastUtil.showToast("手机号不能为空，请重试");
        } else {
            PhoneCheckUtil peUtil = new PhoneCheckUtil(this.getApplicationContext(), this);
            peUtil.phoneCheck(phoneNumberText);
            refreshPage();
            new Thread(runnable).start();
        }
    }

    /**
     * 刷新页面
     */
    private void refreshPage() throws InterruptedException {
        progressBar.show();
        Thread.sleep(200);
        deepBackground.setVisibility(View.VISIBLE);
        plainBackground.setVisibility(View.VISIBLE);
    }

    private void jump_to_country_choice_page() {
        Toast.makeText(this, "国家选择页面还没有写好", Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        inputText = (TextView) findViewById(R.id.input_text);
        content1 = (LinearLayout) findViewById(R.id.content1);
        countryChoice = (TextView) findViewById(R.id.countryChoice);
        countryLine = (View) findViewById(R.id.country_line);
        content2 = (LinearLayout) findViewById(R.id.content2);
        phoneHeader = (TextView) findViewById(R.id.phone_header);
        phoneNumber = (EditText) findViewById(R.id.phone_number);
        phoneLine = (View) findViewById(R.id.phone_line);
        submit = (Button) findViewById(R.id.submit);
        backButton = (ImageView) findViewById(R.id.back_button);
        deepBackground = (View) findViewById(R.id.deepBackground);
        plainBackground = (View) findViewById(R.id.plainBackground);
        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void finishThis() {
        this.finish();
    }
}
