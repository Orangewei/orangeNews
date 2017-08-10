package orange.w.view_wrapper;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import orange.w.R;

/**
 * Created by zqw on 2017/7/17.
 */

public class RecyclerViewWrapper extends BaseViewWrapper {
    public RecyclerViewWrapper(View itemView) {
        super(itemView);
    }


    public RecyclerView getRecyclerView() {
        return (RecyclerView) rootView().findViewById(R.id.rv_container);
    }

    public SwipeRefreshLayout getRefreshView() {
        return (SwipeRefreshLayout) rootView().findViewById(R.id.refreshView);
    }
}