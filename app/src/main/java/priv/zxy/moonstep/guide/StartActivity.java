package priv.zxy.moonstep.guide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hanks.htextview.HTextView;

import priv.zxy.moonstep.R;
import priv.zxy.moonstep.kernel.Application;
import priv.zxy.moonstep.kernel.BaseActivity;
import priv.zxy.moonstep.kernel.MessageReceiverService;
import priv.zxy.moonstep.kernel.bean.ServiceBase;
import priv.zxy.moonstep.login.view.UserLoginActivity;
import priv.zxy.moonstep.main.view.MainActivity;
import priv.zxy.moonstep.utils.LogUtil;
import priv.zxy.moonstep.utils.SharedPreferencesUtil;

/**
 * 这里的关键点是使用定时器Handler.postDelayed(new Runnable, int millions);
 * 这里有个问题就是如果单单使用上面的写法，那么Handler只会被调用一次，想要实现定时器，就用递归的方法，反复调用自身就好了。
 */
public class StartActivity extends BaseActivity {

    private static final String TAG = "StartActivity";

    private Button bt;

    private boolean isStarted = false;

    private String[] words = {"晚霞淌了千年", "", "历经多少云雾", "" ,"飞跃多少天河"," ", "只为与你相见！"};

    private static int seconds = 0;

    private HTextView hTextView;

    private ImageView imageView;

    private String url = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page);

        initStartImageUrl();

        initView();
    }

    private void initStartImageUrl(){
        int number = (int)(1 + Math.random()*(Application.START_IMAGE_MAX_NUMBER - 1 + 1));
        Log.d(TAG,"打印的数字是:" + String.valueOf(number));
        url = ServiceBase.START_PAGE_URL + String.valueOf(number)+".png";
    }

    private void initView(){
        bt = findViewById(R.id.clickJump);
        hTextView = findViewById(R.id.hTextView);
        imageView = findViewById(R.id.imageView);

        Glide.with(this).load(url).placeholder(R.drawable.start_background).dontAnimate().into(imageView);

        LogUtil.d(TAG, "url:" + url);
        imageView.animate().scaleX(1.2f).scaleY(1.2f).setDuration(8000).start();

        bt.setOnClickListener(v->{
            jump_to_LogUtilin_page();
            isStarted = true;
        });


        final Handler mHandler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                switch (seconds){
                    case 0:
                        hTextView.animateText(words[seconds]);
                        break;
                    case 1:
                        break;
                    case 2:
                        hTextView.animateText(words[seconds]);
                        break;
                    case 3:
                        bt.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        hTextView.animateText(words[seconds]);
                        break;
                    case 5:
                        break;
                    case 6:
                        hTextView.animateText(words[seconds]);
                        break;
                    case 7:
                        break;
                    case 8:
                        if (!isStarted){
                            jump_to_LogUtilin_page();
                        }
                        mHandler.removeCallbacks(this);
                        break;
                }
                seconds += 1;
                mHandler.postDelayed(this, 1000);
            }
        };
        mHandler.postDelayed(runnable, 1000);


    }

    /**
     * 如果上次已经成功登录过了，并将成功登录的信息保存在了mysp文件中
     * 通过检索，直接跳入MainActivity中
     * 如果上次登录失败，登录成功与否的标记位被修改为false，那么就要进入到登录页面
     */
    public  void jump_to_LogUtilin_page(){
        if ( SharedPreferencesUtil.getInstance(this).isSuccessedLogined()){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            //当无需通过LogUtilinActivity登录的时候就要开启MessageReceiverService
            startService(new Intent(this, MessageReceiverService.class));
        }else{
            Intent intent = new Intent(this, UserLoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}