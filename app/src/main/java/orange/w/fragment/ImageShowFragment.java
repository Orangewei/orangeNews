package orange.w.fragment;


import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import orange.w.R;
import orange.w.adapter.ImageShouAdapter;
import orange.w.bean.TouTiaoImageBean;
import orange.w.bean.TouTiaoImageListBean;
import orange.w.common.APP;
import orange.w.http.TouTiaoApi;
import orange.w.utils.LogUtil;
import orange.w.view_wrapper.RecyclerViewWrapper;

/**
 * A simple {@link Fragment} subclass.
 * 图片浏览-->图片列表
 */
public class ImageShowFragment extends BaseFragment {
    private RecyclerViewWrapper recyclerViewWrapper;
    private ImageShouAdapter mAdapter;
    protected final String URL = "?category=%E7%BB%84%E5%9B%BE&utm_source=toutiao&max_behot_time=0&as=A195B9640B4A5D9&cp=594BDAF54D498E1";
    private List<TouTiaoImageListBean> mData = new ArrayList<>();

    @Override
    protected int layoutId() {
        return R.layout.view_recycle_view;
    }


    @Override
    protected void onInflated(View contentView) {
        recyclerViewWrapper = new RecyclerViewWrapper(contentView);
        mAdapter = new ImageShouAdapter(APP.appContext, mData);
        recyclerViewWrapper.getRecyclerView().setLayoutManager(new LinearLayoutManager(APP.appContext));
        recyclerViewWrapper.getRecyclerView().setAdapter(mAdapter);
        recyclerViewWrapper.getRefreshView().setRefreshing(true);
        recyclerViewWrapper.getRefreshView().setColorSchemeColors(getResources().getColor(R.color.colorblue));
        recyclerViewWrapper.getRefreshView().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        initData();
    }

    private void initData() {
        Observable.just(0)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Integer, Observable<TouTiaoImageBean>>() {
                    @Override
                    public Observable<TouTiaoImageBean> apply(@NonNull Integer integer) throws Exception {
                        return TouTiaoApi.imageList(URL);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    Observer<TouTiaoImageBean> observer = new Observer<TouTiaoImageBean>() {
        @Override
        public void onSubscribe(@NonNull Disposable disposable) {

        }

        @Override
        public void onNext(@NonNull TouTiaoImageBean touTiaoImageBean) {
            LogUtil.e("请求图册信息", touTiaoImageBean.toString());
            mData.clear();
            mData.addAll(touTiaoImageBean.getData());
            mAdapter.notifyDataSetChanged();
            recyclerViewWrapper.getRefreshView().setRefreshing(false);
        }

        @Override
        public void onError(@NonNull Throwable throwable) {

        }

        @Override
        public void onComplete() {

        }
    };
}
