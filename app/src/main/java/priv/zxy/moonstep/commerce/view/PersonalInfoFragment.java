package priv.zxy.moonstep.commerce.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import priv.zxy.moonstep.BuildConfig;
import priv.zxy.moonstep.R;
import priv.zxy.moonstep.customview.MyDialog;
import priv.zxy.moonstep.utils.LogUtil;
import priv.zxy.moonstep.utils.SharedPreferencesUtil;
import priv.zxy.moonstep.login.view.UserLoginActivity;

/**
 * 检查settings按钮设置是否可以生效，若是不能生效，则说明是toolbar设置的问题，针对toolbar进行改进。
 */
public class PersonalInfoFragment extends Fragment implements OnMenuItemClickListener, OnMenuItemLongClickListener {

    private static final String TAG = "PersonalInfoFragment";
    private View view;
    private ContextMenuDialogFragment mMenuDiaLogFragment;
    private ArrayList<MenuObject> menuObjects = new ArrayList<>();
    private Context mContext;
    private Activity mActivity;
    private Button settings;
    private CircleImageView userPhoto;
    private TextView nickName;
    private TextView userLevel;
    private TextView userPet;
    private TextView userRace;
    private Button magicWend;
    private Button addFriend;
    private Button sendMessage;
    private TextView signature;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getContext();
        mActivity = this.getActivity();
        setHasOptionsMenu(true);//在Fragment要想让onCreateOptionsMenu生效必须先调用setHasOptionsMenu的方法
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fg_main_first_subpage4, container, false);
        initView();
        updateData();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void initView() {
        initToolbar();
        initMenuFragment();
        userPhoto = (CircleImageView) view.findViewById(R.id.userPhoto);
        nickName = (TextView) view.findViewById(R.id.nickName);
        userLevel = (TextView) view.findViewById(R.id.userLevel);
        userPet = (TextView) view.findViewById(R.id.userPet);
        userRace = (TextView) view.findViewById(R.id.userRace);
        magicWend = (Button) view.findViewById(R.id.magicWend);
        addFriend = (Button) view.findViewById(R.id.addFriend);
        sendMessage = (Button) view.findViewById(R.id.sendMessage);
        signature = (TextView) view.findViewById(R.id.signature);
    }

    //若要使用页面设置弹窗效果，必须要使用toolbar，然而约束布局toolbar不显示
    private void initToolbar() {
        Toolbar mToolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) mActivity).setSupportActionBar(mToolbar);
        if (((AppCompatActivity) mActivity).getSupportActionBar() != null) {
            ((AppCompatActivity) mActivity).getSupportActionBar().setHomeButtonEnabled(true);
            ((AppCompatActivity) mActivity).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) mActivity).getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDiaLogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDiaLogFragment.setItemClickListener(this);
        mMenuDiaLogFragment.setItemLongClickListener(this);
    }

    private void popupDiaLogUtil() {
        final MyDialog myDialog = new MyDialog(mActivity);
        myDialog.setTitle("退出提示!");
        myDialog.setContent("退出登陆后我们会继续保留您的账户数据，记得常回来看看哦！");
        myDialog.setNegativeClickLister("取消", new MyDialog.onNegativeClickListener() {
            @Override
            public void onNegativeClick() {
                myDialog.dismiss();
            }
        });
        myDialog.setPositiveClickLister("确认", () -> {
            menuObjects.clear();
            Intent intent = new Intent(mActivity, UserLoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            new Thread(() -> {
                EMClient.getInstance().logout(true);//退出EMC的服务，让当前用户不在线
            }).start();
            if (BuildConfig.DEBUG) LogUtil.d("PersonalInfoFragment", "用户已经成功下线");
            startActivity(intent);
        });
        myDialog.show();
    }

    private List<MenuObject> getMenuObjects() {
        menuObjects.clear();
        MenuObject close = new MenuObject("关闭");
        close.setResource(R.mipmap.close);

        MenuObject send = new MenuObject("修改背景图片");
        send.setResource(R.mipmap.wrench);

        MenuObject like = new MenuObject("修改个人信息");
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.mipmap.personal_info);
        like.setBitmap(b);

        MenuObject addFr = new MenuObject("黑名单");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.mipmap.black_list));
        addFr.setDrawable(bd);

        MenuObject addFav = new MenuObject("意见反馈");
        addFav.setResource(R.mipmap.feedback);

        MenuObject LogUtilOut = new MenuObject("退出登录");
        LogUtilOut.setResource(R.mipmap.log_out);

        menuObjects.add(close);
        menuObjects.add(send);
        menuObjects.add(like);
        menuObjects.add(addFr);
        menuObjects.add(addFav);
        menuObjects.add(LogUtilOut);
        return menuObjects;
    }

    //初始化选项菜单
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //一旦不设置menu.clear()属性，就会导致activity的的menu和fragment的menu一起显示出来，展示的效果会变成三个点加menu的菜单
        menu.clear();
        mActivity.getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (getFragmentManager().findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDiaLogFragment.show(getFragmentManager(), ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        switch (position) {
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:
                //这里设置一个弹窗，确认是否登出当前账号
                popupDiaLogUtil();
                break;
        }
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
    }

    public void updateData() {
        nickName.setText(SharedPreferencesUtil.getInstance(mContext).readMySelfInformation().get("nickName"));
        userLevel.setText(SharedPreferencesUtil.getInstance(mContext).readMySelfInformation().get("userLevel"));
        userPet.setText(SharedPreferencesUtil.getInstance(mContext).readMySelfInformation().get("userPet"));
        userRace.setText(SharedPreferencesUtil.getInstance(mContext).readMySelfInformation().get("userRace"));
        signature.setText(SharedPreferencesUtil.getInstance(mContext).readMySelfInformation().get("signature"));
    }
}