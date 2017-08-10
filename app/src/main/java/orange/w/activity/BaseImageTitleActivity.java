package orange.w.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;

import orange.w.R;
import orange.w.databinding.ActivityTouTiaoWebViewBinding;

public abstract class BaseImageTitleActivity extends AppCompatActivity {
    private ActivityTouTiaoWebViewBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_tou_tiao_web_view);
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        onInflater(mBinding.nsvWvContent);
    }

    protected abstract void onInflater(NestedScrollView view);

    public void loadImage(String url) {
        mBinding.pvHeader.load(url);
    }

    public void setToolBar(String title) {
        mBinding.toolbar.setTitle(title);
    }

    public void setToolBarLayout(String title) {
        mBinding.toolbarLayout.setTitle(Html.fromHtml("<font fontSize=12>" + title + "</font>"));
    }

    public void setButtonListener(View.OnClickListener listener) {
        mBinding.fab.setOnClickListener(listener);
    }
}
