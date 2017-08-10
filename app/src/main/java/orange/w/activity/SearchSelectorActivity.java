package orange.w.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import orange.w.R;
import orange.w.bean.SearchSelectorBean;
import orange.w.customview.SearchSelectorView;
import orange.w.utils.ToastUtil;

public class SearchSelectorActivity extends Activity {
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_selector);
        findViewById(R.id.bt_popu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopu();
            }
        });
    }

    private void showPopu() {
        View view = getLayoutInflater().inflate(R.layout.view_popu_search, null, false);
        initSelrtorView(view);
        mPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        mPopupWindow.showAsDropDown(SearchSelectorActivity.this.findViewById(R.id.bt_popu));
    }

    private void initSelrtorView(View view) {
        SearchSelectorView selectorView = (SearchSelectorView) view.findViewById(R.id.ssv_selector);
        List<String> list = new ArrayList<>();
        list.add("测试标签1");
        list.add("测试标签2");
        list.add("测试标签3");
        list.add("测试标签4");
        list.add("测试标签4");

        Observer<SearchSelectorBean> observer = new Observer<SearchSelectorBean>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {

            }

            @Override
            public void onNext(@NonNull SearchSelectorBean searchSelectorBean) {
                ToastUtil.showToast("点击了" +searchSelectorBean.dataType+ searchSelectorBean.position);

            }

            @Override
            public void onError(@NonNull Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }
        };
        selectorView.setObserver(observer);
        selectorView.setSelected(list);
        selectorView.setHistory(list);
        selectorView.setPlace(list);
    }
}
