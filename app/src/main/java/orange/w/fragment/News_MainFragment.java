package orange.w.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import orange.w.R;
import orange.w.common.Url;
import orange.w.databinding.FragmentNewsBinding;

/**
 * A simple {@link Fragment} subclass.
 * 新闻fragment(每日头条)
 */
public class News_MainFragment extends Fragment {
    private FragmentNewsBinding mBinding;
    private List<Fragment> mData = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private News_ListFragment newsListFragment;

    public News_MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false);
        initView();
        return mBinding.getRoot();
    }

    private void initView() {
        mData.clear();
        for (int i = 0; i < 7; ++i) {
            newsListFragment = new News_ListFragment();
            newsListFragment.setUrl(Url.homepath[i]);
            mData.add(newsListFragment);
        }
        mAdapter = new MyPagerAdapter(getChildFragmentManager(), mData);
        mBinding.vpNewsFragment.setAdapter(mAdapter);
        mBinding.tabTitles.setTabGravity(TabLayout.GRAVITY_FILL);
        mBinding.tabTitles.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorblue));
        mBinding.tabTitles.setTabMode(TabLayout.MODE_SCROLLABLE);
        mBinding.tabTitles.setupWithViewPager(mBinding.vpNewsFragment);
    }

    class MyPagerAdapter extends FragmentPagerAdapter {
        private String[] titleArr = {"推荐", "热点", "娱乐", "科技", "体育", "军事", "图片"};
        private List<Fragment> mData;

        public MyPagerAdapter(FragmentManager fm, List<Fragment> mData) {
            super(fm);
            this.mData = mData;
        }

        @Override
        public Fragment getItem(int position) {
            return mData.get(position);
        }

        @Override
        public int getCount() {
            return titleArr.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleArr[position];
        }
    }
}
