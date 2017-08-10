package orange.w.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zqw on 2017/7/20.
 */

public abstract class BaseFragment extends Fragment {
    protected abstract int layoutId();

    public BaseFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(layoutId(), container, false);
        onInflated(view);
        return view;
    }

    protected abstract void onInflated(View contentView);
}
