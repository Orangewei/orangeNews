package orange.w.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import orange.w.R;
import orange.w.adapter.SearchSelectorAdapter;
import orange.w.bean.SearchSelectorBean;

/**
 * Created by zqw on 2017/7/18.
 * 筛选选择view
 */

public class SearchSelectorView extends RelativeLayout {
    private Context mContext;
    private MyGridView gv_selected;
    private MyGridView gv_history;
    private MyGridView gv_place;
    private TextView tv_selected;
    private TextView tv_history;
    private TextView tv_place;
    private SearchSelectorAdapter mSelectedAdapter;
    private SearchSelectorAdapter mHistoryAdapter;
    private SearchSelectorAdapter mPlaceAdapter;
    private List<String> selectedData;
    private List<String> historyData;
    private List<String> placeData;
    private Observer<SearchSelectorBean> observer;

    public SearchSelectorView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public SearchSelectorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public SearchSelectorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_search_selector, this, true);
        gv_selected = (MyGridView) view.findViewById(R.id.gv_selected);//已选
        gv_history = (MyGridView) view.findViewById(R.id.gv_history);//历史选择
        gv_place = (MyGridView) view.findViewById(R.id.gv_place);//城市选择
        tv_selected = (TextView) view.findViewById(R.id.tv_selected);//已选标题
        tv_history = (TextView) view.findViewById(R.id.tv_history);//历史选择标题
    }

    /**
     * @param dataList 已选列表
     */
    public void setSelected(List<String> dataList) {
        gv_selected.setNumColumns(3);
        setCommonProperty(gv_selected, selectedData, dataList, mSelectedAdapter, 1);
    }

    /**
     * @param dataList 历史选择列表
     */
    public void setHistory(List<String> dataList) {
        tv_history.setVisibility(VISIBLE);
        gv_history.setVisibility(VISIBLE);
        setCommonProperty(gv_history, historyData, dataList, mHistoryAdapter, 2);
    }

    /**
     * @param dataList 选择地点
     */
    public void setPlace(List<String> dataList) {
        setCommonProperty(gv_place, placeData, dataList, mPlaceAdapter, 3);

    }

    public void setObserver(Observer<SearchSelectorBean> observer) {
        this.observer = observer;
    }

    /**
     * @param gridView
     * @param list     数据源
     * @param data     传入数据
     * @param adapter  适配器
     * @param dataType 1:已选 2:历史 3:当前
     */
    private void setCommonProperty(MyGridView gridView, List<String> list, List<String> data, SearchSelectorAdapter adapter, int dataType) {
        if (adapter == null) {
            list = new ArrayList<>();
            list.addAll(data);
            adapter = new SearchSelectorAdapter(mContext, list, dataType, observer);
            gridView.setAdapter(adapter);
        } else {
            list.clear();
            list.addAll(data);
            adapter.notifyDataSetChanged();
        }
    }
}
