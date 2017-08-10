package orange.w.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import orange.w.R;
import orange.w.activity.TouTiaoWebViewActivity;
import orange.w.bean.TouTiaoListBean;
import orange.w.common.APP;
import orange.w.view_wrapper.RecyclerViewWrapper;

/**
 * Created by zqw on 2017/7/17.
 * 每日头条适配器
 */

public class TouTiaoAdapter extends RecyclerView.Adapter {
    private List<TouTiaoListBean> mData;
    private Context mContext;

    public TouTiaoAdapter(List<TouTiaoListBean> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 0) {
            TextView itemView = new TextView(mContext);
//            itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(40f)));
            return new RecyclerView.ViewHolder(itemView) {
            };
        }
        if (viewType == 1) {
            TextView itemView = new TextView(mContext);
//            itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ConvertUtils.dp2px(40f)));
            return new RecyclerView.ViewHolder(itemView) {
            };
        }
        /**
         * 使用这种inflate方法可以match_parent
         */
        View item = LayoutInflater.from(APP.appContext).inflate(R.layout.item_news, parent, false);
        return new RecyclerViewWrapper(item);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position == 0) {
            return;
        }
        if (position == 1) {
            TextView itemView = (TextView) holder.itemView;
            itemView.setGravity(Gravity.CENTER_VERTICAL);
            itemView.setTextColor(Color.parseColor("#666666"));
            itemView.setPadding(24, 0, 0, 0);
            itemView.setText("今日热闻");
            return;
        }
        TextView tv_title = (TextView) holder.itemView.findViewById(R.id.tv_title);
        TextView tv_media = (TextView) holder.itemView.findViewById(R.id.tv_media);
        ImageView iv_thumbnail = (ImageView) holder.itemView.findViewById(R.id.iv_thumbnail);
        tv_title.setText(mData.get(position - 1).getTitle());
        tv_media.setText(mData.get(position - 1).getSource());
        Picasso.with(mContext).load(mData.get(position - 1).getImage_url()).into(iv_thumbnail);
        tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TouTiaoWebViewActivity.launch(mData.get(position - 1));
            }
        });
    }

}
