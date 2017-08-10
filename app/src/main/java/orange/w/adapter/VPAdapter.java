package orange.w.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.ArrayList;
import java.util.List;

import orange.w.bean.TouTiaoImageListBean;
import orange.w.fragment.ImageItemFragment;

/**
 * Created by zhouwei on 17/5/21.
 */

public class VPAdapter extends FragmentPagerAdapter {
    private TouTiaoImageListBean mData;
    private List<Fragment> mFragments = new ArrayList<>();

    public VPAdapter(FragmentManager fm, TouTiaoImageListBean mData) {
        super(fm);
        this.mData = mData;
        for (int i = 0; i < mData.getImage_list().size(); i++) {
            mFragments.add(new ImageItemFragment(mData.getImage_list().get(i).getPc_url()));
        }
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

   /* @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }*/

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

  /*  @Override
    public CharSequence getPageTitle(int position) {
//        return TITLE[position];
    }
*/


}
