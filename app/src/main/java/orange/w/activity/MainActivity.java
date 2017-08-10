package orange.w.activity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.squareup.picasso.Picasso;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.PicassoEngine;

import java.util.List;

import io.reactivex.functions.Consumer;
import orange.w.R;
import orange.w.customview.XCRoundImageView;
import orange.w.databinding.ActivityMainBinding;
import orange.w.fragment.ImageShowFragment;
import orange.w.fragment.News_MainFragment;
import orange.w.utils.ToastUtil;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

/**
 * APP主界面 主要为控住区域
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private static final String TAG = "MainActivity";
    private ActivityMainBinding mBinding;
    private News_MainFragment newsMainFragment;
    private ImageShowFragment imageShowFragment;
    private FragmentManager manager;
    private XCRoundImageView iv_userIcon;//用户头像 点击调出图片选择器更换头像(知乎)
    private int REQUEST_CODE_CHOOSE = 12;//知乎图片请求码,接收返回信息时使用
    private List<Uri> mSelected;//接收返回图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mContext = this;
        initView();
    }

    @SuppressLint("ResourceType")
    private void initView() {
        //显示toolbar
        mBinding.toolMain.setTitle("Orange_主页");
        setSupportActionBar(mBinding.toolMain);
        //绑定侧边栏
        mBinding.navigationView.setItemIconTintList(null);
        View headerView = mBinding.navigationView.getHeaderView(0);
        TextView user = (TextView) headerView.findViewById(R.id.tv_userName);
        iv_userIcon = (XCRoundImageView) headerView.findViewById(R.id.iv_userIcon);
        iv_userIcon.setOnClickListener(this);
        user.setText("Orange");

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mBinding.drawerLayoutMain, mBinding.toolMain, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        mBinding.drawerLayoutMain.addDrawerListener(actionBarDrawerToggle);
        mBinding.navigationView.setNavigationItemSelectedListener(navigationLostener);
        //底部功能按钮
        mBinding.bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBinding.bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC
                );
        mBinding.bottomNavigationBar.setBarBackgroundColor("#FCFCFC");
        mBinding.bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.home2, "主页").setInActiveColor(R.color.colorbttonfont).setActiveColorResource(R.color.colorblue))
                .addItem(new BottomNavigationItem(R.mipmap.comment, "段子").setInActiveColor(R.color.colorbttonfont).setActiveColorResource(R.color.colorAccent))
                .addItem(new BottomNavigationItem(R.mipmap.photo, "图片").setInActiveColor(R.color.colorbttonfont).setActiveColorResource(R.color.colororange))
                .addItem(new BottomNavigationItem(R.mipmap.play, "视频").setInActiveColor(R.color.colorbttonfont).setActiveColorResource(R.color.colorpurple2))
                .setFirstSelectedPosition(0)
                .initialise();
        mBinding.bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int i) {
                switch (i) {
                    case 0:
                        mBinding.toolMain.setTitle("Orange_主页");
                        if (newsMainFragment==null){
                            newsMainFragment=new News_MainFragment();
                        }
                        manager.beginTransaction().replace(R.id.fl_main, newsMainFragment).commit();
                        break;
                    case 1:
                        mBinding.toolMain.setTitle("Orange_段子");
                        break;
                    case 2:
                        mBinding.toolMain.setTitle("Orange_图片");
                        if (imageShowFragment==null){
                            imageShowFragment=new ImageShowFragment();
                        }
                        manager.beginTransaction().replace(R.id.fl_main, imageShowFragment).commit();
                        break;
                    case 3:
                        mBinding.toolMain.setTitle("Orange_视频");
                        break;
                }
            }

            @Override
            public void onTabUnselected(int i) {

            }

            @Override
            public void onTabReselected(int i) {

            }
        });
        //加载默认fragment
        newsMainFragment = new News_MainFragment();
        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fl_main, newsMainFragment).commit();
    }

    /**
     * 侧滑菜单监听
     */
    NavigationView.OnNavigationItemSelectedListener navigationLostener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item_setting:
                    Log.e(TAG, "onNavigationItemSelected: 设置");
                    break;
                case R.id.item_theme:
                    Log.e(TAG, "onNavigationItemSelected: 主题");

                    break;
                case R.id.item_love:
                    Log.e(TAG, "onNavigationItemSelected: 收藏");

                    break;
                case R.id.item_share:
                    Log.e(TAG, "onNavigationItemSelected: 分享");

                    break;
                case R.id.logout:
                    Log.e(TAG, "onNavigationItemSelected: 退出");
                    break;
            }
            return true;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_userIcon:
                ToastUtil.showToast("更换头像");
                choosePhotos();
                break;
        }
    }

    /**
     * 选择头像
     */
    private void choosePhotos() {
        requestRxPermissions(READ_EXTERNAL_STORAGE);

    }

    //请求权限
    private void requestRxPermissions(String... permissions) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(permissions).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean granted) throws Exception {
                if (granted) {
                    //已经获得权限
                    Matisse.from(MainActivity.this)
                            .choose(MimeType.allOf())
                            .countable(true)
                         /*   .capture(true)
                            .captureStrategy(
                                    new CaptureStrategy(true, "com.zhihu.matisse.sample.fileprovider"))*/
                            .maxSelectable(9)
//                            .addFilter(new com.zhihu.matisse.sample.GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                            .gridExpectedSize(
                                    getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                            .thumbnailScale(0.85f)
                            .imageEngine(new PicassoEngine())
                            .forResult(REQUEST_CODE_CHOOSE);
                } else {
                    //未获得权限
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            Picasso.with(MainActivity.this).load(mSelected.get(0)).into(iv_userIcon);
        }
    }

}
