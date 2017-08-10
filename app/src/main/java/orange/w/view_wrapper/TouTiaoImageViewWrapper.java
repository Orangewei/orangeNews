package orange.w.view_wrapper;

import android.view.View;
import android.widget.TextView;

import orange.w.R;
import orange.w.customview.PlusImageView;

/**
 * Created by zqw on 2017/7/20.
 */

public class TouTiaoImageViewWrapper extends BaseViewWrapper {
    private TextView tv_title;
    private TextView tv_author;
    private PlusImageView iv_image;

    public TouTiaoImageViewWrapper(View rootView) {
        super(rootView);
        tv_title = (TextView) rootView().findViewById(R.id.tv_title);
        tv_author = (TextView) rootView().findViewById(R.id.tv_author);
        iv_image = (PlusImageView) rootView().findViewById(R.id.iv_image);
    }

    public TextView getTitleView() {
        return tv_title;
    }

    public TextView getAuthorView() {
        return tv_author;
    }

    public PlusImageView getImageView() {
        return iv_image;

    }
}
