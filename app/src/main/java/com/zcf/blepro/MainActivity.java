package com.zcf.blepro;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcf.blepro.fragment.ContactFragMent;
import com.zcf.blepro.fragment.DEmoFragMent;
import com.zcf.blepro.fragment.HomeFragMent;
import com.zcf.blepro.fragment.SettingFragMent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_slide)
    LinearLayout llSlide;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.ll_setting)
    LinearLayout llSetting;
    @BindView(R.id.ll_demo)
    LinearLayout llDemo;
    @BindView(R.id.ll_contact)
    LinearLayout llContact;
    @BindView(R.id.iv_iv)
    ImageView ivIv;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_sigal)
    ImageView ivSigal;
    private Fragment nowFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        toolbar.setNavigationIcon(R.mipmap.home_icon_menu_default);
        getAndroiodScreenProperty();

        replaceFragment("home");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tvTitle.setVisibility(View.GONE);
        ivIv.setImageResource(R.mipmap.home_icon_logo_default);
    }


    public void getAndroiodScreenProperty() {
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (height / density);// 屏幕高度(dp)


        Log.d("h_bl", "屏幕宽度（像素）：" + width);
        Log.d("h_bl", "屏幕高度（像素）：" + height);
        Log.d("h_bl", "屏幕密度（0.75 / 1.0 / 1.5）：" + density);
        Log.d("h_bl", "屏幕密度dpi（120 / 160 / 240）：" + densityDpi);
        Log.d("h_bl", "屏幕宽度（dp）：" + screenWidth);
        Log.d("h_bl", "屏幕高度（dp）：" + screenHeight);


        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) llSlide.getLayoutParams();
//获取当前控件的布局对象
        params.width = (int) (width * 0.8);//设置当前控件布局的高度
        llSlide.setLayoutParams(params);//将设置好的布局参数应用到控件中
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(Gravity.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.ll_home, R.id.ll_setting, R.id.ll_demo, R.id.ll_contact})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_home:
                drawerLayout.setBackgroundResource(R.mipmap.home_bg);
                tvTitle.setVisibility(View.GONE);
                ivIv.setVisibility(View.VISIBLE);
                ivSigal.setVisibility(View.VISIBLE);
                replaceFragment("home");
                break;
            case R.id.ll_setting:
                drawerLayout.setBackgroundColor(getResources().getColor(R.color.c121212));
                ivIv.setVisibility(View.GONE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("Settings");
                ivSigal.setVisibility(View.GONE);
                replaceFragment("setting");
                break;
            case R.id.ll_demo:
                drawerLayout.setBackgroundColor(getResources().getColor(R.color.c121212));
                ivIv.setVisibility(View.GONE);
                tvTitle.setVisibility(View.VISIBLE);
                tvTitle.setText("Demo Mode");
                ivSigal.setVisibility(View.GONE);
                replaceFragment("demo");
                break;
            case R.id.ll_contact:
                drawerLayout.setBackgroundColor(getResources().getColor(R.color.c121212));
                tvTitle.setVisibility(View.VISIBLE);
                ivIv.setVisibility(View.GONE);
                tvTitle.setText("Contact");
                ivSigal.setVisibility(View.GONE);
                replaceFragment("cont");
                break;


        }
        drawerLayout.closeDrawer(Gravity.START);

    }

    private void replaceFragment(String tag) {
        if (nowFragment != null) {
            getSupportFragmentManager().beginTransaction().hide(nowFragment).commit();
        }
        nowFragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (nowFragment == null) {
            switch (tag) {
                case "home":
                    nowFragment = new HomeFragMent();
                    break;
                case "setting":
                    nowFragment = new SettingFragMent();
                    break;
                case "demo":
                    nowFragment = new DEmoFragMent();
                    break;
                case "cont":
                    nowFragment = new ContactFragMent();
                    break;
            }
            getSupportFragmentManager().beginTransaction().add(R.id.content_frame, nowFragment, tag).commit();
        } else {
            getSupportFragmentManager().beginTransaction().show(nowFragment).commit();
        }
    }

    public void reFragment(String aaaaa) {
        drawerLayout.setBackgroundResource(R.mipmap.home_bg);
        tvTitle.setVisibility(View.GONE);
        ivIv.setVisibility(View.VISIBLE);
        ivSigal.setVisibility(View.VISIBLE);
        if (nowFragment != null) {
            getSupportFragmentManager().beginTransaction().hide(nowFragment).commit();
        }
        nowFragment = HomeFragMent.newInstance(aaaaa);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, nowFragment).commit();
    }
}
