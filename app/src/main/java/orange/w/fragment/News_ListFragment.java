package orange.w.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import orange.w.R;
import orange.w.adapter.TouTiaoAdapter;
import orange.w.bean.TouTiaoBean;
import orange.w.bean.TouTiaoListBean;
import orange.w.common.APP;
import orange.w.http.TouTiaoApi;
import orange.w.utils.LogUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class News_ListFragment extends Fragment {
    private String URL;
    private static final String TAG = "News_ListFragment";
    private RecyclerView rv_newsList;
    private TouTiaoAdapter mAdapter;
    private List<TouTiaoListBean> mData = new ArrayList<>();
    private SwipeRefreshLayout refreshView;

    public News_ListFragment() {
        // Required empty public constructor
    }

    public void setUrl(String url) {
        URL = url;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_recycle_view, container, false);
        rv_newsList = (RecyclerView) view.findViewById(R.id.rv_container);
        refreshView = (SwipeRefreshLayout) view.findViewById(R.id.refreshView);
        initView();
        refreshView.setColorSchemeColors(getResources().getColor(R.color.colorblue));
        refreshView.setRefreshing(true);
        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        initData();
        return view;
    }

    /**
     * 初始化布局 recycleView相关
     */
    private void initView() {
        mAdapter = new TouTiaoAdapter(mData, getContext());
        rv_newsList.setLayoutManager(new LinearLayoutManager(APP.appContext));
        rv_newsList.setAdapter(mAdapter);
    }

    /**
     * 数据请求
     */
    private void initData() {
        Observer<TouTiaoBean> observer = new Observer<TouTiaoBean>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {
                LogUtil.e(TAG, "onSubscribe");
            }
            @Override
            public void onNext(@NonNull TouTiaoBean o) {
                mData.clear();
                mData.addAll(o.getData());
                mAdapter.notifyDataSetChanged();
                if (refreshView.isRefreshing()) {
                    refreshView.setRefreshing(false);
                }
            }
            @Override
            public void onError(@NonNull Throwable throwable) {
                LogUtil.e(TAG, "onError:" + throwable.getMessage());
            }
            @Override
            public void onComplete() {
                LogUtil.e(TAG, "onComplete");
            }
        };
        Observable.just(0)
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Integer, Observable<TouTiaoBean>>() {
                    @Override
                    public Observable<TouTiaoBean> apply(Integer integer) throws Exception {
                        return  new TouTiaoApi().newsList(URL);
                    }
                })
                .doOnNext(new Consumer<Object>() {
                    @Override
                    public void accept(Object response) throws Exception {
                        //本地数据存储 打印log都可以
                        LogUtil.e("网络请求数据",response.toString());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
