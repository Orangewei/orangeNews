package orange.w.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import orange.w.bean.TouTiaoListBean;
import orange.w.common.APP;
import orange.w.common.C;

public class TouTiaoWebViewActivity extends BaseImageTitleActivity {
    private TouTiaoListBean mData;
    private WebView webView;
    @Override
    protected void onInflater(NestedScrollView view) {
        mData = (TouTiaoListBean) getIntent().getSerializableExtra(C.BEAN);
        view.addView(new WebView(APP.appContext));
        webView= (WebView) view.getChildAt(0);
        initView();
    }

    private void initView() {
        loadImage(mData.getImage_url());
        setToolBar(mData.getTitle());
        setToolBarLayout(mData.getTitle());
        setButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        initWebView();
    }

    private void initWebView() {
        WebSettings settings = webView.getSettings();
        settings.supportZoom();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);// 应用可以有缓存
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);// 优先使用缓存
        settings.setAppCacheEnabled(true);// 缓存最多可以有10M
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                view.loadUrl(url);
                return true;//super.shouldOverrideUrlLoading(view, url);
            }
        });
        webView.loadUrl("http://www.toutiao.com/a" + mData.getGroup_id());
    }

    public static void launch(TouTiaoListBean bean) {
        Intent intent = new Intent(APP.appContext, TouTiaoWebViewActivity.class);
        intent.putExtra(C.BEAN, bean);
        APP.appContext.startActivity(intent);
    }
}
