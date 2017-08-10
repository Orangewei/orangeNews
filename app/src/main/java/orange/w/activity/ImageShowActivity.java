package orange.w.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import orange.w.R;
import orange.w.adapter.VPAdapter;
import orange.w.bean.TouTiaoImageListBean;
import orange.w.common.APP;
import orange.w.common.C;
import orange.w.customview.PlusImageView;

/**
 * 照片展示
 */
public class ImageShowActivity extends BaseImageTitleActivity {
    private Context mContext;
    private View mView;
    private PlusImageView iv_bg;
    private ViewPager viewPager;
    private VPAdapter vpAdapter;
    private TouTiaoImageListBean mData;

    @Override
    protected void onInflater(NestedScrollView view) {
        mContext = this;
        mData = (TouTiaoImageListBean) getIntent().getSerializableExtra(C.BEAN);
        mView = LayoutInflater.from(this).inflate(R.layout.activity_image_show, view, false);
        view.addView(mView);
        iv_bg = (PlusImageView) mView.findViewById(R.id.iv_bg);
        viewPager = (ViewPager) mView.findViewById(R.id.my_viewpager);
        loadImage(mData.getImage_list().get(0).getPc_url());
        setToolBar(mData.getTitle());
        setToolBarLayout(mData.getTitle());
        initViewPager();
    }

    private void initViewPager() {
        vpAdapter = new VPAdapter(getSupportFragmentManager(), mData);
        viewPager.setAdapter(vpAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        // 首次进入展示第二页
        viewPager.setCurrentItem(1);
    }

    public static void launch(TouTiaoImageListBean bean) {
        Intent intent = new Intent(APP.appContext, ImageShowActivity.class);
        intent.putExtra(C.BEAN, bean);
        APP.appContext.startActivity(intent);
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Picasso.with(APP.appContext)
                    .load(mData.getImage_list()
                    .get(position).getPc_url())
                    .transform(new Trans(APP.appContext))
                    .into(iv_bg);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    class Trans implements Transformation {
        RenderScript rs;

        public Trans(Context context) {
            rs = RenderScript.create(context);
        }

        @Override
        public Bitmap transform(Bitmap source) {
            // Create another bitmap that will hold the results of the filter.
            Bitmap blurredBitmap = source.copy(Bitmap.Config.ARGB_8888, true);

            // Allocate memory for Renderscript to work with
            Allocation input = Allocation.createFromBitmap(rs, blurredBitmap, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SHARED);
            Allocation output = Allocation.createTyped(rs, input.getType());

            // Load up an instance of the specific script that we want to use.
            ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
            script.setInput(input);

            // Set the blur radius
            script.setRadius(15);

            // Start the ScriptIntrinisicBlur
            script.forEach(output);

            // Copy the output to the blurred bitmap
            output.copyTo(blurredBitmap);

            source.recycle();

            return blurredBitmap;
        }

        @Override
        public String key() {
            return "blur";
        }
    }
}
