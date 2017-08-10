package orange.w.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import orange.w.R;
import orange.w.bean.SearchSelectorBean;

/**
 * Created by zqw on 2017/7/19.
 */

public class SearchSelectorAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mData;
    private int dataType;//数据源类型
    private Observer<SearchSelectorBean> observer;

    public SearchSelectorAdapter(Context mContext, List<String> mData, int dataType) {
        this.mContext = mContext;
        this.mData = mData;
        this.dataType = dataType;
    }

    public SearchSelectorAdapter(Context mContext, List<String> mData, int dataType, Observer<SearchSelectorBean> observer) {
        this.mContext = mContext;
        this.mData = mData;
        this.dataType = dataType;
        this.observer = observer;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_search_selector, parent, false);
            holder = new ViewHolder();
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_content.setText(mData.get(position));
        if (dataType == 1) {
            Drawable drawable = mContext.getDrawable(R.drawable.ic_delete);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.tv_content.setCompoundDrawables(null, null, drawable, null);
            holder.tv_content.setCompoundDrawablePadding(6);
        }
        holder.tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable<SearchSelectorBean> observable = Observable.create(new ObservableOnSubscribe<SearchSelectorBean>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<SearchSelectorBean> observableEmitter) throws Exception {
                        observableEmitter.onNext(new SearchSelectorBean(dataType, position));
                    }
                });
                observable.subscribe(observer);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tv_content;
    }
}
