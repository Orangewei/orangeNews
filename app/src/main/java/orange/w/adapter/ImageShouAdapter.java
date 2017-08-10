package orange.w.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import orange.w.R;
import orange.w.activity.ImageShowActivity;
import orange.w.bean.TouTiaoImageListBean;
import orange.w.view_wrapper.TouTiaoImageViewWrapper;

/**
 * Created by zqw on 2017/7/20.
 */

public class ImageShouAdapter extends RecyclerView.Adapter<TouTiaoImageViewWrapper> {
    private Context mContext;
    private List<TouTiaoImageListBean> mData;

    public ImageShouAdapter(Context mContext, List<TouTiaoImageListBean> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public TouTiaoImageViewWrapper onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_toutiao_image, parent, false);
        return new TouTiaoImageViewWrapper(view);
    }

    @Override
    public void onBindViewHolder(TouTiaoImageViewWrapper holder, final int position) {
        holder.getTitleView().setText(mData.get(position).getTitle());
        holder.getAuthorView().setText(mData.get(position).getSource());
        holder.getImageView().load(mData.get(position).getImage_list().get(0).getPc_url());
        holder.getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageShowActivity.launch(mData.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
